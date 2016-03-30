import java.util.Scanner;

public class Solution {

	// These are the grundy numbers for this problem, as determined by a separate program
	// the two 0 states (0 and 1) plus the six primes gives a repeating pattern 9 long
	static long g[] = {0, 0, 1, 1, 2, 2, 3, 3, 4};
	
	static long grundy(long num) {
		return g[(int)(num % 9)];
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int t = in.nextInt();
		
		for (int i = 0; i < t; i++) {
			int n = in.nextInt();
			long nimber = 0;
			for (int j = 0; j < n; j++) {
				nimber = nimber ^ grundy(in.nextLong());
			}
			System.out.println(nimber == 0 ? "Sandy" : "Manasa");
		}
		in.close();
	}

}
