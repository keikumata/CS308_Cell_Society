package utility;

import java.util.HashMap;
import java.util.LinkedList;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class GridFiller {
	private double r;
	private CellGen cellGen;
	private int boardSize;

	public GridFiller(int gridSize, int boardSize, int cellSides) {
		this.boardSize = boardSize;
		if (cellSides == 6) {
			r = gridSize / (Math.sqrt(3) * (boardSize + 0.5));
			cellGen = new HexGen(r);
		} else if (cellSides == 3) {
			r = gridSize / (boardSize * Math.sqrt(3) / 2);
			cellGen = new TriGen(r);
		} else {
			r = gridSize / boardSize;
			cellGen = new RecGen(r);
		}
	}

	public Group fill(int[][] map, LinkedList<Shape> indexMap,
			HashMap<Integer, Color> stateColorMap, int type) {
		Group g = new Group();
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				Polygon polygon = cellGen.makeShape(i, j);
				indexMap.add(polygon);
				int state = map[i][j];
				if (type == 3 && state > 2) {
					state = 2;
				}
				polygon.setStroke(stateColorMap.get(state));
				polygon.setFill(stateColorMap.get(state));
				g.getChildren().add(polygon);
			}
		}

		return g;
	}

}
