package utility;

import javafx.scene.Node;

/**
 * 
 * @author keiyoshikoshi
 * Made to contain information about the cell - we can use this to put in grids/2D arrays instead of numbers
 * 
 */
public class Cell {
	int state;
	Node shape;
	int property;
	public Cell(Node shape, int state, int property) {
		this.state = state;
		this.shape = shape;
		this.property = property;
	}
}
