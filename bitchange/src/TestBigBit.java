import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class TestBigBit {
	
	int n;
	BigInteger a;
	BigInteger b;
	BigInteger c;
	BigBit abb;
	BigBit bbb;
	BigBit cbb;
	
	Random r;
	

	@Before
	public void setUp() throws Exception {
		r = new Random();
		n = r.nextInt(100000);
		a = new BigInteger(n, r);
		b = new BigInteger(n, r);
		c = a.add(b);
		abb = new BigBit(n, a);
		bbb = new BigBit(n, b);
		cbb = new BigBit(abb, bbb);
		
	}
	
	@Test
	public void baseTest() {
		Assert.assertTrue("A fail", a.toString(16).equals(abb.toString()));
		Assert.assertTrue("B fail", b.toString(16).equals(bbb.toString()));
		Assert.assertTrue("C fail", c.toString(16).equals(cbb.toString()));
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
		for (int i = 0; i < 100; i++) {
			int idx = r.nextInt(n);
			abb.setBit(idx, 0);
			a = a.clearBit(idx);
			if (!a.toString(16).equals(abb.toString())) {
				System.out.println(a.testBit(idx));
				System.out.println(abb.testBit(idx));
				System.out.println(a.toString(16));
				System.out.println(abb.toString());
			}
			Assert.assertTrue(String.format("Failed %d %d ", idx, idx>>5), (a.toString(16).equals(abb.toString())));
		}
	}

	@Test 
	public void TestSetBit() {
		for (int i = 0; i < 100; i++) {
			int idx = r.nextInt(n);
			abb.setBit(idx, 1);
			a = a.setBit(idx);
			if (!a.toString(16).equals(abb.toString())) {
				System.out.println(a.toString(16));
				System.out.println(abb.toString());
			}
			Assert.assertTrue("Failed a " + idx, (a.toString(16).equals(abb.toString())));
		}
	}
	
	@Test 
	public void TestClrAndSumBit() {
		for (int i = 0; i < 1000; i++) {
			int idx = r.nextInt(n);
			if (r.nextBoolean()) {
				abb.setBit(idx, 0);
				a = a.clearBit(idx);
			}
			else {
				bbb.setBit(idx, 0);
				b = b.clearBit(idx);
			}
			c = a.add(b);
			cbb.updateSum(idx);
			if (!c.toString(16).equals(cbb.toString())) {
					System.out.println(
							String.format("Error: %d, %d, %d",idx, idx/32, idx % 32));
				Assert.fail();
			}
		}
	}

	@Test 
	public void TestSetAndSumBit() {
		List<String> errors = new LinkedList<>();
		for (int i = 0; i < 1000; i++) {
			int idx = r.nextInt(n);
			if (r.nextBoolean()) {
				abb.setBit(idx, 1);
				a = a.setBit(idx);
			}
			else {
				bbb.setBit(idx, 1);
				b = b.setBit(idx);
			}
			c = a.add(b);
			cbb.updateSum(idx);
			if (!c.toString(16).equals(cbb.toString())) {
				errors.add(String.format("idx %d, %x", idx, idx));
			}
		}
		Assert.assertTrue(errors.isEmpty());
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
