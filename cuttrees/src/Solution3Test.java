import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class Solution3Test {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws FileNotFoundException {
		System.setIn(new FileInputStream("test.txt"));
		Solution3.main(null);
	}

	@Test
	public void test2() throws FileNotFoundException {
		System.setIn(new FileInputStream("test2.txt"));
		Solution3.main(null);
	}
	@Test
	public void test3() throws FileNotFoundException {
		System.setIn(new FileInputStream("test3.txt"));
		Solution3.main(null);
	}
	@Test
	public void test4() throws FileNotFoundException {
		System.setIn(new FileInputStream("test4.txt"));
		Solution3.main(null);
	}

}
