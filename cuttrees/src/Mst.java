import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;


public class Mst {
	class Node implements Comparable<Node>{
		int id;
		List<Edge> edges;
		
		public int key = Integer.MAX_VALUE;
		boolean inQ = true;
		
		public Node(int id) {
			this.id = id;
			edges = new LinkedList<>();
		}
		
		public void seen() {
			inQ = false;
		}
		
		public boolean isInQ() {
			return inQ;
		}
		
		public void addEdge(Node to, int weight) {
			edges.add(new Edge(this, to, weight));
			to.edges.add(new Edge(to, this, weight));
		}
		
		public List<Edge> getEdges() {
			return edges;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(key, o.key);
		}
		
	}
	
	class Edge {
		Node from;
		Node to;
		int weight;
		
		public Edge (Node from, Node to, int weight) {
			this.from = from;
			this.to = to;
			this.weight = weight;
		}

		public Node getFrom() {
			return from;
		}

		public Node getTo() {
			return to;
		}

		public int getWeight() {
			return weight;
		}
		
	}
	
	// due to nature of the problem, make the array and all loops 1-based
	Node[] nodes;
	
	public void initNodes(int N) {
		nodes = new Node[N+1];
		for (int i = 1; i <= N; i++) {
			nodes[i] = new Node(i);
		}
	}
	
	public void initEdges(int M, Scanner in) {
		for (int i = 1; i <= M; i++) {
			int f = in.nextInt();
			int t = in.nextInt();
			int w = in.nextInt();
			nodes[f].addEdge(nodes[t], w);
		}
	}
	
	public long primMst(int s) {
		// oddly enough, for this problem we don't really need to find the spanning tree, just
		// the total weight as we construct it.
		PriorityQueue<Node> queue = new PriorityQueue<>();
		
		// init the start node
		nodes[s].key = 0;
		// Queue up all the nodes
		for (int i = 1; i < nodes.length; i++) {
			queue.add(nodes[i]);
		}
		long path = 0;
		
		// start the algorithm
		while (!queue.isEmpty()) {
			Node u = queue.poll();
			u.seen();
			path += u.key;
			for (Edge e : u.getEdges()) {
				Node v = e.to;
				if (v.isInQ() && e.getWeight() < v.key) {
					// ARG!  must remove the element and re-insert to get the queue to see changed value
					queue.remove(v);
					v.key = e.getWeight();
					queue.add(v);
				}
			}
		}
		return path;
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Mst s = new Mst();
		int N = in.nextInt();
		s.initNodes(N);

		int M = in.nextInt();
		s.initEdges(M, in);
		
		int S = in.nextInt();
		long path = s.primMst(S);
		System.out.println(path);

	}

}
