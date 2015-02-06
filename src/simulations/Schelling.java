package simulations;

import java.util.List;
import java.util.Random;

import cellsociety_team05.SceneUpdater;
import utility.MapCopier;


public class Schelling extends Sim{
	private int threshold;
	
	public Schelling (int game, int size, int delay,int cellSides, List<Integer> params) {
		super(game, size, delay, cellSides, params);
		threshold = params.get(2); // 3rd parameter
	}
	
	public void nextGen(SceneUpdater updater){
		int[][] tempMap = MapCopier.copyOfArray(map);
		int counter = 0;
		List<Integer> emptyCells = getEmptyCells();
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map.length; col++) {
				if (!computeNeighbourhood(row, col)) {
					updateState(row, col, tempMap, emptyCells,counter+1);
					counter++;
				}
                updater.updateScene(row,col,tempMap[row][col]);
			}
		}
		this.map = tempMap;
	}

    private void updateState(int row, int col, int[][] tempMap, List<Integer> emptyCells, int counter) {
        int cellState=map[row][col];
		tempMap[row][col] = 0;
		Random randomGenerator = new Random();
		int oldIndex=row*map.length+col;
		int newIndex=randomGenerator.nextInt(emptyCells.size());
		int moveTo = emptyCells.get(newIndex);
		emptyCells.remove(newIndex);
        emptyCells.add(oldIndex);
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
	public String simTitle() {
		return "Schelling's Model of Segregation";
	}
}
