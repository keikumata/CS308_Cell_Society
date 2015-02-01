package cellsociety_team05;

public class SimData {
	private int[][] map;
	private int sim;
	private String title;

	public SimData(int g, int[][] map, String title){
		sim=g;
		this.map = map;
		this.title = title;
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
}
