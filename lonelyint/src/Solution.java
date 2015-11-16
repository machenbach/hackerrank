import java.util.Scanner;


public class Solution {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int lonely = 0;
		int n = in.nextInt();
		for (int i = 0; i < n; i++) {
			lonely ^= in.nextInt();
		}
		in.close();
		System.out.println(lonely);
	}

}
