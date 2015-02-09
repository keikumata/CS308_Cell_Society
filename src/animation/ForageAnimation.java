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
 * @author Kei Yoshikoshi
 * Implements the animation for Foraging of Ants. Creates specific sliders and labels
 */
public class ForageAnimation extends GUICreator {
	private int[] numbers;
	public ForageAnimation(Timeline animation, Stage s, int fps) throws Exception {
		super(animation, s, fps);
		// TODO Auto-generated constructor stub
		numbers = new int[]{0,5,6,7,8,10,11};
	}
	/**
	 * Creates a HashMap that stores the name of the parameter given an index
	 * @return HashMap of index and parameter name
	 */
	private HashMap<Integer,String> paramTitles() {
		HashMap<Integer,String> paramTitles = new HashMap<>();
		paramTitles.put(0, "Maximum # of Ants");
		paramTitles.put(5, "Lifespan of Ant");
		paramTitles.put(6, "Maximum Amount of Pheromone");
		paramTitles.put(7, "Evaporation Rate");
		paramTitles.put(8, "Diffusion Rate");
		paramTitles.put(10, "# of Ants Born per Reproduction");
		paramTitles.put(11, "Cycles before Reproduction");
		return paramTitles;
	}
	/**
	 * Creates labels for each type of cell
	 */
	@Override
	public HashMap<Integer, String> paramLabels () {
		HashMap<Integer,String> paramlabels = new HashMap<>();
		paramlabels.put(3, "Ants");
		return paramlabels;
	}

	/**
	 * Creates parameter sliders that allows for the user to edit parameters during the simulation
	 * @return GridPane of sliders
	 */
	
	@Override
	public GridPane paramSliders(List<Integer> params) {
		GridPane grid = new GridPane();
		grid.setTranslateX(LOCATION_OF_PARAM_SLIDERS);
		int location = 0;
		int max;
		for (int i: numbers) {
			if (i==5) max = 1000;
			else max = 100;
			
			Group sliderAndlabel = makeSlider(MIN_PERCENTAGE,max,params.get(i),SIZE_OF_GRID,Y_LOCATION_OF_SLIDER);
			Slider s = (Slider) sliderAndlabel.getChildren().get(0);
			Label paramLabel = (Label) sliderAndlabel.getChildren().get(1);
			final int innerCounter = i;
			s.valueProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable,
						Number oldValue, Number newValue) {
					paramLabel.setText(String.format("%.1f", newValue.doubleValue()));
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
