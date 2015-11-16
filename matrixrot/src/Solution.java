import java.util.Scanner;




public class Solution {
	
	// Ring Matrix stores a matrix in a series of rings
	// Access to elements is standard x, y, but this maps to a ring and an offset
	
	class RingMatrix {
	
	
		int[][] matrix;
		int m, n;
		
		int rotation = 0;  // when we rotate, the offset
		
		public RingMatrix(int m, int n) {
			// number of rings
			this.m = m;
			this.n = n;
			
			int rings = Math.min(m, n) / 2;
			matrix = new int[rings][2 * (m + n - 2)];
		}
		
		public void set(int i, int j, int val) {
			matrix[ring(i, j)][offset(i, j)] = val;
		}
		
		public int get(int i, int j) {
			return matrix[ring(i, j)][offset(i, j)];
		}
		
		public void setRotation(int r) {
			rotation = r;
		}
	
		public int ring(int i, int j) {
			return ring(i, j, 0, m, n);
		}
		
		int ring(int i, int j, int ul, int m, int n) {
			if (i > ul && j > ul && i < n+ul-1 && j < m+ul-1) {
				return ring(i, j, ul+1, m-2, n-2);
			}
			else {
				return ul;
			}
		}
		
		public int offset(int i, int j) {
			return (offset(i, j, m, n) + rotation) % ringLen(ring(i,j));
		}
		
		int offset(int i, int j, int m, int n)  {
			int r = ring(i, j, 0, m, n);
			int mLen = m-r-1;
			int nLen = n-r-1;
			
			if (i == r) {
				return j-r;
			}
			if (j == mLen) {
				return mLen + i - 2 * r;
			}
			if (i == nLen) {
				return mLen + nLen + (mLen - j) - 2 * r; 
			}
			if (j == r) {
				return mLen + nLen + mLen + (nLen - i) - 3 * r;
			}
			return -1;
		}
		
		int ringLen (int r) {
			return 2 * (m + n - 4 * r -2);
		}
	}
	
	
	public void startit() {
		Scanner in = new Scanner(System.in);
		int m = in.nextInt();
		int n = in.nextInt();
		int rot = in.nextInt();
		
		RingMatrix r = new RingMatrix(n, m);

		for (int i = 0; i < m; i++) {
			for (int j =0; j < n; j++) {
				r.set(i, j, in.nextInt());
			}
		}
		in.close();
		
		r.setRotation(rot);
		for (int i = 0; i < m; i++) {
			for (int j =0; j < n; j++) {
				System.out.print(r.get(i, j)+ " ");
			}
			System.out.println();
		}
	}

	
	
	public static void main(String[] args) {
		new Solution().startit();
	}

}
