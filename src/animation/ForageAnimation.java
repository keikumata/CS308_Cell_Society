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
 * @author Kei Yoshikoshi Implements the animation for Foraging of Ants. Creates
 *         specific sliders and labels
 */
public class ForageAnimation extends GUICreator {
	private int[] numbers;
	private static final int MAX_NUMBER_IF_LARGE = 1000;
	private static final int MAX_NUMBER_IF_NORMAL = 100;
	private static final int MAX_NUMBER_ANTS_INDEX = 0;
	private static final int LIFESPAN_ANTS_INDEX = 5;
	private static final int MAX_PHEROMONE_INDEX = 6;
	private static final int EVAP_RATE_INDEX = 7;
	private static final int DIFF_RATE_INDEX = 8;
	private static final int NUM_ANTS_BORN_PER_REPRODUCTION_INDEX = 10;
	private static final int CYCLES_BEFORE_PRODUCTION_INDEX = 11;
	private static final int ANT_INDEX = 3;

	public ForageAnimation(Timeline animation, Stage s, int fps)
			throws Exception {
		super(animation, s, fps);
		// TODO Auto-generated constructor stub
		numbers = new int[] { 0, 5, 6, 7, 8, 10, 11 };
	}

	/**
	 * Creates a HashMap that stores the name of the parameter given an index
	 * 
	 * @return HashMap of index and parameter name
	 */
	private HashMap<Integer, String> paramTitles() {
		HashMap<Integer, String> paramTitles = new HashMap<>();
		paramTitles.put(MAX_NUMBER_ANTS_INDEX, "Maximum # of Ants");
		paramTitles.put(LIFESPAN_ANTS_INDEX, "Lifespan of Ant");
		paramTitles.put(MAX_PHEROMONE_INDEX, "Maximum Amount of Pheromone");
		paramTitles.put(EVAP_RATE_INDEX, "Evaporation Rate");
		paramTitles.put(DIFF_RATE_INDEX, "Diffusion Rate");
		paramTitles.put(NUM_ANTS_BORN_PER_REPRODUCTION_INDEX,
				"# of Ants Born per Reproduction");
		paramTitles.put(CYCLES_BEFORE_PRODUCTION_INDEX,
				"Cycles before Reproduction");
		return paramTitles;
	}

	/**
	 * Creates labels for each type of cell
	 */
	@Override
	public HashMap<Integer, String> paramLabels() {
		HashMap<Integer, String> paramlabels = new HashMap<>();
		paramlabels.put(ANT_INDEX, "Ants");
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
		int location = 0;
		int max;
		for (int i : numbers) {
			if (i == 5)
				max = MAX_NUMBER_IF_LARGE;
			else
				max = MAX_NUMBER_IF_NORMAL;

			Group sliderAndlabel = makeSlider(MIN_PERCENTAGE, max,
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
			grid.add(sliderTitle, 0, location);
			grid.add(sliderAndlabel, 1, location);
			location++;
		}
		return grid;
	}

}
