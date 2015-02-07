package simulations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utility.MapCopier;
import utility.Neighborhood;
import cellsociety_team05.SceneUpdater;

public class Wator extends Sim {
    private HashMap<Integer,Fish> fishMap = new HashMap<>();;
    private int sharkHP;
    private int fishHP;
    private int lifeCycle;
    
    public Wator (int sim, int size, int delay,int cellSides, List<Integer> params, SceneUpdater updater) {
        super(sim, size, delay, cellSides, params,updater);
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
                if(map[row][col]>0){
                    fishMap.put(row*length+col, new Fish());
                }
            }
        }
    }
    
    private class Fish {
        public int age = 0;
        
        public Fish(){
            
        }
        
        public Fish(int age){
            this.age=age;
        }
        public void grows(){
            age++;
        }
    }

    public void nextGen(){
        int[][] tempMap = MapCopier.copyOfArray(map);
        List<Integer> deadFish = new ArrayList<Integer>();
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map.length; col++) {
                Neighborhood neighborhood = findFish(row,col,deadFish,tempMap);
                if(map[row][col]==1 && !deadFish.contains(row*map.length+col)){
                    fishMap.get(row*map.length+col).grows();
                    updateFish(row,col,tempMap,deadFish,neighborhood);
                }else if(map[row][col]>1){
                    fishMap.get(row*map.length+col).grows();
                    updateShark(row,col,tempMap,deadFish,neighborhood);
                }
            }
        }
        this.map = MapCopier.copyOfArray(tempMap);
    }
    
    private void updateShark (int row, int col, int[][] tempMap, List<Integer> deadFish, Neighborhood neighborhood) {
        if(!neighborhood.fish.isEmpty()){
            cellNextGen(neighborhood.fish,tempMap,deadFish,row, col,tempMap[row][col]+fishHP,sharkHP);
        }else if(!neighborhood.empty.isEmpty()){
            int nextHP;
            if(tempMap[row][col]==2){
                nextHP=0;
            }else{
                nextHP=tempMap[row][col]-1;
            }
            cellNextGen(neighborhood.empty,tempMap,deadFish,row, col,nextHP,sharkHP);
        }else if(tempMap[row][col]==2){
            tempMap[row][col]=0;
            updater.updateScene(row,col,tempMap[row][col]);
        }else{
            tempMap[row][col]--;
            updater.updateScene(row,col,tempMap[row][col]);
        }
    }

    private Neighborhood findFish (int row,int col, List<Integer> deadFish, int[][] tempMap) {
        Neighborhood neighborhood = new Neighborhood();
        if(cellSides==6 && col%2==0){
            neighbors=hexneighbors_2;
        }else if(cellSides==6 && col%2==1){
            neighbors=hexneighbors_1;
        }else{
            neighbors=normal4neighbors;
        }
        for (int[] neighbor:neighbors) {
            if ((row+neighbor[0]>= 0 && row+neighbor[0] < map.length) && (col+neighbor[1] >= 0 && col+neighbor[1] < map.length) && map[row + neighbor[0]][col + neighbor[1]]<2) {
                int index=(row+neighbor[0])*map.length+col+neighbor[1];
                if(tempMap[row + neighbor[0]][col + neighbor[1]]==1 && !deadFish.contains(index)){
                    neighborhood.fish.add(index);
                }else if(tempMap[row + neighbor[0]][col + neighbor[1]]==0){
                    neighborhood.empty.add(index);
                }
            }
        }
        return neighborhood;
    }
    
    private void cellNextGen (List<Integer> list, int[][] tempMap, List<Integer> deadFish, int row, int col, int nextHP,int type) {     
        int index = rand.nextInt(list.size());
        int next=list.get(index);
        int nextY=next % map.length;
        int nextX=(next-nextY)/ map.length;
        if(tempMap[nextX][nextY]==1){
            deadFish.add(next);
        }
        tempMap[nextX][nextY]=nextHP;
        updater.updateScene(nextX,nextY,nextHP);
        if(fishMap.get(row*map.length+col).age>=lifeCycle){
            tempMap[row][col]=type;
            fishMap.put(row*map.length+col,new Fish());
            fishMap.put(nextX*map.length+nextY,new Fish());
        }else{
            tempMap[row][col]=0; 
            fishMap.put(nextX*map.length+nextY,new Fish(fishMap.get(row*map.length+col).age));
            fishMap.remove(row*map.length+col);     
        }
        updater.updateScene(row,col,tempMap[row][col]);
    }

    private void updateFish (int row, int col, int[][] tempMap, List<Integer> deadFish, Neighborhood neighborhood) {
        if(!neighborhood.empty.isEmpty()){
            cellNextGen(neighborhood.empty,tempMap,deadFish,row, col,1,1);
        }else{
            tempMap[row][col]=1;
        }
    }

    public String simTitle() {
        return "Wa-Tor World";
    }
}