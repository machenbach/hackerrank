import java.math.BigInteger;
import java.util.Scanner;

public class Solution {


	class BigBit {
			int bitSize;
			int byteCnt;
			long[] bits;
			
			BigBit s1;
			BigBit s2;
			
			public BigBit(int bitSize, BigInteger val) {
				this.bitSize = bitSize;
				byteCnt = (bitSize >> 5) + 1;
				bits = new long[byteCnt];
				BigInteger mask = BigInteger.valueOf(0xffffffffl);
				for (int i = 0; i < byteCnt; i++) {
					bits[i] = (val.and(mask)).longValue();
					val = val.shiftRight(32);
				}
			}
			
			public BigBit(int bitSize, String str) {
				this(bitSize, new BigInteger(str, 16));
			}
			
			public BigBit(BigBit b) {
				bitSize = b.bitSize;
				byteCnt = b.byteCnt;
				bits = b.bits.clone();
			}
			
			public BigBit(BigBit a, BigBit b) {
				this(a);
				s1 = a;
				s2 = b;
				addIn(b);
				
			}
			
			public void setBit(int idx, int bit) {
				int bdx = idx >> 5;
				long mask = 0x1l << (idx & 0x1f);
				if (bit == 0) {
					mask ^= 0xffffffffl;
					bits[bdx] &= mask;
				}
				else {
					bits[bdx] |= mask;
				}
			}
			
			public int testBit(int idx) {
				int bdx = idx >> 5;
				long mask = 1l << (idx & 0x1f);
				return (bits[bdx] & mask) == 0 ? 0 : 1;
			}
			
			public void addIn (BigBit o) {
				long carry = 0;
				for (int i = 0; i < byteCnt; i++) {
					long k = bits[i] + o.bits[i] + carry;
					bits[i] = (k & 0xffffffffl);
					carry = k >> 32;;
				}
			}
			
			public void updateSum (int idx) {
				int bdx = idx >> 5;
				long carry = 0;
				if (bdx > 0) {
					carry = (s1.bits[bdx - 1] + s2.bits[bdx - 1]) >> 32;
				}
				for (int i = bdx; i < byteCnt; i++) {
					long k = s1.bits[i] + s2.bits[i] + carry;
					carry = k >> 32;
					k &= 0xffffffffl;
					if (k == bits[i]) {
						break;
					}
					bits[i] = k;
				}
			}
			
			public String toString() {
				StringBuffer sb = new StringBuffer();
				for (int i = byteCnt - 1; i >= 0; i--) {
					sb.append(String.format("%08x", bits[i]));
				}
				while (sb.length() > 1 && sb.charAt(0) == '0') {
					sb.deleteCharAt(0);
				}
				
				if (sb.length() == 0) {
					sb.append("0");
				}
				return sb.toString();
			}
			
		}


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
