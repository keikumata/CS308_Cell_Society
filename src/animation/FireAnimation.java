package animation;

import java.util.HashMap;

import javafx.animation.Timeline;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class FireAnimation extends GUICreator {

	public FireAnimation(Timeline animation, Stage s, int fps,AnimatedGraph ag) throws Exception {
		super(animation, s, fps,ag);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Slider paramSliders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<Integer, String> paramLabels() {
		HashMap<Integer,String> paramlabels = new HashMap<>();
		paramlabels.put(0, "Forest");
		paramlabels.put(1, "Fire");
		paramlabels.put(2, "Burned");
		return paramlabels;
	}
	
}
