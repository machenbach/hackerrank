import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

class Tree {
	int nodes;
	List<Integer>[] edges;
	
	public Tree(int nodes, List<Integer>[] edges) {
		this.nodes = nodes;
		this.edges = edges;
	}
	
	public Subtree subtree(int i) {
		return new Subtree(i);
	}
	
	public Subtree subtree(int i, Subtree st) {
		return new Subtree(i, st);
	}
	
	
	public class Subtree implements Iterable<Integer>{
		long subtree;
		int externalLinks;
		
		public Subtree(int i) {
			subtree = 1l << i;
			externalLinks = edges[i].size();
		}
		
		public Subtree(int i, Subtree st) {
			subtree = st.subtree | 1L << i;
			externalLinks = st.externalLinks + edges[i].size() - 2;
		}
		
	
		public int getExternalLinks() {
			return externalLinks;
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
}


public class Solution2 {
	
	int maxEdges;
	int nNodes;
	
	Tree tree;
	
	List<Integer>[] edges;
	
	public Solution2(int n, int k) {
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
	
	@SuppressWarnings("unchecked")
	public void init(Scanner in) {
		edges = new List[nNodes + 1];
		// build the tree
		for (int i = 1; i < nNodes; i++) {
			int p = in.nextInt();
			int c = in.nextInt();
			addLink(p, c);
		}
		tree = new Tree(nNodes, edges);
	}

	public long subTrees() {
		Deque<Tree.Subtree> q = new LinkedList<>();
		Set<Tree.Subtree> seen = new HashSet<>();
		long res = 0;
		
		for (int i = 1; i <= nNodes; i++) {
			q.add(tree.subtree(i));
		}
		
		while (!q.isEmpty()) {
			Tree.Subtree s = q.poll();
			seen.add(s);
			if (s.getExternalLinks() <= maxEdges) {
				res++;
			}
			// for each of the nodes in the subtree, and the next set of connections
			for (int n : s) {
				for (int c : edges[n]) {
					Tree.Subtree st = tree.subtree(c, s);
					if (!seen.contains(st)) {
						seen.add(st);
						q.push(st);
					}
				}
			}
		}
		return res + 1;
	}
	
	long solve() {
		return subTrees();
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int k = in.nextInt();
		
		Solution2 s = new Solution2(n, k);
		s.init(in);
		in.close();
		System.out.println(s.solve());

	}

}
