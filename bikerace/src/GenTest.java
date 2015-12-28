import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class GenTest {

	public static void main(String[] args) {
		Random r = new Random();
		try {
			PrintWriter pr = new PrintWriter("test1.txt");
			pr.println("250 250 250");
			for (int i = 0; i < 500; i++) {
				pr.println(String.format("%s %s", r.nextInt(10000000),r.nextInt(10000000)));
			}
			pr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
