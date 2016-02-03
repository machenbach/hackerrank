import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Gentest {
	static void test3() {
		try (PrintWriter pr = new PrintWriter("test3.txt")) {
			pr.println("50 25");
			for (int i = 2; i <= 50; i++) {
				pr.println(i + " " + i/2);
			}
			pr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void test4() {
		try (PrintWriter pr = new PrintWriter("test4.txt")) {
			pr.println("50 25");
			for (int i = 2; i <= 50; i++) {
				pr.println(i + " " + (i-1));
			}
			pr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		test4();
	}

}
