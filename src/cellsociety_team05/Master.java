package cellsociety_team05;

import java.io.FileNotFoundException;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import org.xml.sax.SAXException;

import error.XMLNotFoundException;
import simulations.Sim;

public class Master {
	private int fps = 10;
	private Timeline animation = new Timeline();
	private Sim sim;
	private SceneUpdater updater;
	private KeyFrame frame;

	public void init(Stage s) throws FileNotFoundException,
			NullPointerException {
		Initializer initializer = new Initializer();
		try {
			FileChooser fc = new FileChooser();
			initializer.readXML(fc.showOpenDialog(s).getAbsolutePath());
			sim = initializer.setup();
		} catch (NullPointerException e) {
			XMLNotFoundException xml = new XMLNotFoundException(
					"file not found");
			sim = xml.returnDefaultSim();
			sim.initMap();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		updater = new SceneUpdater(s, animation, fps);
		fps = sim.getData().simFPS();
		try {
			updater.newScene(sim.getData(), sim.getParameters());
		} catch (Exception e) {
			e.printStackTrace();
		}
		play();
	}

	public KeyFrame addKeyFrame(int frameRate) {
		return new KeyFrame(Duration.millis(1000 / frameRate), e -> evolve(e));
	}

	private void evolve(ActionEvent e) {
		sim.nextGen(updater);
		updater.updateGraph(sim.cellProportions());
		try {
			sim.setNewParams(updater.newParams());
		} catch (NullPointerException npe) {
		}
	}

	public void play() {
		frame = addKeyFrame(fps);
		animation.getKeyFrames().add(frame);
		animation.setCycleCount(Animation.INDEFINITE);
	}
}