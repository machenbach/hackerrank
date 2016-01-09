import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

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
	Map<Pair<T>, Long> w; // weights between nodes
	
	Map<Pair<T>, Long> cn; // induced capacity
	Map<T, List<T>> gn; // induced graph
	List<T> V;				// vertices in the graph (Edges are c)
	
	public T s;
	public T t;
	
	public Flow(T s, T t) {
		this.s = s;
		this.t= t;
	}
	
	public void init(List<T> nodes, Map<Pair<T>, Long> cap, Map<Pair<T>, Long> w) {
		V = new ArrayList<>(nodes);
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
	
	// find a new path through the residual graph using Dijsktra's algorthim
	boolean findPath(List<T> path) {
		Map<T, T> prev = new HashMap<>();
		Map<T, Node> nodes = new HashMap<>();

		PriorityQueue<Node> q = new PriorityQueue<Node>();
		// initialize with the starting vertex.
		Node ns = new Node(s, 0l);
		nodes.put(s, ns);
		q.add(ns);
		while (true) {
			if (q.isEmpty()) {
				return false;
			}
			Node node = q.poll();
			if (node.n.equals(t)) {
				break;
			}
			for (T c : gn.getOrDefault(node.n, new LinkedList<T>())) {
				Pair<T> p = new Pair<>(node.n, c);
				if (!nodes.containsKey(c)) {
					nodes.put(c, new Node(c));
				}
				Node cnode = nodes.get(c);
				if (cnode.d > ladd(node.d, w.get(p))) {
					if (q.contains(cnode)) {
						q.remove(cnode);
					}
					cnode.d = ladd(node.d, w.get(p));
					prev.put(c, node.n);
					q.add(cnode);
				}
			}
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
	
	String s1 = "S1";
	String t1 = "T1";
	
	Map<String, Coord> riders;
	Map<String, Coord> bikes;
	Map<Pair<String>, Long> weight; 	// pairwise weights
	
	
	public void init (Scanner in) {
		rn = in.nextInt();
		bn = in.nextInt();
		k = in.nextInt();
		
		weight = new HashMap<>();
		weight.put(new Pair<String>(s, s1), 0l);
		weight.put(new Pair<String>(t1, t), 0l);

		riders = new HashMap<>();
		for (int i=0; i < rn; i++) {
			String r = String.format("R%s", i);
			riders.put(r, new Coord(in.nextInt(), in.nextInt()));
			// weight of path from start to rider is 0
			weight.put(new Pair<>(s1, r), 0l);
		}
		
		bikes = new HashMap<>();
		for (int i = 0; i < bn; i++) {
			String b = String.format("B%s", i);
			bikes.put("B" + i, new Coord(in.nextInt(), in.nextInt()));
			// weight of path from bike to t is 0
			weight.put(new Pair<>(b, t1), 0l);
		}
		
		// compute the weight array
		for (String r : riders.keySet()) {
			for (String b : bikes.keySet()) {
				weight.put(new Pair<>(r,b), riders.get(r).dist2(bikes.get(b)));
			}
		}
		
	}
	
	List<String> nodes() {
		List<String> n = new ArrayList<>();
		n.add(s);
		n.add(s1);
		n.addAll(riders.keySet());
		n.addAll(bikes.keySet());
		n.add(t1);
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
			c.put(new Pair<>(s1, r), 1l);
		}
		
		// and the sink
		for (String b : bikes.keySet()) {
			c.put(new Pair<>(b, t1), 1l);
		}
		
		c.put(new Pair<>(s, s1), (long)k);
		c.put(new Pair<>(t1, t), (long)k);
		return c;
	}
	
	long maxFlow(Map<Pair<String>, Long> flows) {
		List<Long> l = new ArrayList<>();
		Map<Long, Pair<String>> tmp = new HashMap<>();
		
		for (Map.Entry<Pair<String>, Long> e : flows.entrySet()) {
			if (e.getValue() > 0) {
				if (!e.getKey().u.equals(s) && !e.getKey().v.equals(t)
						&& !e.getKey().u.equals(s1) && !e.getKey().v.equals(t1)) {
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
		f.init(nodes(), capacity(), weight);
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
