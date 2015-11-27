import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {
	
	static long distance(String s) {
		byte[] bytes = s.getBytes();
		int len = s.length();
		int n = Math.floorDiv(len, 2);
		long tot = 0;
		for (int i = 0; i < n; i++) {
			tot += Math.abs(bytes[i] - bytes[len - i - 1]);
		}
		return tot;
	}

	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			int t = Integer.parseInt(br.readLine());
			for (int i = 0; i < t; i++) {
				System.out.println(distance(br.readLine()));
			}
			br.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
