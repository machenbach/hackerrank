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
		primes = IntStream.of(2, 3, 5, 7, 11, 13).boxed().collect(Collectors.toSet());
	}
	
	int grundy(int num) {
		if (g[num] >= 0) {
			return g[num];
		}
		
		Set<Integer> n = primes.stream().map(x -> num - x).filter(x -> x >= 0).collect(Collectors.toSet());
		Set<Integer> r = IntStream.range(0,  num+1).boxed().collect(Collectors.toSet());
		for (int i : n) {
			r.remove(grundy(i));
		}
		g[num] = Collections.min(r);
		return g[num];
	}

	

	public static void main(String[] args) {
		Grundy g = new Grundy();
		for (int i = 0; i <= 200; i++) {
			System.out.println(String.format("%3d: %3d", i, g.grundy(i)));
		}
	}

}
