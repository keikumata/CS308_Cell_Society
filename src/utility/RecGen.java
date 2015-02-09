package utility;

import javafx.scene.shape.Polygon;

public class RecGen extends CellGen {

	public RecGen(double r) {
		super(r);
	}

	public Polygon makeShape(int i, int j) {
		Polygon polygon = new Polygon();
		polygon.getPoints().addAll(
				new Double[] { cellLength * i, cellLength * j,
						cellLength * (i + 1), cellLength * j,
						cellLength * (i + 1), cellLength * (j + 1),
						cellLength * i, cellLength * (j + 1) });
		return polygon;
	}

}
