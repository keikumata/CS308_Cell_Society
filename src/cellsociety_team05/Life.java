package cellsociety_team05;

import java.util.List;

// http://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
// http://stackoverflow.com/questions/16706716/using-two-values-for-one-switch-case-statement
// http://stackoverflow.com/questions/4892992/array-seems-to-be-getting-passed-by-reference-in-java-how-is-this-possible


public class Life  extends Sim{
    
    public Life (int sim, int size, int delay, List<Integer> params) {
        super(sim, size, delay, params);
    }
    
    public void nextGen(){
        int[][] tempMap = copyOfArray(map);
        for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map.length; col++) {
				tempMap[row][col] = updateState(row, col);
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
    	for (int r = -1; r <= 1; r++ ) {
        	for (int c = -1; c <= 1; c++ ) {
        		if ( (row+r >= 0 && row+r < map.length) && ( col+c >= 0 && col+c < map.length) 
        				&& !(r==0 && c==0) ) {
            			friends += map[row + r][col + c];
            		}
        		}
        	}
        return friends;
    }
    
}
