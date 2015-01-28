package cellsociety_team05;


import java.util.Random;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.GridPane;
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

public class Game extends Application {

	int boardSizeK = 11;
	int[][] gameMatrix = new int[boardSizeK][boardSizeK];
	
	void makeDummyData() {
		for (int i = 0; i < boardSizeK; i++) {
			for (int j = 0; j < boardSizeK; j++) {
				Random rand = new Random();
				gameMatrix[i][j] = rand.nextInt(2); // for some reason this breaks if 3?
			}
		}
	}
	
	@Override
	public void start(Stage stage) throws Exception {

		makeDummyData();  gameMatrix
		
		GridPane grid = setUpGridPane(boardSizeK);
		
		updateBoard(grid, boardSizeK, gameMatrix);
		
		Scene scene = new Scene(grid, 400, 400);
		
		
		stage.setTitle("Grid Pane");
		stage.setScene(scene);
		stage.show();
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
		
		switch (state) {
		case 0:
			r.setFill(Color.ANTIQUEWHITE);
			break;
		case 1:
			r.setFill(Color.BLACK);
			break;
		default:
			Random rand = new Random();
			r.setFill(Color.rgb(rand.nextInt(), rand.nextInt(), rand.nextInt()));
			break;
		}
		
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
