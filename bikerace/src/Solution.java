import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class HashDefault<K, V> extends HashMap<K, V> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	V defval;
	
	public HashDefault(V defval) {
		this.defval = defval;
	}
	
	public HashDefault(Map<K, V> m, V defval) {
		super(m);
		this.defval = defval;
	}
	
	@Override
	public V get(Object key) {
		return super.getOrDefault(key, defval);
	}
}


class Pair<T> {
	public T u;
	public T v;
	
	public Pair(T u, T v) {
		this.u = u;
		this.v = v;
	}
	
	public Pair<T> antipair() {
		return new Pair<>(v, u);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((u == null) ? 0 : u.hashCode());
		result = prime * result + ((v == null) ? 0 : v.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Pair<?> other = (Pair<?>) obj;
		if (u == null) {
			if (other.u != null) {
				return false;
			}
		} else if (!u.equals(other.u)) {
			return false;
		}
		if (v == null) {
			if (other.v != null) {
				return false;
			}
		} else if (!v.equals(other.v)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("(%s, %s)", u, v);
	}

	
}


// Making this work for bipartite graph
class Flow<T> {
	Map<Pair<T>, Long> f; // flow function
	Map<Pair<T>, Long> c; // capacity
	Map<Pair<T>, Long> w; // weights between nodes
	
	Map<Pair<T>, Long> cn; // induced capacity
	Map<T, List<T>> gn; // induced graph
	Set<T> Vn;			// nodes in the induced graph

	Set<T> A;			// one set
	Set<T> B;			// the other
	Set<T> V;			// A U B
	
	public T s;
	public T t;
	
	public Flow(T s, T t) {
		this.s = s;
		this.t= t;
	}
	
	public void init(Set<T> a, Set<T> b, Map<Pair<T>, Long> cap, Map<Pair<T>, Long> w) {
		this.A = new HashSet<>(a);
		this.B = new HashSet<>(b);
		V = new HashSet<>(A);
		V.addAll(B);
		V.add(s);
		V.add(t);
		c = new HashDefault<>(cap, 0l);
		this.w = new HashDefault<>(w, Long.MAX_VALUE);
		f = new HashDefault<>(0l);
	}
	
	
	// find a new path through the residual graph using Bellman-Ford algorithm
	boolean findPath(List<T> path) {
		Map<T, T> prev = new HashMap<>();
		// create distances, default is infinity, value at start is 0
		Map<T, Long> d = new HashDefault<>(Long.MAX_VALUE);
		d.put(s, 0l);
		
		for (int i = 0; i < Vn.size(); i++) {
			// for each edge
			for (T u : gn.keySet()) {
				for (T v : gn.get(u)) {
					// relax the edge
					Pair<T> p = new Pair<>(u, v);
					long dist = ladd(d.get(u), w.get(p));
					if (d.get(v) > dist) {
						d.put(v, dist);
						prev.put(v, u);
					}
				}
			}
		}
		
		// check for negative cycles
		for (T u : gn.keySet()) {
			for (T v : gn.get(u)) {
				if (d.get(v) > ladd(d.get(u), w.get(new Pair<>(u, v)))) {
					System.out.println("neg cycle");
					return false;
				}
			}
		}
		
		T p = t;
		while (!s.equals(p)) {
			if (p == null) {
				System.out.println("no path");
				return false;
			}
			path.add(0, p);
			p = prev.get(p);
		}
		path.add(0,s);
		return true;
	}
	
	long getFlow(List<T> path) {
		long flow = Long.MAX_VALUE;
		T u = null;
		for (T v : path) {
			if (u != null) {
				flow = Math.min(flow, cn.get(new Pair<>(u, v)));
			}
			u = v;
		}
		return flow;
	}
	
	void printflows() {
		System.out.println("---");
		for (Map.Entry<Pair<T>, Long> e : f.entrySet()) {
			if (e.getValue() > 0) {
				if (!e.getKey().u.equals(s) && !e.getKey().v.equals(t)) {
					System.out.println(e.getKey() + " " + w.get(e.getKey()));
				}
			}
		}

	}
	
	void removeNegativeWeights() {
		Iterator<Map.Entry<Pair<T>, Long>> it = w.entrySet().iterator();
		while (it.hasNext()) {
			long l = it.next().getValue();
			if (l < 0) it.remove();
		}
		
	}
	
	// return the element of B in this path
	Pair<T> addAugmenting(long flow, List<T> path) {
		Pair<T> lastP = null;
		if (path.size() < 2) return null;
		T u = path.remove(0);
		removeNegativeWeights();
		for (T v : path) {
			Pair<T> p = new Pair<>(u, v);
			f.put(p, f.get(p) + flow);
			f.put(p.antipair(), -f.get(p));
			if (B.contains(v)) {
				lastP = p;
			}
			u = v;
		}
		return lastP;
	}
	
	// use the current flows to create a new residual graph
	void createResidual(Pair<T> lastP) {
		cn = new HashMap<>();
		gn = new HashMap<>();
		Vn = new HashSet<>();
		
		for (T u : V) {
			for (T v : V) {
				Pair<T> p = new Pair<T>(u, v);
				long f1 = f.get(p);
				long c1 = c.get(p);
				long r = c1 - f1;
				if (r > 0) {
					Vn.add(u);
					Vn.add(v);
					cn.put(p, r);
					gn.putIfAbsent(u, new LinkedList<>());
					gn.get(u).add(v);
				}
			}
		}
		
		if (lastP == null) return;
		// we now need to add a negative weight to w from the last B to some element of A
		removeNegativeWeights();
		for (T u : Vn) {
			if (A.contains(u)) {
				Pair<T> p = new Pair<>(lastP.v, u);
				w.put(p, -w.get(lastP));
				if (!cn.containsKey(p)) {
					cn.put(p, 1l);
				}
				gn.putIfAbsent(lastP.v, new ArrayList<>());
				gn.get(lastP.v).add(u);
			}
		}
	}
	
	long ladd(long a, long b) {
		if (a == Long.MAX_VALUE || b == Long.MAX_VALUE) {
			return Long.MAX_VALUE;
		}
		return a + b;
	}
	
	public Map<Pair<T>, Long> solve() {
		// loop until there are no more flows
		Pair<T> lastP = null;
		while(true) {
			List<T> path = new ArrayList<>();
			createResidual(lastP);
			if (!findPath(path)) {
				return f;
			}
			long flow = getFlow(path);
			lastP = addAugmenting(flow, path);
			//printflows();
		}
		
	}
}


class Coord {
	public int x;
	public int y;
	
	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public long dist2(Coord o) {
		long dx = x - o.x;
		long dy = y - o.y;
		return dx * dx + dy * dy;
	}
	
	@Override
	public String toString() {
		return String.format("(%s, %s)", x, y);
	}
}


public class Solution {
	
	
	int rn;
	int bn;
	int k;
	
	String s = "S";
	String t = "T";
	
	Map<String, Coord> riders;
	Map<String, Coord> bikes;
	Map<Pair<String>, Long> weight; 	// pairwise weights
	
	
	public void init (Scanner in) {
		rn = in.nextInt();
		bn = in.nextInt();
		k = in.nextInt();
		
		weight = new HashMap<>();
		riders = new HashMap<>();
		for (int i=0; i < rn; i++) {
			String r = String.format("R%s", i);
			riders.put(r, new Coord(in.nextInt(), in.nextInt()));
			// weight of path from start to rider is 0
			weight.put(new Pair<>(s, r), 0l);
		}
		
		bikes = new HashMap<>();
		for (int i = 0; i < bn; i++) {
			String b = String.format("B%s", i);
			bikes.put("B" + i, new Coord(in.nextInt(), in.nextInt()));
			// weight of path from bike to t is 0
			weight.put(new Pair<>(b, t), 0l);
		}
		
		// compute the weight array
		for (String r : riders.keySet()) {
			for (String b : bikes.keySet()) {
				weight.put(new Pair<>(r,b), riders.get(r).dist2(bikes.get(b)));
				
			}
		}
		
	}
	
	
	
	Map<Pair<String>, Long> capacity() {
		Map<Pair<String>, Long> c = new HashMap<>();
		// all the cross pairs
		for (String r : riders.keySet()) {
			for (String b : bikes.keySet()) {
				Pair<String> p = new Pair<>(r, b);
				c.put(p, 1l);
			}
		}
		
		// add the source
		for (String r : riders.keySet()) {
			c.put(new Pair<>(s, r), 1l);
		}
		
		// and the sink
		for (String b : bikes.keySet()) {
			c.put(new Pair<>(b, t), 1l);
		}
		
		return c;
	}
	
	long maxFlow(Map<Pair<String>, Long> flows) {
		List<Long> l = new ArrayList<>();
		Map<Long, Pair<String>> tmp = new HashMap<>();
		
		for (Map.Entry<Pair<String>, Long> e : flows.entrySet()) {
			if (e.getValue() > 0 && riders.containsKey(e.getKey().u) 
					&& bikes.containsKey(e.getKey().v)) {
				long d = riders.get(e.getKey().u).dist2(bikes.get(e.getKey().v));
				l.add(d);
				tmp.put(d, e.getKey());
			}
		}
		Collections.sort(l);
		
		System.out.println("+---");
		int i = 1;
		for (long lo : l) {
			System.out.println(i++ + " " + lo + " " + tmp.get(lo));
		}
		if (l.size() < k) return 0l;
		return (l.get(k-1));
	}
	
	public long solve() {
		Flow<String> f = new Flow<>(s, t);
		f.init(riders.keySet(), bikes.keySet(), capacity(), weight);
		Map<Pair<String>, Long> flows = f.solve();
		return maxFlow(flows);
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Solution s = new Solution();
		s.init(in);
		in.close();
		System.out.println(s.solve());
	}

}
