import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class GenTest {

	public static void main(String[] args) {
		Random r = new Random();
		for (int N = 10; N <= 100000; N *= 10) {
			try {
				PrintWriter pr = new PrintWriter(String.format("test%s.txt",N));
				pr.println(1);
				pr.println(N);
				for (int i = 0; i < N; i++) {
					pr.print(r.nextInt(1000000000) + " ");
				}
				pr.close();
			} catch (FileNotFoundException e) {
				System.out.println(N + "-- blah!");
			}
		}
	}

}
