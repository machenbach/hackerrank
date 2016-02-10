import org.junit.Before;
import org.junit.Test;

public class SubtreeTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		Subtree st = new Subtree(1);
		for (int i:st) {
			System.out.println(i);
		}
	}
	@Test
	public void test2() {
		Subtree st = new Subtree(1);
		for(int i = 1; i <= 10; i++) {
			st = new Subtree(i, st);
		}
		for (int i:st) {
			System.out.println(i);
		}
	}

}
