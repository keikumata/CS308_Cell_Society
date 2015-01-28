package cellsociety_team05;


import java.util.HashMap;
import java.util.Random;

import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
	HashMap<Integer, Color> stateColorMap;
	
	public Scene newScene(SimData simData) throws Exception {
		int[][] map=simData.getMap();
	    int boardSizeK=map[0].length;
		
		GridPane grid = setUpGridPane(boardSizeK);
		
		updateBoard(grid, boardSizeK, map);
		
		return new Scene(grid, 769, 769);
	}
	
	void updateBoard(GridPane grid, int boardSize, int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				fillInRowCol(grid, boardSize, matrix[i][j], i, j);
			}
		}
	}
	
	Color getStates(int state) {
		if(!stateColorMap.containsKey(state)) {
			Random rand = new Random();
			Color c = Color.color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble());
			int i = 0;
			while (stateColorMap.containsValue(c) && i < (255*255*255) ) { 
				c = Color.color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble());
				i++;
			}
			stateColorMap.put(state, c);
		}
		return stateColorMap.get(state);
	}
	
	void fillInRowCol(GridPane grid, int boardSize, int state, int row, int col) {
		Rectangle r = new Rectangle();
		r.setFill(getStates(state));

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
