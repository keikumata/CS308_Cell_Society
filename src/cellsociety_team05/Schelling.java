package cellsociety_team05;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class Schelling extends Sim{
	private int threshold;
	private HashMap<Pair, Pair> myMap = new HashMap<>();
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

				int cellState = map[row][col]; //blue = 1, red = 2, empty = 0;	
				boolean satisfied = computeNeighbourhood(map, row, col, cellState);
				if (!satisfied) 
					updateState(row, col, tempMap, cellState);
//				this.map = tempMap;
			}
		}
		this.map = tempMap;
	}

	
	public void updateState(int row, int col, int[][] tempMap, int cellState) {
		tempMap[row][col] = 0;
		ArrayList<Pair> emptyCells = new ArrayList<>();
		Random randomGenerator = new Random();
		if (cellState!=0) {
			for (int r = 0; r < map.length; r++) {
				for (int c = 0; c < map.length; c++) {
					if (r!=row && c!= col && tempMap[r][c]==0) {
						emptyCells.add(new Pair(r,c));
					}
				}
			}
			Pair empty = emptyCells.get(randomGenerator.nextInt(emptyCells.size()-1));
			myMap.put(new Pair(row,col), empty);
			tempMap[empty.r][empty.c] = cellState;
		}
	}
	public HashMap<Pair,Pair> getMap() {
		return myMap;
	}
	public boolean computeNeighbourhood(int[][] map, int row, int col, int cellState) {
		int same = 0; 
		int total = 0;
		if (cellState!=0) {
			for (int r = -1; r <= 1; r++ ) {
				for (int c = -1; c <= 1; c++ ) {
					if ((row+r>=0 && row+r<map.length)&& (col+c >= 0 && col+c < map.length) && !(r==0 && c==0)) {
						if (map[row+r][col+c] == cellState) {
							same++;
						}
						if (map[row+r][col+c]!=0) {
							total++;
						}
					}
				}
			}
		}
		else {
			return true;
		}
		return total!=0 && (same*100/total) >= threshold;
	}

}
