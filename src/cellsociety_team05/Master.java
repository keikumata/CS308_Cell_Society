package cellsociety_team05;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Master {
    private static final int NUM_FRAMES_PER_SECOND = 60;
    private Timeline animation = new Timeline();
    Sim sim;
    SceneUpdater updater;

    public void init (Stage s) throws Exception {
        Initializer initializer = new Initializer();
        initializer.readXML();
        sim = initializer.setup();
        updater = new SceneUpdater(s);
        updater.newScene(sim.getData());
    }
    
    public KeyFrame addKeyFrame (int frameRate) {
        return new KeyFrame(Duration.millis(1000 / frameRate), e -> evolve(e));
    }
    
    private void evolve (ActionEvent e) {
        sim.nextGen();
        try {
            updater.newScene(sim.getData());
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void play(){
        KeyFrame frame = addKeyFrame(NUM_FRAMES_PER_SECOND);
        animation.getKeyFrames().add(frame);
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
    }
}
