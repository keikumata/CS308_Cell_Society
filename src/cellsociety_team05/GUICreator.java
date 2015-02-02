package cellsociety_team05;

import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.xml.sax.SAXException;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


public class GUICreator {
	private Timeline animation;
	private Button play;
	private Button pause;
	private Button load;
	

	public GUICreator(Timeline animation) throws FileNotFoundException, SAXException {
		this.animation = animation;
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
	private void addloadXMLButton() throws FileNotFoundException, SAXException {
		load = new Button("load new XML");
		load.setOnAction(e->loadAction(e));
	}
	private void loadAction(ActionEvent e) {
		Initializer init = new Initializer();
//		init.readXML(f);
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
		hbox.getChildren().addAll(play, pause);
		hbox.setAlignment(Pos.CENTER);
		return hbox;
	}
}
