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

public class WatorAnimation extends GUICreator {

	public WatorAnimation(Timeline animation, Stage s, int fps)
			throws Exception {
		super(animation, s, fps);
	}
	private HashMap<Integer,String> paramTitles() {
		HashMap<Integer,String> paramTitles = new HashMap<>();
		paramTitles.put(2, "Energy of Fish");
		paramTitles.put(3, "Initial Energy Level of Shark");
		paramTitles.put(4, "Turns Before Reproduction");
		return paramTitles;
	}
	@Override
	public HashMap<Integer,String> paramLabels() {
		HashMap<Integer,String> paramlabels = new HashMap<>();
		paramlabels.put(1, "Fish");
		paramlabels.put(2, "Sharks");
		return paramlabels;
	}

	@Override
	public GridPane paramSliders(List<Integer> params) {
		GridPane grid = new GridPane();
		grid.setTranslateX(LOCATION_OF_PARAM_SLIDERS);
		for (int i=2; i<params.size();i++) {
			Group sliderAndlabel = makeSlider(MIN_PERCENTAGE,MAX_PERCENTAGE,params.get(i),SIZE_OF_GRID,Y_LOCATION_OF_SLIDER);
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
			grid.add(sliderTitle, 0, i-2);
			grid.add(sliderAndlabel, 1, i-2);
		}
		return grid;
	}
}