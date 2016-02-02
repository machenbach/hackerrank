import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

class Key  {
	int amt;
	Set<Integer> coins;
	public Key(int amt, Set<Integer> coins) {
		this.amt = amt;
		this.coins =  new TreeSet<Integer>((a, b) -> b.compareTo(a));
		this.coins.addAll(coins);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amt;
		result = prime * result + ((coins == null) ? 0 : coins.hashCode());
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
		if (amt != other.amt)
			return false;
		if (coins == null) {
			if (other.coins != null)
				return false;
		} else if (!coins.equals(other.coins))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return String.format("%s: %s", amt, coins);
	}
	
	
	
}

public class Solution {
	static Map<Key, Long> vals = new HashMap<>();
	
	public static long getChange(int amt, Set<Integer> coins) {
		// if no amount, or no coins, return 0
		if (coins.size() == 0 || amt < 0) {
			return 0;
		}
		// if we only have one coin, see if can make change with it
		if (coins.size() == 1) {
			int c = coins.toArray(new Integer[0])[0];
			return amt % c == 0 ? 1 : 0;
		}
		// look this up
		Key k = new Key(amt, coins);
		if (vals.containsKey(k)) {
			return vals.get(k);
		}
		
		// Otherwise, do it the hard way
		
		long tot = 0;
		for (int c : coins) {
			int tamt = amt;
			Set<Integer> tcoins =  new TreeSet<Integer>((a, b) -> b.compareTo(a));
			tcoins.addAll(coins);
			tcoins.remove(c);
			do {
				tot += getChange(tamt, tcoins);
				tamt -= c;
			} while (tamt >= 0);
			break;
		}
		// save the value
		vals.put(k, tot);
		return tot;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int amt = in.nextInt();
		int m = in.nextInt();
		// tree set sorted highest to lowest
		Set<Integer> coins = new TreeSet<Integer>((a, b) -> b.compareTo(a));
		for (int i = 0; i < m; i++) {
			coins.add(in.nextInt());
		}
		in.close();
		
		System.out.println(getChange(amt, coins));
		
	}

}
