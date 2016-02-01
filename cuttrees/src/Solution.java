import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

class Combo {
	class Pair {
		int n;
		int k;
		
		public Pair(int n, int k) {
			this.n = n;
			this.k = k;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + k;
			result = prime * result + n;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Pair other = (Pair) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (k != other.k)
				return false;
			if (n != other.n)
				return false;
			return true;
		}

		private Combo getOuterType() {
			return Combo.this;
		}
	}
	// max n we'll use
	int n;
	
	// save the values we've seen
	Map<Pair, BigInteger> comboNbyK;
	
	public Combo(int n) {
		this.n = n;
		comboNbyK = new HashMap<>();
	}
	
	// combinations of n taken k at a time
	BigInteger combo (int n, int k) {
		if (n == 1) {
			return BigInteger.ONE;
		}
		if (k == 0) {
			return BigInteger.ONE;
		}
		if (k == 1) {
			return BigInteger.valueOf(n);
		}
		// use the symmetry of combo
		if (k > (n >> 1)) {
			return combo(n, n-k);
		}
		
		Pair p = new Pair(n, k);
		
		// if we seen this already, return the value
		if (comboNbyK.containsKey(p)) {
			return comboNbyK.get(p);
		}
		
		// otherwise use recursive function
		BigInteger c = combo(n-1, k-1).add(combo(n-1, k));
		comboNbyK.put(p, c);
		return c;
	}
	
	BigInteger sumTo(int k) {
		BigInteger res = BigInteger.ZERO;
		for (int i = 1; i <= k; i++) {
			res = res.add(combo(n, i));
		}
		return res;
	}
	
}


public class Solution {
	Map<Integer, Combo> combos;
	Map<Integer, List<Integer>> edges;
	Set<Integer> seen;
	int N;
	int K;
	int startNode;
	
	public Solution(int n, int k) {
		N = n;
		K = k;
		edges = new HashMap<>();
		combos = new HashMap<>();
	}
	
	void addLink (int p, int c) {
		edges.putIfAbsent(p, new LinkedList<>());
		edges.get(p).add(c);
		edges.putIfAbsent(c, new LinkedList<>());
		edges.get(c).add(p);
	}
	
	public void init(Scanner in) {
		// build the tree
		for (int i = 1; i < N; i++) {
			int p = in.nextInt();
			int c = in.nextInt();
			addLink(p, c);
			startNode = p;
		}
	}
	
	Combo getCombo(int n) {
		combos.putIfAbsent(n, new Combo(n));
		return combos.get(n);
	}
	
	int unseenEdges(int node) {
		int r = 0;
		
		for (int i : edges.get(node)) {
			if (!seen.contains(i)) r++;
		}
		return r;
	}
	
	// The number of cuts at this node.  
	BigInteger cutsAtNode(int node) {
		if (edges.get(node) == null) {
			// no children, the single uplink
			return BigInteger.ONE;
		}
		
		int n = unseenEdges(node);
		
		if (n == 0) return BigInteger.ONE;
		
		Combo combo = getCombo(n);
		// downlinks are the sum of combinations taken 
		BigInteger c = combo.sumTo(K);
		// add one for the up link
		return c.add(BigInteger.ONE);
	}
	
	
	public BigInteger solve(int start) {
		// we will traverse the graph BFS.  Start at any node
		if (edges.get(start) == null || edges.get(start).size() == 0) {
			return BigInteger.ZERO;
		}
		Queue<Integer> q = new LinkedList<>();
		seen = new HashSet<>();
		seen.add(start);
		q.add(start);
		
		BigInteger res = BigInteger.ONE;
		while (!q.isEmpty()) {
			int node = q.poll();
			res = res.add(cutsAtNode(node));
			for (int c : edges.get(node)) {
				if (!seen.contains(c)) {
					seen.add(c);
					q.add(c);
				}
			}
		}
		return res;
	}
	

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int k = in.nextInt();
		
		Solution s = new Solution(n, k);
		s.init(in);
		in.close();
		for (int i = 1; i <=n; i++) {
			System.out.println(i + " " + s.solve(i));
		}
	}

}
