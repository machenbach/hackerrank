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
		for (Subtree st : sts) {
			res.add(new HashSet<>(st.subtree));
		}
		return res;
	}

	@Test
	public void test() throws FileNotFoundException {
		System.out.println(subtrees2.size());
		System.out.println(subtrees3.size());
	}
	
	@Test
	public void commonTrees() throws FileNotFoundException {
		subtrees2.retainAll(subtrees3);
		System.out.println(subtrees2.size());
	}
	
	@Test
	public void markdiff() throws FileNotFoundException {
		for (Set<Integer> s : subtrees2) {
			System.out.print(s);
			System.out.println(subtrees3.contains(s) ? "  <-- 3" : "");
		}
	}

}
