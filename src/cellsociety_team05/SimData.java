package cellsociety_team05;

public class SimData {
	private int[][] map;
	private int sim;
	private String title;
    private int fps;

	public SimData(int g, int fps,int[][] map, String title){
		sim=g;
		this.map = map;
		this.title = title;
		this.fps=fps;
	}

	public int[][] getMap(){
		return map;
	}

	public int simType(){
		return sim;
	}
	public String simName() {
		return title;
	}
	public int simFPS() {
        return fps;
    }
}
