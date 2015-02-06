package cellsociety_team05;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import utility.ColorPicker;
import utility.GUICreator;
import utility.SimData;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

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
	LinkedList<Rectangle> indexMap = new LinkedList<Rectangle>();
	private static final int SIZE_OF_GRID = 400;
	private static final int WIDTH_OF_WINDOW = 600;
	private static final int HEIGHT_OF_WINDOW = 400;
	private Stage s; 
	private GridPane grid;
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
		grid = setUpGridPane(boardSizeK);
		updateBoard(grid, boardSizeK, map);

		Group root = new Group();
		BorderPane border = new BorderPane();
		root.getChildren().add(border);

		Scene wholeScene = new Scene(root, WIDTH_OF_WINDOW, HEIGHT_OF_WINDOW);

		border.setCenter(grid);
		
		GUICreator gc = new GUICreator(ani, s);
//		border.setRight(gc.addButtonGrid());
		root.getChildren().add(gc.addButtonGrid());
		
		s.setScene(wholeScene);
		s.setTitle(simData.simName());
		s.setResizable(false);
	}

	void updateBoard(GridPane grid, int boardSize, int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				fillInRowCol(grid, boardSize, matrix[i][j], i, j);
			}
		}
	}

	public void updateScene(int row, int col, int state){
        int index=row*boardSizeK+col;
        if(state>2){
            state=2;
        }
        Rectangle changedRec=indexMap.get(index);
        changedRec.setFill(stateColorMap.get(state));
        changedRec.setStroke(stateColorMap.get(state));
	}

	private List<Integer> getChangedIndexes (int[][] newMap) {

		List<Integer> changedIndexes = new ArrayList<Integer>();
		for (int i = 0; i < newMap.length; i++) {
			for (int j = 0; j < newMap.length; j++) {
				if(map[i][j]!=newMap[i][j]){
					changedIndexes.add(i*newMap.length+j);
				}
			}
		}
		if(changedIndexes.size()==0){
			System.out.print("steady state reached!");
			ani.stop();

		}
		return changedIndexes;
	}

	void fillInRowCol(GridPane grid, int boardSize, int state, int row, int col) {
		Rectangle r = new Rectangle();            
		if(state>2){
            state=2;
        }
		r.setFill(stateColorMap.get(state));
		r.setStroke(stateColorMap.get(state));
		r.heightProperty().bind(grid.heightProperty().divide(boardSize));
		r.widthProperty().bind(grid.widthProperty().divide(boardSize));
		grid.add(r, col, row);
		indexMap.add(r);
	}

	GridPane setUpGridPane(int boardSize) {
		GridPane grid = new GridPane();
		grid.setGridLinesVisible(true);
		grid.setPrefSize(SIZE_OF_GRID, SIZE_OF_GRID);
		grid.setMaxSize(Region.USE_PREF_SIZE,Region.USE_PREF_SIZE);
		for (int i = 0; i < boardSize; i++) {
			ColumnConstraints c = new ColumnConstraints();
			c.setPercentWidth(100.0 / boardSize);

			RowConstraints r = new RowConstraints();
			r.setPercentHeight(100.0 / boardSize);

			grid.getColumnConstraints().add(c);
			grid.getRowConstraints().add(r);
		}
		return grid;
	}
}
