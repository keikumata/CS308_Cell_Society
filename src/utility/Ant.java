package utility;

import java.util.Random;

public class Ant {
    private int age=0;
    private int life;
    public int dir;
    private final int[][] directions={{-1,0},{0,1},{1,0},{0,-1}};
    private int[] coor;
    
    public Ant(Random rand,int life,int[] nestCoor){
        coor=nestCoor;
        this.life=life;
        dir=rand.nextInt();
    }
    
    public void changeDir(int dir){
        coor[0]+=directions[dir][0];
        coor[1]+=directions[dir][1];
        this.dir=dir;
    }
    
    public boolean grow(){
        age++;
        if(age>life){
            return true;
        }else{
            return false;
        }
    }
    
}
