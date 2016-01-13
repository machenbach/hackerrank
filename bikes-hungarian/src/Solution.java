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
	
	boolean[] riderAssign;
	boolean[] bikeAssign;
	int[] riderZeros;
	int[] bikesZeros;
	

	int bikeZero(int r, long[][] m) {
		for (int b =0; b < bikeN; b++) {
			if (m[r][b] == 0) {
				return b;
			}
		}
		return -1;
	}

	int riderZero(int b, long[][] m) {
		for (int r =0; r < riderN; r++) {
			if (m[r][b] == 0) {
				return r;
			}
		}
		return -1;
	}
	
	List<Long> assign() {
		// get a copy of the riderbike distances
		riderAssign = new boolean[riderN];
		bikeAssign = new boolean[bikeN];
		int assignments = 0;
		List<Long> assigned = new ArrayList<>();
		
		while (assignments < k) {
			boolean progress;
			riderZeros = new int[riderN];
			bikesZeros = new int[bikeN];
			
			// adjust the rows and columns
			long[][] reduced = Arrays.stream(riderBikeDist).map(x -> x.clone()).toArray(long[][]::new);
			for (int r = 0; r < riderN; r++) {
				if (riderAssign[r]) continue;
				// find the min for the rider
				long m = Long.MAX_VALUE;
				for (int b = 0; b < bikeN; b++) {
					if (!bikeAssign[b]) m = Math.min(m, reduced[r][b]);
				}
				
				for (int b = 0; b < bikeN; b++) {
					if (bikeAssign[b]) continue;
					// now adjust for the minimum.  If this is a new zero, record it
					if (reduced[r][b] != 0 && reduced[r][b] == m) {
						riderZeros[r]++;
						bikesZeros[b]++;
					}
					reduced[r][b] -= m;
				}
			}

			do {
				progress = false;
				for (int r = 0; r < riderN; r++) {
					if (riderZeros[r] == 1  && !riderAssign[r]) {
						int b = bikeZero(r, reduced);
						if (b < 0 || bikeAssign[b]) continue;
						// assign, and reduce the numbers of zero
						assignments++;
						riderAssign[r] = true;
						bikeAssign[b] = true;
						riderZeros[r]--;
						bikesZeros[b]--;
						assigned.add(riderBikeDist[r][b]);
						progress = true;
					}
				}
				
				
			} while(assignments < k && progress);

			
			reduced = Arrays.stream(riderBikeDist).map(x -> x.clone()).toArray(long[][]::new);
			for (int b = 0; b < bikeN; b++) {
				if (bikeAssign[b]) continue;
				// find the min for the rider
				long m = Long.MAX_VALUE;
				for (int r = 0; r < riderN; r++) {
					if (!riderAssign[r]) m = Math.min(m, reduced[r][b]);
				}
				
				for (int r = 0; r < riderN; r++) {
					if (riderAssign[r]) continue;
					// now adjust for the minimum.  If this is a new zero, record it
					if (reduced[r][b] != 0 && reduced[r][b] == m) {
						riderZeros[r]++;
						bikesZeros[b]++;
					}
					reduced[r][b] -= m;
				}
			}
			
			// scan through for bikes or riders with only one zero, make the assignment
			
			do {
				progress = false;

				for (int b = 0; b < bikeN; b++) {
					if (bikesZeros[b] == 1 && !bikeAssign[b]) {
						int r = riderZero(b, reduced);
						if (r < 0 || riderAssign[r]) continue;
						// assign, and reduce the numbers of zero
						assignments++;
						riderAssign[r] = true;
						bikeAssign[b] = true;
						riderZeros[r]--;
						bikesZeros[b]--;
						assigned.add(riderBikeDist[r][b]);
						progress = true;
					}
				}
				
			} while(assignments < k && progress);
		}
		return assigned;
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
