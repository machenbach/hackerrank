import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Key {
	String prefix;
	String remaining;
	int k;
	
	public Key(String prefix, String remaining, int k) {
		this.prefix = prefix;
		this.remaining = remaining;
		this.k = k;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + k;
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		result = prime * result + ((remaining == null) ? 0 : remaining.hashCode());
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
		Key other = (Key) obj;
		if (k != other.k)
			return false;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		if (remaining == null) {
			if (other.remaining != null)
				return false;
		} else if (!remaining.equals(other.remaining))
			return false;
		return true;
	}
	
}


public class Solution {
	
	boolean isSquare(String s) {
		if (s.length() % 2 != 0) {
			return false;
		}
		int m = s.length()/2;
		return s.substring(0, m).equals(s.substring(m));
	}
	
	Map<Key, BigInteger> calls = new HashMap<>();
	
	public BigInteger countSquares(String prefix, String remaining, int k) {
		Key key = new Key(prefix, remaining, k);
		if (calls.containsKey(key)) {
			return calls.get(key);
		}
		if (k == 0) {
			return isSquare(prefix) ? BigInteger.ONE : BigInteger.ZERO;
		}
		if (remaining.length() == 0) {
			return BigInteger.ZERO;
		}
		BigInteger res = countSquares(prefix + remaining.charAt(0), remaining.substring(1), k-1);
		res = res.add(countSquares(prefix, remaining.substring(1), k));
		calls.put(key, res);
		return res;
	}
	
	public BigInteger countSquares(String s) {
		BigInteger res = BigInteger.ZERO;
		for (int k = s.length() % 2 == 0 ? s.length() : s.length() - 1; k >= 2; k -= 2) {
			res = res.add(countSquares("", s, k));
		}
		return res;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int T = in.nextInt();
		in.nextLine();
		for (int i = 0; i < T; i++) {
			Solution s = new Solution();
			System.out.println(s.countSquares(in.nextLine().trim()));
		}
		in.close();
	}

}
