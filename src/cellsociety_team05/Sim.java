package cellsociety_team05;

import java.util.List;
import java.util.Random;

public class Sim {
    protected int sim;
    protected List<Integer> params;
    protected SimData gameData;
    protected int[][] map;
    protected int delay;
    
    public Sim(int sim, int size, int delay, List<Integer> params){
        map=new int[size][size];
        this.sim=sim;
        this.params = params;
        this.delay = delay;
    }
    
    public void initMap (int size, int cellTypes,int[] params) {
        int[] population = new int[cellTypes];
        for(int i=0;i<cellTypes;i++){
            population[i]=(int) Math.pow(size,2)*params[i]/100;
        }
        for(int i=0;i<cellTypes;i++){
            populate(i,population,size);
        }
    }

    protected void populate (int fill, int[] population, int size) {
        int count = 0;
        while(count<population[fill]){
            fillCell(fill,size);
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
}
