import java.util.HashMap;
import java.util.Map;

public class Qhack {

	// playing with sequences
	
	public long subseqcount(String seq) {
		Map<Character, Integer> l = new HashMap<>();
		long[] N = new long[seq.length() + 1];
		N[0] = 1;
		for (int i = 1; i <= seq.length(); i++) {
			N[i] = 2 * N[i-1];
			char x = seq.charAt(i-1);
			if (l.containsKey(x)) {
				N[i] -= N[l.get(x)-1];
			}
			l.put(x, i);
		}
		return N[seq.length()];
	}

	void printtab(long[][] N) {
		System.out.println("=======");
		for (int i = 0; i < N.length; i++) {
			for (int j = 0; j < N[i].length; j++) {
				System.out.print(N[i][j] + " ");
			}
			System.out.println();
		}
	}
	public long numCS(String xx, String yy) {
		long[][] N = new long[xx.length()+1][yy.length() + 1];
		
		for (int i = 0; i <= xx.length(); i++) {
			N[i][0] = 1;
		}
		for (int j = 1; j <= yy.length(); j++) {
			N[0][j] = 1;
		}
		for (int i = 1; i <= xx.length(); i++) {
			char x = xx.charAt(i-1);
			for (int j = 1; j <= yy.length(); j++) {
				char y = yy.charAt(j-1);
				if (x == y) {
					N[i][j] = 2 * N[i-1][j-1];
				}
				else {
					N[i][j] = N[i-1][j] + N[i][j-1];
				}
				printtab(N);
			}
		}
		return N[xx.length()][yy.length()];
	}
	
	
	public static void main(String[] args) {
		Qhack q = new Qhack();
		System.out.println(q.numCS("abab", "ab"));

	}

}
