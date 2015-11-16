import java.util.Scanner;


public class Solution {
	class Node {
		public int left;
		public int right;
		public Node(int left, int right) {
			this.left = left;
			this.right = right;
		}
	}
	
	// Tree is represented as array of nodes.  Root index is 1, length array is n+1;
	Node[] tree;
	
	void initTree (Scanner in) {
		int n = in.nextInt();
		tree = new Node[n+1];
		for (int i = 1; i <= n; i++) {
			tree[i] = new Node(in.nextInt(), in.nextInt());
		}
	}
	
	String printTree(int node) {
		if (node > 0) {
			return printTree(tree[node].left) + node + " " + printTree(tree[node].right); 
		}
		else {
			return "";
		}
	}
	
	void swapNode(int node, int swapat, int level) {
		if (node > 0) {
			if (level % swapat == 0) {
				int tmp = tree[node].left;
				tree[node].left = tree[node].right;
				tree[node].right = tmp;
			}
			swapNode(tree[node].left, swapat, level + 1);
			swapNode(tree[node].right, swapat, level + 1);
		}
	}
	
	public void doSwapTree() {
		Scanner in = new Scanner(System.in);
		
		initTree(in);
		
		int T = in.nextInt();
		
		for (int i = 0; i < T; i++) {
			int swapat = in.nextInt();
			swapNode(1, swapat, 1);
			System.out.println(printTree(1));
		}
		
		in.close();
	}

	public static void main(String[] args) {
		new Solution().doSwapTree();
	}

}
