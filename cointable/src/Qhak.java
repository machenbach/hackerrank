
public class Qhak {
	public static void main(String[] args) {
		byte[][] b;
		b = new byte[2][4];
		b[0] = "abcd".getBytes();
		b[1] = "efgh".getBytes();
		b[0][0] = '*';
		
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print((char)b[i][j]);
			}
		}
		
	}

}
