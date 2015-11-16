
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.Test;

public class LonelyTest {

	@Test
	public void test() {
		String testIn = "5 4 4 5 2 5";
		String testOut = "2\r\n";
		InputStream in = new ByteArrayInputStream(testIn.getBytes());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setIn(in);
		System.setOut(new PrintStream(out));
		Solution.main(null);
		assertTrue(testOut.equals(out.toString()));
	}

}
