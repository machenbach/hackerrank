

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public class Solution {
	
	class BigBit {
		int bitSize;
		int byteCnt;
		byte[] bits;
		
		public BigBit(int bitSize, BigInteger val) {
			this.bitSize = bitSize;
			byteCnt = (bitSize >> 8) + 1;
			bits = Arrays.copyOf(val.toByteArray(), byteCnt);
		}
		
		public BigBit(BigBit b) {
			bitSize = b.bitSize;
			byteCnt = b.byteCnt;
			bits = b.bits.clone();
		}
		
		public void setBit(int idx, int bit) {
			int bdx = idx >> 8;
			int mask = 0x1 << (idx % 8);
			if (bit == 0) {
				mask = -1 ^ mask;
				bits[bdx] = (byte) (bits[bdx] & mask);
			}
			else {
				bits[bdx] = (byte) (bits[bdx] | mask);
			}
		}
		
		public int testBit(int idx) {
			int bdx = idx >> 8;
			int mask = 1 << (idx % 8);
			return (bits[bdx] & mask) == 0 ? 0 : 1;
		}
		
		public void addTo (BigBit o) {
			int lastCarry = 0;
			int nextCarry = 0;
			for (int i = 0; i < byteCnt; i++) {
				nextCarry = (bits[i] & o.bits[i]) >> 7;
				bits[i] += (o.bits[i] + lastCarry);
				lastCarry = nextCarry;
			}
		}
	}

	BigBit a;
	BigBit b;
	BigBit c;
	
	boolean cDirty = true;
	
	public void init(int n, Scanner in) {
		a = new BigBit(n, in.nextBigInteger(2));
		b = new BigBit(n, in.nextBigInteger(2));
	}
	
	public void parse(Scanner in) {
		String cmd = in.next();
		int idx = in.nextInt();
		int bit = 0;
		
		switch (cmd) {
			case "set_a":
				bit = in.nextInt();
				a.setBit(idx, bit);;
				cDirty = true;
				break;
				
			case "set_b":
				bit = in.nextInt();
				b.setBit(idx, bit);
				cDirty = true;
				break;
				
			case "get_c":
				if (cDirty) {
					c = new BigBit(a);
					c.addTo(b);
					cDirty = false;
				}
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
		System.out.println();

	}

}
