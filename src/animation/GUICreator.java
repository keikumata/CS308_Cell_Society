// This entire file is part of my masterpiece.
// Kei Yoshikoshi
package animation;

import java.util.HashMap;
import java.util.List;

import javafx.animation.Timeline;
import javafx.scene.Group;
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
	protected static final int Y_LOCATION_OF_SLIDER = 100;
	protected static final int LENGTH_OF_SLIDER = 250;
	protected static final int MIN_PERCENTAGE = 1;
	protected static final int MAX_PERCENTAGE = 100;
	protected static final int SLIDER_INDEX = 0;
	protected static final int LABEL_INDEX = 1;
	protected HashMap<Integer, Integer> newParams;

	public GUICreator(Timeline animation, Stage s, int fps) throws Exception {
		newParams = new HashMap<Integer, Integer>();
	}

	/**
	 * Takes in different parameters to create a slider. Makes it more general
	 * in case the sliders have to be located in different places
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
	protected Group makeSliderAndLabel(int min, int max, int value,
			int sliderX, int sliderY) {
		Group root = new Group();
		Slider slider = new Slider();
		slider.setMin(min);
		slider.setMax(max);
		slider.setValue(value);
		slider.setShowTickMarks(true);
		slider.setMinWidth(LENGTH_OF_SLIDER);

		Label label = new Label(Double.toString(slider.getValue()));

		root.getChildren().addAll(slider, label);
		slider.setTranslateX(sliderX);
		slider.setTranslateY(sliderY);

		label.setTranslateX(sliderX + LENGTH_OF_SLIDER);
		label.setTranslateY(sliderY);

		return root;
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
