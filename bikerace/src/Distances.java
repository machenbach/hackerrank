import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Distances {
	static class Coord implements Comparable<Coord> {
		public int x;
		public int y;
		
		public Coord (int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public long dist2 (Coord o) {
			long dx = x - o.x;
			long dy = y - o.y;
			return dx * dx + dy * dy;
		}

		@Override
		public int compareTo(Coord o) {
			if (x == o.x) {
				return Integer.compare(y, o.y);
			}
			return Integer.compare(x, o.x);
		}

		@Override
		public String toString() {
			return String.format("(%s, %s)", x, y);
		}
	}
	
	static void initArray(Coord[] a, int n, Scanner in) {
		for (int i = 0; i < n; i++) {
			a[i] = new Coord(in.nextInt(), in.nextInt());
		}
		Arrays.sort(a);
	}

	public static void main(String[] args) {
		String inName = "test6.txt";
		String outName = "test6.csv";
		try {
			Scanner in = new Scanner(new FileInputStream(inName));
			PrintWriter pr = new PrintWriter(outName);
			int nr = in.nextInt();
			int nb = in.nextInt();
			int k = in.nextInt();
			Coord[] riders = new Coord[nr];
			Coord[] bikes = new Coord[nb];
			initArray(riders, nr, in);
			initArray(bikes, nb, in);
			in.close();
			
			// header line:  empty cell, loc riders
			pr.print(", ");
			for (int i = 0; i < nr; i++) {
				pr.print(String.format("\"%s\",", riders[i]));
			}
			pr.println();
			
			// now for each row. coord bike
			for (int b = 0; b < nb; b++) {
				pr.print(String.format("\"%s\",", bikes[b]));
				for (int r = 0; r < nr; r++) {
					pr.print(String.format("%s, ", bikes[b].dist2(riders[r])));
				}
				pr.println();
			}
			pr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
