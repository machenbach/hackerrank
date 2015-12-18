import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

public class Solution {
	
	long[] score; // player 0, player 1
	
	int[] bricks;
	
	static class Node {
		int p;
		int pos;
		int take;
		Node child;
		
		static Map<Node, Node> nodes = new HashMap<>(); 
		
		static Node node(int p, int pos) {
			Node n = new Node(p, pos);
			Node r = nodes.get(n);
			if (r == null) {
				nodes.put(n, n);
				r = n;
			}
			return r;
		}
		
		
		public Node(int p, int pos) {
			this.p = p;
			this.pos = pos;
		}
		
		public int getP() {
			return p;
		}

		public int getPos() {
			return pos;
		}
		
		public int getTake() {
			return take;
		}

		public void setTake(int take) {
			this.take = take;
		}

		public Node getChild() {
			return child;
		}

		public void setChild(Node child) {
			this.child = child;
		}

		@Override
		public String toString() {
			return String.format("(%s, %s)", p, pos);
		}

		@Override
		public int hashCode() {
			return Integer.hashCode(p + pos);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			if (p != other.p)
				return false;
			if (pos != other.pos)
				return false;
			return true;
		}

	}
	
	Map<Node, long[]> seen;
	
	LinkedList<Node> queue;
	
	// get the next player
	int otherP(int p) {
		return p == 0 ? 1 : 0;
	}
	
	boolean isClosed(Node n) {
		return seen.get(n) != null;
	}
	
	// A node can be unseen, seen, or closed.
	// an unseen node does not exist in the seen map
	// a seen node exists in the seen map, but has no value (value is null)
	// a closed node exists in the seen map, with an array value
	
	// process the node.
	void process(Node n) {
		// if the node is closed, just return
		if (isClosed(n)) {
			return;
		}
		
		// if the node points to one of the last three elements, we can sum it up, and close it.
		long[] res = new long[2];
		if (bricks.length - n.getPos() <= 3) {
			long val = 0;
			for (int i = n.getPos(); i < bricks.length; i++) {
				val += bricks[i];
			}
			res[n.getP()] = val;
			// how many did we take
			n.setTake(bricks.length-n.getPos());
			n.setChild(null);
			// close the node
			seen.put(n, res);
			return;
		}
		
		// otherwise, we need to find child nodes based on taking one, two or three bricks.
		// we can close n if all the children are closed
		boolean canClose = true;
		Node[] children = new Node[3];
		for (int i = 0; i < 3; i++) {
			Node child = Node.node(otherP(n.getP()), n.getPos() + i + 1);
			children[i] = child;
			// if the child is not closed
			if (!isClosed(child)) {
				canClose = false;
			}
		}
		
		// if we can close the node, find our best move, and close it
		if (canClose) {
			long ret = Long.MIN_VALUE;
			int val = 0;
			for (int i = 0; i < 3; i++) {
				val += bricks[i + n.getPos()];
				long[] val2 = seen.get(children[i]);
				if (ret < val + val2[n.getP()]) {
					res = val2;
					ret = val + val2[n.getP()];
					// note the move and the number
					n.setChild(children[i]);
					n.setTake(i + 1);
				}
			}
			res = res.clone();
			res[n.getP()] = ret;
			seen.put(n, res);
		}
		else {
			// otherwise, we re-queue n and the children.  Add at the start of the queue, so this behaves as a stack
			queue.addFirst(n);
			for (Node c : children) {
				// if we've seen the child, it's already in the queue.  Otherwise, mark it seen and insert
				if (!seen.containsKey(c)) {
					seen.put(c, null);
					queue.addFirst(c);
				}
			}
		}
	}

	void printMoves(Node n) {
		while (n != null) {
			System.out.println(String.format("Player %s take %s", n.getP(), n.getTake()));
			n = n.getChild();
		}
	}
	
	public long play() {
		Node root = Node.node(0, 0);
		seen.put(root, null);
		queue = new LinkedList<>();
		queue.add(root);
		
		// process the queue
		while (!queue.isEmpty()) {
			process(queue.poll());
		}
		
		// printMoves(root);
		
		long [] score = seen.get(root);		
		return score[0];
	}
	
	public void init(Scanner in) {
		seen = new HashMap<>();
		int n = in.nextInt();
		bricks = new int[n];
		for (int i = 0; i < n; i++) {
			bricks[i] = in.nextInt();
		}
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int t = in.nextInt();
		for (int i = 0; i < t; i++) {
			Solution s = new Solution();
			s.init(in);
			System.out.println(s.play());
		}
	}

}
