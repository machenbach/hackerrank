import java.io.StringBufferInputStream;

import org.junit.Test;

public class SolutionTest {

	@Test
	public void test() {
		System.setIn(new StringBufferInputStream("4"));
		Solution.main(null);
	}

}
