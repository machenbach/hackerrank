import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class Comp2and3 {

	Set<Set<Integer>> subtrees2;
	Set<Set<Integer>> subtrees3;

	@Before
	public void setUp() throws FileNotFoundException {
		Scanner s2in = new Scanner(new File("test2.txt"));
		int n = s2in.nextInt();
		int k = s2in.nextInt();
		
		Solution2 s2 = new Solution2(n, k);
		s2.init(s2in);
		s2in.close();
		
		
		Scanner s3in = new Scanner(new File("test2.txt"));
		s3in.nextInt();
		s3in.nextInt();
		Solution3 s3 = new Solution3(n, k);
		s3.init(s3in);
		s3in.close();
		
		subtrees2 = cst(s2.subTrees());
		subtrees3 = s3.subtrees();
		
	}
	
	
	Set<Set<Integer>> cst(Set<Subtree> sts) {
		Set<Set<Integer>> res = new HashSet<>();
		return res;
	}

	@Test
	public void test() throws FileNotFoundException {
		System.out.println(subtrees2.size());
		System.out.println(subtrees3.size());
	}
	
	@Test
	public void diff23() throws FileNotFoundException {
		System.out.println("2 - 3");
		subtrees2.removeAll(subtrees3);
		for (Set<Integer> s : subtrees2) {
			System.out.println(s);
		}
	}

	@Test
	public void diff32() throws FileNotFoundException {
		System.out.println("3 - 2");
		subtrees3.removeAll(subtrees2);
		for (Set<Integer> s : subtrees3) {
			System.out.println(s);
		}
	}
}
