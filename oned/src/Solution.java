import java.util.BitSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Solution {
	BitSet game;
	Queue<Integer> active;
	int skip;
	BitSet seen;
	int N;
	
	public Solution(BitSet game, int skip) {
		this.game = game;
		this.skip = skip;
		active = new PriorityQueue<Integer>((Integer x, Integer y) -> y.compareTo(x));
		N = game.size();
		seen = new BitSet(N);
	}
	
	public void add(int i) {
		if (i >= 0 && !seen.get(i) && !game.get(i)) {
			active.add(i);
			seen.set(i);
		}
	}
	
	public String solve() {
		add(0);
		
		while (true) {
			Integer next = active.poll();
			if (next == null) {
				return "NO";
			}
			if (next > N) {
				return "YES";
			}
			add(next + 1);
			add(next + skip);
			add(next - 1);
		}
		
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int T = in.nextInt();
		for (int t = 0; t < T; t++) {
			int n = in.nextInt();
			int skip = in.nextInt();
			BitSet game = new BitSet(n);
			for (int i = 0; i < n; i++) {
				int val = in.nextInt();
				game.set(i, val == 1);
			}
			String res = new Solution(game, skip).solve();
			System.out.println(res);
		}
		in.close();
		

	}

}
