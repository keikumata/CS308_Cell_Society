package cellsociety_team05;

import java.util.HashMap;
import java.util.LinkedList;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import utility.ColorPicker;
import utility.GridFiller;
import utility.SimData;
import animation.AnimatedGraph;
import animation.FireAnimation;
import animation.GUICreator;
import animation.LifeAnimation;
import animation.SchellingAnimation;
import animation.SlimeMoldAnimation;
import animation.WatorAnimation;

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
	private static final int WIDTH_OF_WINDOW = 800;
	private static final int HEIGHT_OF_WINDOW = 400;
	private Stage s;
	private int boardSizeK;
	private int[][] map;
	private Timeline ani;
	private int fps;
	private GUICreator gc;
	private int type;
	private AnimatedGraph ag;
	private int count=0;

	public SceneUpdater(Stage s, Timeline animation, int fps){
		this.s = s;
		ani=animation;
		this.fps = fps;
	}

	public void newScene(SimData simData) throws Exception {
		type = simData.simType();
		map=simData.getMap();
		boardSizeK=map[0].length;
		stateColorMap=ColorPicker.setColors(simData.simType());

		GridFiller gridFiller = new GridFiller(HEIGHT_OF_WINDOW,boardSizeK,simData.simShape());
		Group grid = gridFiller.fill(map,indexMap,stateColorMap);
		Scene wholeScene = new Scene(grid, WIDTH_OF_WINDOW, HEIGHT_OF_WINDOW);
		gc = returnGUI();

		ag = new AnimatedGraph(gc.paramLabels());

		grid.getChildren().add(gc.addButtonGrid());
		grid.getChildren().add(gc.makeSlider());
		grid.getChildren().add(ag.init());

		s.setScene(wholeScene);
		s.setTitle(simData.simName());
		s.setResizable(false);
	}
	public void updateGraph(HashMap<Integer,Integer> cellProportions) {
		ag.addData(count++, cellProportions);
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
	/**
	 * DUPLICATE CODE WITH READER CLASS - need to find a way to fix - Kei
	 * @return
	 * @throws Exception
	 */
	public GUICreator returnGUI() throws Exception{
		GUICreator gui = null;
		switch (type) {
		case 1:
			gui = new SchellingAnimation(ani,s,fps, ag);
			break;
		case 2:
			gui = new FireAnimation(ani,s,fps,ag);
			break;
		case 3:
			gui = new WatorAnimation(ani,s,fps,ag);
			break;
		case 4:
			gui = new LifeAnimation(ani,s,fps,ag);
			break;
		case 5:
			gui = new SlimeMoldAnimation(ani,s,fps,ag);
			break;
		}
		return gui;
	}
	public HashMap<Integer,String> getParameterLabels() {
		return gc.paramLabels();
	}
}
