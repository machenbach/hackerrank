

import java.math.BigInteger;
import java.util.Scanner;

public class Solution {

	BigInteger a;
	BigInteger b;
	BigInteger c;
	
	boolean cDirty = true;
	
	public void init(Scanner in) {
		a = in.nextBigInteger(2);
		b = in.nextBigInteger(2);
	}
	
	void changeBit(BigInteger v, int idx, int bit) {
		if (bit == 0) { v.clearBit(idx); }
		else { v.setBit(idx); }
	}
	
	public void parse(Scanner in) {
		String cmd = in.next();
		int idx = in.nextInt();
		int bit = 0;
		
		switch (cmd) {
			case "set_a":
				bit = in.nextInt();
				if (bit == 0) { 
					a = a.clearBit(idx); }
				else { 
					a = a.setBit(idx); }
				cDirty = true;
				break;
				
			case "set_b":
				bit = in.nextInt();
				if (bit == 0) { 
					b = b.clearBit(idx); }
				else { 
					b = b.setBit(idx); }
				cDirty = true;
				break;
				
			case "get_c":
				if (cDirty) {
					c = a.add(b);
					cDirty = false;
				}
				System.out.print(c.testBit(idx) ? "1" : "0");
		}
		
	}
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int q = in.nextInt();
		Solution s = new Solution();
		s.init(in);
		for (int i = 0; i < q; i++) {
			s.parse(in);
		}
		in.close();
		System.out.println();

	}

}
