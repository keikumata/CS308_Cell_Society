package cellsociety_team05;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Master {
	private static final int NUM_FRAMES_PER_SECOND = 1;
	private Timeline animation = new Timeline();
	private Sim sim;
	private SceneUpdater updater;

	public void init (Stage s) throws Exception {
		Initializer initializer = new Initializer();
		initializer.readXML();
		sim = initializer.setup();
		updater = new SceneUpdater(s);
		updater.newScene(sim.getData());
	}

	public KeyFrame addKeyFrame (int frameRate) {
		return new KeyFrame(Duration.millis(1000 / frameRate), e->update());
	}

	public void play(){
		KeyFrame frame = addKeyFrame(NUM_FRAMES_PER_SECOND);
		animation.setCycleCount(Animation.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}
	private void update() {
		sim.nextGen();
		System.out.println("updating");
		updater.newScene(sim.getData());
	}
}
