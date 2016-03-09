import java.math.BigInteger;
import java.util.Scanner;

public class Solution {
	
	BigInteger[][] seqs;
	
	public BigInteger countSquares(String s) {
		// init arrays
		seqs = new BigInteger[s.length()+1][s.length() + 1];
		for (int i = 0; i <= s.length(); i++) {
			seqs[0][i] = BigInteger.ZERO;
			seqs[i][0] = BigInteger.ZERO;
		}
		
		for (int i = 1; i <= s.length(); i++) {
			for (int j = 1; j <= s.length(); j++) {
				if (s.charAt(i-1) == s.charAt(j-1)) {
					seqs[i][j] = BigInteger.ONE.add(seqs[i-1][j]).add(seqs[i][j-1]);
				}
				else {
					seqs[i][j] = seqs[i][j-1];
				}
			}
		}
		
		for (int i = 0; i <= s.length(); i++) {
			for (int j = 0; j <= s.length(); j++) {
				System.out.print(String.format("%3s", seqs[i][j]));
			}
			System.out.println();
		}
		return seqs[s.length()][s.length()];
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
