// This entire file is part of my masterpiece.
// Mengchao Feng
package cellsociety_team05;
	
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
    //private Snake myGame;
    private Master master;
    
	@Override
	public void start(Stage s) {
		try {
		    master = new Master();
		    master.init(s);
	        s.show();
	        master.play();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
