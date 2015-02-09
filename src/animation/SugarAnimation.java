package animation;

import java.util.HashMap;
import java.util.List;

import javafx.animation.Timeline;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * 
 * @author Kei Yoshikoshi Implements the animation for Sugar. Creates specific
 *         sliders and labels However, because Sugar Animation was not complete,
 *         I have not finished implementing this class.
 *
 */
public class SugarAnimation extends GUICreator {

	public SugarAnimation(Timeline animation, Stage s, int fps)
			throws Exception {
		super(animation, s, fps);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Creates labels for each type of cell
	 */
	@Override
	public HashMap<Integer, String> paramLabels() {
		HashMap<Integer, String> paramlabels = new HashMap<>();
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
		// TODO Auto-generated method stub
		return null;
	}

}
