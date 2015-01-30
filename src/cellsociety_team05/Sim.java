package cellsociety_team05;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

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
    protected static int[][] copyOfArray(int[][] original) {
	    int[][] copy = new int[original.length][];
	    for (int i = 0; i < original.length; i++) {
	        copy[i] = Arrays.copyOf(original[i], original.length);
	    }
	    return copy;
	}
    public class Pair {
		int r; int c;
		public Pair(int row, int col) {
			r = row;
			c = col;
		}
	}
    public void initMap () {
        int[] population = new int[cellTypes+1];
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
        return new SimData(sim, map);
    }

    private void fillCell (int fill, int size) {
        Random rand = new Random();
        int x = rand.nextInt(size);
        int y = rand.nextInt(size);
        if(!checkCell(x, y, fill)){
            setCell(x, y, fill);
            return;
        }else{
            fillCell(fill, size);
        }
    }
    
    public boolean checkCell(int x, int y, int value){
    	return map[x][y]==value;
    }
    
    public void setCell(int x, int y, int value){
        map[x][y]=value;
    }

    public void nextGen () { 
        
    }
}
