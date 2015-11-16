import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;


public class utreeTest {
	public void doTheTest(String testIn) {
		InputStream in = new ByteArrayInputStream(testIn.getBytes());
		System.setIn(in);
		Solution.main(null);
	}

	@Test
	public void test() {
		doTheTest("3 0 1 4");
	}

}
