
class BIT {
	long[] b;
	int n;
	
	public BIT (int n) {
		this.n = n;
		b = new long[n + 1];
	}
	
	public long get(int i) {
		long res = 0;
		while (i != 0) {
			res += b[i];
			i -= (i & -i);
		}
		return res;
	}
	
	public void set(int i, long val) {
		while (i <= n) {
			b[i] += val;
			i += (i & -i);
		}
	}
}


public class Qhack {

	public static void main(String[] args) {
		BIT b = new BIT(20);
		for (int i = 1; i <= 20; i++) {
			b.set(i, i);
		}
		
		for (int i = 1;  i <= 20; i++) {
			System.out.println(b.get(i));
		}

	}

}
