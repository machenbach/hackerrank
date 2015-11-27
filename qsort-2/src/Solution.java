import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Solution {

	static void printQ(Queue<Integer> q) {
		for (int i : q) {
			System.out.print(i + " ");
		}
		System.out.println();
	}
	
	
	static Queue<Integer> sort (Queue<Integer> l) {
		if (l.size() <= 1) {
			return l;
		}
		int p = l.poll();
		Queue<Integer> left = new LinkedList<>();
		Queue<Integer> right = new LinkedList<>();
		for (int i : l) {
			if (i < p) {
				left.add(i);
			}
			else {
				right.add(i);
			}
		}
		left = sort(left);
		right = sort(right);
		left.add(p);
		left.addAll(right);
		
		printQ(left);
		return left;
		
	}

	public static void main(String[] args) {
		Queue<Integer> l = new LinkedList<>();
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		for (int i = 0; i < n; i++) {
			l.add(in.nextInt());
		}
		sort(l);

	}

}
