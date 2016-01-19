import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class bhack {
	public static void main(String[] args) {
		Map<Integer, Integer> m = new HashMap<Integer, Integer>();
		for (int i = 0; i < 10; i++) {
			m.put(i, i-5);
		}
		System.out.println(m.size());
		for (Map.Entry<Integer, Integer> e : m.entrySet()) {
			System.out.println(String.format("%s %s", e.getKey(), e.getValue()));
		}
		System.out.println(m.values());
		
		Iterator<Map.Entry<Integer, Integer>> it = m.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, Integer> e = it.next();
			if (e.getValue() < 0) it.remove();
			}
		
		System.out.println(m.size());
		for (Map.Entry<Integer, Integer> e : m.entrySet()) {
			System.out.println(String.format("%s %s", e.getKey(), e.getValue()));
		}
		System.out.println(m.values());
		
	}

}
