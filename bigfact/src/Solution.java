import java.math.BigInteger;
import java.util.Scanner;

public class Solution {
	static BigInteger fac (long n) {
		BigInteger res = BigInteger.valueOf(1);
		for (long i = 2; i <= n; i++) {
			res = res.multiply(BigInteger.valueOf(i));
		}
		return res;
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println(fac(in.nextLong()));
		in.close();

	}

}
