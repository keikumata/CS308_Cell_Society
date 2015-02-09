package simulations;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import utility.Agent;
import utility.Ant;
import utility.Pheromone;
import cellsociety_team05.SceneUpdater;

public class Sugar extends Sim{
    private int[][] agentMap;
    private int preset;
    private Map<Integer, Agent> agents = new HashMap<Integer,Agent>();
    public int patchCap;
    private int initSugar;
    private int sugarMetabolism;
    private int vision;
    private int agentNum=0;
    private int growBack;
    private int growBackInt;
    
    public Sugar (int sim, int cellTypes, int size, int delay, int cellSides, List<Integer> params) {
        super(sim, cellTypes, size, delay, cellSides, params);
        agentMap=new int[size][size];
        double iniagents=(double) params.get(0);
        agentNum=(int) (calculateTotal()*iniagents/100);
        preset=params.get(1);
        initSugar=params.get(2);
        sugarMetabolism=params.get(3);
        vision=params.get(4);
        patchCap=params.get(5);
        growBack=params.get(6);
        growBackInt=params.get(7);
    }

    public void nextGen (SceneUpdater updater) {
        
    }

    private void reproduce() {
        
    }

    private void moveAgent (SceneUpdater updater) {
        int[] next=null;
        try{
            Iterator<Entry<Integer,Ant>> it = ants.entrySet().iterator();
        while(it.hasNext()){
            Entry<Integer, Ant> antEntry = it.next();
            Ant ant=antEntry.getValue();
            if(ant.grow()){
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
