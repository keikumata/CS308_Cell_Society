package cellsociety_team05;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Schelling extends Sim{
	private int threshold;
	public Schelling (int game, int size, int delay, List<Integer> params) {
		super(game, size, delay, params);
		threshold = params.get(2); // 3rd parameter
	}

	// update sim.setCell /getData
	public void nextGen(){
		int[][] map = this.map;

		int[][] tempMap = copyOfArray(map);

		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map.length; col++) {

				int cellState = map[row][col]; //red = 1, blue = 2, empty = 0;	
				boolean satisfied = computeNeighbourhood(map, row, col, cellState);
				if (!satisfied) 
					updateState(row, col, tempMap, cellState);
			}
		}
		map = tempMap;
	}

	public class Pair {
		int r; int c;
		public Pair(int row, int col) {
			r = row;
			c = col;
		}
	}
	public void updateState(int row, int col, int[][] tempMap, int cellState) {
		tempMap[row][col] = 0;
		ArrayList<Pair> emptyCells = new ArrayList<>();
		Random randomGenerator = new Random(1234);
		if (cellState!=0) {
			for (int r = 0; row < map.length; r++) {
				for (int c = 0; col < map.length; c++) {
					if (r!=row && c!=col && tempMap[r][c]==0) {
						emptyCells.add(new Pair(r,c));
					}
				}
			}
			Pair empty = emptyCells.get(randomGenerator.nextInt(emptyCells.size()-1));
			tempMap[empty.r][empty.c] = cellState;
		}
	}

	public boolean computeNeighbourhood(int[][] map, int row, int col, int cellState) {

		boolean satisfied = false;
		int same = 0; int total=0;

		for (int r = -1; r <= 1; r++ ) {
			for (int c = -1; c <= 1; c++ ) {

				if ( (row+r >= 0 && row+r <= map.length) && ( col+c >= 0 && col+c <= map.length) 
						&& !(r==0 && c==0) && map[row + r][col + c]==cellState) {
					same++;
				}
				if ((row+r >= 0 && row+r <= map.length) && ( col+c >= 0 && col+c <= map.length) 
						&& !(r==0 && c==0) && map[row+r][col+c]!=0) {
					total++;
				}
			}

		}
		if (same/total > threshold) satisfied = true;
		return satisfied;
	}

}
