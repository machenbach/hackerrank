

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Solution {
	int n, m, k;
	String[] map;
	
	public void init () throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Scanner in = new Scanner(br.readLine());
		n = in.nextInt();
		m = in.nextInt();
		k = in.nextInt();
		in.close();
		map = new String[n];
		for (int i = 0; i < n; i++) {
			map[i] = br.readLine();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
