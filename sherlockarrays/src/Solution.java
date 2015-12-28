import java.util.Scanner;

public class Solution {
	int N;
	
	// interestingly enough, we don't need to save the elements themselves, just the sum so far
	long[] sumSoFar;
	
	public Solution(int n) {
		N = n;
	}
	
	long total = 0;
	public void init(Scanner in) {
		sumSoFar = new long[N];
		for (int i = 0; i <N; i++) {
			total += in.nextLong();
			sumSoFar[i] = total;
		}
	}
	
	// Binary search for solution
	public int solve() {
		// special cases
		if (N == 1) {
			return 0;
		}
		else if (N ==2) {
			return -1;
		}
		int l = 0;
		int r = N - 1;
		int lastp = -1;
		while (true) {
			int p = Math.floorDiv(l + r, 2);
			if (p == lastp) break;
			lastp = p;
			if (p == 0 && p == N-1) {
				return -1;
			}
			long val = total - sumSoFar[p-1] - sumSoFar[p];
			if (val == 0) {
				return p;
			}
			else if (val > 0) {
				l = p;
			}
			else {
				r = p+1;
			}
		}
		return -1;
	}
	
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int T = in.nextInt();
		for (int i = 0; i < T; i++) {
			Solution s = new Solution(in.nextInt());
			s.init(in);
			System.out.println(s.solve() < 0 ? "NO" : "YES");
		}
		
	}

}
