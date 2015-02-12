package animation;

import java.util.HashMap;
import java.util.List;

import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * 
 * @author Kei Yoshikoshi Implements the animation for Wa-Tor World. Creates
 *         specific sliders and labels
 *
 */
public class WatorAnimation extends GUICreator {
	private static final int FISH_INDEX = 1;
	private static final int SHARK_INDEX = 2;
	private static final int ENERGY_OF_FISH_INDEX = 2;
	private static final int INIT_ENERGY_LEVEL_SHARK_INDEX = 3;
	private static final int TURNS_BEFORE_REPRODUCTION_INDEX = 4;

	public WatorAnimation(Timeline animation, Stage s, int fps)
			throws Exception {
		super(animation, s, fps);
	}

	/**
	 * Creates a HashMap that stores the name of the parameter given an index
	 * 
	 * @return HashMap of index and parameter name
	 */
	private HashMap<Integer, String> paramTitles() {
		HashMap<Integer, String> paramTitles = new HashMap<>();
		paramTitles.put(ENERGY_OF_FISH_INDEX, "Energy of Fish");
		paramTitles.put(INIT_ENERGY_LEVEL_SHARK_INDEX,
				"Initial Energy Level of Shark");
		paramTitles.put(TURNS_BEFORE_REPRODUCTION_INDEX,
				"Turns Before Reproduction");
		return paramTitles;
	}

	/**
	 * Creates labels for each type of cell
	 */
	@Override
	public HashMap<Integer, String> paramLabels() {
		HashMap<Integer, String> paramlabels = new HashMap<>();
		paramlabels.put(FISH_INDEX, "Fish");
		paramlabels.put(SHARK_INDEX, "Sharks");
		return paramlabels;
	}

	/**
	 * Creates parameter sliders that allows for the user to edit parameters
	 * during the simulation
	 * 
	 * @return GridPane of sliders
	 */
	@Override
	public GridPane paramSliders(List<Integer> params) {
		GridPane grid = new GridPane();
		grid.setTranslateX(LOCATION_OF_PARAM_SLIDERS);
		for (int i = 2; i < params.size(); i++) {
			Group sliderAndlabel = makeSlider(MIN_PERCENTAGE, MAX_PERCENTAGE,
					params.get(i), SIZE_OF_GRID, Y_LOCATION_OF_SLIDER);
			Slider s = (Slider) sliderAndlabel.getChildren().get(0);
			Label paramLabel = (Label) sliderAndlabel.getChildren().get(1);
			final int innerCounter = i;
			s.valueProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(
						ObservableValue<? extends Number> observable,
						Number oldValue, Number newValue) {
					paramLabel.setText(String.format("%.1f",
							newValue.doubleValue()));
					newParams.put(innerCounter, (int) newValue.doubleValue());
				}
			});
			Label sliderTitle = new Label(paramTitles().get(i));
			grid.add(sliderTitle, 0, i - 2);
			grid.add(sliderAndlabel, 1, i - 2);
		}
		return grid;
	}
}