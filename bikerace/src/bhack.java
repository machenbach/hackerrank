import java.util.Arrays;

public class bhack {
	public static void main(String[] args) {
		int[] a = {1, 2, 4, 6, 8};
		System.out.println(Arrays.binarySearch(a, 2));
		System.out.println(Arrays.binarySearch(a, 3));
		System.out.println(Arrays.binarySearch(a, 5));
		System.out.println(Arrays.binarySearch(a, 9));
	}

}
