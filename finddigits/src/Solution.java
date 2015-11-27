

import java.util.Scanner;

public class Solution {
	
	static int countDivs(long n) {
		String num = Long.toString(n);
		int tot = 0;
		for (char c : num.toCharArray()) {
			int i = (int)(c-'0');
			if (i != 0 && n % i == 0) {
				tot++;
			}
		}
		return tot;
		
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int T = in.nextInt();
		for (int i = 0; i < T; i++) {
			System.out.println(countDivs(in.nextLong()));
		}

	}

}
