package animation;

import java.util.HashMap;
import java.util.List;

import javafx.animation.Timeline;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LifeAnimation extends GUICreator {

	public LifeAnimation(Timeline animation, Stage s, int fps, AnimatedGraph ag) throws Exception {
		super(animation, s, fps, ag);
		// TODO Auto-generated constructor stub
	}


	@Override
	public HashMap<Integer, String> paramLabels() {
		HashMap<Integer,String> paramlabels = new HashMap<>();
		paramlabels.put(1, "Alive");
		return paramlabels;
	}

	@Override
	public GridPane paramSliders(List<Integer> params) {
		return null;
	}
}
