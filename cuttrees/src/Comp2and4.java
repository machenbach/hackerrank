import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class Comp2and4 {

	long s2count;
	long s4count;
	@Before
	public void setUp() throws FileNotFoundException {
		Scanner s2in = new Scanner(new File("test2.txt"));
		int n = s2in.nextInt();
		int k = s2in.nextInt();
		
		Solution2 s2 = new Solution2(n, k);
		s2.init(s2in);
		s2in.close();
		
		
		Scanner s4in = new Scanner(new File("test2.txt"));
		s4in.nextInt();
		s4in.nextInt();
		Solution4 s4 = new Solution4(n, k);
		s4.init(s4in);
		s4in.close();
		
		s2count = s2.count();
		s4count = s4.treeCount();
	}
	
	
	Set<Set<Integer>> cst(Set<Tree.Subtree> sts) {
		Set<Set<Integer>> res = new HashSet<>();
		return res;
	}

	@Test
	public void test() throws FileNotFoundException {
		System.out.println(s2count);
		System.out.println(s4count);
	}
	
}
