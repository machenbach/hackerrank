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
	public void testx() throws FileNotFoundException {
		System.setIn(new FileInputStream("testx.txt"));
		Solution.main(null);
	}
}
