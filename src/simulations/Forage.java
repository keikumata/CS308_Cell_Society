package simulations;

import java.util.List;

import cellsociety_team05.SceneUpdater;

public class Forage extends Sim{
    private int maxAnt;
    private int[] nest = new int[2];
    private int[] food = new int[2];
    private int life;
    private int maxPhero;
    private double evapRate;
    private double diffRate;
    public Forage (int sim, int size, int delay, int cellSides, List<Integer> params) {
        super(sim, size, delay, cellSides, params);
        maxAnt=params.get(0);
        nest[0]=params.get(1);
        nest[1]=params.get(2);
        food[0]=params.get(3);
        food[1]=params.get(4);
        life=params.get(5);
        maxPhero=params.get(6);
        evapRate=params.get(7)/100;
        diffRate=params.get(8)/100;
    }

    public void nextGen (SceneUpdater updater) {
        
    }

    public String simTitle () {
        return "Ant Foraging";
    }
    
}
