package cellsociety_team05;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Master {
    private static final int NUM_FRAMES_PER_SECOND = 60;
    private Timeline animation = new Timeline();
    Sim sim;

    public Scene init (Stage s) throws Exception {
        Initializer initializer = new Initializer();
        initializer.readXML();
        sim = initializer.setup();
        SceneUpdater updater = new SceneUpdater();
        return updater.newScene(sim.getData());
    }
    
    public KeyFrame addKeyFrame (int frameRate) {
        return new KeyFrame(Duration.millis(1000 / frameRate), e -> evolve(e));
    }
    
    private void evolve (ActionEvent e) {
        sim.nextGen();
    }

    public void play(){
        KeyFrame frame = addKeyFrame(NUM_FRAMES_PER_SECOND);
        animation.getKeyFrames().add(frame);
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
    }
}
