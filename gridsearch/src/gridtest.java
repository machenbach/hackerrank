import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Test;


public class gridtest {
	
	public void doTheTest(String fileName) throws FileNotFoundException {
		InputStream in = new FileInputStream(fileName);
		System.setIn(in);
		Solution.main(null);
	}

	
	@Test
	public void test2() throws FileNotFoundException {
		doTheTest("src/input.txt");
	}

}
