package animation;

import java.util.HashMap;
import java.util.List;

import javafx.animation.Timeline;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
/**
 * 
 * @author Kei Yoshikoshi
 * Implements the animation for Game of Life. Creates specific sliders and labels
 *
 */
public class LifeAnimation extends GUICreator {

	public LifeAnimation(Timeline animation, Stage s, int fps) throws Exception {
		super(animation, s, fps);
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
