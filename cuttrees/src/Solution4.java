import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
public class Solution4 {

	int maxEdges;
	int nNodes;
	
	List<Integer>[] edges;
	
	int[] parent;
	Set<Integer>[] children;
	int root;
	
	Map<Integer, Long> aMap;
	Map<Integer, Long> bMap;
	
	public Solution4(int n, int k) {
		nNodes = n;
		maxEdges = k;
	}
	
	void addLink (int p, int c) {
		if (edges[p] == null) {
			edges[p] = new LinkedList<>();
		}
		if (edges[c] == null) {
			edges[c] = new LinkedList<>();
		}
		edges[p].add(c);
		edges[c].add(p);
	}
	
	long seen = 0;
	
	public void buildTree(int i) {
		if (children[i] == null) {
			children[i] = new HashSet<>();
		}
		for (int c : edges[i]) {
			if ((seen & 1l<<c) != 0) continue;
			seen |= 1l<<c;
			parent[c] = i;
			children[i].add(c);
			buildTree(c);
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public void init(Scanner in) {
		edges = (List<Integer>[]) new List[nNodes + 1];
		parent = new int[nNodes + 1];
		children = (Set<Integer>[]) new Set[nNodes + 1];
		aMap = new HashMap<>();
		bMap = new HashMap<>();
		// build the tree
		for (int i = 1; i < nNodes; i++) {
			int p = in.nextInt();
			int c = in.nextInt();
			addLink(p, c);
			root = p;
		}
		seen |= 1l<<root;
		buildTree(root);
	}
	
	Set<Set<Integer>> combo(Set<Integer> s, int k) {
		Set<Set<Integer>> res = new HashSet<>();
		if (k == 0) {
			res.add(new HashSet<>());
			return res;
		}
		for (int e : s) {
			Set<Integer> c = new HashSet<>(s);
			c.remove(e);
			for (Set<Integer> sc : combo(c, k - 1)) {
				sc.add(e);
				res.add(sc);
			}
		}
		return res;
	}

	public long A(int r) {
		if (children[r].size() == 0) {
			return 1l;
		}
		if (aMap.containsKey(r)) {
			return aMap.get(r);
		}
		long res = 1l;
		for (int c : children[r]) {
			res *= (A(c) + 1L);
		}
		aMap.put(r, res);
		return res;
	}
	
	public long B(int r) {
		if (children[r].size() == 0) {
			return 0l;
		}
		if (bMap.containsKey(r)) {
			return bMap.get(r);
		}
		long res = 0;
		for (int c : children[r]) {
			res += (A(c) + B(c));
		}
		bMap.put(r, res);
		return res;
	}
	
	
	public long treeCount() {
		return A(root) + B(root);
	}

	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int k = in.nextInt();
		
		Solution4 s = new Solution4(n, k);
		s.init(in);
		in.close();
		System.out.println(s.treeCount());
	}

}
