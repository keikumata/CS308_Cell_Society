package animation;

import java.util.HashMap;

import javafx.animation.Timeline;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class WatorAnimation extends GUICreator {

	public WatorAnimation(Timeline animation, Stage s, int fps,AnimatedGraph ag)
			throws Exception {
		super(animation, s, fps,ag);
	}

	@Override
	public Slider paramSliders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<Integer,String> paramLabels() {
		HashMap<Integer,String> paramlabels = new HashMap<>();
		paramlabels.put(1, "Fish");
		paramlabels.put(2, "Sharks");
		return paramlabels;
	}

}
