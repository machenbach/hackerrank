import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Collectors;

class AddKey {
	public long sum;
	public List<Long> p;
	public int d;
	
	public AddKey(long sum, List<Long> p, int d) {
		this.sum = sum;
		this.p = new LinkedList<>(p);
		this.d = d;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + d;
		result = prime * result + ((p == null) ? 0 : p.hashCode());
		result = prime * result + (int) (sum ^ (sum >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddKey other = (AddKey) obj;
		if (d != other.d)
			return false;
		if (p == null) {
			if (other.p != null)
				return false;
		} else if (!p.equals(other.p))
			return false;
		if (sum != other.sum)
			return false;
		return true;
	}
	
}

public class Solution {
	int N, K;
	List<Long> sums;
	Map<AddKey, Integer> sumCount; 
	
	public Solution(int N, int K, String s) {
		this.N = N;
		this.K = K;
		Scanner in = new Scanner(s);
		sums = new ArrayList<>();
		sumCount = new HashMap<>();
		
		while (in.hasNextLong()) {
			sums.add(in.nextLong());
		}
		in.close();
		Collections.sort(sums);
		
	}
	
	// count the number of ways given the elements in p to add to sum, with d numbers
	int countSums(long sum, List<Long> p, int d) {
		AddKey key = new AddKey(sum, p, d);
		if (sumCount.containsKey(key)) {
			return sumCount.get(key);
		}
		if (sum < 0) {
			return 0;
		}
		if (sum == 0) {
			return 1;
		}
		
		if (p.size() <= d) {
			int tot = 0;
			long psum = p.stream().mapToLong(l -> l).sum();
			if (psum == sum) {
				tot = 1;
			}
			return tot;
		}
		int tot = 0;
		for (int i = 0; i < p.size(); i++) {
			long l = p.get(i);
			List<Long> np = new ArrayList<Long>(p);
			np.remove(i);			
			tot += (countSums(sum - l, np, d - 1) + countSums(sum, np, d));
		}
		sumCount.put(key, tot);
		return tot;
	}
	
	
	List<Long> possibleSequence() {
		return sums.stream().filter(l -> l % K == 0).map(l -> l / K).collect(Collectors.toList());
	}
	
	public List<Long> solve() {
		List<Long> a = possibleSequence();
		if (a.size() == N) {
			return a;
		}
		
		for (int i = 0; i < a.size(); i++) {
			long s = a.get(i) * K;
			int cnt = countSums(s, a, K);
			for (int c = 0; c < cnt; c++) {
				a.remove(i);
			}
			if (cnt != 0 && s == a.get(i) * K) {
				i++;
			}
		}
		return a;
	}
	

	public static void main(String[] args) {
		int T;
		Solution s;
		Scanner in = new Scanner(System.in);
		T = in.nextInt();
		for (int t = 0; t < T; t++) {
			int N = in.nextInt();
			int K = in.nextInt();
			in.nextLine();
			s = new Solution(N, K, in.nextLine());
			for (long l : s.solve()) {
				System.out.print(l + " ");
			}
			System.out.println();
		}
		in.close();

	}

}
