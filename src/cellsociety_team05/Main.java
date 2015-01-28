package cellsociety_team05;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * This is the main program, it is basically boilerplate to create an animated scene.
 *
 * @author Kei Yoshikoshi
 */

// CHANGE THE ABOVE DESCRIPTION
public class Main extends Application {
	private static final int SIZE_OF_SCREEN = 400;
	
	private Stage myStage;

	/**
	 * Set things up at the beginning.
	 */
	@Override
	public void start (Stage s) {
		myStage = s;
		s.setTitle("Test");
		// attach game to the stage and display it
		// now start splash screen 
		SceneUpdater SU = new SceneUpdater(s, "nothing", SIZE_OF_SCREEN);
	}
	/**
	 * Start the program.
	 */
	public static void main (String[] args) {
		launch(args);
	}
}