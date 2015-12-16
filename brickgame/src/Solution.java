import java.util.LinkedList;
import java.util.Scanner;

public class Solution {
	
	long[] score; // player 0, player 1
	
	LinkedList<Integer> bricks;  // stack of bricks
	
	long[] play(int p, LinkedList<Integer> q) {
		// if the queue has three or few elements in it, best move is take them all
		long[] res = new long[2];
		if (q.size() <= 3) {
			long val = 0;
			for (int i : q) {
				val += i;
			}
			res[p] = val;
			return res;
		}
		long ret = Long.MIN_VALUE;
		int val = 0;
		LinkedList<Integer> q1 = q;
		for (int i = 0; i < 3; i++) {
			q1 = (LinkedList<Integer>) q1.clone();
			val += q1.poll();
			long[] val2 = play(p == 0 ? 1 : 0, q1);
			if (ret < val + val2[p]) {
				res = val2;
				ret = val;
			}
		}
		res[p] += ret;
		return res;
	}
	
	public long play() {
		long [] ret = play(0, bricks);
		return Math.max(ret[0], ret[1]);
	}
	
	public void init(Scanner in) {
		int n = in.nextInt();
		bricks = new LinkedList<>();
		for (int i = 0; i < n; i++) {
			bricks.add(in.nextInt());
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
