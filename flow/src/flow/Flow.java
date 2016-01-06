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

public class Flow {
	Map<Pair<Integer>, Integer> f; // flow function
	Map<Pair<Integer>, Integer> c; // capacity
	Map<Pair<Integer>, Integer> cn; // induced capacity
	Map<Integer, List<Integer>> gn; // induced graph
	List<Integer> V;				// vertices in the graph (Edges are c)
	
	public static int s = 0;
	public static int t = Integer.MAX_VALUE;
	
	public void init(Integer[] nodes, Map<Pair<Integer>, Integer> cap) {
		V = Arrays.asList(nodes);
		c = new HashMap<>(cap);
		f = new HashMap<>();
	}
	
	// use the current flows to create a new residual graph
	void createResidual() {
		cn = new HashMap<>();
		gn = new HashMap<>();
		
		for (int u : V) {
			for (int v : V) {
				Pair<Integer> p = new Pair<Integer>(u, v);
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
	boolean findPath(List<Integer> path) {
		Queue<Integer> q = new ArrayDeque<>();
		Set<Integer> seen = new HashSet<>();
		Map<Integer, Integer> prev = new HashMap<>();
		// initialize with the starting vertex.
		q.add(s);
		seen.add(s);
		while (!q.isEmpty()) {
			int n = q.poll();
			if (n == t) {
				int p = t;
				while (p != s) {
					path.add(0, p);
					p = prev.get(p);
				}
				path.add(0,s);
				return true;
			}
			for (int c : gn.getOrDefault(n, new ArrayList<Integer>())) {
				if (!seen.contains(c)) {
					seen.add(c);
					prev.put(c, n);
					q.add(c);
				}
			}
		}
		return false;
	}
	
	int getFlow(List<Integer> path) {
		int flow = Integer.MAX_VALUE;
		int u = -1;
		for (int v : path) {
			if (u != -1) {
				flow = Math.min(flow, cn.get(new Pair<>(u, v)));
			}
			u = v;
		}
		return flow;
	}
	
	void addAugmenting(int flow, List<Integer> path) {
		int u = -1;
		for (int v : path) {
			if (u != -1) {
				Pair<Integer> p = new Pair<>(u, v);
				Pair<Integer> antip = new Pair<>(v, u);
				f.put(p, f.getOrDefault(p, 0) + flow);
				f.put(antip, -f.get(p));
			}
			u = v;
		}
		
	}
	
	public Map<Pair<Integer>, Integer> solve() {
		// loop until there are no more flows
		while(true) {
			List<Integer> path = new ArrayList<>();
			createResidual();
			if (!findPath(path)) {
				return f;
			}
			System.out.println(path);
			int flow = getFlow(path);
			addAugmenting(flow, path);
		}
		
	}
	
	public static void printGraph(Integer[] V, Map<Pair<Integer>, Integer> f, Map<Pair<Integer>, Integer> c) {
		for (int u : V) {
			for (int v : V) {
				Pair<Integer> p = new Pair<>(u, v);
				int cap = c.getOrDefault(p, 0);
				if (cap > 0) {
					System.out.println(String.format("%s: %s/%s", p, f.getOrDefault(p, 0), cap));
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Integer[] nodes = {s, 1, 2, 3, 4, t};

		Map<Pair<Integer>, Integer> c = new HashMap<>();
		c.put(new Pair<>(s, 1), 16);
		c.put(new Pair<>(s, 2), 13);
		c.put(new Pair<>(1, 2), 10);
		c.put(new Pair<>(2, 4), 14);
		c.put(new Pair<>(1, 3), 12);
		c.put(new Pair<>(2, 1), 4);
		c.put(new Pair<>(3, 2), 9);
		c.put(new Pair<>(2, 4), 14);
		c.put(new Pair<>(4, 3), 7);
		c.put(new Pair<>(3, t), 20);
		c.put(new Pair<>(4, t), 4);
		
		Flow f = new Flow();
		f.init(nodes, c);
		Map<Pair<Integer>, Integer> flows = f.solve();
		printGraph(nodes, flows, c);

	}

}
