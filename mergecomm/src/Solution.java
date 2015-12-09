
import java.util.Scanner;




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
	DisjointInt d;

	public Solution(int N) {
		d = new DisjointInt(N);
	}

	public void merge(int i, int j) {
		d.union(i, j);
	}

	public int query(int i) {
		return d.findSet(i).getSize();
	}
	

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		Solution s = new Solution(N);

		int Q = in.nextInt();
		for (int q = 0; q < Q; q++) {
			String command = in.next();
			switch (command) {
			case "Q":
				System.out.println(s.query(in.nextInt()));
				break;

			case "M":
				s.merge(in.nextInt(), in.nextInt());
				break;
			}
		}
		in.close();
	}

}
