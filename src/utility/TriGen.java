package utility;

import javafx.scene.shape.Polygon;

public class TriGen extends CellGen {

	public TriGen(double r) {
		super(r);
	}

	public Polygon makeShape(int i, int j) {
		Polygon polygon = new Polygon();
		if (i % 2 == 0 && j % 2 == 0) {
			polygon.getPoints().addAll(
					new Double[] {
							cellLength * j / 2,
							(Math.sqrt(3) / 2 + Math.sqrt(3) * i / 2)
									* cellLength,
							(0.5 + j / 2) * cellLength,
							Math.sqrt(3) * i / 2 * cellLength,
							(1 + j / 2) * cellLength,
							(Math.sqrt(3) / 2 + Math.sqrt(3) * i / 2)
									* cellLength });
		} else if (i % 2 == 1 && j % 2 == 0) {
			polygon.getPoints()
					.addAll(new Double[] {
							cellLength * j / 2,
							(Math.sqrt(3) / 2 + Math.sqrt(3)
									* Math.floor(i / 2))
									* cellLength,
							(0.5 + j / 2) * cellLength,
							Math.sqrt(3) * (Math.floor(i / 2) + 1) * cellLength,
							(1 + j / 2) * cellLength,
							(Math.sqrt(3) / 2 + Math.sqrt(3)
									* Math.floor(i / 2))
									* cellLength });
		} else if (i % 2 == 0 && j % 2 == 1) {
			polygon.getPoints().addAll(
					new Double[] {
							(0.5 + Math.floor(j / 2)) * cellLength,
							Math.sqrt(3) * (i / 2) * cellLength,
							(1 + Math.floor(j / 2)) * cellLength,
							(Math.sqrt(3) / 2 + Math.sqrt(3) * (i / 2))
									* cellLength,
							(1.5 + Math.floor(j / 2)) * cellLength,
							Math.sqrt(3) * (i / 2) * cellLength });
		} else if (i % 2 == 1 && j % 2 == 1) {
			polygon.getPoints()
					.addAll(new Double[] {
							(0.5 + Math.floor(j / 2)) * cellLength,
							Math.sqrt(3) * (Math.floor(i / 2) + 1) * cellLength,
							(1 + Math.floor(j / 2)) * cellLength,
							(Math.sqrt(3) / 2 + Math.sqrt(3)
									* Math.floor(i / 2))
									* cellLength,
							(1.5 + Math.floor(j / 2)) * cellLength,
							Math.sqrt(3) * (Math.floor(i / 2) + 1) * cellLength });
		}
		return polygon;
	}
}
