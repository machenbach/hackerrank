import java.util.Scanner;


public class Solution {
	
	public int[] init (Scanner in) {
		int n = in.nextInt();
		int[] ary = new int[n+1];
		for (int i = 1; i <= n; i++) {
			ary[i] = in.nextInt();
		}
		return ary;
	}
	
	public int maxElem(int[] ary) {
		int max = Integer.MIN_VALUE;
		for (int i = 1; i < ary.length; i++) {
			max = Math.max(max, ary[i]); 
		}
		return max;
	}
	
	public int solveContig(int[] ary) {
		int start = 1;
		while(start < ary.length && ary[start] < 0) {
			start++;
		}
		if (start == ary.length) {
			return 0;
		}
		
		int maxSoFar = ary[start];
		int maxSum = Integer.MIN_VALUE;
		for (int i = start + 1; i < ary.length; i++) {
			maxSoFar = Math.max(0, maxSoFar + ary[i]);
			maxSum = Math.max(maxSum, maxSoFar);
		}
		return maxSum;
	}
	
	public int SolveNonContig(int[] ary) {
		int start = 1;
		while(start < ary.length && ary[start] < 0) {
			start++;
		}
		if (start == ary.length) {
			return 0;
		}
		
		int max = ary[start];
		for (int i = start + 1; i < ary.length; i++) {
			max = Math.max(max, max + ary[i]);
		}
		return max;
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int t = in.nextInt();
		for (int i = 0; i < t; i++) {
			Solution s = new Solution();
			int[] ary = s.init(in);
			
			// check if we have positive numbers or not
			int m = s.maxElem(ary);
			if (m <= 0) {
				// special case.  Everything is negative or 0, so the answer is the least negative number
				System.out.println(String.format("%s %s", m, m));
			}
			else {
				// we have some positives, so the other solutions will work
				System.out.println(String.format("%s %s", s.solveContig(ary), s.SolveNonContig(ary)));
			}
		}
	}

}
