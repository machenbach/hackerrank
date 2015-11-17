import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;


public class Solution {
	
	class Node {
		int id;
		List<Node> children;
		Node parent;
		int nodeCnt;
		
		public Node (int id) {
			this.id = id;
			children = new ArrayList<>();
		}
		
		public void addChild(Node n) {
			children.add(n);
			n.parent = this;
		}
		
		public List<Node> getChildren() {
			return children;
		}

		public Node getParent() {
			return parent;
		}

		public int getNodeCnt() {
			return nodeCnt;
		}

		public void setNodeCnt(int nodeCnt) {
			this.nodeCnt = nodeCnt;
		}

		public int getId() {
			return id;
		}

		@Override
		public String toString() {
			return String.format("<%s>", id);
		}
		
		
	}
	
	int N, M;
	
	// need to count the children nodes
	void childCount(Node n) {
		int cnt = 1;
		// leaf nodes have no children
		for (Node c : n.getChildren()) {
			childCount(c);
			cnt += c.getNodeCnt();
		}
		n.setNodeCnt(cnt);
	}
	
	// init the node array
	
	Node[] nodes;
	public void initNodes() {
		nodes = new Node[N+1];
		for (int i = 1; i <=N; i++) {
			nodes[i] = new Node(i);
		}
	}

	// initialize the tree from scanner
	void initTree(Scanner in) {
		for (int i = 0; i < M; i++) {
			int c = in.nextInt();
			int p = in.nextInt();
			nodes[p].addChild(nodes[c]);
		}
		childCount(nodes[1]);
	}

	/*
	 * algorithm as follows: for the tree to able to break subtrees of even
	 * numbers of nodes, the root node must have an even number of children.
	 * Algorithm uses a greedy approach, finding the smallest number of nodes
	 * that are even, and removing that tree. The number of nodes removed goes
	 * up the tree, meaning that the root node
	 */

	// Only sub-trees with even numbes of nodes can take part in this
	PriorityQueue<Node> active;
	
	void initActive() {
		active = new PriorityQueue<>((Node n1, Node n2) -> Integer.compare(n1.getNodeCnt(), n2.getNodeCnt()));
		for (int i = 1; i <= N; i++) {
			if (nodes[i].getNodeCnt() % 2 == 0) {
				active.add(nodes[i]);
			}
		}
	}
	
	void removeChildren(Node n) {
		for (Node c : n.getChildren()) {
			removeChildren(c);
		}
		active.remove(n);
	}
	
	// we are cutting tree at n.  Fix the counts of the parents, and re-do the priority queue
	void fixCounts(Node n) {
		int cnt = n.getNodeCnt();

		Node p = n.getParent();
		while (p != null) {
			active.remove(p);
			p.setNodeCnt(p.getNodeCnt() - cnt);
			active.add(p);
			p = p.getParent();
		}
	}
	
	public int countCuts() {
		int cuts = 0;
		initActive();
		while (!active.isEmpty()) {
			Node n = active.poll();
			cuts++;
			fixCounts(n);
			removeChildren(n);
		}
		// This actually counts the subtrees.  The number of cuts is one less
		return cuts-1;
		
	}
	
	public Solution(int n, int m) {
		N = n;
		M = m;
	}
	

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int m = in.nextInt();
		
		Solution s = new Solution(n, m);
		s.initNodes();
		s.initTree(in);
		in.close();
		
		System.out.println(s.countCuts());

	}

}
