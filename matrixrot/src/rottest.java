import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Test;


public class rottest {
	public void doTheTest(String fileName) throws FileNotFoundException {
		InputStream in = new FileInputStream(fileName);
		System.setIn(in);
		Solution.main(null);
		System.out.println();
	}


	@Test
	public void test() throws FileNotFoundException {
		doTheTest("src/test.txt");
	}

	@Test
	public void test2() throws FileNotFoundException {
		doTheTest("src/test2.txt");
	}
	@Test
	public void test3() throws FileNotFoundException {
		doTheTest("src/test3.txt");
	}
	@Test
	public void test4() throws FileNotFoundException {
		doTheTest("src/test4.txt");
	}
}
