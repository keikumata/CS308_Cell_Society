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

public class FireAnimation extends GUICreator {
	public FireAnimation(Timeline animation, Stage s, int fps,AnimatedGraph ag) throws Exception {
		super(animation, s, fps,ag);
	}

	@Override
	public HashMap<Integer, String> paramLabels() {
		HashMap<Integer,String> paramlabels = new HashMap<>();
		paramlabels.put(0, "Forest");
		paramlabels.put(1, "Fire");
		paramlabels.put(2, "Burned");
		return paramlabels;
	}

	@Override
	public GridPane paramSliders(List<Integer> params) {
		GridPane grid = new GridPane();
		grid.setTranslateX(LOCATION_OF_PARAM_SLIDERS);

		Group sliderAndlabel = makeSlider(1,100,params.get(1),SIZE_OF_GRID,100);
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
		Label sliderTitle = new Label("Fire Probability");
		grid.add(sliderTitle, 0, 0);
		grid.add(sliderAndlabel, 1, 0);
		
		return grid;
	}
}
