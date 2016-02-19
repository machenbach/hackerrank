

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

class Edge {
	public int from;
	public int to;
	public int w;
	
	public Edge(int from, int to, int w) {
		this.from = from;
		this.to = to;
		this.w = w;
	}
	
	@Override
	public String toString() {
		return String.format("%s -> %s: %s", from, to, w);
	}
	
}


public class Solution {
	int n, m, k;
	byte[][] map;
	
	List<Edge>[] e;
	int[] distTo;
	int[] pathTo;
	boolean[] seen;
	int star;
	
	int node(int i, int j) {
		return i * m +j;
	}
	
	void addEdges(int i, int j, byte b) {
		int node = node(i, j);
		if (b == '*') {
			star = node;
			return;
		}
		if (i > 0) {
			e[node].add(new Edge(node, node - m, b=='U' ? 0 : 1));
		}
		if (i < n-1) {
			e[node].add(new Edge(node, node + m, b=='D' ? 0 : 1));
		}
		if (j > 0) {
			e[node].add(new Edge(node, node - 1, b=='L' ? 0 : 1));
		}
		if (j < m - 1)  {
			e[node].add(new Edge(node, node + 1, b=='R' ? 0 : 1));
		}
		
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
		distTo = new int[n * m];
		pathTo = new int[n * m];
		Arrays.fill(distTo, Integer.MAX_VALUE);
		Arrays.fill(pathTo, Integer.MAX_VALUE);
		
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
		Queue<Edge> q = new LinkedList<>();
		q.addAll(e[0]);
		distTo[0] = 0;
		pathTo[0] = 0;
		while (! q.isEmpty()) {
			Edge edge = q.poll();
			if (distTo[edge.to] > distTo[edge.from] + edge.w) {
				distTo[edge.to] = distTo[edge.from] + edge.w;
				pathTo[edge.to] = pathTo[edge.from] + 1;
			}
			if (pathTo[edge.to] <= k && !seen[edge.to]) {
				seen[edge.to] = true;
				q.addAll(e[edge.to]);
			}
		}
		if (distTo[star] < Integer.MAX_VALUE) {
			return distTo[star];
		}
		return -1;
	}

	public static void main(String[] args) {
		Solution s = new Solution();
		try {
			s.init();
			System.out.println(s.solve());
		} catch (IOException e) {
			System.out.println(-1);
		}

	}

}
