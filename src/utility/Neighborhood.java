package utility;

import java.util.ArrayList;
import java.util.List;

public class Neighborhood {
    public static final int[][] hexneighbors_1={{0,1},{0,-1},{-1,0},{1,-1},{1,0},{1,1}};
    public static  final int[][] hexneighbors_2={{0,1},{0,-1},{-1,0},{-1,-1},{1,0},{-1,1}};
    public static  final int[][] normal8neighbors = {{1,1},{-1,-1},{1,-1},{-1,1},{0,1},{0,-1},{1,0},{-1,0}};
    public static  final int[][] normal4neighbors = {{0,1},{0,-1},{1,0},{-1,0}};
    public List<Integer> fish = new ArrayList<Integer>();
    public List<Integer> empty = new ArrayList<Integer>();
    
    public static int[][] getNeighbors (int cellSides, int col) {
        if(cellSides==6 && col%2==0){
            return hexneighbors_2;
        }else if(cellSides==6 && col%2==1){
            return hexneighbors_1;
        }else{
            return normal8neighbors;
        }
    }
}
