// This entire file is part of my masterpiece.
// Kei Yoshikoshi

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
 * @author Kei Yoshikoshi Implements the animation for Schelling (Segregation).
 *         Creates specific sliders and labels
 *
 */
public class SchellingAnimation extends GUICreator {
	private static final int BLUE_INDEX = 1;
	private static final int RED_INDEX = 2;
	private static final int GRID_TITLE_X_INDEX = 0;
	private static final int GRID_TITLE_Y_INDEX = 0;
	private static final int GRID_SLIDER_X_INDEX = 1;
	private static final int GRID_SLIDER_Y_INDEX = 0;
	private static final int THRESHOLD_INDEX = 2;

	public SchellingAnimation(Timeline animation, Stage s, int fps)
			throws Exception {
		super(animation, s, fps);
	}

	/**
	 * Creates labels for each type of cell
	 */
	@Override
	public HashMap<Integer, String> paramLabels() {
		HashMap<Integer, String> paramlabels = new HashMap<>();
		paramlabels.put(BLUE_INDEX, "Blue");
		paramlabels.put(RED_INDEX, "Red");
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

		Group sliderAndlabel = makeSliderAndLabel(MIN_PERCENTAGE, MAX_PERCENTAGE,
				params.get(THRESHOLD_INDEX), SIZE_OF_GRID, Y_LOCATION_OF_SLIDER);
		Slider s = (Slider) sliderAndlabel.getChildren().get(SLIDER_INDEX);
		Label paramLabel = (Label) sliderAndlabel.getChildren()
				.get(LABEL_INDEX);

		s.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
				paramLabel.setText(String.format("%.1f", newValue.doubleValue()));
				newParams.put(THRESHOLD_INDEX, (int) newValue.doubleValue());
			}
		});

		Label sliderTitle = new Label("Threshold");

		grid.add(sliderTitle, GRID_TITLE_X_INDEX, GRID_TITLE_Y_INDEX);
		grid.add(sliderAndlabel, GRID_SLIDER_X_INDEX, GRID_SLIDER_Y_INDEX);

		return grid;
	}
}
