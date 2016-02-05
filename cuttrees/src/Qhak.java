import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

public class Qhak {
	 /**
	   * This method makes a "deep clone" of any object it is given.
	   */
	  public static Object deepClone(Object object) {
	    try {
	      ByteArrayOutputStream baos = new ByteArrayOutputStream();
	      ObjectOutputStream oos = new ObjectOutputStream(baos);
	      oos.writeObject(object);
	      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
	      ObjectInputStream ois = new ObjectInputStream(bais);
	      return ois.readObject();
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	      return null;
	    }
	  }

	
	public static Set<Set<Integer>> combos(Set<Integer> s) {
		Set<Set<Integer>> r = new HashSet<>();
		for (int e : s) {
			Set<Integer> sb = new HashSet<>(s);
			sb.remove(e);
			Set<Set<Integer>> csub = combos(sb);
			r.addAll(new HashSet<Set<Integer>>((Set<Set<Integer>>)deepClone(csub)));
			Set<Integer> t = new HashSet<>();
			t.add(e);
			r.add(t);
			for (Set<Integer> n : csub) {
				n.add(e);
				r.add(n);
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
			System.out.println(total(combos(s)) + " " + (tot * 1l<<(i-1)));
		}
	}

}
