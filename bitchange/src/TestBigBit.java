import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class TestBigBit {
	class BigBit {
		int bitSize;
		int byteCnt;
		byte[] bits;
		
		public BigBit(int bitSize, BigInteger val) {
			this.bitSize = bitSize;
			byteCnt = (bitSize >> 3) + 1;
			bits = Arrays.copyOf(val.toByteArray(), byteCnt);
		}
		
		public BigBit(BigBit b) {
			bitSize = b.bitSize;
			byteCnt = b.byteCnt;
			bits = b.bits.clone();
		}
		
		public void setBit(int idx, int bit) {
			int bdx = idx >> 3;
			int mask = 0x1 << (idx % 8);
			if (bit == 0) {
				mask = -1 ^ mask;
				bits[bdx] = (byte) (bits[bdx] & mask);
			}
			else {
				bits[bdx] = (byte) (bits[bdx] | mask);
			}
		}
		
		public String toString() {
			StringBuffer sb = new StringBuffer();
			boolean firstZero = false;
			for (int i = byteCnt - 1; i >= 0; i--) {
				if (firstZero || bits[i] != 0) {
					firstZero = true;
					sb.append(Integer.toHexString(Byte.toUnsignedInt(bits[i])));
				}
			}
			if (sb.length() == 0) {
				sb.append("0");
			}
			return sb.toString();
		}
		
		public int testBit(int idx) {
			int bdx = idx >> 3;
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
	
	int n;
	BigInteger a;
	BigInteger b;
	BigBit abb;
	BigBit bbb;
	
	Random r;
	

	@Before
	public void setUp() throws Exception {
		r = new Random();
		n = r.nextInt(100000);
		a = new BigInteger(n, r);
		b = new BigInteger(n, r);
		abb = new BigBit(n, a);
		bbb = new BigBit(n, b);
		
	}
	
	@Test
	public void testTestBit() {
		for (int i = 0; i < 100000; i++) {
			int idx = r.nextInt(n);
			Assert.assertTrue("Failed a " + i, a.testBit(idx) == (abb.testBit(idx) == 1));
			Assert.assertTrue("Failed b " + i, b.testBit(idx) == (bbb.testBit(idx) == 1));
		}
	}

}
