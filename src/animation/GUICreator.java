package animation;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cellsociety_team05.Master;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public abstract class GUICreator {
	protected static final int SIZE_OF_GRID = 600;
	protected static final int LOCATION_OF_PARAM_SLIDERS = 800;
	private Timeline animation;
	private Button play;
	private Button pause;
	private Button load;
	private Button right;
	private Button left;
	private Button down;
	private Button up;
	private Stage s;
	private int fps;
	private AnimatedGraph ag;
	protected HashMap<Integer, Integer> newParams;


	public GUICreator(Timeline animation, Stage s, int fps, AnimatedGraph ag) throws Exception {
		this.animation = animation;
		this.s = s;
		this.fps = fps;
		this.ag = ag;
		newParams = new HashMap<Integer, Integer>();
		addPlayButton();
		addPauseButton();
		addloadXMLButton();
		addArrowButtons();
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
	private void addArrowButtons() {
		left = new Button("<");
		left.setOnAction(e->leftAction(e));

		right = new Button(">");
		right.setOnAction(e->rightAction(e));

		up = new Button("^");
		up.setOnAction(e->upAction(e));

		down = new Button("v");
		down.setOnAction(e->downAction(e));
	}
	/**
	 * 
	 * Nic will add actions that these buttons do in regards to the Infinite grid type
	 * 
	 */
	private void leftAction(ActionEvent e) {

	}
	private void rightAction(ActionEvent e) {

	}
	private void upAction(ActionEvent e) {

	}
	private void downAction(ActionEvent e){

	}
	public Group fpsSlider() {
		Group sliderAndlabel = makeSlider(1,100,fps,SIZE_OF_GRID,100);
		Slider s = (Slider) sliderAndlabel.getChildren().get(0);
		Label fpsLabel = (Label) sliderAndlabel.getChildren().get(1);
		Integer fpscopy = fps;
		s.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
				double ratio = newValue.doubleValue()/fpscopy;
				fps = (int) newValue.doubleValue();
				fpsLabel.setText(String.format("%.1f", (double) fps));
				animation.setRate(ratio);
			}
		});
		return sliderAndlabel;
	}
	
	protected Group makeSlider(int min, int max, int value, int sliderX, int sliderY) {
		Group root = new Group();
		Slider slider = new Slider();
		slider.setMin(min);
		slider.setMax(max);
		slider.setValue(value);
		slider.setShowTickMarks(true);

		final Label label = new Label(
				Double.toString(slider.getValue()));
		
		root.getChildren().addAll(slider,label);
		slider.setTranslateX(sliderX);
		slider.setTranslateY(sliderY);
		
		label.setTranslateX(sliderX+150);
		label.setTranslateY(sliderY);

		return root;
	}
	
	public GridPane addButtonGrid() {
		GridPane pane = new GridPane();
		pane.setTranslateX(SIZE_OF_GRID);
		pane.add(play, 0, 0);
		pane.add(pause, 0, 1);
		pane.add(load, 0, 2);
		pane.add(left, 2, 1);
		pane.add(up, 3, 0);
		pane.add(down, 3, 2);
		pane.add(right, 4, 1);
		return pane;
	}
	
	public HashMap<Integer, Integer> newParams() {
		return newParams;
	}
	public abstract GridPane paramSliders(List<Integer> params);
	public abstract HashMap<Integer, String> paramLabels();
}
