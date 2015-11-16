
public class Solution {
	public static void main(String[] args) {
		int[] ary = { 1, 2, 3, 4};
		int n = 4;
		
		int tot = 0;
		// ones
		for (int i = 0; i < n; i++) {
			System.out.println(" " + ary[i]);
			tot += ary[i];
		}
		
		tot = 0;
		// twos
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				int t = ary[i] ^ ary[j];
				System.out.println(" " + ary[i] + " " + ary[j] + ": " + t);
				tot += t;
			}
		}
		System.out.println(tot);
		tot = 0;
		// twos
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int t = ary[i] ^ ary[j];
				System.out.println(" " + ary[i] + " " + ary[j] + ": " + t);
				tot += t;
			}
		}
		System.out.println(tot);

		// threes
		tot = 0;
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				for (int k = j + 1; k < n; k++) {
					int t = ary[i] ^ ary[j] ^ ary[k];
					System.out.println(" " + ary[i] + " " + ary[j] + " " + ary[k] + ": " + t);
					tot += t;
				}
			}
		}
		System.out.println(tot);
		tot = 0;
		// all of them
		int t = 0;
		for (int i = 0; i < n; i++) {
			t ^= ary[i];
			System.out.print(" " + ary[i]);
		}
		System.out.println(": " + t);
		tot += t;
		
		for (int i = 0; i < n; i++) {
			System.out.println(" " + (t ^ ary[i]));
			
		}
		
		System.out.println(tot);
	}

}
