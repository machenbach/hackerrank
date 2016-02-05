import java.util.ArrayList;
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
	
	Map<Integer, List<Integer>> edges;
	
	Map<Integer, Integer> parent;
	Map<Integer, List<Integer>> children;
	int root;
	
	long[] kcount;
	
	public Solution3(int n, int k) {
		nNodes = n;
		maxEdges = k;
	}
	
	void addLink (int p, int c) {
		edges.putIfAbsent(p, new LinkedList<>());
		edges.get(p).add(c);
		edges.putIfAbsent(c, new LinkedList<>());
		edges.get(c).add(p);
	}
	
	long seen = 0;
	
	public void buildTree(int i) {
		children.putIfAbsent(i, new ArrayList<>());
		for (int c : edges.get(i)) {
			if ((seen & 1l<<c) != 0) continue;
			seen |= 1l<<c;
			parent.put(c, i);
			children.get(i).add(c);
			buildTree(c);
			
		}
	}
	
	public void init(Scanner in) {
		edges = new HashMap<>();
		parent = new HashMap<>();
		children = new HashMap<>();
		kcount = new long[nNodes + 1];
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
	
	long solve() {
		return 0;
	}
	
	public void printTree(int r, String prefix) {
		System.out.println(prefix + r);
		for (int c : children.get(r)) {
			printTree(c, prefix + "   ");
		}
	}
	
	public void printTree() {
		printTree(root, "");
	}
	
	public long sumlist(List<Long> s) {
		int n = s.size();
		long tot = 0;
		for (long l : s) {
			tot+=l;
		}
		return tot * (1l << (n-1));
	}
	
	public long sumlist2(List<Long> s, int max) {
		if (max == 0 || s.size() < max) return 0l;
		long tot = 0;
		for (long l : s) {
			List<Long> st = new ArrayList<>(s);
			st.remove(l);
			tot += (l + sumlist2(st, max-1));
		}
		return tot;
	}
	
	public long subcount(int r, int d) {
		List<Long> l = new ArrayList<>();
		for (int c : children.get(r)) {
			l.add(subcount(c, d+1));
		}
		return sumlist(l) + 1;
	}
	
	public long subcount() {
		return subcount(root, 1);
	}
	
	public long cutcount(int r) {
		// at each node, the cutcount is the sum of the children cut counts,
		// plus the count of the powerset with the node r and its children that
		// make the cut (so to speak)
		long tot = 1;  // count me
		for (int c : children.get(r)) {
			tot += cutcount(c);
		}
		Set<Set<Integer>> ps = powerset(new HashSet<>(children.get(r)));
		// if this is the root, the cutoff is maxEdges, otherwise maxEdges -1, to account for the parent link
		int k = r == root?maxEdges:(maxEdges-1);
		for (Set<Integer> cs : ps) {
			if (cs.size() <= k) tot++;
		}
		
		return tot;
	}
	
	public long cutcount() {
		return cutcount(root) + 1;
	}
	
	
	public Set<Set<Integer>> subtrees(int r) {
		Set<Set<Integer>> res = new HashSet<>();
		Set<Set<Integer>> ps = powerset(new HashSet<>(children.get(r)));
		for(Set<Integer> cs : ps) {
			for (int c : cs) {
				Set<Set<Integer>> st = subtrees(c);
				res.addAll(st);
				for (Set<Integer> cst : st) {
					if (cst.contains(c)) {
						Set<Integer> ts = new HashSet<>(cst);
						ts.add(r);
						res.add(ts);
					}
				}
			}
		}
		
		res.add(Collections.singleton(r));
		System.out.println(r + " " + res);
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
