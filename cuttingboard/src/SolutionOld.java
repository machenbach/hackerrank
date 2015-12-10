import java.math.BigInteger;
import java.util.PriorityQueue;
import java.util.Scanner;


class CutQueue extends PriorityQueue<Integer> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	long total;
	
	public CutQueue(int n) {
		super(n, (Integer i, Integer j) -> Integer.compare(j, i));
	}


	@Override
	public boolean add(Integer e) {
		total += e;
		return super.add(e);
	}

	@Override
	public Integer poll() {
		Integer e = super.poll();
		total -= e;
		return e;
	}

	public long getTotal() {
		return total;
	}
	
	
	
}
public class SolutionOld {
	// Horizontal, vertical (m, n in the problem)
	int h;
	int v;
	
	// Number of cuts in each direction
	int hCuts = 1;
	int vCuts = 1;
	
	
	// the cost (sorted highest to lowest)
	CutQueue hCosts;
	CutQueue vCosts;
	
	public SolutionOld(int m, int n) {
		h = m;
		v = n;

		hCosts = new CutQueue(h);
		vCosts = new CutQueue(v);
	}
	
	public void initCosts(Scanner in) {
		for (int i = 0; i < h; i++) {
			int n = in.nextInt();
			hCosts.add(n);
		}
		
		for (int i = 0; i < v; i++) {
			int n = in.nextInt();
			vCosts.add(n);
		}
		
	}
	
	public int singleCost() {
		// from the currently most expensive q
		if (vCosts.getTotal() * vCuts > hCosts.getTotal() * hCuts) {
			hCuts++;
			return vCosts.isEmpty() ? 0 : vCosts.poll() * vCuts;
		}
		else {
			vCuts++;
			return hCosts.isEmpty() ? 0 : hCosts.poll() * hCuts;
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
			SolutionOld s = new SolutionOld(m, n);
			s.initCosts(in);
			System.out.println(s.cost());
		}

	}

}
