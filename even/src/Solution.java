import java.util.ArrayList;
import java.util.List;
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
		
		public void removeChild(Node n) {
			// remove the child, and adjust the count
			nodeCnt -= n.getNodeCnt();
			children.remove(n);
		}
		
		public List<Node> getChildren() {
			return children;
		}

		public Node getParent() {
			return parent;
		}

		public void setParent(Node parent) {
			this.parent = parent;
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
	
	public void childCnt() {
		childCount(nodes[1]);
	}
	
	// traverse the tree, cutting lowest even number
	
	
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
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
