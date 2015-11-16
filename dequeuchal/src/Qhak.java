import java.util.HashMap;


public class Qhak {
	
	public static class IncMap extends HashMap<Integer, Integer> {
		
		public void inc(int key) {
			this.compute(key, (k,  v) -> (v == null) ? 1 : v+1 );
		}
		
		public void dec(int key) {
			this.computeIfPresent(key, (k, v) -> { if (v == 1) this.remove(k); return v-1; } );
		}
	}
	public static void main(String[] args) {
		IncMap m = new IncMap();
		for (int i = 0; i < 10; i++) {
			m.inc(1);
		}
		System.out.println(m.get(1));
		for (int i = 0; i < 10; i++) {
			m.dec(1);
		}
		System.out.println(m.containsKey(1) ? "Fail" : "Pass");
	}

}
