import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;


public class Solution2 {
	
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
		
		public void setParent(Node n) {
			parent = n;
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
			return String.format("<%s:%s>", id, nodeCnt);
		}

		@Override
		public boolean equals(Object obj) {
			// TODO Auto-generated method stub
			return id == ((Node)obj).id;
		}

		@Override
		public int hashCode() {
			return Integer.hashCode(id);
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

	// we are cutting tree at n.  Fix the counts of the parents, and re-do the priority queue
	void fixParents(Node n) {
		int cnt = n.getNodeCnt();

		Node p = n.getParent();
		if (p != null) {
			p.getChildren().remove(n);
		}
		while (p != null) {
			p.setNodeCnt(p.getNodeCnt() - cnt);
			p = p.getParent();
		}
		n.setParent(null);

	}
	
	List<Node> cutTrees = new ArrayList<>();
	

	// count the cuts in node n and all it's sub trees
	int cuts(Node n) {
		if (n.getNodeCnt() == 2) {
			fixParents(n);
			return 1;
		}
		
		int t = 0;
		for (Node c : new ArrayList<>(n.getChildren())) {
			t += cuts(c);
		}
		if (t == 0 && n.getNodeCnt() % 2 == 0) {
			fixParents(n);
			return 1;
		}
		
		return t;
	}
	
	public int countCuts() {
		int tot = 0;
		int t = 0;
		do {
			t = 0;
			for (Node c : new ArrayList<>(nodes[1].getChildren())) {
				t += cuts(c);
			}
			tot += t;
		} while (t != 0);
		return tot;
	}
	
	public void printCuts() {
		for (Node n : cutTrees) {
			System.out.println(n);
		}
	}
	
	public void printTree(Node n, String prefix) {
		System.out.println(prefix + n);
		for (Node c : n.getChildren()) {
			printTree(c, prefix + "   ");
		}
	}
	
	public void printTree(Node n) {
		printTree(n, "");
	}
	
	public void printTree() {
		printTree(nodes[1]);
	}
	
	public Solution2(int n, int m) {
		N = n;
		M = m;
	}
	

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int m = in.nextInt();
		
		Solution2 s = new Solution2(n, m);
		s.initNodes();
		s.initTree(in);
		in.close();
		//s.printTree();
		
		System.out.println(s.countCuts());
		//s.printCuts();

	}

}
