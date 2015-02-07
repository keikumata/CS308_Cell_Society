package simulations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cellsociety_team05.SceneUpdater;
import utility.MapCopier;
import utility.Neighborhood;

public class Fire extends Sim{
	private float fireProb;

	public Fire (int sim, int size, int delay,int cellSides, List<Integer> params) {
		super(sim, size, delay, cellSides, params);
		fireProb=params.get(1);
		fireProb=fireProb/100;
	}

	public void nextGen(SceneUpdater updater){
		int[][] tempMap = MapCopier.copyOfArray(map);
		List<Integer> burningTrees = new ArrayList<Integer>();
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map.length; col++) {
				if(map[row][col]==1){
					checkFire(row,col,tempMap,burningTrees);
				}
				updater.updateScene(row,col,tempMap[row][col]);
			}
		}
		this.map = tempMap;
	}

	private void checkFire (int row, int col, int[][] tempMap, List<Integer> burningTrees) {
		tempMap[row][col] = 2;
        neighbors=Neighborhood.getNeighbors(cellSides,col);
		for (int[] neighbor:neighbors) {
			if ((row+neighbor[0]>= 0 && row+neighbor[0] < map.length) && (col+neighbor[1] >= 0 && col+neighbor[1] < map.length) && map[row + neighbor[0]][col + neighbor[1]]==0) {
				int treeIndex=(row+neighbor[0])*map.length+col+neighbor[1];
				if(!burningTrees.contains(treeIndex)){
					tempMap[row + neighbor[0]][col + neighbor[1]]=updateState(row + neighbor[0], col + neighbor[1], burningTrees);
				}
			}
		}
	}

	private int updateState(int row, int col, List<Integer> burningTrees) {
		Random rand = new Random();
		float fire = rand.nextFloat();
		//        System.out.println(fire);
		if(fire<fireProb){
			burningTrees.add(row*map.length+col);
			return 1;
		}else{
			return 0;
		}
	}
	public String simTitle() {
		return "Spreading of Fire";
	}
}