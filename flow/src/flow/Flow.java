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
import java.util.TreeSet;

public class Flow {
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
			result = prime * result + getOuterType().hashCode();
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
			if (!getOuterType().equals(other.getOuterType())) {
				return false;
			}
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

		private Flow getOuterType() {
			return Flow.this;
		}

		@Override
		public String toString() {
			return String.format("(%s, %s)", u, v);
		}

		
	}

	Map<Pair<Integer>, Integer> f; // flow function
	Map<Pair<Integer>, Integer> c; // capacity
	Map<Pair<Integer>, Integer> cn; // induced capacity
	Map<Integer, List<Integer>> gn; // induced graph
	List<Integer> V;				// vertices in the graph (Edges are c)
	
	int s = 0;
	int t = Integer.MAX_VALUE;
	
	public void init() {
		Integer[] nodes = {s, 1, 2, 3, 4, t};
		V = Arrays.asList(nodes);
		f = new HashMap<>();
		
		c = new HashMap<>();
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
	
	public void solve() {
		// loop until there are no more flows
		while(true) {
			List<Integer> path = new ArrayList<>();
			createResidual();
			if (!findPath(path)) {
				return;
			}
			int flow = getFlow(path);
			addAugmenting(flow, path);
		}
		
	}
	
	public void printGraph() {
		Set<Integer> nodes = new TreeSet<>(V);
		for (int u : nodes) {
			for (int v : nodes) {
				Pair<Integer> p = new Pair<>(u, v);
				int cap = c.getOrDefault(p, 0);
				if (cap > 0) {
					System.out.println(String.format("%s: %s/%s", p, f.getOrDefault(p, 0), cap));
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Flow f = new Flow();
		f.init();
		f.solve();
		f.printGraph();

	}

}
