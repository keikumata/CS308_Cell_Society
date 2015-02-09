package simulations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import utility.MapCopier;
import utility.Neighborhood;
import cellsociety_team05.SceneUpdater;


public class Schelling extends Sim{
	private int threshold;
	private int blueTotal;
	private int redTotal;

	public Schelling (int game, int cellTypes, int size, int delay,int cellSides, List<Integer> params) {
		super(game, cellTypes, size, delay, cellSides, params);
		blueTotal = params.get(0);
		redTotal = params.get(1);
		threshold = params.get(2); // 3rd parameter
	}
	
	public void nextGen(SceneUpdater updater){
		int[][] tempMap = MapCopier.copyOfArray(map);
		List<Integer> emptyCells = getEmptyCells();
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map.length; col++) {
				if (!computeNeighbourhood(row, col)) {
					updateState(row, col, tempMap, emptyCells,updater);
				}
			}
		}
		this.map = MapCopier.copyOfArray(tempMap);
	}
	
	private void updateState(int row, int col, int[][] tempMap, List<Integer> emptyCells, SceneUpdater updater) {
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
		updater.updateScene(x,y,cellState);
		updater.updateScene(row,col,0);
	}

	public boolean computeNeighbourhood(int row, int col) {
        int cellState=map[row][col];
        int same = 0; 
        int total = 0;
        neighbors=Neighborhood.getNeighbors(cellSides,col,8);
		if (cellState!=0) {
			for (int[] neighbor:neighbors) {
				if ((row+neighbor[0]>=0 && row+neighbor[0]<map.length)&& (col+neighbor[1] >= 0 && col+neighbor[1] < map.length)) {
					if (map[row+neighbor[0]][col+neighbor[1]]!=0) {
						if (map[row+neighbor[0]][col+neighbor[1]] == cellState) {
							same++;
						}
						total++;
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

	@Override
	public HashMap<Integer, Integer> cellProportions() {
		HashMap<Integer,Integer> ret = new HashMap<>();
		ret.put(1, blueTotal);
		ret.put(2, redTotal);
		return ret;
	}

	@Override
	public void setNewParams(HashMap<Integer,Integer> params) {
		if(!params.isEmpty()) 
			threshold = params.get(0);
	}
}