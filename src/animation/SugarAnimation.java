package animation;

import java.util.HashMap;
import java.util.List;

import javafx.animation.Timeline;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
/**
 * 
 * @author Kei Yoshikoshi
 * Implements the animation for Sugar. Creates specific sliders and labels
 * However, because Slime Mold is not complete, I have not finished implementing this class.
 *
 */
public class SugarAnimation extends GUICreator {

    public SugarAnimation(Timeline animation, Stage s, int fps) throws Exception {
        super(animation, s, fps);
        // TODO Auto-generated constructor stub
    }

    public Slider paramSliders () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HashMap<Integer, String> paramLabels () {
    	HashMap<Integer,String> paramlabels = new HashMap<>();
		paramlabels.put(3, "Ants");
		return paramlabels;
    }

	@Override
	public GridPane paramSliders(List<Integer> params) {
		// TODO Auto-generated method stub
		return null;
	}

}
