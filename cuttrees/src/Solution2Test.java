import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

public class Solution2Test {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws FileNotFoundException {
		System.setIn(new FileInputStream("test.txt"));
		Solution2.main(null);
	}

	@Test
	public void test2() throws FileNotFoundException {
		System.setIn(new FileInputStream("test2.txt"));
		Solution2.main(null);
	}
	@Test
	public void testh2() throws FileNotFoundException {
		System.setIn(new FileInputStream("testh2.txt"));
		Solution2.main(null);
	}
	@Test
	public void test3() throws FileNotFoundException {
		System.setIn(new FileInputStream("testh6.txt"));
		Solution2.main(null);
	}

}
