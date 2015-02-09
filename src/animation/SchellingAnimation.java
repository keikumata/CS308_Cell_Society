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

public class SchellingAnimation extends GUICreator {

	public SchellingAnimation(Timeline animation, Stage s, int fps,AnimatedGraph ag)
			throws Exception {
		super(animation, s, fps,ag);
		// TODO Auto-generated constructor stub
	}

	@Override
	public HashMap<Integer, String> paramLabels() {
		HashMap<Integer,String> paramlabels = new HashMap<>();
		paramlabels.put(1, "Blue");
		paramlabels.put(2, "Red");
		return paramlabels;
	}

	@Override
	public GridPane paramSliders(List<Integer> params) {
		GridPane grid = new GridPane();
		grid.setTranslateX(LOCATION_OF_PARAM_SLIDERS);
		Group sliderAndlabel = makeSlider(1,100,params.get(2),SIZE_OF_GRID,100);
		Slider s = (Slider) sliderAndlabel.getChildren().get(0);
		Label paramLabel = (Label) sliderAndlabel.getChildren().get(1);
		s.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
				paramLabel.setText(String.format("%.1f", newValue.doubleValue()));
				//				if (newParams.isEmpty()) 
				//					newParams.add((int) newValue.doubleValue());
				//				else 
				//					newParams.set(0, (int) newValue.doubleValue());
				newParams.put(0, (int) newValue.doubleValue());
			}
		});
		Label sliderTitle = new Label("Threshold");
		grid.add(sliderTitle, 0, 0);
		grid.add(sliderAndlabel, 1, 0);

		return grid;
	}
}
