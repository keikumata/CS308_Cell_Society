package cellsociety_team05;

import java.util.List;

public class Fire extends Sim{
    public Fire (int game, int size, int delay, List<Integer> params) {
        super(game, size, delay, params);
    }
    
    public void nextGen(){
        
    }
    public String simTitle() {
		return "Spreading of Fire";
	}
}
