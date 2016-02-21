

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Solution2 {
	int n, m, k, nm;
	byte[][] map;
	
	int[][] d;		// distance
	int[][] w;		// weight matrix
	int star;
	
	void addEdges(int i, int j, byte b) {
		int node = node(i, j);
		
		if (b == '*') {
			star = node;
			return;
		}
		
		if (i > 0) {
			w[node][node-m] = b=='U' ? 0 : 1;
		}
		if (i < n-1) {
			w[node][node+m] = b=='D' ? 0 : 1;
		}
		if (j > 0) {
			w[node][node - 1] = b=='L' ? 0 : 1;
		}
		if (j < m - 1)  {
			w[node][node + 1] = b=='R' ? 0 : 1;
		}
		
	}
	
	int node(int i, int j) {
		return i*m + j;
	}

	
	public void init () throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Scanner in = new Scanner(br.readLine());
		n = in.nextInt();
		m = in.nextInt();
		k = in.nextInt();
		nm = n*m;
		in.close();
		// create the various arrays
		
		map = new byte[n][m];
		for (int i = 0; i < n; i++) {
			map[i] = br.readLine().trim().getBytes();
		}

		d = new int[nm][nm];
		w = new int[nm][nm];
		
		for (int i = 0; i < nm; i++) {
			for (int j = 0; j < nm; j++) {
				d[i][j] = i==j ? 0 : Integer.MAX_VALUE;
				w[i][j] = i==j ? 0 : Integer.MAX_VALUE;
			}
		}
		// create weight matrix
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				addEdges(i, j, map[i][j]);
			}
		}
	}
	
	int[][] copy(int[][] a) {
		int [][] c = new int[a.length][];
		for(int i = 0; i < a.length; i++) {
		    c[i] = a[i].clone();
		}
		return c;
	}
	
	int addem(int a, int b) {
		if (a == Integer.MAX_VALUE || b == Integer.MAX_VALUE) {
			return Integer.MAX_VALUE;
		}
		return a + b;
	}
	
	int[][] extendByOne(int[][] d, int[][] w) {
		int[][] dp = new int[nm][nm];
		for (int i = 0; i < nm; i++) {
			for (int j = 0; j < nm; j++) {
				dp[i][j] = Integer.MAX_VALUE;
				for (int k = 0; k < nm; k++) {
					dp[i][j] = Math.min(dp[i][j], addem(d[i][k], w[k][j]));
				}
			}
		}
		return dp;
	}
	
	public int solve() {
		int[][] d = w;
		int m = k - 2;
		while (m > 0) {
			d = extendByOne(d, d);
			m >>= 1;
		}
		if (k % 2 == 1) {
			d = extendByOne(d, w);
		}
		if (d[0][star] < Integer.MAX_VALUE) {
			return d[0][star];
		}
		
		return -1;
	}
	
	public static void main(String[] args) {
		Solution2 s = new Solution2();
		try {
			s.init();
			System.out.println(s.solve());
		} catch (IOException e) {
			System.out.println(-1);
		}

	}

}
