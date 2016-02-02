import java.util.ArrayList;
import java.util.BitSet;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

class WorkFrame {
	public int n;
	public BitSet bs;
	public int level;
	public BitSet pbs;
	public boolean process;
	
	public WorkFrame (int n, BitSet pbs, int level) {
		this.n = n;
		this.pbs = pbs;
		this.level = level;
		this.bs = new BitSet();
	}
}

public class Solution {
	
	int nNodes;
	int t;
	ArrayList<List<Integer>> children;
	int[] parents;
	
	void addNode (int p, int c) {
		children.get(p).add(c);
		parents[c] = p;
	}
	
	int root() {
		for (int r : parents) {
			if (r != 0) {
				while (parents[r] != 0) {
					r = parents[r];
				}
				return r;
			}
		}
		return 0;
	}
	
	public void init(Scanner in) {
		nNodes = in.nextInt();
		t = in.nextInt();
		children = new ArrayList<>(nNodes + 1);
		for (int i = 0; i < nNodes + 1; i++) {
			children.add(new LinkedList<>());
		}
		parents = new int [nNodes + 1];
		for (int i = 1; i < nNodes; i++) {
			addNode(in.nextInt(), in.nextInt());
		}
	}
	
	
	public long solve() {
		Deque<WorkFrame> q = new LinkedList<>();
		long tot = 0;
		
		q.push(new WorkFrame(root(), null, 1));
		
		while (!q.isEmpty()) {
			WorkFrame f = q.pop();
			if (f.process) {
				tot += f.bs.get(Math.max(1, f.n - t), Math.min(f.n + t, nNodes)+ 1).cardinality();
				if (f.pbs != null) {
					f.pbs.or(f.bs);
					f.pbs.set(f.n);
				}
			}
			else {
				f.process = true;
				q.push(f);
				for (int c : children.get(f.n)) {
					q.push(new WorkFrame(c, f.bs, f.level+1));
				}
			}
			
		}
		
		return tot;
	}
	
	
	public static void main(String[] args) {
		Solution s = new Solution();
		Scanner in = new Scanner(System.in);
		s.init(in);
		in.close();
		System.out.println(s.solve());
	}
}
