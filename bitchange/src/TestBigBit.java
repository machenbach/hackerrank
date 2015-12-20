import java.math.BigInteger;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class TestBigBit {
	
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
	public void testStr() {
		for (int i = 0; i < 10000; i++) {
			BigInteger a = BigInteger.valueOf(i);
			BigBit b = new BigBit(32, a);
			Assert.assertTrue(String.format("%s != %s", a.toString(16), b.toString()), a.toString(16).equals(b.toString()));
		}
	}
	
	@Test
	public void testStr2() {
		for (long i = Integer.MAX_VALUE; i < Integer.MAX_VALUE + 10000; i++) {
			BigInteger a = BigInteger.valueOf(i);
			BigBit b = new BigBit(32, a);
			Assert.assertTrue(String.format("%s != %s", a.toString(16), b.toString()), a.toString(16).equals(b.toString()));
		}
	}
	@Test
	public void testBigStr() {
		Assert.assertTrue(String.format("%s != %s", a.toString(16), abb.toString()), a.toString(16).equals(abb.toString()));
		Assert.assertTrue(String.format("%s != %s", b.toString(16), bbb.toString()), b.toString(16).equals(bbb.toString()));
	}
	
	@Test
	public void testTestBit() {
		for (int i = 0; i < 100000; i++) {
			int idx = r.nextInt(n - 1);
			Assert.assertTrue(String.format("failed n = %d %d, %x", n, idx, idx), a.testBit(idx) == (abb.testBit(idx) == 1));
			Assert.assertTrue(String.format("failed n = %d %d, %x", n, idx, idx), b.testBit(idx) == (bbb.testBit(idx) == 1));
		}
	}
	

	@Test 
	public void TestClrBit() {
		for (int i = 0; i < 100000; i++) {
			int idx = r.nextInt(n);
			abb.setBit(idx, 0);
			Assert.assertTrue("Failed a " + i, (abb.testBit(idx) == 0));
		}
	}

	@Test 
	public void TestSetBit() {
		for (int i = 0; i < 100000; i++) {
			int idx = r.nextInt(n);
			abb.setBit(idx, 1);
			Assert.assertTrue("Failed a " + i, (abb.testBit(idx) == 1));
		}
	}
	
	@Test
	public void testSum() {
		for (int i = 0; i < 1000; i++) {
			for (int j = i; j < 1000; j++) {
				BigInteger a = BigInteger.valueOf(i);
				BigInteger b = BigInteger.valueOf(j);
				BigInteger c = a.add(b);
				
				BigBit ab = new BigBit(32, a);
				BigBit bb = new BigBit(32, b);
				ab.addIn(bb);
				Assert.assertTrue(String.format("(%s, %s) %s != %s", i, j, c.toString(16), ab.toString()), 
						c.toString(16).equals(ab.toString()));
				
			}
		}
	}
	
	@Test
	public void testSum2() {
		for (int i = 0; i < 1000; i++) {
			for (int j = i; j < 1000; j++) {
				BigInteger a = BigInteger.valueOf(i);
				BigInteger b = BigInteger.valueOf(j);
				BigInteger c = a.add(b);
				
				BigBit ab = new BigBit(32, a);
				BigBit bb = new BigBit(32, b);
				
				BigBit cb = new BigBit(ab, bb);
				Assert.assertTrue(String.format("(%s, %s) %s != %s", i, j, c.toString(16), cb.toString()), 
						c.toString(16).equals(cb.toString()));
				
			}
		}
	}
	@Test
	public void bigSum() {
		BigInteger c = a.add(b);
		abb.addIn(bbb);
		Assert.assertTrue(String.format("%s != %s", c.toString(16), abb.toString()), 
				c.toString(16).equals(abb.toString()));
	}

}
