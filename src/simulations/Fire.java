package simulations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import cellsociety_team05.SceneUpdater;
import utility.MapCopier;
import utility.Neighborhood;

public class Fire extends Sim{
	private float fireProb;
	private int fireTotal;
	private int forestTotal;
	private int burnedTotal;

	public Fire (int sim, int size, int delay,int cellSides, List<Integer> params) {
		super(sim, size, delay, cellSides, params);
		fireProb=params.get(1);
		fireProb=fireProb/100;
	}

	public void nextGen(SceneUpdater updater){
		int[][] tempMap = MapCopier.copyOfArray(map);
		List<Integer> burningTrees = new ArrayList<Integer>();
		fireTotal=0; burnedTotal=0; 
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map.length; col++) {
				if(map[row][col]==1){
					fireTotal++;
					System.out.println("hey");
					checkFire(row,col,tempMap,burningTrees);
				}
				
				updater.updateScene(row,col,tempMap[row][col]);
			}
		}
		System.out.println("Forest total is: " + forestTotal);
		System.out.println("Fire total is: " + fireTotal);
		System.out.println("Burned total is: " + burnedTotal);
		this.map = tempMap;
	}

	private void checkFire (int row, int col, int[][] tempMap, List<Integer> burningTrees) {
		tempMap[row][col] = 2;
        neighbors=Neighborhood.getNeighbors(cellSides,col,4);
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
		if(fire<fireProb){
			burningTrees.add(row*map.length+col);
			burnedTotal++;
			return 1;
		}else{
			return 0;
		}
	}
	public String simTitle() {
		return "Spreading of Fire";
	}

	@Override
	public HashMap<Integer, Integer> cellProportions() {
		HashMap<Integer,Integer> ret = new HashMap<>();
		ret.put(0, (calculateTotal()-fireTotal-burnedTotal)*100/calculateTotal());
		ret.put(1, fireTotal*100/calculateTotal());
		ret.put(2, burnedTotal*100/calculateTotal());
		return ret;
	}
}