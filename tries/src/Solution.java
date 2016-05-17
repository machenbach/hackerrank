import java.util.Scanner;

class Trie {
	class Node {
		Node[] pref = new Node[26];
		int count = 0;
	}
	
	Node root = new Node();
	
	public void add (String s) {
		Node n = root;
		for (int i = 0; i < s.length(); i++) {
			int c = s.charAt(i) - 'a';
			if (n.pref[c] == null) {
				n.pref[c] = new Node();
			}
			n = n.pref[c];
			n.count++;
		}
	}
	
	public int find (String pref) {
		Node n = root;
		for (int i = 0; i < pref.length(); i++) {
			int  c = pref.charAt(i) - 'a';
			if (n.pref[c] == null) {
				return 0;
			}
			n = n.pref[c];
		}
		return n.count;
	}
}

public class Solution {

	public static void main(String[] args) {
		Trie trie = new Trie();
		
		Scanner in = new Scanner(System.in);
		int t = in.nextInt();
		in.nextLine();
		for (int i = 0; i < t; i++) {
			String[] s = in.nextLine().split(" ");
			if ("add".equals(s[0])) {
				trie.add(s[1]);
			}
			if ("find".equals(s[0])) {
				System.out.println(trie.find(s[1]));
			}
		}
		in.close();
	}

}
