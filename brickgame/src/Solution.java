import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Solution {
	
	long[] score; // player 0, player 1
	
	long total;
	
	int[] bricks;
	
	class Node {
		int p;
		int pos;
		
		public Node(int p, int pos) {
			this.p = p;
			this.pos = pos;
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
	
	
	long[] play(int p, int pos) {
		Node n = new Node(p, pos);
		if (seen.containsKey(n)) {
			return seen.get(n);
		}
		
		// if the queue has three or few elements in it, best move is take them all
		long[] res = new long[2];
		if (bricks.length - pos <= 3) {
			long val = 0;
			for (int i = pos; i < bricks.length; i++) {
				val += bricks[i];
			}
			res[p] = val;
			seen.put(n, res);
			return res;
		}
		long ret = Long.MIN_VALUE;
		int val = 0;
		for (int i = pos; i < pos + 3; i++) {
			val += bricks[i];
			long[] val2 = play(p == 0 ? 1 : 0, i+1);
			if (ret < val + val2[p]) {
				res = val2;
				ret = val + val2[p];
			}
		}
		res[p] = ret;
		seen.put(n, res.clone());
		return res;
	}
	
	public long play() {
		long [] ret = play(0, 0);
		System.out.println(String.format("t=%s, a=%s, %s, atot=%s", total, ret[0], ret[1], ret[0]+ret[1]));
		return Math.max(ret[0], ret[1]);
	}
	
	public void init(Scanner in) {
		seen = new HashMap<>();
		total = 0;
		int n = in.nextInt();
		bricks = new int[n];
		for (int i = 0; i < n; i++) {
			bricks[i] = in.nextInt();
			total += bricks[i];
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
