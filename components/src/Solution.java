import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * @author mike
 *
 * Disjoint set based solely on integers, and optimized for that
 */
class DisjointInt {
	
	class DsNode {
		DsNode parent;
		int rank;
		int size;
		int id;
		
		public DsNode(int id) {
			parent = this;
			rank = 0;
			size = 1;
			this.id = id;
		}

		public DsNode getParent() {
			return parent;
		}

		public void setParent(DsNode parent) {
			this.parent = parent;
		}

		public int getRank() {
			return rank;
		}

		public void setRank(int rank) {
			this.rank = rank;
		}

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}

		@Override
		public String toString() {
			return String.format("<%s>", id);
		}
		
		
	}
	
	DsNode[] sets;
	
	
	public DisjointInt(int n) {
		sets = new DsNode[n + 1];
		for (int i = 1; i <= n; i++) {
			sets[i] = new DsNode(i);
		}
	}
	
	DsNode findSet(DsNode n) {
		if (n != n.getParent()) {
			n.setParent(findSet(n.getParent()));
		}
		return n.getParent();
	}
	
	public DsNode findSet(int i) {
		return findSet(sets[i]);
	}
	
	void link (DsNode x, DsNode y) {
		if (x == y) return;
		
		if (x.getRank() > y.getRank()) {
			y.setParent(x);
			x.setSize(x.getSize() + y.getSize());
		}
		else {
			x.setParent(y);
			y.setSize(x.getSize() + y.getSize());
			if (x.getRank() == y.getRank()) {
				y.setRank(y.getRank() + 1);
			}
		}
	}
	
	void union(DsNode x, DsNode y) {
		link(findSet(x), findSet(y));
	}
	
	public void union(int x, int y) {
		union(sets[x], sets[y]);
	}
	
}


public class Solution {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		DisjointInt d = new DisjointInt(N * 2);
		
		Set<Integer> connected = new HashSet<>(N);
		
		for (int i = 0; i < N; i++) {
			int x = in.nextInt();
			int y = in.nextInt();
			connected.add(x);
			d.union(x, y);
		}
		in.close();
		int max = 0;
		int min = Integer.MAX_VALUE;
		
		for (int i : connected) {
			int s = d.findSet(i).getSize();
			max = Math.max(s, max);
			min = Math.min(s, min);
		}
		
		System.out.println(min + " " + max);
		

	}

}
