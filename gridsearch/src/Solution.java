import java.util.Scanner;


public class Solution {
	String[] grid;
	String[] pattern;
	
	boolean hasPatternInGrid(int r, int c) {
		if (r + pattern.length > grid.length) {
			return false;
		}
		if (c + pattern[0].length() > grid[0].length()) {
			return false;
		}
		for (int i = 1; i < pattern.length; i++) {
			//if (! grid[i + r].substring(c, pattern[i].length()).equals(pattern[i])) {
			 if (grid[i + r].indexOf(pattern[i], c) != c) {
				return false;
			}
		}
		return true;
	}
	
	
	public String hasPattern() {
		int c = -1;
		for (int r = 0; r < grid.length - pattern.length + 1; r++) {
			do {
				c = grid[r].indexOf(pattern[0], c + 1);
				if (c >= 0 && hasPatternInGrid(r, c)) {
					return "YES";
				}
			} while ( c >= 0);
		}
		return "NO";
	}
	
	String[] readGrid (Scanner in) {
		int gr = in.nextInt();
		int gc = in.nextInt();
		in.nextLine(); // skip past the eol still in the buffer
		String[] g = new String[gr];
		for (int i = 0; i < gr; i++) {
			g[i] = in.nextLine();
		}
		return g;
	}
	
	public Solution(Scanner in) {
		grid = readGrid(in);
		pattern = readGrid(in);
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int T = in.nextInt();
		for (int i = 0; i < T; i++) {
			Solution soln = new Solution(in);
			System.out.println(soln.hasPattern());
		}
	}

}
