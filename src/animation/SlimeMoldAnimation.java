package animation;

import java.util.HashMap;
import java.util.List;

import javafx.animation.Timeline;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SlimeMoldAnimation extends GUICreator {

	public SlimeMoldAnimation(Timeline animation, Stage s, int fps,AnimatedGraph ag)
			throws Exception {
		super(animation, s, fps,ag);
		// TODO Auto-generated constructor stub
	}

	@Override
	public HashMap<Integer, String> paramLabels() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected GridPane paramSliders(List<Integer> params) {
		// TODO Auto-generated method stub
		return null;
	}

}
