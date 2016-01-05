import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class Solution {
	class Coord {
		public int x;
		public int y;
		
		public Coord(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public long dist2(Coord o) {
			long dx = x - o.x;
			long dy = y - o.y;
			return dx * dx + dy * dy;
		}
		
		@Override
		public String toString() {
			return String.format("(%s, %s)", x, y);
		}
	}
	
	class Pair {
		public int r;
		public int b;
		
		public Pair (int r, int b) {
			this.r = r;
			this.b = b;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + b;
			result = prime * result + r;
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
			if (b != other.b)
				return false;
			if (r != other.r)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return String.format("<%s, %s>", r, b);
		}

		private Solution getOuterType() {
			return Solution.this;
		}
	}
	
	class DPair implements Comparable<DPair>{
		Pair p;
		long d;
		
		public DPair( int r, int b, long d) {
			p = new Pair(r, b);
			this.d = d;
		}

		@Override
		public int compareTo(DPair o) {
			return Long.compare(d, o.d);
		}
		
		@Override
		public String toString() {
			return String.format("%s: %s", p, d);
		}

		
	}
	
	
	int rn;
	int bn;
	int k;
	
	Coord[] riders;
	Coord[] bikes;
	PriorityQueue<DPair> queue;
	Map<Integer, Set<Integer>> dr;	// distinct riders
	Map<Integer, Set<Integer>> db;	// 
	
	
	public void init (Scanner in) {
		rn = in.nextInt();
		bn = in.nextInt();
		k = in.nextInt();
		
		queue = new PriorityQueue<>();
		
		riders = new Coord[rn];
		dr = new HashMap<>();
		for (int i=0; i < rn; i++) {
			riders[i] = new Coord(in.nextInt(), in.nextInt());
		}
		
		bikes = new Coord[bn];
		db = new HashMap<>();
		for (int i = 0; i < bn; i++) {
			bikes[i] = new Coord(in.nextInt(), in.nextInt());
		}
		
		for (int r = 0; r < rn; r++) {
			for (int b = 0; b < bn; b++) {
				queue.add(new DPair(r, b, riders[r].dist2(bikes[b])));
			}
		}
	}
	
	public long solve() {
		Set<Pair> p = new HashSet<>();
		long maxD = Long.MIN_VALUE;
		while (dr.size() < k || db.size() < k  ||
				new HashSet<Set<Integer>>(dr.values()).size() < k ||
				new HashSet<Set<Integer>>(db.values()).size() < k) {
			DPair d = queue.poll();
			if (d == null) break;
			maxD = Math.max(maxD, d.d);
			if (!db.containsKey(d.p.b)) {
				db.put(d.p.b, new HashSet<>());
			}
			if (!dr.containsKey(d.p.r)) {
				dr.put(d.p.r, new HashSet<>());
			}
			db.get(d.p.b).add(d.p.r);
			dr.get(d.p.r).add(d.p.b);
			p.add(d.p);
			
		}
		return maxD;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Solution s = new Solution();
		s.init(in);
		in.close();
		System.out.println(s.solve());
	}

}
