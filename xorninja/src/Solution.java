import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

import java.math.BigInteger;
import java.util.Scanner;

public class Solution {
	public static long xorSum(int n, Scanner in) {
		BigInteger tot = ZERO;
		for (int i = 0; i < n; i++) {
			tot = tot.or(in.nextBigInteger());
		}
		BigInteger twoNminusOne = ONE.shiftLeft(n-1);
		tot = tot.multiply(twoNminusOne);
		return tot.mod(BigInteger.valueOf(1000000007l)).longValue();
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int t = in.nextInt();
		for (int i = 0; i < t; i++) {
			int n = in.nextInt();
			System.out.println(xorSum(n, in));
		}
		in.close();
	}

}
