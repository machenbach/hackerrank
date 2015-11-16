import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;


public class Tester {

	@Test
	public void test0() throws FileNotFoundException {
		long start = System.nanoTime();
		System.setIn(new FileInputStream("test.txt"));
		Solution.main(null);
		long end = System.nanoTime();
		System.out.println((end-start)/1000000);
	}

	@Test
	public void test() throws FileNotFoundException {
		long start = System.nanoTime();
		System.setIn(new FileInputStream("test1.txt"));
		Solution.main(null);
		long end = System.nanoTime();
		System.out.println((end-start)/1000000);
	}

}
