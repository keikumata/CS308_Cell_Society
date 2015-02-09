package simulations;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import utility.Ant;
import utility.Pheromone;
import cellsociety_team05.SceneUpdater;

public class Forage extends Sim{
    private int maxAnt;
    private int[][] antMap;
    private int[] nest = new int[2];
    private int[] food = new int[2];
    private int life;
    private int worldAge=0;
    private int maxPhero;
    private double evapRate;
    private double diffRate;
    private Map<Integer, Ant> ants = new HashMap<Integer,Ant>();
    private Pheromone homePh;
    private Pheromone foodPh;
    private Integer startAnt;
    public int foraged;
    private int antPerGen;
    private int antNum=0;
    
    public Forage (int sim, int cellTypes, int size, int delay, int cellSides, List<Integer> params) {
        super(sim, cellTypes, size, delay, cellSides, params);
        antMap=new int[size][size];
        maxAnt=params.get(0);
        nest[0]=params.get(1);
        nest[1]=params.get(2);
        food[0]=params.get(3);
        food[1]=params.get(4);
        life=params.get(5);
        maxPhero=params.get(6);
        evapRate=params.get(7)/1000;
        diffRate=params.get(8)/1000;
        startAnt=params.get(9);
        antPerGen=params.get(10);
    }
    
    public void initMap () {
        setCell(nest[0], nest[1], 1);
        setCell(food[0], food[1], 2);
        for(int i=0;i<startAnt;i++){
            ants.put(i+1,new Ant(rand,life,nest));
            antMap[nest[0]][nest[1]]++;
            antNum++;
        }
        homePh = new Pheromone(evapRate, diffRate, maxPhero, maxAnt, cellSides,size);
        homePh.maxPhero(nest[0],nest[1]);
        foodPh = new Pheromone(evapRate, diffRate, maxPhero, maxAnt, cellSides,size);
    }

    public void nextGen (SceneUpdater updater) {
        worldAge++;
        foodPh.nextGen();
        homePh.nextGen();
        moveAnt(updater);
        if(worldAge % 10==0){
            reproduce();
        }
    }

    private void reproduce() {
        for(int i=0;i<antPerGen;i++){
            ants.put(worldAge+i,new Ant(rand,life,nest));
            antMap[nest[0]][nest[1]]++;
            antNum++;
        }
    }

    private void moveAnt (SceneUpdater updater) {
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
		ret.put(3, antNum*100/calculateTotal());
		return ret;
    }

	@Override
	public void setNewParams(HashMap<Integer,Integer> params) {
		// TODO Auto-generated method stub
		
	}
    
}
