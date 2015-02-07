package utility;

import java.io.FileNotFoundException;

import cellsociety_team05.Master;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class GUICreator {
	private static final int SIZE_OF_GRID = 400;
	private Timeline animation;
	private Button play;
	private Button pause;
	private Button load;
	private Stage s;
	private int fps;


	public GUICreator(Timeline animation, Stage s, int fps) throws Exception {
		this.animation = animation;
		this.s = s;
		this.fps = fps;
		addPlayButton();
		addPauseButton();
		addloadXMLButton();
		//		addHBox();
		addButtonGrid();

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
		animation.stop();
		Master master = new Master();
		try {
			master.init(s);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public Slider makeSlider() {
		Slider slider = new Slider();
		slider.setMin(1);
		slider.setMax(100);
		slider.setValue(fps);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(50);
		slider.setMinorTickCount(5);
		slider.setBlockIncrement(10);
		
		final Label fpsLabel = new Label(
				Double.toString(slider.getValue()));

		slider.valueProperty().addListener(new ChangeListener<Number>() {
		    @Override
		    public void changed(ObservableValue<? extends Number> observable,
		            Number oldValue, Number newValue) {
				// change fps
				fps = (int) newValue.doubleValue();
				fpsLabel.setText(String.format("%.2f", newValue));
//				Timeline timeline = new Timeline();
//		        timeline.getKeyFrames().add();
//		        timeline.playFromStart();
			}
		});
		slider.setTranslateX(SIZE_OF_GRID);
		slider.setTranslateY(200);
		return slider;
	}
	public int getFPS() {
		return fps;
	}
	public GridPane addButtonGrid() {
		GridPane pane = new GridPane();
		pane.setTranslateX(SIZE_OF_GRID);
		pane.add(play, 0, 0);
		pane.add(pause, 0, 1);
		pane.add(load, 0, 2);
		return pane;
	}
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
