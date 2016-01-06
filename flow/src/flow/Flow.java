package flow;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Flow<T> {
	Map<Pair<T>, Long> f; // flow function
	Map<Pair<T>, Long> c; // capacity
	Map<Pair<T>, Long> cn; // induced capacity
	Map<T, List<T>> gn; // induced graph
	List<T> V;				// vertices in the graph (Edges are c)
	
	public T s;
	public T t;
	
	public Flow(T s, T t) {
		this.s = s;
		this.t= t;
	}
	
	public void init(T[] nodes, Map<Pair<T>, Long> cap) {
		V = Arrays.asList(nodes);
		c = new HashMap<>(cap);
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
					gn.putIfAbsent(u, new ArrayList<>());
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
			for (T c : gn.getOrDefault(n, new ArrayList<T>())) {
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
			System.out.println(path);
			long flow = getFlow(path);
			addAugmenting(flow, path);
		}
		
	}
	
	public static void printGraph(String[] V, Map<Pair<String>, Long> f, Map<Pair<String>, Long> c) {
		for (String u : V) {
			for (String v : V) {
				Pair<String> p = new Pair<>(u, v);
				long cap = c.getOrDefault(p, 0l);
				if (cap > 0) {
					System.out.println(String.format("%s: %s/%s", p, f.getOrDefault(p, 0l), cap));
				}
			}
		}
	}
	
	public static void main(String[] args) {
		String s = "s";
		String t = "t";
		String[] nodes = {s, "n1", "n2", "n3", "n4", t};

		Map<Pair<String>, Long> c = new HashMap<>();
		c.put(new Pair<>(s, "n1"), 16l);
		c.put(new Pair<>(s, "n2"), 13l);
		c.put(new Pair<>("n1", "n2"), 10l);
		c.put(new Pair<>("n2", "n4"), 14l);
		c.put(new Pair<>("n1", "n3"), 12l);
		c.put(new Pair<>("n2", "n1"), 4l);
		c.put(new Pair<>("n3", "n2"), 9l);
		c.put(new Pair<>("n2", "n4"), 14l);
		c.put(new Pair<>("n4", "n3"), 7l);
		c.put(new Pair<>("n3", t), 20l);
		c.put(new Pair<>("n4", t), 4l);
		
		Flow<String> f = new Flow<>(s, t);
		f.init(nodes, c);
		Map<Pair<String>, Long> flows = f.solve();
		printGraph(nodes, flows, c);

	}

}
