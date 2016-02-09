import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
public class Solution3 {

	int maxEdges;
	int nNodes;
	
	List<Integer>[] edges;
	
	int[] parent;
	Set<Integer>[] children;
	int root;
	
	Map<Integer, Set<Set<Integer>>> memsubtrees;
	
	public Solution3(int n, int k) {
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
		memsubtrees = new HashMap<>();
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
	
	// return the powerset (set of all subsets) of s
	public Set<Set<Integer>> powerset(Set<Integer> s) {
		Set<Set<Integer>> r = new HashSet<>();
		if (s.size() == 0) {
			r.add(new HashSet<>());
			return r;
		}
		
		for (int e : s) {
			Set<Integer> sb = new HashSet<>(s);
			sb.remove(e);
			Set<Set<Integer>> csub = powerset(sb);
			r.addAll(new HashSet<Set<Integer>>(csub));
			Set<Integer> t = new HashSet<>();
			t.add(e);
			r.add(t);
			for (Set<Integer> n : csub) {
				Set<Integer> tn = new HashSet<>(n);
				tn.add(e);
				r.add(tn);
			}
		}
		return r;
	}
	
	public Set<Set<Integer>> subtrees(Set<Integer> s) {
		Set<Set<Integer>> res = new HashSet<>();
		for (int c : s) {
			res.addAll(subtrees(c));
		}
		return res;
	}
	
	
	public boolean containsAny(Set<Integer> s1, Set<Integer> s2) {
		for (int i : s2) {
			if (s1.contains(i)) return true;
		}
		return false;
	}
	
	public Set<Integer> uniontrees(Set<Integer> s) {
		Set<Integer> res = new HashSet<>();
		for (Set<Integer> e : subtrees(s)) {
			res.addAll(e);
		}
		return res;
	}
	public Set<Set<Integer>> subtrees(int r) {
		if (memsubtrees.containsKey(r)) {
			return memsubtrees.get(r);
		}
		
		Set<Set<Integer>> res = new HashSet<>();
		Set<Set<Integer>> ps = powerset(children[r]);
		//System.out.println(">" + r + " " + ps);
		for(Set<Integer> cs : ps) {
			Set<Set<Integer>> st = subtrees(cs);
			res.addAll(st);
			Set<Integer> u = uniontrees(cs);
			u.add(r);
			res.add(u);
			u = new HashSet<>(cs);
			cs.add(r);
			res.add(cs);
			for (Set<Integer> cst : st) {
				if (containsAny(cst, children[r])) {
					Set<Integer> ts = uniontrees(cst);
					ts.add(r);
					res.add(ts);
				}
			}
		}
		
		res.add(Collections.singleton(r));
		memsubtrees.put(r, res);
		//System.out.println("<" + r + " " + res);
		return res;
	}
	
	public Set<Set<Integer>> subtrees() {
		return subtrees(root);
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int k = in.nextInt();
		
		Solution3 s = new Solution3(n, k);
		s.init(in);
		in.close();
		System.out.println("----");
		for (Set<Integer> sc : s.subtrees()) {
			System.out.println(sc);
		}
	}

}
