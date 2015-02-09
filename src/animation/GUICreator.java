package animation;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

import cellsociety_team05.Master;
import javafx.animation.Animation;
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

/**
 * 
 * @author Kei Yoshikoshi The superclass that creates all of the GUI. This class
 *         creates all the Nodes and sets all the location and values.
 *         Subclasses include all the animation classes of the simulations.
 */
public abstract class GUICreator {
	protected static final int SIZE_OF_GRID = 600;
	protected static final int LOCATION_OF_PARAM_SLIDERS = 720;
	private static final int X_LOCATION_OF_FPS_SLIDER = 620;
	private static final int Y_LOCATION_OF_FPS_SLIDER = 570;
	protected static final int Y_LOCATION_OF_SLIDER = 100;
	protected static final int LENGTH_OF_SLIDER = 250;
	protected static final int MIN_PERCENTAGE = 1;
	protected static final int MAX_PERCENTAGE = 100;
	private Timeline animation;
	private Button play;
	private Button pause;
	private Button load;
	private Button step;
	private Stage s;
	private int fps;
	protected HashMap<Integer, Integer> newParams;

	public GUICreator(Timeline animation, Stage s, int fps) throws Exception {
		this.animation = animation;
		this.s = s;
		this.fps = fps;
		newParams = new HashMap<Integer, Integer>();
		addPlayButton();
		addPauseButton();
		addStepButton();
		addloadXMLButton();
		addButtonGrid();

	}

	/**
	 * Creates a play button and sets its action
	 */
	private void addPlayButton() {
		play = new Button("play");
		play.setOnAction(e -> playAction(e));

	}

	/**
	 * Sets the play action. Starts the animation and keeps it going
	 * indefinitely.
	 * 
	 * @param e
	 */
	private void playAction(ActionEvent e) {
		animation.setCycleCount(Animation.INDEFINITE);
		animation.play();
	}

	/**
	 * Creates a pause button and sets its action
	 */
	private void addPauseButton() {
		pause = new Button("pause");
		pause.setOnAction(e -> pauseAction(e));
	}

	/**
	 * Sets the pause action. Stops the animation.
	 * 
	 * @param e
	 */
	private void pauseAction(ActionEvent e) {
		animation.stop();
	}

	/**
	 * Creates a load button and sets its action.
	 * 
	 * @throws Exception
	 */
	private void addloadXMLButton() throws Exception {
		load = new Button("load new XML");
		load.setOnAction(e -> loadAction(e));
	}

	/**
	 * Sets the load action. Stops the animation and reinitiates the scene
	 * 
	 * @param e
	 */
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

	/**
	 * Creates a step button and sets its action.
	 */
	private void addStepButton() {
		step = new Button("step");
		step.setOnAction(e -> stepAction(e));
	}

	/**
	 * Sets the step action. Stops the animation, sets the animation count to 1
	 * (one frame) and plays it.
	 * 
	 * @param e
	 */
	private void stepAction(ActionEvent e) {
		animation.stop();
		animation.setCycleCount(1);
		animation.play();
	}

	/**
	 * Creates a slider to change FPS
	 * 
	 * @return GridPane that contains the FPS slider and label
	 */
	public GridPane fpsSlider() {
		GridPane grid = new GridPane();
		Group sliderAndlabel = makeSlider(MIN_PERCENTAGE, MAX_PERCENTAGE, fps,
				X_LOCATION_OF_FPS_SLIDER, Y_LOCATION_OF_FPS_SLIDER);
		Slider s = (Slider) sliderAndlabel.getChildren().get(0);
		Label fpsLabel = (Label) sliderAndlabel.getChildren().get(1);
		Integer fpscopy = fps;
		s.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
				double ratio = newValue.doubleValue() / fpscopy;
				fps = (int) newValue.doubleValue();
				fpsLabel.setText(String.format("%.1f", (double) fps));
				animation.setRate(ratio);
			}
		});
		Label sliderTitle = new Label("FPS");

		grid.add(sliderTitle, 0, 0);
		grid.add(sliderAndlabel, 1, 0);
		grid.setTranslateX(X_LOCATION_OF_FPS_SLIDER);
		grid.setTranslateY(Y_LOCATION_OF_FPS_SLIDER);
		return grid;
	}

	/**
	 * Takes in different parameters to create a slider
	 * 
	 * @param min
	 *            : Minimum value of the slider
	 * @param max
	 *            : Maximum value of the slider
	 * @param value
	 *            : The starting value of the slider
	 * @param sliderX
	 *            : The x-coordinate of the slider
	 * @param sliderY
	 *            : The y-coordinate of the slider
	 * @return
	 */
	protected Group makeSlider(int min, int max, int value, int sliderX,
			int sliderY) {
		Group root = new Group();
		Slider slider = new Slider();
		slider.setMin(min);
		slider.setMax(max);
		slider.setValue(value);
		slider.setShowTickMarks(true);
		slider.setMinWidth(LENGTH_OF_SLIDER);

		final Label label = new Label(Double.toString(slider.getValue()));

		root.getChildren().addAll(slider, label);
		slider.setTranslateX(sliderX);
		slider.setTranslateY(sliderY);

		label.setTranslateX(sliderX + LENGTH_OF_SLIDER);
		label.setTranslateY(sliderY);

		return root;
	}

	/**
	 * Adds the different buttons to a GridPane
	 * 
	 * @return GridPane with the different buttons in them
	 */
	public GridPane addButtonGrid() {
		GridPane pane = new GridPane();
		pane.setTranslateX(SIZE_OF_GRID);
		pane.add(play, 0, 0);
		pane.add(pause, 0, 1);
		pane.add(load, 0, 2);
		pane.add(step, 0, 3);
		return pane;
	}

	/**
	 * Returns the HashMap with the new parameters in them. Created to pass the
	 * map from front end to back end
	 * 
	 * @return
	 */
	public HashMap<Integer, Integer> newParams() {
		return newParams;
	}

	/**
	 * Abstract method that creates parameter sliders
	 * 
	 * @param The
	 *            different parameters that each simulation has
	 * @return GridPane that contains the labels and sliders
	 */
	public abstract GridPane paramSliders(List<Integer> params);

	/**
	 * Abstract method that creates a map that contains labels for cell types
	 * 
	 * @return: Map with labels for each cell type
	 */
	public abstract HashMap<Integer, String> paramLabels();
}
