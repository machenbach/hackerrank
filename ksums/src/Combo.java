import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Combo {
	class Pair {
		int n;
		int k;
		
		public Pair(int n, int k) {
			this.n = n;
			this.k = k;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + k;
			result = prime * result + n;
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
			Pair other = (Pair) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (k != other.k)
				return false;
			if (n != other.n)
				return false;
			return true;
		}

		private Combo getOuterType() {
			return Combo.this;
		}
	}
	// save the values we've seen
	Map<Pair, BigInteger> comboNbyK;
	
	public Combo() {
		comboNbyK = new HashMap<>();
	}
	
	// combinations of n taken k at a time
	public BigInteger combo (int n, int k) {
		if (n == 1) {
			return BigInteger.ONE;
		}
		if (k == 0) {
			return BigInteger.ONE;
		}
		if (k == 1) {
			return BigInteger.valueOf(n);
		}
		// use the symmetry of combo
		if (k > (n >> 1)) {
			return combo(n, n-k);
		}
		
		Pair p = new Pair(n, k);
		
		// if we seen this already, return the value
		if (comboNbyK.containsKey(p)) {
			return comboNbyK.get(p);
		}
		
		// otherwise use recursive function
		BigInteger c = combo(n-1, k-1).add(combo(n-1, k));
		comboNbyK.put(p, c);
		return c;
	}
	
	public static void main(String[] args) {
		Combo c = new Combo();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			System.out.print("> ");
			String s;
			try {
				s = br.readLine();
			} catch (IOException e) {
				break;
			}
			try (Scanner in = new Scanner (s)) {
				int n = in.nextInt();
				int k = in.nextInt();
				in.close();
				if (n == 0 || k == 0) break;
				BigInteger com = c.combo(n, k);
				System.out.println(com);
				if (com.compareTo(BigInteger.valueOf(100000)) > 0) {
					System.out.println("Too big");
				}
			}
			catch (NoSuchElementException e) {
				break;
			}
		}
	}
	
	
	
}
