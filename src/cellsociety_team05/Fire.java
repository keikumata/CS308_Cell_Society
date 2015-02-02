package cellsociety_team05;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fire extends Sim{
    private float fireProb;
    
    public Fire (int sim, int size, int delay, List<Integer> params) {
        super(sim, size, delay, params);
        fireProb=params.get(1);
        fireProb=fireProb/100;
    }
    
    public void nextGen(){
        int[][] tempMap = copyOfArray(map);
        List<Integer> burningTrees = new ArrayList<Integer>();
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map.length; col++) {
                if(map[row][col]==1){
                    tempMap=checkFire(row,col,tempMap,burningTrees);
                }
            }
        }
        this.map = tempMap;
    }
    
    private int[][] checkFire (int row, int col, int[][] tempMap, List<Integer> burningTrees) {
        int[][] neighbors = {{0,1},{0,-1},{1,0},{-1,0}};
        tempMap[row][col] = 2;
        for (int[] neighbor:neighbors) {
            if ((row+neighbor[0]>= 0 && row+neighbor[0] < map.length) && (col+neighbor[1] >= 0 && col+neighbor[1] < map.length) && map[row + neighbor[0]][col + neighbor[1]]==0) {
                int treeIndex=(row+neighbor[0])*map.length+col+neighbor[1];
                if(!burningTrees.contains(treeIndex)){
                    tempMap[row + neighbor[0]][col + neighbor[1]]=updateState(row + neighbor[0], col + neighbor[1], burningTrees);
                }
            }
        }
        return tempMap;
    }

    private int updateState(int row, int col, List<Integer> burningTrees) {
        Random rand = new Random();
        float fire = rand.nextFloat();
        System.out.println(fire);
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
