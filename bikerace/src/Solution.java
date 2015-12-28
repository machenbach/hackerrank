import java.util.Arrays;
import java.util.Scanner;

public class Solution {
	class Point implements Comparable<Point>{
		public int x;
		public int y;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public Point (int x) {
			this.x = x;
			this.y = 0;
		}
		
		public long dist2(Point o) {
			long xd = x - o.x;
			long yd = y - o.y;
			return xd * xd + yd * yd;
		}

		@Override
		public int compareTo(Point o) {
			return Long.compare(x, o.x);
		}

		@Override
		public String toString() {
			return String.format("(%s, %s)", x, y);
		}
		
		
	}
	
	int rn;
	int bn;
	int k;
	
	Point[] riders;
	Point[] bikes;
	
	class Pair {
		public long d = Long.MAX_VALUE;
		public int r = -1;
		public int b = -1;
		@Override
		public String toString() {
			return String.format("<%s, %s: %s>", r, b, d);
		}
		
	}
	
	private Pair bruteForce(int rmin, int rmax, int bmin, int bmax) {
		Pair pmin = new Pair();
		for (int r = rmin; r < rmax; r++) {
			for (int b = bmin; b < bmax; b++) {
				long d = riders[r].dist2(bikes[b]);
				if (d < pmin.d) {
					pmin.d = d;
					pmin.r = r;
					pmin.b = b;
				}
			}
		}
		return pmin;
	}


	// search for the closest rider/bike pair in this section
	Pair closestPair (int rmin, int rmax, int bmin, int bmax) {
		// if few enough r and b, brute force search
		if (rmax - rmin < Integer.MAX_VALUE || bmax - bmin < Integer.MAX_VALUE) {
			return bruteForce(rmin, rmax, bmin, bmax);
		}
		
		// else divide in half
		// split the riders in half
		int rmid = ((rmin + rmax)/2) + 1;
		// find the point to split the bikes
		int bmid = Arrays.binarySearch(bikes, new Point(riders[rmid].x));
		if (bmid < 0) {
			bmid = -(bmid + 1);
		}
		// get left and right closes, and work with the minimum distance
		Pair lPair = closestPair(rmin, rmid, bmin, bmid);
		Pair rPair = closestPair(rmid, rmax, bmid, bmax);
		
		Pair pair = lPair.d < rPair.d ? lPair : rPair;
		
		// for this we need the real distance, not the square
		long dist = (long)Math.ceil(Math.sqrt(pair.d));
		long minDist = riders[rmid].x - dist;
		long maxDist = riders[rmid].x + dist;
		
		// now find the minimum rider-bike pair in the distance in the middle
		int trMin = rmin;
		int trMax = rmax;
		int tbMin = bmin;
		int tbMax = bmax;
		
		for (int i = rmid - 1; i >= rmin; i--) {
			if (riders[i].x >= minDist) {
				trMin = i;
			}
		}

		for (int i = rmid; i < rmax; i++) {
			if (riders[i].x <= maxDist) {
				trMax = i + 1;
			}
		}

		for (int i = bmid - 1; i >= bmin; i--) {
			if (bikes[i].x >= minDist) {
				tbMin = i;
			}
		}

		for (int i = bmid; i < bmax; i++) {
			if (bikes[i].x <= maxDist) {
				tbMax = i + 1;
			}
		}
		
		Pair mPair = bruteForce(trMin, trMax, tbMin, tbMax);
		return mPair.d < pair.d ? mPair : pair;
		
	}
	
	Pair closestPair() {
		return closestPair(0, rn, 0, bn);
	}
	
	// remove the rider/bike pair, and adjust the arrays.
	void removePair (Pair p) {
		for (int i = p.b; i < bn-1; i++) {
			bikes[i] = bikes[i+1];
		}
		for (int i = p.r; i < rn-1; i++) {
			riders[i] = riders[i+1];
		}
		bn--;
		rn--;
		
	}
	
	public long solve() {
		// sort the arrays
		Arrays.sort(riders);
		Arrays.sort(bikes);
		
		long max = 0;
		for (int i = 0; i < k; i++) {
			Pair p = closestPair();
			max = Math.max(max,p.d);
			removePair(p);
		}
		return max;
	}
	
	public void init (Scanner in) {
		rn = in.nextInt();
		bn = in.nextInt();
		k = in.nextInt();
		
		riders = new Point[rn];
		for (int i=0; i < rn; i++) {
			riders[i] = new Point(in.nextInt(), in.nextInt());
		}
		
		bikes = new Point[bn];
		for (int i = 0; i < bn; i++) {
			bikes[i] = new Point(in.nextInt(), in.nextInt());
		}
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Solution s = new Solution();
		s.init(in);
		in.close();
		System.out.println(s.solve());
	}

}
