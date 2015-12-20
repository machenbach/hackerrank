

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Assert;
import org.junit.Test;


public class SolutionTest {
	
	boolean compare(String file1, String file2) {
		FileReader r1 = null;
		FileReader r2 = null;
		boolean ok = true;
		try {
			r1 = new FileReader(file1);
			r2 = new FileReader(file2);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int c1 = 0;
		int c2 = 0;
		long pos = 0;
		while (c1 != -1 && c2 != -1) {
			try {
				c1 = r1.read();
				c2 = r2.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (c1 != c2) {
				ok = false;
				System.out.println(String.format("Diff at %s: %c %c", pos, c1,c2));
			}
			pos++;
		}
		return ok;
	}

	@Test
	public void test() throws FileNotFoundException {
		System.setIn(new FileInputStream("test.txt"));
		Solution.main(null);
	}

	@Test
	public void test1() throws FileNotFoundException {
		System.setIn(new FileInputStream("test1.txt"));
		Solution.main(null);
	}
	@Test
	public void test2() throws FileNotFoundException {
		System.setIn(new FileInputStream("test2.txt"));
		Solution.main(null);
	}

	@Test
	public void test4() throws FileNotFoundException {
		PrintStream oldSys = System.out;
		System.setIn(new FileInputStream("test4.txt"));
		PrintStream pr = new PrintStream("try4.txt");
		System.setOut(pr);
		Solution.main(null);
		pr.close();
		System.setOut(oldSys);
		Assert.assertTrue(compare("answer4.txt", "try4.txt"));
	}

	
	@Test
	public void test6() throws FileNotFoundException {
		PrintStream oldSys = System.out;
		System.setIn(new FileInputStream("test6.txt"));
		PrintStream pr = new PrintStream("try6.txt");
		System.setOut(pr);
		Solution.main(null);
		pr.close();
		System.setOut(oldSys);
		Assert.assertTrue(compare("answer6.txt", "try6.txt"));
	}
}
