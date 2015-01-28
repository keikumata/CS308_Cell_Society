package cellsociety_team05;

import java.util.Arrays;

// http://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
// http://stackoverflow.com/questions/16706716/using-two-values-for-one-switch-case-statement
// http://stackoverflow.com/questions/4892992/array-seems-to-be-getting-passed-by-reference-in-java-how-is-this-possible


public class Life  extends Sim{
    
    public Life (int sim, int size) {
        super(sim, size);
    }
    
    // update sim.setCell /getData
    public void nextGen(){
       // int type = this.sim;
        //   int boardSize = map.length;
    	
        int[][] map = this.map;
        
        int[][] tempMap = copyOfArray(map);
        
        for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map.length; col++) {
				
				int cellState = map[row][col]; //alive=1, dead=0
				int friends = computeNeighbourhood(map, row, col);
				tempMap[row][col] = updateState(friends, cellState);
				
			
			}
		}
        map = tempMap;
    }

	public static int[][] copyOfArray(int[][] original) {
	    int[][] copy = new int[original.length][];
	    for (int i = 0; i < original.length; i++) {
	        copy[i] = Arrays.copyOf(original[i], original.length);
	    }
	    return copy;
	}
		
    
    public int updateState(int friends, int cellState) {

    	if (friends < 2) {
			if (cellState == 0) {
				return 0;
			}
			if (cellState == 1) {
				return 0;
			}
    	}
    	if (friends == 2) {
			if (cellState == 0) {
				return 0;
			}
			if (cellState == 1) {
				return 1;
			}
    	}
    	if (friends == 3) {
			if (cellState == 0) {
				return 1;
			}
			if (cellState == 1) {
				return 1;
			}
    	}
    	if (friends > 3) {
    		return 0;
    	}
    	return 0;
    }
    
    public int computeNeighbourhood(int[][] map, int row, int col) {
    	
    	int friends = 0;
    	
    	for (int r = -1; r <= 1; r++ ) {
        	for (int c = -1; c <= 1; c++ ) {
        		
        		if ( (row+r >= 0 && row+r <= map.length) && ( col+c >= 0 && col+c <= map.length) 
        				&& !(r==0 && c==0) ) {
        			
            			friends += map[row + r][col + c];
            		}
        		}

        	}
        return friends;
    }
    
}
