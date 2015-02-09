package utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Agent {
    private int age;
    private int gender;
    private int vision;
    private int sugar;
    private int life;
    private int meta;
    private int[] loc;
    
    public Agent(int[] iniSugar, int[] vis, int[] met,Random rand, int[] loc){
        sugar=RandomRange.randInt(iniSugar[0],iniSugar[1],rand);
        vision=RandomRange.randInt(vis[0],vis[1],rand);
        meta=RandomRange.randInt(met[0],met[1],rand);
        this.loc=loc;
    }

    public int[] findNext (int[][] map,int[][] agentMap, Random rand) {
        HashMap<Integer,int[]> candidates = new HashMap<>();
        int candidateC=1;
        int thisSugar=0;
        for(int i=0;i<vision;i++){
            int[][] potentials={{loc[0]+i+1,loc[1]},{loc[0]-i-1,loc[1]},{loc[0],loc[1]+i+1},{loc[0],loc[1]-i-1}};
            for(int[] potential:potentials){
                if(map[potential[0]][potential[1]]>thisSugar && agentMap[potential[0]][potential[1]]==0){
                    candidateC=1;
                    thisSugar=map[potential[0]][potential[1]];
                    candidates.put(candidateC,potential);
                }else if(map[potential[0]][potential[1]]==thisSugar){
                    candidates.put(candidateC,potential);
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
}
