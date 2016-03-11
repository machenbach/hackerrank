import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int T = in.nextInt();
		for (int t = 0; t < T; t++) {
			int n = in.nextInt();
			int[] p1 = new int[n];
			for (int i = 0; i < n; i++) {
				p1[i] = in.nextInt();
			}
			int nimber = 0;
			for (int i = 0; i < n; i++) {
				nimber ^= (Math.abs(p1[i] - in.nextInt()) - 1);
			}
			if (nimber == 0) {
				System.out.println("player-1");
			} else {
				System.out.println("player-2");
			}
		}
		in.close();

	}

}
