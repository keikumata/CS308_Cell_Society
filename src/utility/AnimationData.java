package utility;

public class AnimationData {
	private int fps;
	private boolean changed = false;
	public AnimationData(int fps, boolean changed) {
		this.fps = fps;
		this.changed = changed;
	}
	public int getFPS() {
		return fps;
	}
	public boolean changed() {
		return changed;
	}
}
