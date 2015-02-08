package utility;

public class Pheromone {
    private double evapRate=0.0001;
    private double diffRate=0.0001;
    private double maxPhero=100;
    private int cellSides;
    private int[][] pheroMap;
    
    public Pheromone(double evap,double diff,double max,int cellSides){
        evapRate=evap/100;
        diffRate=diff/100;
        maxPhero=max;
        this.cellSides=cellSides;
    }
    
    public void topOffPhero(int row, int col){
        int[][] neighbors = Neighborhood.getNeighbors(cellSides,col,8);
        int max=0;
        for(int[] neighbor:neighbors){
            if ((row+neighbor[0]>=0 && row+neighbor[0]<pheroMap.length)&& (col+neighbor[1] >= 0 && col+neighbor[1] < pheroMap.length) && pheroMap[row+neighbor[0]][col+neighbor[1]]>max) {
                max=pheroMap[row+neighbor[0]][col+neighbor[1]];
            }
        }
        if(pheroMap[row][col]<max-2){
            pheroMap[row][col]=max-2;
        }
    }
    
    public void nextGen(){
        int[][] tempMap = MapCopier.copyOfArray(pheroMap);
        for(int i=0;i<pheroMap.length;i++){
            for(int j=0;j<pheroMap.length;j++){
                int[][] neighbors = Neighborhood.getNeighbors(cellSides,j,8);
                double dif=pheroMap[i][j]*diffRate;
                double evap=pheroMap[i][j]*evapRate;
                tempMap[i][j]-=evap;
                for(int[] neighbor:neighbors){
                    if ((i+neighbor[0]>=0 && i+neighbor[0]<pheroMap.length)&& (j+neighbor[1] >= 0 && j+neighbor[1] < pheroMap.length)) {
                        tempMap[i+neighbor[0]][j+neighbor[1]]+=dif;
                    }
                }
            }
        }
        pheroMap=MapCopier.copyOfArray(tempMap);
    }
}
