package animation;

import java.util.HashMap;
import java.util.List;

import javafx.animation.Timeline;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * 
 * @author Kei Yoshikoshi Implements the animation for Slime Mold. Creates
 *         specific sliders and labels However, because Slime Mold is not
 *         complete, I have not finished implementing this class.
 * 
 */
public class SlimeMoldAnimation extends GUICreator {

	public SlimeMoldAnimation(Timeline animation, Stage s, int fps)
			throws Exception {
		super(animation, s, fps);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Creates labels for each type of cell
	 */
	@Override
	public HashMap<Integer, String> paramLabels() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Creates parameter sliders that allows for the user to edit parameters
	 * during the simulation
	 * 
	 * @return null since there are no changeable parameters for the SlimeMold
	 *         (incomplete)
	 */
	@Override
	public GridPane paramSliders(List<Integer> params) {
		// TODO Auto-generated method stub
		return null;
	}

}
