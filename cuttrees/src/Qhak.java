import java.util.HashSet;
import java.util.Set;

public class Qhak {
	public static Set<Set<Integer>> powerset(Set<Integer> s) {
		Set<Set<Integer>> r = new HashSet<>();
		for (int e : s) {
			Set<Integer> sb = new HashSet<>(s);
			sb.remove(e);
			Set<Set<Integer>> csub = powerset(sb);
			r.addAll(new HashSet<Set<Integer>>(csub));
			Set<Integer> t = new HashSet<>();
			t.add(e);
			r.add(t);
			for (Set<Integer> n : csub) {
				Set<Integer> tn = new HashSet<>(n);
				tn.add(e);
				r.add(tn);
			}
		}
		return r;
	}
	
	public static long total(Set<Set<Integer>> s) {
		long tot = 0;
		for (Set<Integer> c : s) {
			for (int i : c) {
				tot+= i;
			}
		}
		return tot;
	}

	public static void main(String[] args) {
		Set<Integer> s= new HashSet<>();
		long tot = 0;
		for (int i = 1; i <= 10; i++) {
			s.add(i);
			tot += i;
			System.out.println(total(powerset(s)) + " " + (tot * 1l<<(i-1)));
		}
	}

}
