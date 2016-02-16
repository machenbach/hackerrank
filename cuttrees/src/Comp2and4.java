import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

public class Comp2and4 {

	long s2count;
	long s4count;
	
	long setup(String fileName) throws FileNotFoundException {
		
		Scanner s4in = new Scanner(new File(fileName));
		int n = s4in.nextInt();
		int k = s4in.nextInt();
		
		Solution4 s4 = new Solution4(n, k);
		s4.init(s4in);
		s4in.close();
		
		return s4.treeCount();
	}
	
	

	@Test
	public void test() throws FileNotFoundException {
		System.out.println(setup("test.txt"));
	}
	
	@Test
	public void test2() throws FileNotFoundException {
		System.out.println(setup("test2.txt"));
	}
	
	@Test
	public void test3() throws FileNotFoundException {
		System.out.println(setup("test3.txt"));
	}
	
	@Test
	public void test4() throws FileNotFoundException {
		System.out.println(setup("test4.txt"));
	}
	
	@Test
	public void testh2() throws FileNotFoundException {
		System.out.println(setup("testh2.txt"));
	}
	
	@Test
	public void testh5() throws FileNotFoundException {
		System.out.println(setup("testh6.txt"));
	}
	
}
