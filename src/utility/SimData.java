package utility;

public class SimData {
	private int[][] map;
	private int sim;
	private String title;
	private int fps;
	private int sides;

	public SimData(int g, int fps, int sides, int[][] map, String title) {
		sim = g;
		this.map = map;
		this.title = title;
		this.fps = fps;
		this.sides = sides;
	}

	public int[][] getMap() {
		return map;
	}

	public int simType() {
		return sim;
	}

	public String simName() {
		return title;
	}

	public int simFPS() {
		return fps;
	}

	public int simShape() {
		return sides;
	}
}
