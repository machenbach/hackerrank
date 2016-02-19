import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

public class SolutionTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test0() throws FileNotFoundException {
		System.setIn(new FileInputStream("test0.txt"));
		Solution.main(null);
		
	}

	@Test
	public void test1() throws FileNotFoundException {
		System.setIn(new FileInputStream("test1.txt"));
		Solution.main(null);
		
	}

	@Test
	public void test3() throws FileNotFoundException {
		System.setIn(new FileInputStream("test3.txt"));
		Solution.main(null);
		
	}
}
