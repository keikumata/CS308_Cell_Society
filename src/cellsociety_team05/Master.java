package cellsociety_team05;

import java.io.IOException;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Master {
    private static final int NUM_FRAMES_PER_SECOND = 60;
    private Timeline animation;

    public Scene init (Stage s) throws Exception {
        Initializer initializer = new Initializer();
        initializer.readXML();
        Sim sim = initializer.setup();
        SceneUpdater updater = new SceneUpdater();
        return updater.newScene(sim.getData());
    }

    public KeyFrame addKeyFrame (int frameRate) {
        return new KeyFrame(Duration.millis(1000 / frameRate));
    }
    
    public void play(){
        KeyFrame frame = addKeyFrame(NUM_FRAMES_PER_SECOND);
        animation.getKeyFrames().add(frame);
        animation.play();
    }
<<<<<<< HEAD
=======
    
   
>>>>>>> 70fb0a76d6a417bb50e736115b0725f6b71a4602
}
