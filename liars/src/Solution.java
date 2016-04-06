import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Solution {
	static class Group {
		int id;
		int liars;
		
		public Group(int id, int liars) {
			this.id = id;
			this.liars = liars;
		}
		
		public int getLiars() {
			return liars;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + id;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Group other = (Group) obj;
			if (id != other.id)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return String.format("G%s[%s]", id, liars);
		}
	}

	public static void main(String[] args) {
		Map<Integer, Set<Group>> nodeToGroup = new HashMap<>();
		Map<Group, Set<Integer>> groupToNode = new HashMap<>();
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int m = in.nextInt();
		Group[] groups = new Group[m];
		for (int i = 0; i < m; i++) {
			int f = in.nextInt();
			int t = in.nextInt();
			int liars = in.nextInt();
			Group g = new Group(i, liars);
			groups[i] = g;
			Set<Integer> s = new HashSet<>();
			for (int j = f; j <= t; j++) {
				s.add(j);
			}
			groupToNode.put(g, s);
			for (int j = f; j <= t; j++) {
				nodeToGroup.putIfAbsent(j, new HashSet<>());
				nodeToGroup.get(j).add(g);
			}
		}
		in.close();
		System.out.println("Group to node");
		for (Group g : groups) {
			System.out.println(String.format("%s - %s", g, groupToNode.get(g)));
		}
		
		System.out.println("Node to Group");
		for (int i = 1; i <= n; i++) {
			System.out.println(String.format("%s - %s", i, nodeToGroup.get(i)));
		}
		
		System.out.println("Group connects");
		for (Group p : groups) {
			System.out.print(p + " -- ");
			Set<Integer> pSet = groupToNode.get(p);
			for (Group c : groups) {
				Set<Integer> cSet = new HashSet<>(groupToNode.get(c));
				cSet.retainAll(pSet);
				if (cSet.size() != 0) {
					System.out.print(c + " ");
				}
			}
			System.out.println();
		}

	}

}
