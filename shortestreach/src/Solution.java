import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;


public class Solution {
	class Node {
		int id;
		Map<Node, Integer> edges;
		
		public Node (int id) {
			this.id = id;
			edges = new HashMap<Solution.Node, Integer>();
		}
		
		public int getId() {
			return id;
		}
		
		public void addEdge(Node n, int w) {
			if (edges.containsKey(n)) {
				if (edges.get(n) <= w) {
					return;
				}
			}
			edges.put(n, w);
		}
		
		public Set<Node> getEdges() {
			return edges.keySet();
		}
		
		public int getEdge(Node m) {
			if (edges.containsKey(m)) {
				return edges.get(m);
			}
			return -1;
		}
		
		long path = -1;
		
		public boolean notSeen() {
			return path == -1;
		}
		
		public boolean setPath(long w) {
			// always set the shortest path
			if (path == -1) {
				path = w;
				return true;
			}
			
			boolean set = w < path;
			path = Math.min(path, w);
			return set;
		}
		
		public long getPath() {
			return path;
		}
	}
	
	public void traverse(Node S) {
		Queue<Node> active = 
				new PriorityQueue<Solution.Node>((Node n1, Node n2) 
						-> Long.compare(n1.getPath(), n2.getPath()));
		
		active .add(S);
		S.setPath(0);
		
		while (!active.isEmpty()) {
			Node n = active.poll();
			for (Node m : n.getEdges()) {
				if (m.setPath(n.getPath() + n.getEdge(m))) {
					active.add(m);
				}
				
			}
		}
		
	}
	
	Node[] nodes;
	Node start;
	
	public void init (Scanner in) {
		int nodeNum;
		int edgeNum;
		
		nodeNum = in.nextInt();
		edgeNum = in.nextInt();
		
		nodes = new Node[nodeNum];
		for (int i = 0; i < nodeNum; i++) {
			nodes[i] = new Node(i);
		}
		for (int i = 0; i < edgeNum; i++) {
			int n1 = in.nextInt();
			int n2 = in.nextInt();
			int w = in.nextInt();
			nodes[n1-1].addEdge(nodes[n2-1], w);
			nodes[n2-1].addEdge(nodes[n1-1], w);
		}
		
		start = nodes[in.nextInt()-1];
		
	}
	
	public Node getStart() {
		return start;
	}
	
	public void print(Node s) {
		for (Node n : nodes) {
			if (n.getPath() != 0) {
				System.out.print(n.getPath() + " ");
			}
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int T = in.nextInt();
		
		for (int i = 0; i < T; i++) {
			Solution s = new Solution();
			s.init(in);
			s.traverse(s.getStart());
			s.print(s.getStart());
		}
		
		in.close();
		

	}

}
