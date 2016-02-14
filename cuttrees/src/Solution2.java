import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class Subtree implements Iterable<Integer>{
	long subtree;
	
	public Subtree(int i) {
		subtree = 1l << i;
	}
	
	public Subtree(int i, Subtree st) {
		subtree = st.subtree;
		subtree |= 1L << i;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (subtree ^ (subtree >>> 32));
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
		Subtree other = (Subtree) obj;
		if (subtree != other.subtree)
			return false;
		return true;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>() {
			long bits = subtree;
			int pos = 0;
			long bm = 1l;
			int max = 63 - Long.numberOfLeadingZeros(bits);

			@Override
			public boolean hasNext() {
				return pos < max;
			}

			@Override
			public Integer next() {
				while (pos < max) {
					pos++;
					bm <<= 1L;
					if ((bits & bm) != 0) return pos;
				}
				return pos;
			}
		};
	}

	
}


public class Solution2 {
	
	int maxEdges;
	int nNodes;
	
	Map<Integer, List<Integer>> edges;
	
	public Solution2(int n, int k) {
		nNodes = n;
		maxEdges = k;
	}
	
	void addLink (int p, int c) {
		edges.putIfAbsent(p, new LinkedList<>());
		edges.get(p).add(c);
		edges.putIfAbsent(c, new LinkedList<>());
		edges.get(c).add(p);
	}
	
	public void init(Scanner in) {
		edges = new HashMap<>();
		// build the tree
		for (int i = 1; i < nNodes; i++) {
			int p = in.nextInt();
			int c = in.nextInt();
			addLink(p, c);
		}
	}
	
	int linksOut(Subtree s) {
		Set<Integer> inLinks = new HashSet<>();
		Set<Integer> outLinks = new HashSet<>();
		for (int n : s) {
			inLinks.add(n);
			for (int o : edges.get(n)) {
				outLinks.add(o);
			}
		}
		outLinks.removeAll(inLinks);
		return outLinks.size();
	}
	
	public Set<Subtree> subTrees() {
		Deque<Subtree> q = new LinkedList<>();
		Set<Subtree> seen = new HashSet<>();
		
		for (int i = 1; i <= nNodes; i++) {
			q.add(new Subtree(i));
		}
		
		while (!q.isEmpty()) {
			Subtree s = q.poll();
			seen.add(s);
			// for each of the nodes in the subtree, and the next set of connections
			for (int n : s) {
				for (int c : edges.get(n)) {
					Subtree st = new Subtree(c, s);
					if (!seen.contains(st)) {
						seen.add(st);
						q.push(st);
					}
				}
			}
		}
		return seen;
	}
	
	long solve() {
		Set<Subtree> sts = subTrees();
		//System.out.println("subtrees");
		long tot = 0;
		for (Subtree s : sts) {
			if (linksOut(s) <= maxEdges) tot++;
		}
		return tot + 1;
		
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int k = in.nextInt();
		
		Solution2 s = new Solution2(n, k);
		s.init(in);
		in.close();
		System.out.println(s.solve());
		//Set<Subtree> sts = s.subTrees();
		//System.out.println(sts.size());
		//System.out.println("===");
		//for (Subtree st : sts) {
		//	System.out.println(st.subtree);
		//}

	}

}
