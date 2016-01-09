import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
	Map<Integer, List<Integer>> tree;
	Set<Integer> root;
	int N;
	int K;
	
	public Solution(int n, int k) {
		N = n;
		K = k;
		tree = new HashMap<>();
		root = new HashSet<>();
		combos = new HashMap<>();
		for (int i = 1; i <= N; i++) {
			root.add(i);
		}
	}
	
	void addLink (int p, int c) {
		tree.putIfAbsent(p, new LinkedList<>());
		tree.get(p).add(c);
		
	}
	
	public int init(Scanner in) {
		// build the tree
		for (int i = 1; i < N; i++) {
			int p = in.nextInt();
			int c = in.nextInt();
			root.remove(c);
			addLink(p, c);
		}
		if (root.size() == 1) {
			for(int i : root) {
				return i;
			}
		}
		return 0;	// this can't be right
	}
	
	Combo getCombo(int n) {
		combos.putIfAbsent(n, new Combo(n));
		return combos.get(n);
	}
	
	// The number of cuts at this node.  
	BigInteger cutsAtNode(int node) {
		if (tree.get(node) == null) {
			// no children, the single uplink
			return BigInteger.ONE;
		}
		
		int n = tree.get(node).size();
		
		Combo combo = getCombo(n);
		// downlinks are the sum of combinations taken 
		BigInteger c = combo.sumTo(K);
		// add one for the up link
		return c.add(BigInteger.ONE);
	}
	
	BigInteger cutsAtTree(int node) {
		BigInteger res = cutsAtNode(node);
		for (int c : tree.get(node)) {
			res = res.add(cutsAtNode(c));
		}
		return res;
	}
	
	public BigInteger solve(int root) {
		// all the trees, plus the empty set
		return  cutsAtTree(root).add(BigInteger.ONE);
	}
	

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int k = in.nextInt();
		
		Solution s = new Solution(n, k);
		int root = s.init(in);
		in.close();
		if (root > 0) {
			System.out.println(s.solve(root));
		}
		else {
			System.out.println("Something has gone terribly wrong");
		}

	}

}
