import java.math.BigInteger;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Solution {
	// Horizontal, vertical (m, n in the problem)
	int h;
	int v;
	
	// Number of cuts in each direction
	int hCuts = 1;
	int vCuts = 1;
	
	// the cost (sorted highest to lowest)
	PriorityQueue<Integer> hCosts;
	PriorityQueue<Integer> vCosts;
	
	public Solution(int m, int n) {
		h = m;
		v = n;

		hCosts = new PriorityQueue<Integer>(h, (Integer i, Integer j) -> Integer.compare(j, i));
		vCosts = new PriorityQueue<Integer>(v, (Integer i, Integer j) -> Integer.compare(j, i));
	}
	
	public void initCosts(Scanner in) {
		for (int i = 1; i < h; i++) {
			hCosts.add(in.nextInt());
		}
		
		for (int i = 1; i < v; i++) {
			vCosts.add(in.nextInt());
		}
	}
	
	public int singleCost() {
		// find the most expensive cut
		int vCut = vCosts.isEmpty() ? Integer.MIN_VALUE : vCosts.peek() * hCuts;
		int hCut = hCosts.isEmpty() ? Integer.MIN_VALUE : hCosts.peek() * vCuts;
		if (vCut >= hCut) {
			// take the vertical cut
			vCosts.poll(); // remove this cost
			vCuts++; // add a vertical cut
			return vCut;
		}
		else {
			hCosts.poll(); // remove this cost
			hCuts++; // add a horizontal cut
			return hCut;
			
		}
	}
	
	public int cost() {
		BigInteger total = BigInteger.valueOf(0);
		BigInteger modfac = BigInteger.valueOf(1000000007);
		while (!(hCosts.isEmpty() && vCosts.isEmpty())) {
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
