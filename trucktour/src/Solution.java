import java.util.Scanner;

public class Solution {
	
	class Station {
		// don't bother with getters and setters
		public int id;
		public int gas;
		public int distance;
		public Station next;
		
		public Station(int id, int gas, int distance) {
			this.id = id;
			this.gas = gas;
			this.distance = distance;
			next = null;
		}
	}
	
	static class CircList {
		Station head = null;
		Station tail = null;
		
		public void add(Station n) {
			if (head == null) {
				head = n;
				tail = n;
			}
			else {
				tail.next = n;
				tail = n;
			}
			n.next = null;
		}
		
		public Station getHead() {
			return head;
		}
		
		public Station getNext (Station n) {
			if (n.next != null) {
				return n.next;
			}
			else {
				return head;
			}
		}
	}
	int N;
	public Solution (int n) {
		N = n; 
	}
	
	public CircList initList(Scanner in) {
		CircList list = new CircList();
		for (int i = 0; i < N; i++) {
			Station n = new Station(i, in.nextInt(), in.nextInt());
			list.add(n);
		}
		return list;
	}
	
	long totalGas = 0;
	long totalDistance = 0;
	Station start;
	Station last;
	
	public void fixStart(CircList list) {
		// move the start point to where we have enough gas
		while (totalGas < totalDistance) {
			totalDistance -= start.distance;
			totalGas -= start.gas;
			start = list.getNext(start);
		}
		// and set the next station
		totalDistance += start.distance;
		totalGas += start.gas;
		last = list.getNext(start);
	}
	
	public int solve(CircList list) {
		start = list.getHead();
		totalGas = start.gas;
		totalDistance = start.distance;
		last = list.getNext(start);
		long totalNodes = 0;
		
		// while processing this algorithm, we must keep last ahead of start until the very end
		while (start != last) {
			if (totalNodes++ > 4 * N) {
				// we've gone around the circle twice, and not found a start.
				return -1;
			}
			if (totalGas < totalDistance) {
				fixStart(list);
			}
			else {
				totalGas += last.gas;
				totalDistance += last.distance;
				last = list.getNext(last);
			}
		}
		return start.id;
	}
	

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Solution s = new Solution(in.nextInt());
		CircList list = s.initList(in);
		in.close();
		System.out.println(s.solve(list));
		

	}

}
