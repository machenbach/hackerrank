import java.math.BigInteger;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Solution {
	
	long h;
	long v;
	
	long hCuts = 1;
	long vCuts = 1;
	
	PriorityQueue<Cut> costs;
	
	class Cut implements Comparable<Cut> {
		long cost;
		boolean vertical;
		
		public Cut (int cost, boolean vertical) {
			this.cost = cost;
			this.vertical = vertical;
		}

		@Override
		public int compareTo(Cut o) {
			return Long.compare(o.cost, cost);
		}

		public boolean isVertical() {
			return vertical;
		}

		public long getCost() {
			return cost * (vertical ? vCuts : hCuts);
		}

		@Override
		public String toString() {
			return String.format("<%s:%s>", cost, vertical ? "v" : "h");
		}
		
		
		
	}

	
	public Solution(int m, int n) {
		h = m;
		v = n;

		costs = new PriorityQueue<>();
	}

	public void initCosts(Scanner in) {
		for (int i = 1; i < h; i++) {
			int n = in.nextInt();
			costs.add(new Cut(n, false));
		}
		
		for (int i = 1; i < v; i++) {
			int n = in.nextInt();
			costs.add(new Cut(n, true));
		}
		
	}
	
	public long singleCost() {
		Cut c = costs.poll();
		long cost = c.getCost();
		if (c.isVertical()) {
			hCuts++;
		}
		else {
			vCuts++;
		}
		return cost;
	}
	
	public int cost() {
		BigInteger total = BigInteger.valueOf(0);
		BigInteger modfac = BigInteger.valueOf(1000000007);
		while (!costs.isEmpty()) {
			total = total.add(BigInteger.valueOf(singleCost()));
		}
		return total.mod(modfac).intValue();
	}
 

	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int t = in.nextInt();
		for (int i = 0; i < t; i++) {
			int m = in.nextInt();
			int n = in.nextInt();
			Solution s = new Solution(m, n);
			s.initCosts(in);
			System.out.println(s.cost());
		}

	}

}
