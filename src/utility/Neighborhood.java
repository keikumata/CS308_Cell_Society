package utility;

import java.util.ArrayList;
import java.util.List;

public class Neighborhood {
    public static final int[][] hexneighbors_1={{0,1},{0,-1},{-1,0},{1,-1},{1,0},{1,1}};
    public static  final int[][] hexneighbors_2={{0,1},{0,-1},{-1,0},{-1,-1},{1,0},{-1,1}};
    public static  final int[][] normal8neighbors = {{1,1},{-1,-1},{1,-1},{-1,1},{0,1},{0,-1},{1,0},{-1,0}};
    public static  final int[][] normal4neighbors = {{0,1},{0,-1},{1,0},{-1,0}};
    // 1:north 2:east 3:south 4:west
    public static  final int[][][] antNeighbors = {{{-1,-1},{-1,0},{-1,1}},{{-1,1},{0,1},{1,1}},{{1,-1},{1,0},{1,1}},{{1,-1},{0,-1},{-1,-1}}};
    public static  final int[][][] antHexNeighbors_1 = {{{0,-1},{-1,0},{0,1}},{{0,1},{1,1}},{{1,-1},{1,0},{1,1}},{{0,-1},{1,-1}}};
    public static  final int[][][] antHexNeighbors_2 = {{{-1,-1},{-1,0},{-1,1}},{{-1,1},{0,1}},{{0,-1},{1,0},{0,1}},{{-1,-1},{0,-1}}};
    public List<Integer> fish = new ArrayList<Integer>();
    public List<Integer> empty = new ArrayList<Integer>();
    
    public static int[][] getNeighbors (int cellSides, int col, int neighborNum) {
        if(cellSides==6 && col%2==0){
            return hexneighbors_2;
        }else if(cellSides==6 && col%2==1){
            return hexneighbors_1;
        }else if(neighborNum==4){
            return normal4neighbors;
        }else{
            return normal8neighbors;
        }
    }
    
    public static int[][] getAntNeighbors (int cellSides, int col, int dir) {
        if(cellSides==6 && col%2==0){
            return antHexNeighbors_2[dir];
        }else if(cellSides==6 && col%2==1){
            return antHexNeighbors_1[dir];
        }else{
            return antNeighbors[dir];
        }
    }
}
