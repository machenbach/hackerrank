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
	Map<Pair<T>, Integer> f; // flow function
	Map<Pair<T>, Integer> c; // capacity
	Map<Pair<T>, Integer> cn; // induced capacity
	Map<T, List<T>> gn; // induced graph
	List<T> V;				// vertices in the graph (Edges are c)
	
	public T s;
	public T t;
	
	public Flow(T s, T t) {
		this.s = s;
		this.t= t;
	}
	
	public void init(T[] nodes, Map<Pair<T>, Integer> cap) {
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
				int f1 = f.getOrDefault(p, 0);
				int c1 = c.getOrDefault(p, 0);
				int r = c1 - f1;
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
	
	int getFlow(List<T> path) {
		int flow = Integer.MAX_VALUE;
		T u = null;
		for (T v : path) {
			if (u != null) {
				flow = Math.min(flow, cn.get(new Pair<>(u, v)));
			}
			u = v;
		}
		return flow;
	}
	
	void addAugmenting(int flow, List<T> path) {
		T u = null;
		for (T v : path) {
			if (u != null) {
				Pair<T> p = new Pair<>(u, v);
				Pair<T> antip = new Pair<>(v, u);
				f.put(p, f.getOrDefault(p, 0) + flow);
				f.put(antip, -f.get(p));
			}
			u = v;
		}
		
	}
	
	public Map<Pair<T>, Integer> solve() {
		// loop until there are no more flows
		while(true) {
			List<T> path = new ArrayList<>();
			createResidual();
			if (!findPath(path)) {
				return f;
			}
			System.out.println(path);
			int flow = getFlow(path);
			addAugmenting(flow, path);
		}
		
	}
	
	public static void printGraph(String[] V, Map<Pair<String>, Integer> f, Map<Pair<String>, Integer> c) {
		for (String u : V) {
			for (String v : V) {
				Pair<String> p = new Pair<>(u, v);
				int cap = c.getOrDefault(p, 0);
				if (cap > 0) {
					System.out.println(String.format("%s: %s/%s", p, f.getOrDefault(p, 0), cap));
				}
			}
		}
	}
	
	public static void main(String[] args) {
		String s = "s";
		String t = "t";
		String[] nodes = {s, "n1", "n2", "n3", "n4", t};

		Map<Pair<String>, Integer> c = new HashMap<>();
		c.put(new Pair<>(s, "n1"), 16);
		c.put(new Pair<>(s, "n2"), 13);
		c.put(new Pair<>("n1", "n2"), 10);
		c.put(new Pair<>("n2", "n4"), 14);
		c.put(new Pair<>("n1", "n3"), 12);
		c.put(new Pair<>("n2", "n1"), 4);
		c.put(new Pair<>("n3", "n2"), 9);
		c.put(new Pair<>("n2", "n4"), 14);
		c.put(new Pair<>("n4", "n3"), 7);
		c.put(new Pair<>("n3", t), 20);
		c.put(new Pair<>("n4", t), 4);
		
		Flow<String> f = new Flow<>(s, t);
		f.init(nodes, c);
		Map<Pair<String>, Integer> flows = f.solve();
		printGraph(nodes, flows, c);

	}

}
