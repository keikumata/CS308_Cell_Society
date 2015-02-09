package utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Agent {
    public int age=0;    
    private int size;
    public int gender;
    public int vision;
    public int sugar;
    public int ferSugar;
    public int life;
    public int meta;
    public int[] loc;
    public int[] fertile;
    private int preset;
    public ArrayList<Integer> mated = new ArrayList<Integer>();
    
    public Agent(int[] iniSugar, int[] vis, int[] met,Random rand, int[] loc,int preset,int size){
        sugar=RandomRange.randInt(iniSugar[0],iniSugar[1],rand);
        ferSugar=sugar;
        vision=RandomRange.randInt(vis[0],vis[1],rand);
        meta=RandomRange.randInt(met[0],met[1],rand);
        this.preset=preset;
        this.loc=loc;
        this.size=size;
    }        
    
    public Agent(int iniSugar, int vis, int met,Random rand, int[] loc,int preset,int size,int maxLife,int[] fertile){
        sugar=iniSugar;
        ferSugar=sugar;
        vision=vis;
        meta=met;
        life=maxLife;
        gender=rand.nextInt(2);
        this.preset=preset;
        this.loc=loc;
        this.fertile=fertile;
        this.size=size;
    }     
    public Agent(int iniSugar, int vis, int met,int age,int ferSu, Random rand, int[] loc,int preset,int size,int maxLife,int[] fertile){
        sugar=iniSugar;
        ferSugar=ferSu;
        vision=vis;
        meta=met;
        life=maxLife;
        this.age=age;
        gender=rand.nextInt(2);
        this.preset=preset;
        this.loc=loc;
        this.fertile=fertile;
        this.size=size;
    } 
    
    public Agent(int[] iniSugar, int[] vis, int[] met,Random rand, int[] loc,int preset,int size,int[] maxLife,int[] fertile){
        sugar=RandomRange.randInt(iniSugar[0],iniSugar[1],rand);
        ferSugar=sugar;
        vision=RandomRange.randInt(vis[0],vis[1],rand);
        meta=RandomRange.randInt(met[0],met[1],rand);
        life=RandomRange.randInt(maxLife[0],maxLife[1],rand);
        gender=rand.nextInt(2);
        this.preset=preset;
        this.loc=loc;
        this.fertile=fertile;
        this.size=size;
    }

    public int[] findNext (int[][] map, Random rand) {
        HashMap<Integer,int[]> candidates = new HashMap<>();
        int candidateC=1;
        int thisSugar=0;
        for(int i=0;i<vision;i++){
            int[][] potentials={{loc[0]+i+1,loc[1]},{loc[0]-i-1,loc[1]},{loc[0],loc[1]+i+1},{loc[0],loc[1]-i-1}};
            for(int[] potential:potentials){
                if((potential[0]>=0 && potential[0]<map.length) && (potential[1] >= 0 && potential[1] < map.length)){
                    if(map[potential[0]][potential[1]]>thisSugar && map[potential[0]][potential[1]]!=5){
                        candidateC=1;
                        thisSugar=map[potential[0]][potential[1]];
                        candidates.put(candidateC,potential);
                    }else if(map[potential[0]][potential[1]]==thisSugar){
                        candidates.put(candidateC,potential);
                        candidateC++;
                    }
                }
            }
        }
        if(candidateC>1){
            double distance=100;
            int[] newLoc=new int[2];
            for(int i=0;i<candidateC-1;i++){
                int[] thisCand=candidates.get(i+1);
                double thisDist=Math.pow(thisCand[0]-loc[0],2)+Math.pow(thisCand[1]-loc[1],2);
                if(thisDist<distance){
                    distance=thisDist;
                    newLoc=thisCand;
                }
            }
            return newLoc;
        }else{
            return candidates.get(1);
        }
    }

    public void eat (int sugar) {
        this.sugar+=sugar;
    }

    public boolean grow () {
        age++;
        sugar-=meta;
        boolean starvation=false;
        boolean oldAge=false;
        if(sugar<=0){
            starvation=true;
        }
        if(preset==2){
            if(age>life){
                oldAge=true;
            }
        }
        return starvation || oldAge;
    }

    public void moveTo (int[] next) {
        loc=next;
    }

    public Integer mate (Agent thisAgent,int[][] map,Random rand, Map<Integer, Agent> babies) {
        if(thisAgent.gender!=this.gender && this.active() && thisAgent.active() && !this.hungry() && !thisAgent.hungry() && (thisAgent.hasEmptyPatch(map)!=null || this.hasEmptyPatch(map)!=null)){
            if(!this.mated.contains(thisAgent.getIndex()) && !thisAgent.mated.contains(this.getIndex())){
                for(Integer potential:this.hasEmptyPatch(map)){
                    if(!babies.containsKey(potential)){
                        return potential;
                    }
                }                
                for(Integer potential:thisAgent.hasEmptyPatch(map)){
                    if(!babies.containsKey(potential)){
                        return potential;
                    }
                }
                return null;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    public int getIndex () {
        // TODO Auto-generated method stub
        return loc[0]*size+loc[1];
    }

    private List<Integer> hasEmptyPatch (int[][] map) {
        int[][] neighbors=Neighborhood.getNeighbors(4, 1, 4);
        List<Integer> index = new ArrayList<Integer>();
        for(int[] neighbor:neighbors){
            if((loc[0]+neighbor[0]>=0 && loc[0]+neighbor[0]<map.length) && (loc[1]+neighbor[1] >= 0 && loc[1]+neighbor[1] < map.length) && map[loc[0]+neighbor[0]][loc[1]+neighbor[1]]!=5){
                int newLoc=(loc[0]+neighbor[0])*size+loc[1]+neighbor[1];
                index.add(newLoc);
            }
        }
        return index;
    }

    private boolean hungry () {
        return sugar<ferSugar;
    }    
    
    private boolean active () {
        return age>=fertile[0] && age<=fertile[1];
    }
}
