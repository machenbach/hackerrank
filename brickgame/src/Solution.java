import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

public class Solution {
	
	long[] score; // player 0, player 1
	
	int[] bricks;
	
	class Node {
		int p;
		int pos;
		
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
		
		@Override
		public String toString() {
			return String.format("(%s, %s)", p, pos);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + p;
			result = prime * result + pos;
			return result;
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
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (p != other.p)
				return false;
			if (pos != other.pos)
				return false;
			return true;
		}

		private Solution getOuterType() {
			return Solution.this;
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
	
	void printEntry (String pre, Node n, long[] a) {
		System.out.println(String.format("%s -- %s: %s, %s", pre, n, a[0], a[1]));
	}
	
	// process the node.
	void process(Node n) {
		System.out.println("Process: " + n);
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
			// close the node
			seen.put(n, res);
			printEntry("end", n, res);
			return;
		}
		
		// otherwise, we need to find child nodes based on taking one, two or three bricks.
		// we can close n if all the children are closed
		boolean canClose = true;
		Node[] children = new Node[3];
		for (int i = 0; i < 3; i++) {
			Node child = new Node(otherP(n.getP()), n.getPos() + i + 1);
			children[i] = child;
			// if the child is not closed
			if (!isClosed(child)) {
				// if we haven't seen it, add it to the seen table
				if (!seen.containsKey(child)) {
					seen.put(child, null);
				}
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
				}
			}
			res = res.clone();
			res[n.getP()] = ret;
			seen.put(n, res);
			printEntry("max", n, res);
		}
		// otherwise, we re-queue n and the children.  Add at the start of the queue, so this behaves as a stack
		queue.addFirst(n);
		for (Node c : children) {
			queue.addFirst(c);
		}
	}

	
	public long play() {
		Node root = new Node(0, 0);
		seen.put(root, null);
		queue = new LinkedList<>();
		queue.add(root);
		
		// process the queue
		while (!queue.isEmpty()) {
			process(queue.poll());
		}
		
		long [] score = seen.get(root);
		System.out.println(String.format("%s %s", score[0], score[1]));
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
