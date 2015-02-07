package simulations;

import java.util.ArrayList;
import java.util.List;

import utility.MapCopier;
import cellsociety_team05.SceneUpdater;

public class Fire extends Sim{
    private float fireProb;
    
    public Fire (int sim, int size, int delay,int cellSides, List<Integer> params, SceneUpdater updater) {
        super(sim, size, delay, cellSides, params,updater);
        fireProb=params.get(1);
        fireProb=fireProb/100;
    }
    
    public void nextGen(){
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
        if(cellSides==6 && col%2==0){
            neighbors=hexneighbors_2;
        }else if(cellSides==6 && col%2==1){
            neighbors=hexneighbors_1;
        }else{
            neighbors=normalneighbors;
        }
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
