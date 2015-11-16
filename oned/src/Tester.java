
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;

public class Tester {

	@Test
	public void test() throws FileNotFoundException {
		System.setIn(new FileInputStream("test.txt"));
		Solution.main(null);
	}

}
