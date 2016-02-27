import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class GenTest {

	public static void main(String[] args) {
		try (PrintWriter out = new PrintWriter("testx.txt")) {
			int n = 1000000;
			out.println(n);
			Random r = new Random();
			for (int i = 0; i < n; i++) {
				out.print(r.nextInt(1000000000) + " ");
			}
			out.println();
			out.close();
		} catch (FileNotFoundException e) {
			
		}

	}

}
