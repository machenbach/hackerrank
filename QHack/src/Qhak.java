import java.math.BigInteger;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;


public class Qhak {

	public static void main(String[] args) {
		Scanner in = new Scanner("101010 110011");
		BigInteger b1 = in.nextBigInteger(2);
		BigInteger b2 = in.nextBigInteger(2);
		in.close();
		System.out.println(b1.toString(2));
		System.out.println(b2.toString(2));
		System.out.println(b1.or(b2).toString(2));
		System.out.println(b1.or(b2).bitCount());
		
	}
}
