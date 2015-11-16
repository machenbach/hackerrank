import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;


public class TGen {

	public static void main(String[] args) throws FileNotFoundException {
		PrintWriter pr = new PrintWriter("test1.txt");
		pr.println("100000 50000");
		Random r = new Random();
		for (int i = 0; i < 100000; i++) {
			int n =  r.nextInt(10000000);
			pr.print(String.format("%s ",n));
		}
		pr.println();
		pr.close();

	}

}
