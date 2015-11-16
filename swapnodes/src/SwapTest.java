import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;


public class SwapTest {
	
	public void doTheTest(String testIn) {
		InputStream in = new ByteArrayInputStream(testIn.getBytes());
		System.setIn(in);
		Solution.main(null);
	}

String test1 = 
		"	3" +  
		"	2 3" +
		"	-1 -1" +
		"	-1 -1" +
		"	2" +
		"	1" +
		"	1";

String test2 = 
			" 5" +
			" 2 3" +
			" -1 4" +
			" -1 5" +
			" -1 -1" +
			" -1 -1" +
			" 1" +
			" 2";

String test3 =
			" 11" +
			" 2 3 " +
			" 4 -1 " +
			" 5 -1 " +
			" 6 -1 " +
			" 7 8 " +
			" -1 9 " +
			" -1 -1 " +
			" 10 11 " +
			" -1 -1 " +
			" -1 -1 " +
			" -1 -1 " +
			" 2 " +
			" 2 " +
			"4";
	@Test
	public void test1() {
		doTheTest(test1);
	}
	
	@Test
	public void test2() {
		doTheTest(test2);
	}
	
	@Test
	public void test3() {
		doTheTest(test3);
	}

}
