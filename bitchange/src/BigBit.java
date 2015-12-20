import java.math.BigInteger;


public class BigBit {
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
			int places = 0;
			for (int i = bdx; i < byteCnt; i++) {
				long k = s1.bits[i] + s2.bits[i] + carry;
				bits[i] = (k & 0xffffffffl);
				carry = k >> 32;
				if (carry == 0 && places > 3) {
					break;
				}
				places++;
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
