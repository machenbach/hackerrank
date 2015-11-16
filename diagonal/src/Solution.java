import java.util.Scanner;


public class Solution {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int[] line = new int[n];
		
		int tot1 = 0;
		int tot2 = 0;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				line[j] = in.nextInt();
			}
			tot1 += line[i];
			tot2 += line[n - i - 1];
		}
		in.close();
		System.out.println(Math.abs(tot1 - tot2));
		

	}

}
