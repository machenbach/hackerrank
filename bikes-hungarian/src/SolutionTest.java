import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;

public class SolutionTest {

	@Test
	public void test() throws FileNotFoundException {
		System.setIn(new FileInputStream("test.txt"));
		Solution.main(null);
	}
/*
	@Test
	public void test1() throws FileNotFoundException {
		System.setIn(new FileInputStream("test1.txt"));
		Solution.main(null);
	}
*/
	@Test
	public void test2() throws FileNotFoundException {
		System.setIn(new FileInputStream("test2.txt"));
		Solution.main(null);
	}

	@Test
	public void test4() throws FileNotFoundException {
		System.setIn(new FileInputStream("test4.txt"));
		Solution.main(null);
	}

	@Test
	public void test6() throws FileNotFoundException {
		System.setIn(new FileInputStream("test6.txt"));
		Solution.main(null);
	}
}
