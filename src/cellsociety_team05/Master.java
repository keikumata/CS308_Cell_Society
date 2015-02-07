package cellsociety_team05;

import java.io.FileNotFoundException;

import org.xml.sax.SAXException;

import error.XMLNotFoundException;
import simulations.Sim;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Master {
	private int fps = 10;
	private Timeline animation = new Timeline();
	Sim sim;
	SceneUpdater updater;
	KeyFrame frame; 

	public void init (Stage s) throws FileNotFoundException, NullPointerException{
		Initializer initializer = new Initializer();
		try {
			FileChooser fc = new FileChooser();
			initializer.readXML(fc.showOpenDialog(s).getAbsolutePath());
			sim = initializer.setup();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			XMLNotFoundException xml = new XMLNotFoundException("file not found");
			sim = xml.returnDefaultSim();
			sim.initMap();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updater = new SceneUpdater(s,animation,fps);
		fps = sim.getData().simFPS();
		try {
			updater.newScene(sim.getData());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		play();
	}

	public KeyFrame addKeyFrame (int frameRate) {
		return new KeyFrame(Duration.millis(1000 / frameRate), e -> evolve(e));
	}

	private void evolve (ActionEvent e) {
		sim.nextGen(updater);
//		animation.setRate(updater.getFPS());
		//		frame = addKeyFrame(updater.getFPS());
		//		animation.getKeyFrames().add(frame);
	}
	public void play(){
		frame = addKeyFrame(fps);
		animation.getKeyFrames().add(frame);
		animation.setCycleCount(Animation.INDEFINITE);
	}
}
