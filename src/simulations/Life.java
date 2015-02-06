package simulations;

import java.util.List;

import cellsociety_team05.SceneUpdater;
import utility.MapCopier;

// http://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
// http://stackoverflow.com/questions/16706716/using-two-values-for-one-switch-case-statement
// http://stackoverflow.com/questions/4892992/array-seems-to-be-getting-passed-by-reference-in-java-how-is-this-possible


public class Life  extends Sim{
    
    public Life (int sim, int size, int delay,int cellSides, List<Integer> params, SceneUpdater updater) {
        super(sim, size, delay, cellSides, params,updater);
    }
    
    public void nextGen(){
        int[][] tempMap = MapCopier.copyOfArray(map);
        for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
			    tempMap[row][col]=updateState(row, col);
				updater.updateScene(row,col,tempMap[row][col]);
			}
		}
        this.map = tempMap;
    }
    
    private int updateState(int row, int col) {
        int friends=computeNeighbourhood(this.map, row, col);
    	if (friends < 2 || friends >3) {
    	    return 0;
    	}else if(friends == 3) {
            return 1;
    	}else{
    	    return this.map[row][col];
    	}
    }
    
    private int computeNeighbourhood(int[][] map, int row, int col) {
    	int friends = 0;        
        for (int[] neighbor:neighbors) {
    		if ( (row+neighbor[0] >= 0 && row+neighbor[0] < map.length) && ( col+neighbor[1] >= 0 && col+neighbor[1] < map.length)) {
        			friends += map[row + neighbor[0]][col + neighbor[1]];
        		}
    	}
        return friends;
    }
    public String simTitle() {
		return "Game of Life";
	}
}
