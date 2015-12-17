import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

public class Solution {
	class QNode {
		int p;
		int qlen;
		
		public QNode(int p, LinkedList<Integer> q) {
			this.p = p;
			qlen = q.size();
		}

		@Override
		public int hashCode() {
			return Integer.hashCode(p + qlen);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			QNode other = (QNode) obj;
			if (p != other.p)
				return false;
			if (qlen != other.qlen)
				return false;
			return true;
		}

		
	}
	
	Map<QNode, long[]> states; 
	
	LinkedList<Integer> bricks;  // stack of bricks
	
	@SuppressWarnings("unchecked")
	long[] play(int p, LinkedList<Integer> q) {
		
		// if we've seen this state, return the values
		QNode n = new QNode(p, q);
		if (states.containsKey(n)) {
			System.out.println("hit");
			return states.get(n);
		}
		
		// if the queue has three or few elements in it, best move is take them all
		long[] res = new long[2];
		if (q.size() <= 3) {
			long val = 0;
			for (int i : q) {
				val += i;
			}
			res[p] = val;
			states.put(n, res.clone());
			return res;
		}
		
		// Otherwise, we need to search for it
		long ret = Long.MIN_VALUE;
		int val = 0;
		LinkedList<Integer> q1 = q;
		for (int i = 0; i < 3; i++) {
			q1 = (LinkedList<Integer>) q1.clone();
			val += q1.poll();
			long[] val2 = play(p == 0 ? 1 : 0, q1);
			if (ret < val + val2[p]) {
				res = val2;
				ret = val + val2[p];
			}
		}
		res[p] = ret;
		states.put(n,res.clone());
		return res;
	}
	
	public long play() {
		long [] ret = play(0, bricks);
		return ret[0];
	}
	
	public void init(Scanner in) {
		int n = in.nextInt();
		bricks = new LinkedList<>();
		for (int i = 0; i < n; i++) {
			bricks.add(in.nextInt());
		}
		states = new HashMap<>();
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
