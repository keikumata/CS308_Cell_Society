package simulations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import utility.MapCopier;
import utility.Neighborhood;
import cellsociety_team05.SceneUpdater;

/**
 * 
 * @author Kei Yoshikoshi, Nic Sab, MengChao Feng Simulates Schelling's Model of
 *         Segregation. Is able to update the 2-D array given some data and a
 *         set of rules
 *
 */
public class Schelling extends Sim {
	private int threshold;
	private int blueTotal;
	private int redTotal;

	/**
	 * 
	 * @param game
	 *            : Game type
	 * @param cellTypes
	 *            : how many types of cells
	 * @param size
	 *            : size of grid
	 * @param delay
	 *            : the FPS
	 * @param cellSides
	 *            : how many sides to each cell (polygon)
	 * @param params
	 *            : list of parameters that vary per simulation
	 */
	public Schelling(int game, int cellTypes, int size, int delay,
			int cellSides, List<Integer> params) {
		super(game, cellTypes, size, delay, cellSides, params);
		blueTotal = params.get(0);
		redTotal = params.get(1);
		threshold = params.get(2); // 3rd parameter
	}

	/**
	 * Updates the next generation of cells
	 */
	public void nextGen(SceneUpdater updater) {
		int[][] tempMap = MapCopier.copyOfArray(map);
		List<Integer> emptyCells = getEmptyCells();
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map.length; col++) {
				if (!computeTorrodialHood(row, col)) { // computeNeighbourhood
					updateState(row, col, tempMap, emptyCells, updater);
				}
			}
		}
		this.map = MapCopier.copyOfArray(tempMap);
	}

	/**
	 * Takes in different parameters to update the state of a specific cell
	 * 
	 * @param row
	 * @param col
	 * @param tempMap
	 *            : a copy of the grid
	 * @param emptyCells
	 *            : list of empty cells
	 * @param updater
	 *            : SceneUpdater object
	 */
	private void updateState(int row, int col, int[][] tempMap,
			List<Integer> emptyCells, SceneUpdater updater) {
		int cellState = map[row][col];
		tempMap[row][col] = 0;
		Random randomGenerator = new Random();
		int oldIndex = row * map.length + col;
		int newIndex = randomGenerator.nextInt(emptyCells.size());
		int moveTo = emptyCells.get(newIndex);
		emptyCells.remove(newIndex);
		emptyCells.add(oldIndex);
		int y = moveTo % map.length;
		int x = (moveTo - y) / map.length;
		tempMap[x][y] = cellState;
		updater.updateScene(x, y, cellState);
		updater.updateScene(row, col, 0);
	}

	/**
	 * Computes neighborhood for the torroidal
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean computeTorrodialHood(int row, int col) {
		int cellState = map[row][col];
		int same = 0;
		int total = 0;
		neighbors = Neighborhood.getTorrodialNeighbors();
		if (cellState != 0) {
			for (int[] n : neighbors) {
				if (map[Math.abs((row + n[0]) % map.length)][Math
						.abs((col + n[1]) % map.length)] != 0) {
					if (map[Math.abs((row + n[0]) % map.length)][Math
							.abs((col + n[1]) % map.length)] == cellState) {
						same++;
					}
					total++;
				}
			}
			return total != 0 && (same * 100 / total) >= threshold;
		} else {
			return true;
		}
	}

	/**
	 * Looks at the neighbors and returns whether is satisfied or not
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean computeNeighbourhood(int row, int col) {
		int cellState = map[row][col];
		int same = 0;
		int total = 0;
		neighbors = Neighborhood.getNeighbors(cellSides, col, 8);
		if (cellState != 0) {
			for (int[] n : neighbors) {
				if ((row + n[0] >= 0 && row + n[0] < map.length)
						&& (col + n[1] >= 0 && col + n[1] < map.length)) {
					if (map[row + n[0]][col + n[1]] != 0) {
						if (map[row + n[0]][col + n[1]] == cellState) {
							same++;
						}
						total++;
					}
				}
			}
			return total != 0 && (same * 100 / total) >= threshold;
		} else {
			return true;
		}
	}

	/**
	 * Title of the simulation
	 */
	public String simTitle() {
		return "Schelling's Model of Segregation";
	}

	/**
	 * @return HashMap that contains the cell proportions
	 */
	@Override
	public HashMap<Integer, Integer> cellProportions() {
		HashMap<Integer, Integer> ret = new HashMap<>();
		ret.put(1, blueTotal);
		ret.put(2, redTotal);
		return ret;
	}

	/**
	 * Sets the new parameters that the user changed during simulation
	 * 
	 * @param: Map that contains the new parameters
	 */
	@Override
	public void setNewParams(HashMap<Integer, Integer> params) {
		if (!params.isEmpty())
			threshold = params.get(0);
	}
}