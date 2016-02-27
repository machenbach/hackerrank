import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		int nHeap;
		int[] heaps;
		Scanner in = new Scanner(System.in);
		nHeap = in.nextInt();
		heaps = new int[nHeap];
		int nimSum = 0;
		for (int i = 0; i < nHeap; i++) {
			heaps[i] = in.nextInt();
			nimSum ^= heaps[i];
		}
		in.close();
		int tot = 0;
		for (int i = 0; i < nHeap; i++) {
			if ((nimSum ^ heaps[i]) < heaps[i]) {
				tot++;
			}
		}
		System.out.println(tot);
		

	}

}
