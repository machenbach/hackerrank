import java.util.HashSet;
import java.util.Set;

public class Qhak {
	static Set<Set<Integer>> combo(Set<Integer> s, int k) {
		Set<Set<Integer>> res = new HashSet<>();
		if (k == 0) {
			res.add(new HashSet<>());
			return res;
		}
		for (int e : s) {
			Set<Integer> c = new HashSet<>(s);
			c.remove(e);
			for (Set<Integer> sc : combo(c, k - 1)) {
				sc.add(e);
				res.add(sc);
			}
		}
		return res;
	}
	public static void main(String[] args) {
		Set<Integer> s = new HashSet<>();
		for (int i = 0; i< 10; i++) {
			s.add(i);
		}
		
		for (int i = 0; i < 3; i++) {
			System.out.println(combo(s,i));
		}
	}

}
