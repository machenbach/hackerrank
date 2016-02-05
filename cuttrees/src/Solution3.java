import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
public class Solution3 {

	int maxEdges;
	int nNodes;
	
	Map<Integer, List<Integer>> edges;
	
	Map<Integer, Integer> parent;
	Map<Integer, List<Integer>> children;
	int root;
	
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
		List<Long> l = new ArrayList<>();
		for (int c : children.get(r)) {
			l.add(cutcount(c));
		}
		long tot = 0;
		for (int i = 1; i <= maxEdges; i++) {
			tot += sumlist2(l, i);
		}
		if (children.get(r).size() + 1 <= maxEdges) {
			tot++;
		}
		return tot;
	}
	
	public long cutcount() {
		return cutcount(root) + 2;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int k = in.nextInt();
		
		Solution3 s = new Solution3(n, k);
		s.init(in);
		in.close();
		System.out.println(s.subcount());
	}

}
