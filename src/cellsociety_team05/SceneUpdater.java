package cellsociety_team05;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * 
 * @author Kei Yoshikoshi
 * Reads the Grid object and udpates the scene based on the numbers
 *
 */
public class SceneUpdater {
//	private static final int SIZE_OF_SCREEN = 400;
	public SceneUpdater(Stage s, String type, int size) {
		init(s,size);
	}
	private Scene init (Stage s, int size) {
		Group myRoot = new Group();
		Scene scene = new Scene(myRoot,size, size);
		s.setScene(scene);
		s.show();
		addHBox();
		return scene;
	}
	
	//	public void readGrids(Grid g) {
	//		
	//	}
	private HBox addHBox() {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10);

		Button playButton = new Button("Play");
		playButton.setPrefSize(100, 20);

		Button stopButton = new Button("Stop");
		stopButton.setPrefSize(100, 20);
		hbox.getChildren().addAll(playButton, stopButton);

		return hbox;
	}


}