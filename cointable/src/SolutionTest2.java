import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

public class SolutionTest2 {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test0() throws FileNotFoundException {
		System.setIn(new FileInputStream("test0.txt"));
		Solution2.main(null);
		
	}

	@Test
	public void test1() throws FileNotFoundException {
		System.setIn(new FileInputStream("test1.txt"));
		Solution2.main(null);
		
	}

	@Test
	public void test3() throws FileNotFoundException {
		System.setIn(new FileInputStream("test3.txt"));
		Solution2.main(null);
		
	}
	@Test
	public void test10() throws FileNotFoundException {
		System.setIn(new FileInputStream("test10.txt"));
		Solution2.main(null);
		
	}
}
