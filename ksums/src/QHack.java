import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class QHack {

	public static void main(String[] args) {
		try (Scanner in = new Scanner(new File("test.txt"))) {
			System.out.println(in.nextInt());
			System.out.println(in.nextInt());
			System.out.println(in.nextInt());
			System.out.println(in.nextLine());
			System.out.println(in.nextLine());
			System.out.println(in.nextInt());
			System.out.println(in.nextInt());
			System.out.println(in.nextLine());
			System.out.println(in.nextLine());
		}
		catch (FileNotFoundException e) {
			
		}

	}

}
