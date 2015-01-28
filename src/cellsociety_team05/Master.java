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
    
    public void readXML() throws SAXException {
    	XMLReader xml = XMLReaderFactory.createXMLReader();
		xml.setContentHandler(new Initializer());
		try {
			xml.parse("/Users/keiyoshikoshi/Documents/CS308/workspace/cellsociety_team05/src/cellsociety_team05/example.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
