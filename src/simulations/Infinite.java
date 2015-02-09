package simulations;

import java.util.HashMap;

import utility.MapCopier;
import utility.Pair;

public class Infinite {

	private int[][] m;

	// we have to store hasmap cells somewhere, because otherwise the
	// cells offscreen disapear, so this needs to be saved somewhere.
	public Infinite(int[][] matrix, HashMap<Pair, Integer> cells, int xDt,
			int yDt) {

		// for example North West movement (up and left) is xDt = -1, yDt = +1
		for (Pair cell : cells.keySet()) {
			cell.r += xDt;
			cell.c += yDt;
		}

		m = MapCopier.copyOfArray(matrix);

		// we assume top left square is (0, 0) and bottom right square is (row,
		// col) - this shouldnt matter.
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {

				m[i][j] = cells.get(new Pair(i, j));
			}
		}

		// return m;
	}
}
