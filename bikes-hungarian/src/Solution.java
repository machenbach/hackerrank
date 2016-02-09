import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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



public class Solution {
	int riderN;
	int bikeN;
	int k;
	
	Coord[] riderCoord;
	Coord[] bikeCoord;
	

	long[][] riderBikeDist;

	public void init (Scanner in) {
		riderN = in.nextInt();
		bikeN = in.nextInt();
		k = in.nextInt();
		
		riderCoord = new Coord[riderN];
		bikeCoord = new Coord[bikeN];
		riderBikeDist = new long[riderN][bikeN];
		
		// what the heck.  messing around with intstreams
		IntStream.range(0, riderN).
			forEach(r -> riderCoord[r] = new Coord(in.nextInt(), in.nextInt()));
		
		IntStream.range(0, bikeN).
			forEach(b -> 
				{			
					bikeCoord[b] = new Coord(in.nextInt(), in.nextInt()); 
					IntStream.range(0, riderN).
					forEach( r -> riderBikeDist[r][b] = riderCoord[r].dist2(bikeCoord[b]));
				}
			);
	}
	
	int[] rZeroCnt;
	int[] bZeroCnt;
	boolean[][] marked;
	

	List<Integer> bikeZeros(int r, long[][] m) {
		List<Integer> l = new ArrayList<>();
		for (int b =0; b < bikeN; b++) {
			if (m[r][b] == 0) {
				l.add(b);
			}
		}
		return l;
	}

	List<Integer> riderZeros(int b, long[][] m) {
		List<Integer> l = new ArrayList<>();
		for (int r =0; r < riderN; r++) {
			if (m[r][b] == 0) {
				l.add(r);
			}
		}
		return l;
	}
	
	List<Long> assign() {
		// get a copy of the riderbike distances
		int assignments = 0;
		List<Long> assigned = new ArrayList<>();
		
		while (assignments < k) {
			boolean progress;
			rZeroCnt = new int[riderN];
			bZeroCnt = new int[bikeN];
			
			// adjust the rows and columns
			long[][] reduced = reduce();
		}

		return assigned;
	}

	private long[][] reduce() {
		// copy the array
		long[][] reduced = Arrays.stream(riderBikeDist).map(x -> x.clone()).toArray(long[][]::new);
		for (int r = 0; r < riderN; r++) {
			// find the min for the rider
			long m = Long.MAX_VALUE;
			for (int b = 0; b < bikeN; b++) {
				Math.min(m, reduced[r][b]);
			}
			
			for (int b = 0; b < bikeN; b++) {
				// now adjust for the minimum.  If this is a new zero, record it
				if (reduced[r][b] != 0 && reduced[r][b] == m) {
					rZeroCnt[r]++;
					bZeroCnt[b]++;
				}
				reduced[r][b] -= m;
			}
		}
		
		for (int b = 0; b < bikeN; b++) {
			// find the min for the rider
			long m = Long.MAX_VALUE;
			for (int r = 0; r < riderN; r++) {
				m = Math.min(m, reduced[r][b]);
			}
			
			for (int r = 0; r < riderN; r++) {
				// now adjust for the minimum.  If this is a new zero, record it
				if (reduced[r][b] != 0 && reduced[r][b] == m) {
					rZeroCnt[r]++;
					bZeroCnt[b]++;
				}
				reduced[r][b] -= m;
			}
		}
		return reduced;
	}
	
	public long solve() {
		List<Long> l = assign();
		Collections.sort(l);
		System.out.println(l);
		return l.get(k-1);
	}
	

	

	public static void main(String[] args) {
		Solution s = new Solution();
		Scanner in = new Scanner(System.in);
		s.init(in);
		in.close();
		System.out.println(s.solve());
	}

}
