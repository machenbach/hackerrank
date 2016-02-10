import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SubtreeTest {
	Tree tree;

	@Before
	public void setUp() throws Exception {
		tree = new Tree(10, new List[11]);
	}

	@Test
	public void test() {
		Tree.Subtree st = tree.subtree(1);
		for (int i:st) {
			System.out.println(i);
		}
	}
	@Test
	public void test2() {
		Tree.Subtree st = tree.subtree(1);
		for(int i = 1; i <= 10; i++) {
			st = tree.subtree(i, st);
		}
		for (int i:st) {
			System.out.println(i);
		}
	}

}
