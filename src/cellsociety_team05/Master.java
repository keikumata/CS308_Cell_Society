package cellsociety_team05;

import java.io.FileNotFoundException;

import org.xml.sax.SAXException;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Master {
	private static final int NUM_FRAMES_PER_SECOND = 10;
	private Timeline animation = new Timeline();
	Sim sim;
	SceneUpdater updater;

	public void init (Stage s)  {
		Initializer initializer = new Initializer();
		FileChooser fc = new FileChooser();
		try {
			initializer.readXML(fc.showOpenDialog(s).getAbsolutePath());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updater = new SceneUpdater(s,animation);
		sim = initializer.setup();
		try {
			updater.newScene(sim.getData());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public KeyFrame addKeyFrame (int frameRate) {
		return new KeyFrame(Duration.millis(1000 / frameRate), e -> evolve(e));
	}

	private void evolve (ActionEvent e) {
		sim.nextGen();
		try {
			updater.updateScene(sim.getData());

		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void play(){
		KeyFrame frame = addKeyFrame(NUM_FRAMES_PER_SECOND);
		animation.getKeyFrames().add(frame);
		animation.setCycleCount(Animation.INDEFINITE);
	}
}
