

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

class Node implements Comparable<Node>{
	public int node;
	public List<Edge> edges;
	public int dist;
	public int path;
	public boolean seen;
	public Node pred;
	
	public Node (int node) {
		this.node = node;
		edges = new LinkedList<>();
		dist = Integer.MAX_VALUE;
		path = 0;
	}
	@Override
	public int compareTo(Node o) {
		return Integer.compare(dist, o.dist);
	}
	@Override
	public String toString() {
		return Integer.toString(node);
	}
}

class Edge {
	public Node to;
	public int w;
	
	public Edge(Node to, int w) {
		this.to = to;
		this.w = w;
	}
	
	@Override
	public String toString() {
		return String.format("%s: %s", to, w);
	}
	
}


public class Solution {
	int n, m, k;
	byte[][] map;
	
	Node star;
	Node[] nodes;
	
	void addEdges(Node node, byte b) {
		int i = node.node / m;
		int j = node.node % m;
		
		if (b == '*') {
			star = node;
			return;
		}
		
		if (i > 0) {
			node.edges.add(new Edge(nodes[node.node - m], b=='U' ? 0 : 1));
		}
		if (i < n-1) {
			node.edges.add(new Edge(nodes[node.node + m], b=='D' ? 0 : 1));
		}
		if (j > 0) {
			node.edges.add(new Edge(nodes[node.node - 1], b=='L' ? 0 : 1));
		}
		if (j < m - 1)  {
			node.edges.add(new Edge(nodes[node.node + 1], b=='R' ? 0 : 1));
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
		in.close();
		// read the map
		map = new byte[n][m];
		for (int i = 0; i < n; i++) {
			map[i] = br.readLine().trim().getBytes();
		}
		
		// create the nodes
		nodes = new Node[n*m];
		for (int i = 0; i < n*m; i++) {
			nodes[i] = new Node(i);
		}
		
		// Add the edges
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				addEdges(nodes[node(i, j)], map[i][j]);
			}
		}
	}
	
	
	public int solve() {
		PriorityQueue<Node> q = new PriorityQueue<>();
		Node node = nodes[0];
		node.dist = 0;
		node.path = 0;
		node.seen = true;
		q.add(node);
		
		while (!q.isEmpty()) {
			Node p = q.remove();
			if (p.dist == Integer.MAX_VALUE) continue;
			for (Edge ce : p.edges) {
				Node c = ce.to;
				if (c.dist > p.dist + ce.w) {
					c.path = p.path + 1;
					c.pred = p;
					if (c.path > k) {
						c.dist = Integer.MAX_VALUE;
					}
					else {
						c.dist = p.dist + ce.w;
					}
					// make sure this gets re-inserted and evaluated
					q.remove(c);
					c.seen = false;
				}
				if (!c.seen) {
					c.seen = true;
					q.add(c);
				}
			}
		}
		
		if (star.dist < Integer.MAX_VALUE) {
			return star.dist;
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
