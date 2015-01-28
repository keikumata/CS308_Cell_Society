package cellsociety_team05;

public class SimData {
    private int[][] map;
    private int sim;
    
    public SimData(int g,int[][] map){
        sim=g;
        map=this.map;
    }
    
    public int[][] getMap(){
        return map;
    }
    
    public int simType(){
        return sim;
    }
}
