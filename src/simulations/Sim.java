package simulations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import utility.MapCopier;
import utility.Pair;
import utility.SimData;

public class Sim {
	protected int sim;
	protected List<Integer> params;
	protected SimData gameData;
	protected int[][] map;
	protected int delay;
	protected int cellTypes;
	private int size;

	public Sim(int sim, int size, int delay, List<Integer> params){
		map=new int[size][size];
		this.size=size;
		this.sim=sim;
		this.params = params;
		this.delay = delay;
		if(sim % 2==0){
			cellTypes=1;
		}else{
			cellTypes=2;
		}
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
		return new SimData(type,delay,MapCopier.copyOfArray(map), simTitle());
	}
	public HashMap<Pair,Pair> getMap() {
		return null;
	}
	private void fillCell (int fill, int size) {
		Random rand = new Random();
		int x = rand.nextInt(size);
		int y = rand.nextInt(size);
		if(checkCell(x,y)){
			setCell(x, y, fill);
			return;
		}else{
			fillCell(fill, size);
		}
	}

	public boolean checkCell(int x, int y){
		return map[x][y]==0;
	}

	public void setCell(int x, int y, int value){
		map[x][y]=value;
	}

	public void nextGen () { 
	}
	public String simTitle() {
		return null;
	}
}
