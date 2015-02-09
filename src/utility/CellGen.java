package utility;

import javafx.scene.shape.Polygon;

public abstract class CellGen {
	protected double cellLength;

	public CellGen(double r) {
		cellLength = r;
	}

	public abstract Polygon makeShape(int i, int j);

}