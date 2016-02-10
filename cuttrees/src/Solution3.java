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
	
	public Set<Set<Integer>> uniontrees(Set<Integer> s) {
		Set<Set<Integer>> res = new HashSet<>();
		for (int r : s) {
			res.addAll(subtrees(r));
		}
		return res;
	}
	
	public Set<Set<Integer>> subtrees(int r) {
		if (memsubtrees.containsKey(r)) {
			return memsubtrees.get(r);
		}
		
		Set<Set<Integer>> res = new HashSet<>();
		Set<Set<Integer>> ps = powerset(children[r]);
		for(Set<Integer> cs : ps) {
			// get the union of all subtrees for this set of nodes
			Set<Set<Integer>> all = uniontrees(cs);
			res.addAll(all);
			Set<Integer> t = new HashSet<>();
			for (Set<Integer> cst : all) {
				if (containsAny(cst,cs)) {
					t.addAll(cst);
				}
			}
			t.add(r);
			res.add(t);
			for (Set<Integer> cst : all) {
				if (containsAny(cst, cs)) {
					t = new HashSet<>(cst);
					t.addAll(new HashSet<>(cs));
					t.add(r);
					res.add(t);
				}
			}
		}
		
		res.add(Collections.singleton(r));
		memsubtrees.put(r, new HashSet<>(res));
		return res;
	}
	
	public Set<Set<Integer>> subtrees() {
		return subtrees(root);
	}

	int linksOut(Set<Integer> s) {
		Set<Integer> inLinks = new HashSet<>();
		Set<Integer> outLinks = new HashSet<>();
		for (int n : s) {
			inLinks.add(n);
			for (int o : edges[n]) {
				outLinks.add(o);
			}
		}
		outLinks.removeAll(inLinks);
		return outLinks.size();
	}
	
	public long solve() {
		long res = 0;
		for (Set<Integer> s : subtrees()) {
			if (linksOut(s) <= maxEdges) res++;
		}
		return res + 1;
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int k = in.nextInt();
		
		Solution3 s = new Solution3(n, k);
		s.init(in);
		in.close();
		System.out.println(s.solve());
	}

}
