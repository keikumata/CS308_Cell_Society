package animation;

import java.util.HashMap;
import java.util.List;

import javafx.animation.Timeline;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ForageAnimation extends GUICreator {

    public ForageAnimation(Timeline animation, Stage s, int fps,AnimatedGraph ag) throws Exception {
        super(animation, s, fps,ag);
        // TODO Auto-generated constructor stub
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
