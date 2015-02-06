package cellsociety_team05;

import java.util.HashMap;
import java.util.LinkedList;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import utility.ColorPicker;
import utility.GUICreator;
import utility.GridFiller;
import utility.SimData;

// http://docs.oracle.com/javafx/2/layout/builtin_layouts.htm
// http://docs.oracle.com/javafx/2/api/javafx/scene/layout/GridPane.html#setGridLinesVisible%28boolean%29
// http://www.javacodegeeks.com/2012/07/javafx-20-layout-panes-gridpane.html
// http://stackoverflow.com/questions/23272924/dynamically-add-elements-to-a-fixed-size-gridpane-in-javafx
// http://stackoverflow.com/questions/22002810/javafx-draw-on-center-pane-based-on-user-input-in-left-pane
// http://stackoverflow.com/questions/23272924/dynamically-add-elements-to-a-fixed-size-gridpane-in-javafx
// http://stackoverflow.com/questions/9830206/can-a-gridpane-automatically-resize-its-objects-to-fit-trying-to-set-a-max-wid
// http://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html
// http://www.javacodegeeks.com/2012/07/javafx-20-layout-panes-gridpane.html
// http://stackoverflow.com/questions/13543457/how-do-you-create-a-dictionary-in-java
// http://stackoverflow.com/questions/16148575/hashmap-and-int-as-key

public class SceneUpdater{
	HashMap<Integer, Color> stateColorMap = new HashMap<>();
	LinkedList<Shape> indexMap = new LinkedList<Shape>();
	private static final int WIDTH_OF_WINDOW = 600;
	private static final int HEIGHT_OF_WINDOW = 400;
	private Stage s;
	private int boardSizeK;
	private int[][] map;
	private Timeline ani;

	public SceneUpdater(Stage s, Timeline animation) {
		this.s = s;
		ani=animation;
	}

	public void newScene(SimData simData) throws Exception {
		map=simData.getMap();
		boardSizeK=map[0].length;
		stateColorMap=ColorPicker.setColors(simData.simType());
		GridFiller gridFiller = new GridFiller(HEIGHT_OF_WINDOW,boardSizeK,simData.simShape());
		Group root = gridFiller.fill(map,indexMap,stateColorMap);
		Scene wholeScene = new Scene(root, WIDTH_OF_WINDOW, HEIGHT_OF_WINDOW);
		GUICreator gc = new GUICreator(ani, s);
		root.getChildren().add(gc.addButtonGrid());
		s.setScene(wholeScene);
		s.setTitle(simData.simName());
		s.setResizable(false);
	}

	public void updateScene(int i,int j,int state){
        int index=i*boardSizeK+j;
        if(state>2){
            state=2;
        }
        Shape changedRec=indexMap.get(index);
        changedRec.setFill(stateColorMap.get(state));
        changedRec.setStroke(stateColorMap.get(state));
	}
}
