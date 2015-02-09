package simulations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import cellsociety_team05.SceneUpdater;
import utility.MapCopier;
import utility.Pair;
import utility.SimData;

public abstract class Sim {
	protected int sim;
	protected List<Integer> params;
	protected SimData gameData;
	protected int[][] map;
	protected int delay;
	protected int cellTypes;
	protected int size;
	protected int cellSides;
	protected int[][] neighbors;
    protected Random rand = new Random();
	// abstract class or make the constructor protected
	public Sim(int sim, int cellTypes, int size, int delay, int cellSides, List<Integer> params){
		map=new int[size][size];
		this.size=size;
		this.sim=sim;
		this.params = params;
		this.delay = delay;
		this.cellSides=cellSides;
        this.cellTypes=cellTypes;
	}
	protected List<Integer> getEmptyCells () {
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
	public void initMap () {
		int[] population = new int[cellTypes];
		for(int i=0;i<cellTypes;i++){
			population[i]=(int) Math.pow(size,2)*params.get(i)/100;
			populate(i+1,population[i],size);
		}
	}

	protected void populate (int fill, int population, int size) {
		int count = 0;
		while(count<population){
			fillCell(fill,size);
			count++;
		}
	}

	public SimData getData(){
		int type=sim;
		int delay=this.delay;
		return new SimData(type,delay,cellSides,MapCopier.copyOfArray(map), simTitle());
	}
	private void fillCell (int fill, int size) {
		int x = rand.nextInt(size);
		int y = rand.nextInt(size);
		if(checkCell(x,y)){
			setCell(x, y, fill);
			return;
		}else{
			fillCell(fill, size);
		}
	}

	private boolean checkCell(int x, int y){
		return map[x][y]==0;
	}

	protected void setCell(int x, int y, int value){
		map[x][y]=value;
	}
	protected int calculateTotal() {
		return (int) Math.pow(map.length,2);
	}
	public List<Integer> getParameters() {
		return params;
	}
	public abstract void setNewParams(HashMap<Integer,Integer> params);
	public abstract void nextGen(SceneUpdater updater);
	public abstract String simTitle();
	public abstract HashMap<Integer, Integer> cellProportions();
}