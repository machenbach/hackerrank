package grundynumbers;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Grundy {
	int[] g;
	Set<Integer> primes;
	
	public Grundy() {
		g = new int[1000];
		Arrays.fill(g, -1);
	}
	
	int grundy(int num) {
		if (g[num] < 0) {
			Set<Integer> n = IntStream.of(2, 3, 5, 7, 11, 13).
								filter(x -> num >= x).
								map(x -> grundy(num - x)).
								boxed().collect(Collectors.toSet());
			g[num] = n.isEmpty() ? 0 : IntStream.range(0,  Collections.max(n) + 2).filter(x -> !n.contains(x)).min().getAsInt();
		}
		return g[num];
	}

	

	public static void main(String[] args) {
		Grundy g = new Grundy();
		for (int i = 0; i <= 200; i++) {
			System.out.println(String.format("%3d: %3d", i, g.grundy(i)));
		}
	}

}
