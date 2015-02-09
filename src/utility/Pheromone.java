package utility;

import java.util.HashMap;
import java.util.Random;

public class Pheromone {
    private double evapRate=0.0001;
    private double diffRate=0.0001;
    private int pheroLimit=100;
    private int maxAnt;
    private int cellSides;
    public double[][] pheroMap;
    
    public Pheromone(double evap,double diff,int max,int maxAnt, int cellSides, int size){
        evapRate=evap;
        diffRate=diff;
        pheroLimit=max;
        this.maxAnt=maxAnt;
        this.cellSides=cellSides;
        pheroMap=new double[size][size];
    }
    
    public int[] selectFrontLoc(int[] coor, int[][] fronts, Random rand,int[][] antMap){
        double thisPhero=0;
        HashMap<Integer,int[]> candidates = new HashMap<>();
        int candidateC=1;
        for(int[] front:fronts){
            if((front[0]>=0 && front[0]<pheroMap.length)&& (front[1] >= 0 && front[1] < pheroMap.length) && antMap[front[0]][front[1]]<maxAnt){
                if(pheroMap[front[0]][front[1]]>thisPhero){
                    thisPhero=pheroMap[front[0]][front[1]];
                    candidateC=1;
                    candidates.put(candidateC,front);
                }else if(pheroMap[front[0]][front[1]]==thisPhero){
                    candidates.put(candidateC,front);
                    candidateC++;
                }
            }
        }
        if(candidateC>1){
            return candidates.get(rand.nextInt(candidateC-1)+1);
        }else{
            return candidates.get(1);
        }
    }
    
    public int[] selectSideLoc(int[] coor, int[][] sides, Random rand, int[][] antMap){
        HashMap<Integer,int[]> candidates = new HashMap<>();
        int candidateC=1;
        double thisPhero=0;
        for(int[] side:sides){
            if((side[0]>=0 && side[0]<pheroMap.length)&& (side[1] >= 0 && side[1] < pheroMap.length) && antMap[side[0]][side[1]]<maxAnt){
                if(pheroMap[side[0]][side[1]]>thisPhero){
                    thisPhero=pheroMap[side[0]][side[1]];
                    candidateC=1;
                    candidates.put(candidateC,side);
                }else if(pheroMap[side[0]][side[1]]==thisPhero){
                    candidates.put(candidateC,side);
                    candidateC++;
                }
            }
        }
        if(candidateC>1){
            return candidates.get(rand.nextInt(candidateC-1)+1);
        }else{
            return candidates.get(1);
        }
    }
    
    public void topOffPhero(int row, int col, int[][] map){
        if(map[row][col]==0){
            int[][] neighbors = Neighborhood.getNeighbors(cellSides,col,8);
            double max=0;
            for(int[] neighbor:neighbors){
                if ((row+neighbor[0]>=0 && row+neighbor[0]<pheroMap.length)&& (col+neighbor[1] >= 0 && col+neighbor[1] < pheroMap.length) && pheroMap[row+neighbor[0]][col+neighbor[1]]>max) {
                    max=pheroMap[row+neighbor[0]][col+neighbor[1]];
                }
            }
            if(pheroMap[row][col]<max-2){
                pheroMap[row][col]=max-2;
            }
        }
    }
    
    public void nextGen(){
        double[][] tempMap = MapCopier.copyOfDoubleArray(pheroMap);
        for(int i=0;i<pheroMap.length;i++){
            for(int j=0;j<pheroMap.length;j++){
                int[][] neighbors = Neighborhood.getNeighbors(cellSides,j,8);
                double dif=pheroMap[i][j]*diffRate;
                double evap=pheroMap[i][j]*evapRate;
                tempMap[i][j]-=evap;
                for(int[] neighbor:neighbors){
                    if ((i+neighbor[0]>=0 && i+neighbor[0]<pheroMap.length)&& (j+neighbor[1] >= 0 && j+neighbor[1] < pheroMap.length) && tempMap[i+neighbor[0]][j+neighbor[1]]+dif<pheroLimit) {
                        tempMap[i+neighbor[0]][j+neighbor[1]]+=dif;
                    }
                }
            }
        }
        pheroMap=MapCopier.copyOfDoubleArray(tempMap);
    }

    public void maxPhero (int i, int j) {
        pheroMap[i][j]=pheroLimit;
    }
}
