package mefirst;

import java.util.Scanner;

public class MeFirst {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int a = in.nextInt();
		int b = in.nextInt();
		if (0 < a && a <= 1000 && 0 < b && b <= 1000) {
			System.out.println(a + b);
		}
		else {
			System.out.println("Invalid input");
		}
		in.close();

	}

}
