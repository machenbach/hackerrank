import java.util.Scanner;


public class Solution {
	static void grow(int n) {
		// grow for n cycles
		int h = 1;
		for (int i = 0; i < n; i++) {
			if (i % 2 == 0) { 
				// spring
				h *= 2;;
			}
			else {
				// summer
				h += 1;
			}
		}
		System.out.println(h);
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int T = in.nextInt();
		
		for (int i = 0; i < T; i++) {
			grow(in.nextInt());
		}
		

	}

}
