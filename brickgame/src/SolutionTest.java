import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;

public class SolutionTest {

	@Test
	public void test() throws FileNotFoundException {
		System.setIn(new FileInputStream("test.txt"));
		Solution.main(null);
	}

	@Test
	public void test2() throws FileNotFoundException {
		System.setIn(new FileInputStream("test10.txt"));
		Solution.main(null);
	}

	@Test
	public void test3() throws FileNotFoundException {
		System.setIn(new FileInputStream("test100.txt"));
		Solution.main(null);
	}

	@Test
	public void test4() throws FileNotFoundException {
		System.setIn(new FileInputStream("test1000.txt"));
		Solution.main(null);
	}
	
	@Test
	public void test5() throws FileNotFoundException {
		System.setIn(new FileInputStream("test10000.txt"));
		Solution.main(null);
	}

	@Test
	public void test6() throws FileNotFoundException {
		System.setIn(new FileInputStream("test100000.txt"));
		Solution.main(null);
	}



}
