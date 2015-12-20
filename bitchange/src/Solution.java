import java.util.Scanner;

public class Solution {



	BigBit a;
	BigBit b;
	BigBit c;
	
	public void init(int n, Scanner in) {
		a = new BigBit(n, in.nextBigInteger(2));
		b = new BigBit(n, in.nextBigInteger(2));
		c = new BigBit(a, b);
	}
	
	public void parse(Scanner in) {
		String cmd = in.next();
		int idx = in.nextInt();	
		int bit = 0;
		
		switch (cmd) {
			case "set_a":
				bit = in.nextInt();
				a.setBit(idx, bit);
				c.updateSum(idx);
				break;
				
			case "set_b":
				bit = in.nextInt();
				b.setBit(idx, bit);
				c.updateSum(idx);
				break;
				
			case "get_c":
				System.out.print(c.testBit(idx));
		}
		
	}
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int q = in.nextInt();
		Solution s = new Solution();
		s.init(n, in);
		for (int i = 0; i < q; i++) {
			s.parse(in);
		}
		in.close();

	}

}
