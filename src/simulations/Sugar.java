package simulations;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import utility.Agent;
import utility.Ant;
import utility.Pheromone;
import utility.RandomRange;
import cellsociety_team05.SceneUpdater;

public class Sugar extends Sim{
    private int[][] agentMap;
    private int preset;
    private Map<Integer, Agent> agents = new HashMap<Integer,Agent>();
    public int patchCap;
    private int[] initSugar;
    private int[] metabolism;
    private int[] vision;
    private int agentNum=0;
    private int growBack;
    private int growInt;
    
    public Sugar (int sim, int cellTypes, int size, int delay, int cellSides, List<Integer> params) {
        super(sim, cellTypes, size, delay, cellSides, params);
        agentMap=new int[size][size];
        double iniagents=(double) params.get(0);
        agentNum=(int) (calculateTotal()*iniagents/100);
        preset=params.get(1);
        int[] maxInitSugar={params.get(2),params.get(3)};
        initSugar=maxInitSugar;
        int[] maxMeta={1,params.get(4)};
        metabolism=maxMeta;
        int[] maxVision={1,params.get(5)};
        vision=maxVision;
        patchCap=params.get(6);
        growBack=params.get(7);
        growInt=params.get(8);
    }
    
    public void initMap () {
        int[] population = new int[cellTypes];
        List<Integer> index = new ArrayList<Integer>();
        double unpopulated=Math.pow(size,2);
        for(int i=0;i<cellTypes;i++){
            population[i]=(int) (unpopulated*rand.nextDouble());
            unpopulated-=population[i];
            populate(i+1,population[i],size);
        }
        for(int i=0;i<agentNum;i++){
            fillAgents(i,index);
        }
    }
    
    private void fillAgents(int i,List<Integer> index){            
        int agentRow=RandomRange.randInt(0,size-1,rand);
        int agentCol=RandomRange.randInt(0,size-1,rand);
        if(!index.contains(agentRow*size+agentCol)){
            index.add(agentRow*size+agentCol);
            int[] loc={agentRow,agentCol};
            agentMap[agentRow][agentCol]=1;
            agents.put(i, new Agent(initSugar, vision, metabolism, rand,loc));
            return;
        }else{
            fillAgents(i,index);
        }
    }
    
    public void nextGen (SceneUpdater updater) {
        moveAgent(updater);
    }

    private void reproduce() {
        
    }

    private void moveAgent (SceneUpdater updater) {
        int[] next=null;
        try{
            Iterator<Entry<Integer, Agent>> it = agents.entrySet().iterator();
        while(it.hasNext()){
            Entry<Integer, Agent> agentEntry = it.next();
            Agent agent=agentEntry.getValue();
            int[] next=agent.findNext(map,agentMap,rand);
            if(agent.grow()){
                if(antMap[ant.coor[0]][ant.coor[1]]==1){
                    updater.updateScene(ant.coor[0],ant.coor[1],map[ant.coor[0]][ant.coor[1]]);
                }
                antMap[ant.coor[0]][ant.coor[1]]--;
                antNum--;
                it.remove();
            }else{
                int [][] fronts=ant.getFrontNeighbors();
                if(ant.hasFood){
                    foodPh.topOffPhero(ant.coor[0],ant.coor[1],map);
                    next=homePh.selectFrontLoc(ant.coor,fronts,rand, antMap);
                    if(next==null){
                        next=homePh.selectSideLoc(ant.coor,ant.getSideNeighbors(),rand,antMap);
                    }
                }else{
                    homePh.topOffPhero(ant.coor[0],ant.coor[1],map);
                    next=foodPh.selectFrontLoc(ant.coor, fronts,rand, antMap);
                    if(next==null){
                        next=foodPh.selectSideLoc(ant.coor,ant.getSideNeighbors(),rand,antMap);
                    }
                }
                if(next==null){
                    next=ant.coor;
                }else{
                    ant.changeDir(next);
                }
                if(map[next[0]][next[1]]==0){
                    updater.updateScene(next[0],next[1],3);
                }else if(map[next[0]][next[1]]==1){
                    foraged+=ant.atHome();
                    homePh.maxPhero(ant.coor[0],ant.coor[1]);
                }else{
                    ant.atFood();
                    foodPh.maxPhero(ant.coor[0],ant.coor[1]);
                }
                if(antMap[ant.coor[0]][ant.coor[1]]==1){
                    updater.updateScene(ant.coor[0],ant.coor[1],map[ant.coor[0]][ant.coor[1]]);
                }
                antMap[ant.coor[0]][ant.coor[1]]--;
                antMap[next[0]][next[1]]++;
                ant.moveTo(next);
            }
        }
        } catch ( ConcurrentModificationException e){
            System.out.println("here");
        }
    }

    public String simTitle () {
        return "Ant Foraging";
    }

    @Override
    public HashMap<Integer, Integer> cellProportions () {
    	HashMap<Integer,Integer> ret = new HashMap<>();
		ret.put(3, agentNum*100/calculateTotal());
		return ret;
    }
    
}
