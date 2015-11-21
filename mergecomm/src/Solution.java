
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class Disjoint<T> {
	Map<T, Set<T>> sets;

	public Disjoint() {
		sets = new HashMap<>();
	}

	public void makeSet(T n) {
		Set<T> s = new HashSet<>();
		s.add(n);
		sets.put(n, s);
	}

	public Set<T> findSet(T n) {
		return sets.get(n);
	}

	public void union(T n1, T n2) {
		Set<T> s1 = findSet(n1);
		Set<T> s2 = findSet(n2);
		s1.addAll(s2);
		for (T n : s2) {
			sets.put(n, s1);
		}
	}

}

public class Solution {
	Disjoint<Integer> d;

	public Solution(int N) {
		d = new Disjoint<>();
		for (int i = 1; i <= N; i++) {
			d.makeSet(i);
		}
	}

	public void merge(int i, int j) {
		d.union(i, j);
	}

	public int query(int i) {
		return d.findSet(i).size();
	}
	

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		Solution s = new Solution(N);

		int Q = in.nextInt();
		for (int q = 0; q < Q; q++) {
			String command = in.next();
			switch (command) {
			case "Q":
				System.out.println(s.query(in.nextInt()));
				break;

			case "M":
				s.merge(in.nextInt(), in.nextInt());
				break;
			}
		}
		in.close();
	}

}
