package cellsociety_team05;

import java.util.HashMap;

import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
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
	private static final int SIZE_OF_WINDOW = 400;

	private Stage s; 
	public SceneUpdater(Stage s) {
		this.s = s;
	}

    public void newScene(SimData simData) throws Exception {
		int[][] map=simData.getMap();
	    int boardSizeK=map[0].length;
	    stateColorMap=ColorPicker.setColors(simData.simType());
		GridPane grid = setUpGridPane(boardSizeK);
		updateBoard(grid, boardSizeK, map);
		s.setScene(new Scene(grid, SIZE_OF_WINDOW, SIZE_OF_WINDOW));
	}
	
	void updateBoard(GridPane grid, int boardSize, int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				fillInRowCol(grid, boardSize, matrix[i][j], i, j);
			}
		}
	}
	
	void fillInRowCol(GridPane grid, int boardSize, int state, int row, int col) {
		Rectangle r = new Rectangle();
		
		r.setFill(stateColorMap.get(state));
		r.setStroke(stateColorMap.get(state));
		r.heightProperty().bind(grid.heightProperty().divide(boardSize));
		r.widthProperty().bind(grid.widthProperty().divide(boardSize));
		
		grid.add(r, row, col);

	}
	
	GridPane setUpGridPane(int boardSize) {
		GridPane grid = new GridPane();
		grid.setGridLinesVisible(true);

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
