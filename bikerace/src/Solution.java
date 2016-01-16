import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
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
		c = new HashMap<>(cap);
		this.w = new HashDefault<>(w, Long.MAX_VALUE);
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
					gn.putIfAbsent(u, new LinkedList<>());
					gn.get(u).add(v);
				}
			}
		}
	}
	
	class Node implements Comparable<Node> {
		public T n;
		public Long d;
		
		public Node(T n) {
			this.n = n;
			this.d = Long.MAX_VALUE;
		}

		public Node(T n, Long d) {
			this.n = n;
			this.d = d;
		}

		@Override
		public int compareTo(Flow<T>.Node o) {
			return d.compareTo(o.d);
		}
		
		@Override
		public String toString() {
			return String.format("(%s, %s)", n, d);
		}
	}
	
	long ladd(long a, long b) {
		if (a == Long.MAX_VALUE || b == Long.MAX_VALUE) {
			return Long.MAX_VALUE;
		}
		return a + b;
	}
	
	
	// find a new path through the residual graph using Bellman-Ford algorithm
	boolean findPath(List<T> path) {
		// need to add check for negative cycles
		Map<T, T> prev = new HashMap<>();
		Map<T, Node> nodes = new HashMap<>();
		Set<Node> inQueue = new HashSet<>();

		Deque<Node> q = new LinkedList<Node>();
		// initialize with the starting vertex.
		Node ns = new Node(s, 0l);
		nodes.put(s, ns);
		q.push(ns);
		inQueue.add(ns);
		while (!q.isEmpty()) {
			Node node = q.pop();
			inQueue.remove(node);
			for (T c : gn.getOrDefault(node.n, new LinkedList<T>())) {
				Pair<T> p = new Pair<>(node.n, c);
				if (!nodes.containsKey(c)) {
					nodes.put(c, new Node(c));
				}
				Node cnode = nodes.get(c);
				if (cnode.d > ladd(node.d, w.get(p))) {
					cnode.d = ladd(node.d, w.get(p));
					prev.put(c, node.n);
					if (!inQueue.contains(cnode)) {
						q.add(cnode);
						inQueue.add(cnode);
					}
				}
			}
		}
		
		// return false if no path
		if (prev.get(t) == null) {
			return false;
		}
		
		T p = t;
		while (!p.equals(s)) {
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
	
	void addAugmenting(long flow, List<T> path) {
		T u = null;
		for (T v : path) {
			if (u != null) {
				Pair<T> p = new Pair<>(u, v);
				f.put(p, f.getOrDefault(p, 0l) + flow);
				f.put(p.antipair(), -f.get(p));
				// if v is bike, add cancelling weight
				long wneg = - w.get(p);
				if (B.contains(v)) {
					for (T u1 : A) {
						if (u != u1) {
							Pair<T> p1 = new Pair<>(v, u1);
							w.put(p1, wneg);
						}
					}
				}
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
			printflows();
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
				c.put(p.antipair(), 1l);
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
			if (e.getValue() > 0) {
				if (!e.getKey().u.equals(s) && !e.getKey().v.equals(t)) {
					long d = riders.get(e.getKey().u).dist2(bikes.get(e.getKey().v));
					l.add(d);
					tmp.put(d, e.getKey());
				}
			}
		}
		Collections.sort(l);
		
		System.out.println("+---");
		int i = 1;
		for (long lo : l) {
			System.out.println(i++ + " " + lo + " " + tmp.get(lo));
		}
		System.out.println("+---");
		return l.get(k - 1);
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
