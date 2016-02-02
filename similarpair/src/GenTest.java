import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class GenTest {
	final static int maxn = 100000;
	final static int t = 50000;
	
	public void LinearTree() {
		try (PrintWriter pr = new PrintWriter("test1.txt") ) {
			pr.println(maxn + " " + t);
			for (int i = 1; i < maxn; i++) {
				pr.println(i + " " + (i+1));
			}
			pr.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		GenTest g = new GenTest();
		g.LinearTree();

	}

}
