import java.util.Scanner;


public class Solution {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int t = in.nextInt();
		int tot = 0;
		for (int i = 0; i < t; i++) {
			tot += in.nextInt();
		}
		in.close();
		System.out.println(tot);

	}

}
