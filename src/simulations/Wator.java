package simulations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import utility.Fish;
import utility.MapCopier;
import utility.Neighborhood;
import cellsociety_team05.SceneUpdater;

public class Wator extends Sim {
    private HashMap<Integer,Fish> fishMap = new HashMap<>();;
    private int sharkHP;
    private int fishHP;
    private int lifeCycle;

    public Wator (int sim, int size, int delay,int cellSides, List<Integer> params) {
        super(sim, size, delay, cellSides, params);
        fishHP=params.get(2);
        sharkHP=params.get(3)+2;
        lifeCycle=params.get(4);
    }
    
    public void initMap () {
        int fishPopulation = (int) Math.pow(map.length,2)*params.get(0)/100;
        int sharkPopulation = (int) Math.pow(map.length,2)*params.get(1)/100;
        populate(1,fishPopulation,map.length);
        populate(sharkHP,sharkPopulation,map.length);
        createFish(map.length);
    }

    
    private void createFish (int length) {
        for (int row = 0; row < length; row++) {
            for (int col = 0; col < length; col++) {
                fishMap.put(row*length+col, new Fish());
            }
        }
    }

    public void nextGen(SceneUpdater updater){
        int[][] tempMap = MapCopier.copyOfArray(map);
        List<Integer> deadFish = new ArrayList<Integer>();
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map.length; col++) {
                fishMap.get(row*map.length+col).grows();
                if(map[row][col]==1 && !deadFish.contains(row*map.length+col)){
                    updateFish(row,col,tempMap,deadFish);
                }else if(map[row][col]>1){
                    updateShark(row,col,tempMap,deadFish);
                }
                updater.updateScene(row,col,tempMap[row][col]);
            }
        }
        this.map = tempMap;
    }
    
    private void updateShark (int row, int col, int[][] tempMap, List<Integer> deadFish) {
        boolean birth=true;
        Neighborhood neighborhood = findFish(row,col,deadFish);
        if(!neighborhood.fish.isEmpty()){
            cellNextGen(neighborhood.fish,tempMap,deadFish,row, col,map[row][col]+fishHP,sharkHP);
        }else if(!neighborhood.empty.isEmpty()){
            int nextHP;
            if(map[row][col]==2){
                nextHP=0;
            }else{
                nextHP=map[row][col]-1;
            }
            cellNextGen(neighborhood.empty,tempMap,deadFish,row, col,nextHP,sharkHP);
        }else if(map[row][col]==2){
            tempMap[row][col]=0;
            birth=false;
        }else{
            tempMap[row][col]--;
            birth=false;
        }
        if(birth && fishMap.get(row*map.length+col).age>=lifeCycle){
            tempMap[row][col]=sharkHP;
            fishMap.put(row*map.length+col,new Fish());
        }
    }

    private Neighborhood findFish (int row,int col, List<Integer> deadFish) {
        Neighborhood neighborhood = new Neighborhood();
        for (int[] neighbor:neighbors) {
            if ((row+neighbor[0]>= 0 && row+neighbor[0] < map.length) && (col+neighbor[1] >= 0 && col+neighbor[1] < map.length) && map[row + neighbor[0]][col + neighbor[1]]<2) {
                int index=(row+neighbor[0])*map.length+col+neighbor[1];
                if(map[row + neighbor[0]][col + neighbor[1]]==1 && !deadFish.contains(index)){
                    neighborhood.fish.add(index);
                }else if(map[row + neighbor[0]][col + neighbor[1]]==0){
                    neighborhood.empty.add(index);
                }
            }
        }
        return neighborhood;
    }
    
    private void cellNextGen (List<Integer> list, int[][] tempMap, List<Integer> deadFish, int row, int col, int nextHP,int type) {     
        Random rand = new Random();
        int index = rand.nextInt(list.size());
        int next=list.get(index);
        int nextY=next % map.length;
        int nextX=(next-nextY)/ map.length;
        if(map[nextX][nextY]==1){
            deadFish.add(next);
        }
        tempMap[nextX][nextY]=nextHP;
        tempMap[row][col]=0;        
        if(fishMap.get(row*map.length+col).age>=lifeCycle){
            tempMap[row][col]=type;
            fishMap.put(row*map.length+col,new Fish());
        }
        fishMap.get(nextX*map.length+nextY).age=fishMap.get(row*map.length+col).age;
    }

    private void updateFish (int row, int col, int[][] tempMap, List<Integer> deadFish) {
        Neighborhood neighborhood = findFish(row,col,deadFish);
        if(!neighborhood.empty.isEmpty()){
            cellNextGen(neighborhood.empty,tempMap,deadFish,row, col,1,1);
        }
    }

    public String simTitle() {
		return "Wa-Tor World";
	}

}
