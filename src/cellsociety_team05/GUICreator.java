package cellsociety_team05;

import java.io.FileNotFoundException;

import org.xml.sax.SAXException;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class GUICreator {
	private Timeline animation;
	private Button play;
	private Button pause;
	private Button load;
	private Stage s;
	

	public GUICreator(Timeline animation, Stage s) throws Exception {
		this.animation = animation;
		this.s = s;
		addPlayButton();
		addPauseButton();
		addloadXMLButton();
		addHBox();
		
	}
	private void addPlayButton() {
		play = new Button("play");
		play.setOnAction(e->playAction(e));

	}
	private void playAction(ActionEvent e) {
		animation.play();
	}
	private void addPauseButton() {
		pause = new Button("pause");
		pause.setOnAction(e->pauseAction(e));
	}
	private void pauseAction(ActionEvent e){ 
		animation.stop();
	}
	private void addloadXMLButton() throws Exception {
		load = new Button("load new XML");
		load.setOnAction(e->loadAction(e));
	}
	private void loadAction(ActionEvent e) {
		Master master = new Master();
		master.init(s);
	}
	
//	public GridPane addButtonGrid() {
//
//	}
	public HBox addHBox() {

		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setTranslateX(400);
		hbox.setMinWidth(200);
		hbox.setSpacing(10);
		hbox.setStyle("-fx-background-color: #336699;");
		hbox.getChildren().addAll(play, pause, load);
		hbox.setAlignment(Pos.CENTER);
		return hbox;
	}
}
