import java.util.Scanner;


public class Solution2 {
	int N;
	int[][] sums;
	

	public Solution2(int N) {
		this.N = N;
		sums = new int[N+1][N+1];
	}
	
	int max = Integer.MIN_VALUE;
	int start;
	int stop;
	int maxNC = 0;

	public int getMax() {
		return max;
	}
	
	public int getMaxNC() {
		if (max > 0) {
			return maxNC;
		}
		else {
			return max;
		}
	}

	public int getStart() {
		return start;
	}

	public int getStop() {
		return stop;
	}

	void markMax(int i, int j, int sum) {
		if (sum > max) {
			max = sum;
			start = j;
			stop = i;
		}
	}
	
	public void init (Scanner in) {
		for (int i = 1; i <= N; i++) {
			sums[i][i] = in.nextInt();
			markMax(i, i, sums[i][i]);
			if (sums[i][i] > 0) {
				maxNC += sums[i][i];
			}
		}
	}
	
	
	public void solve() {
		for (int i = 2; i <= N; i++) {
			for (int j = i - 1; j>= 1; j--) {
				sums[i][j] = sums[i-1][j] + sums[i][i];
				markMax(i, j, sums[i][j]);
			}
		}
	}
	

	public void printit() {
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				System.out.print(sums[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int t = in.nextInt();
		for (int i = 0; i < t; i++) {
			Solution2 s = new Solution2(in.nextInt());
			s.init(in);
			s.solve();
			System.out.println(s.getMax() + " " + s.getMaxNC());
			
		}
	}

}
