

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Solution2 {
	int n, m, k;
	byte[][] map;
	
	List<Edge>[] e;
	int[][] distTo;
	boolean[] seen;
	int star;
	int[] pred;
	
	int node(int i, int j) {
		return i * m +j;
	}
	
	void addEdges(int i, int j, byte b) {
		
	}
	
	
	@SuppressWarnings("unchecked")
	public void init () throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Scanner in = new Scanner(br.readLine());
		n = in.nextInt();
		m = in.nextInt();
		k = in.nextInt();
		in.close();
		// create the various arrays
		
		map = new byte[n][m];
		e = new List[n * m];
		seen = new boolean[n * m];
		distTo = new int[n * m][n*m];
		pred = new int[n * m];
		Arrays.fill(distTo, Integer.MAX_VALUE);
		
		for (int i = 0; i < n; i++) {
			map[i] = br.readLine().trim().getBytes();
		}
		
		// create
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				int node = node(i, j);
				e[node] = new LinkedList<>();
				addEdges(i, j, map[i][j]);
			}
		}
	}
	
	public int solve() {
		for (int i = 0; i < m*n; i++) {
			distTo[i][i] = 0;
		}
		
		return -1;
	}
	
	public void printMap() {
		for (int i = 0; i < n; i++) {
			System.out.println(new String(map[i]));
		}
	}
	
	public void printSolution() {
		int node = star;
		while (node > 0) {
			node = pred[node];
			int i = node/m;
			int j = node % m;
			map[i][j] = '.';
		}
		printMap();
	}

	public static void main(String[] args) {
		Solution2 s = new Solution2();
		try {
			s.init();
			System.out.println(s.solve());
			s.printMap();
			System.out.println();
			s.printSolution();
		} catch (IOException e) {
			System.out.println(-1);
		}

	}

}
