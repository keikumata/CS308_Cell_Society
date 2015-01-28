package cellsociety_team05;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

// http://docs.oracle.com/javafx/2/layout/builtin_layouts.htm
// http://docs.oracle.com/javafx/2/api/javafx/scene/layout/GridPane.html#setGridLinesVisible%28boolean%29
// http://www.javacodegeeks.com/2012/07/javafx-20-layout-panes-gridpane.html
// http://stackoverflow.com/questions/23272924/dynamically-add-elements-to-a-fixed-size-gridpane-in-javafx



public class Game extends Application {

	int boardSizeConstant = 11;
	
	@Override
	public void start(Stage stage) throws Exception {
		
		GridPane grid = setUpGridPane(boardSizeConstant);
		
		Rectangle r = new Rectangle(50,50);
		r.setFill(Color.DARKMAGENTA);
		r.setX(50);
		r.setY(78);

		grid.add(r, 3, 2);
		
		Rectangle r2 = new Rectangle();
		
		r2.heightProperty().bind(grid.heightProperty().divide(boardSizeConstant));
		r2.widthProperty().bind(grid.widthProperty().divide(boardSizeConstant));
		
		r2.setFill(Color.DARKMAGENTA);

		grid.add(r2, 4, 5);
		

		
		
		Scene scene = new Scene(grid, 400, 400);
		
		
		stage.setTitle("Grid Pane");
		stage.setScene(scene);
		stage.show();
	}
	
	void fillGridPane(int row, int col) {
		
		
		Rectangle r = new Rectangle(50,50);
		r.setFill(Color.DARKMAGENTA);
		r.setX(50);
		r.setY(78);
	//	r.heightProperty().bind(grid)
	//	grid.add(r, 3, 2);
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
