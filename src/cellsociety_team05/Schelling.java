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
		int[][] tempMap = copyOfArray(map);
		int counter = 0;
		List<Integer> emptyCells = getEmptyCells();
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map.length; col++) {
				if (!computeNeighbourhood(row, col)) {
					updateState(row, col, tempMap, emptyCells,counter+1);
					counter++;
				}
			}
		}
		this.map = tempMap;
	}

	
	private List<Integer> getEmptyCells () {
	    List<Integer> emptyCells = new ArrayList<Integer>();
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map.length; c++) {
                if (map[r][c]==0) {
                    int emptyIndex=r*map.length+c;
                    emptyCells.add(emptyIndex);
                }
            }
        }
        return emptyCells;
    }

    private void updateState(int row, int col, int[][] tempMap, List<Integer> emptyCells, int counter) {
        int cellState=map[row][col];
		tempMap[row][col] = 0;
		Random randomGenerator = new Random();
		int newIndex=randomGenerator.nextInt(emptyCells.size());
		int moveTo = emptyCells.get(newIndex);
		emptyCells.remove(newIndex);
        int y=moveTo % map.length;
        int x=(moveTo - y)/map.length;
		tempMap[x][y] = cellState;
	}
	
	public boolean computeNeighbourhood(int row, int col) {
	    int cellState=map[row][col];
		int same = 0; 
		int total = 0;
		if (cellState!=0) {
			for (int r = -1; r <= 1; r++ ) {
				for (int c = -1; c <= 1; c++ ) {
					if ((row+r>=0 && row+r<map.length)&& (col+c >= 0 && col+c < map.length) && !(r==0 && c==0)) {
						if (map[row+r][col+c]!=0) {
						    if (map[row+r][col+c] == cellState) {
	                            same++;
	                        }
							total++;
						}
					}
				}
			}
	        return total!=0 && (same*100/total) >= threshold;
		}else {
			return true;
		}
	}

}
