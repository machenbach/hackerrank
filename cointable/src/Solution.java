

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
	int[] pred;
	
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
	
	byte fromTo(int n1, int n2) {
		int diff = n2 - n2;
		if (diff == 1) return 'R';
		else if (diff == -1) return 'L';
		else if (diff == m) return 'D';
		else return 'U';
	}
	
	int countPath() {
		List<Integer> path = path();
		int from = -1;
		int tot = 0;
		for (int to : path) {
			if (from != -1) {
				if (fromTo(from, to) != map[from/m][from%m]) {
					tot++;
				}
			}
			from = to;
		}
		return tot;
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
		pred = new int[n * m];
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
	
	List<Integer> path() {
		List<Integer> path = new LinkedList<>();
		int node = star;
		while (node >= 0) {
			path.add(0, node);
			node = pred[node];
		}
		return path;
	}
	
	public int solve() {
		Queue<Edge> q = new LinkedList<>();
		q.addAll(e[0]);
		distTo[0] = 0;
		pathTo[0] = 0;
		pred[0] = -1;
		while (! q.isEmpty()) {
			Edge edge = q.poll();
			if (distTo[edge.to] > distTo[edge.from] + edge.w) {
				distTo[edge.to] = distTo[edge.from] + edge.w;
				pathTo[edge.to] = pathTo[edge.from] + 1;
				pred[edge.to] = edge.from;
			}
			if (pathTo[edge.to] > k) {
				distTo[edge.to] = Integer.MAX_VALUE;
			}
			if (!seen[edge.to]) {
				seen[edge.to] = true;
				q.addAll(e[edge.to]);
			}
		}
		if (distTo[star] < Integer.MAX_VALUE) {
			return distTo[star];
		}
		return -1;
	}
	
	public void printMap(byte[][] map) {
		for (int i = 0; i < n; i++) {
			System.out.println(new String(map[i]));
		}
	}
	public void printMap() {
		printMap(map);
	}
	
	public void printSolution() {
		System.out.println(countPath());
		byte[][] pathmap = new byte[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				pathmap[i][j] = ' ';
			}
		}
		int node = star;
		while (node > 0) {
			node = pred[node];
			int i = node/m;
			int j = node % m;
			pathmap[i][j] = map[i][j];
		}
		printMap(pathmap);
		System.out.println(path());
		System.out.println(String.format("dist = %s, path = %s", distTo[star], pathTo[star]));
	}

	public static void main(String[] args) {
		Solution s = new Solution();
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
