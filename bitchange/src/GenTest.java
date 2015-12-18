import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class GenTest {
	static String rand(Random r, int n) {
		StringBuffer sb = new StringBuffer(n);
		for (int i = 0; i < n; i++) {
			sb.append(r.nextInt(2));
		}
		return sb.toString();
	}
	
	static String cmd(Random r, int n) {
		String s = "";
		int cmd = r.nextInt(3);
		switch(cmd) {
		case 0:
			s = String.format("set_a %s %s", r.nextInt(n), r.nextInt(2));
			break;
		case 1:
			s = String.format("set_b %s %s", r.nextInt(n), r.nextInt(2));
			break;
			
		case 2:
			s = String.format("get_c %s", r.nextInt(n));
		}
		return s;
	}

	public static void main(String[] args) {
		int n = 100000;
		int q = 500000;
		Random r = new Random();
		try {
			PrintWriter pr = new PrintWriter("test1.txt");
			pr.println(String.format("%s %s", n, q));
			pr.println(rand(r, n));
			pr.println(rand(r, n));
			for (int i = 0; i < q; i++) {
				pr.println(cmd(r, n));
			}
			pr.close();
		} catch (FileNotFoundException e) {
			System.out.println("Bleh");
		}
		
		
		

	}

}
