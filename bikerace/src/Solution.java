import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;


class Pair<T> {
	public T u;
	public T v;
	
	public Pair(T u, T v) {
		this.u = u;
		this.v = v;
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



class Flow<T> {
	Map<Pair<T>, Long> f; // flow function
	Map<Pair<T>, Long> c; // capacity
	Map<T, Long> d;		  // distance
	Map<Pair<T>, Long> cn; // induced capacity
	Map<T, PriorityQueue<T>> gn; // induced graph
	List<T> V;				// vertices in the graph (Edges are c)
	
	public T s;
	public T t;
	
	public Flow(T s, T t) {
		this.s = s;
		this.t= t;
	}
	
	public void init(List<T> nodes, Map<Pair<T>, Long> cap, Map<T, Long> d) {
		V = new ArrayList<>(nodes);
		c = new HashMap<>(cap);
		this.d = new HashMap<>(d);
		f = new HashMap<>();
	}
	
	// use the current flows to create a new residual graph
	void createResidual() {
		cn = new HashMap<>();
		gn = new HashMap<>();
		
		for (T u : V) {
			for (T v : V) {
				Pair<T> p = new Pair<T>(u, v);
				long f1 = f.getOrDefault(p, 0l);
				long c1 = c.getOrDefault(p, 0l);
				long r = c1 - f1;
				if (r > 0) {
					cn.put(p, r);
					gn.putIfAbsent(u, 
							new PriorityQueue<>((T a, T b) -> 
								Long.compare(d.getOrDefault(b, 0l), d.getOrDefault(a, 0l))));
					gn.get(u).add(v);
				}
			}
		}
	}
	
	// find a new flow through the residual graph
	boolean findPath(List<T> path) {
		Queue<T> q = new ArrayDeque<>();
		Set<T> seen = new HashSet<>();
		Map<T, T> prev = new HashMap<>();
		// initialize with the starting vertex.
		q.add(s);
		seen.add(s);
		while (!q.isEmpty()) {
			T n = q.poll();
			if (n.equals(t)) {
				T p = t;
				while (!p.equals(s)) {
					path.add(0, p);
					p = prev.get(p);
				}
				path.add(0,s);
				return true;
			}
			for (T c : gn.getOrDefault(n, new PriorityQueue<T>())) {
				if (!seen.contains(c)) {
					seen.add(c);
					prev.put(c, n);
					q.add(c);
				}
			}
		}
		return false;
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
	
	void addAugmenting(long flow, List<T> path) {
		T u = null;
		for (T v : path) {
			if (u != null) {
				Pair<T> p = new Pair<>(u, v);
				Pair<T> antip = new Pair<>(v, u);
				f.put(p, f.getOrDefault(p, 0l) + flow);
				f.put(antip, -f.get(p));
			}
			u = v;
		}
		
	}
	
	public Map<Pair<T>, Long> solve() {
		// loop until there are no more flows
		while(true) {
			List<T> path = new ArrayList<>();
			createResidual();
			if (!findPath(path)) {
				return f;
			}
			long flow = getFlow(path);
			addAugmenting(flow, path);
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
	
	Map<String, Long> dist;	// minimum distance for rider to bike
	
	
	public void init (Scanner in) {
		rn = in.nextInt();
		bn = in.nextInt();
		k = in.nextInt();
		
		riders = new HashMap<>();
		for (int i=0; i < rn; i++) {
			riders.put("R" + i, new Coord(in.nextInt(), in.nextInt()));
		}
		
		bikes = new HashMap<>();
		for (int i = 0; i < bn; i++) {
			bikes.put("B" + i, new Coord(in.nextInt(), in.nextInt()));
		}
		
		// compute the distance array
		dist = new HashMap<>();
		for (String r : riders.keySet()) {
			long d = Long.MAX_VALUE;
			for (String b : bikes.keySet()) {
				d = Math.min(d, riders.get(r).dist2(bikes.get(b)));
			}
			dist.put(r, d);
		}
		
	}
	
	List<String> nodes() {
		List<String> n = new ArrayList<>();
		n.add(s);
		n.addAll(riders.keySet());
		n.addAll(bikes.keySet());
		n.add(t);
		return n;
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
		long f = Long.MIN_VALUE;
		for (Map.Entry<String, Coord> r : riders.entrySet()) {
			for (Map.Entry<String, Coord> b : bikes.entrySet()) {
				long d = r.getValue().dist2(b.getValue());
				f = Math.max(f, d);
			}
		}
		return f;
	}
	
	public long solve() {
		Flow<String> f = new Flow<>(s, t);
		f.init(nodes(), capacity(), dist);
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
