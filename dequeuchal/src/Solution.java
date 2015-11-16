
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Solution {
	public static class IncMap extends HashMap<Integer, Integer> {
		
		public void inc(int key) {
			this.compute(key, (k,  v) -> (v == null) ? 1 : v+1 );
		}
		
		public void dec(int key) {
			this.computeIfPresent(key, (k, v) -> { if (v == 1) this.remove(k); return v-1; } );
		}
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int m = in.nextInt();
		Deque<Integer> deque = new LinkedList<Integer>();
		Deque<Integer> slice = new LinkedList<Integer>();

		IncMap active = new IncMap();

		for (int i = 0; i < n; i++) {
			int num = in.nextInt();
			if (i < m) {
				slice.add(num);
				active.inc(num);
			}
			else {
				deque.add(num);
			}
		}
		
		in.close();
		
		
		int max = active.size();
		
		while (!deque.isEmpty()) {
			int oldVal = slice.poll();
			active.dec(oldVal);
			int newVal = deque.poll();
			slice.add(newVal);
			active.inc(newVal);
			max = Math.max(active.size(), max);
		}
		
		System.out.println(max);
	}
}
