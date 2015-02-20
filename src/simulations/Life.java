package simulations;

import java.util.HashMap;
import java.util.List;

import cellsociety_team05.SceneUpdater;
import utility.MapCopier;
import utility.Neighborhood;

// http://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
// http://stackoverflow.com/questions/16706716/using-two-values-for-one-switch-case-statement
// http://stackoverflow.com/questions/4892992/array-seems-to-be-getting-passed-by-reference-in-java-how-is-this-possible

public class Life extends Sim {
	private int aliveTotal;

	public Life(int sim, int cellTypes, int size, int delay, int cellSides,
			List<Integer> params) {
		super(sim, cellTypes, size, delay, cellSides, params);
	}

	public void nextGen(SceneUpdater updater) {
		int[][] tempMap = MapCopier.copyOfArray(map);
		aliveTotal = 0;
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {

				tempMap[row][col] = updateState(row, col);

				if (updateState(row, col) == 1)
					aliveTotal++;

				updater.updateScene(row, col, tempMap[row][col]);
			}
		}
		map = MapCopier.copyOfArray(tempMap);

		/**
		 * We looked for places that we could replace code with lambda expressions.
		 * However we were unclear on how to replace these double for loops with 
		 * the lambda expressions. We consulted with the TA multiple times to see how 
		 * we would replace this double for loop but they weren't sure either as they
		 * have not used Java 8 for long. This is how we would have implemented it:
		 * 
		 * readWords(input, TagCloud::sanitize, select).stream()
		//         .forEach(w -> {
		//             wordCounts.put(w, wordCounts.getOrDefault(w, 0) + 1);
		//         });
		 * 
		 * So for each 'w', aka the element, we would be doing some operation (in the above example,
		 * it would be putting objects in the map. For our case, for each loop in each loop (double for loop)
		 * we would do the following operations.
		 * 
		 * tempMap[row][col] = updateState(row, col);

				if (updateState(row, col) == 1)
					aliveTotal++;

				updater.updateScene(row, col, tempMap[row][col]);
		 */
		//		 
	}

	private int updateState(int row, int col) {
		int friends = computeNeighbourhood(row, col);
		if (friends < 2 || friends > 3) {
			return 0;
		} else if (friends == 3) {
			return 1;
		} else {
			return map[row][col];
		}
	}

	private int computeNeighbourhood(int row, int col) {
		int friends = 0;

		neighbors = Neighborhood.getTorrodialNeighbors();

		for (int[] n : neighbors) {
			friends += map[Math.abs((row + n[0]) % map.length)][Math
			                                                    .abs((col + n[1]) % map.length)];
		}

		return friends;
	}

	public String simTitle() {
		return "Game of Life";
	}

	@Override
	public HashMap<Integer, Integer> cellProportions() {
		HashMap<Integer, Integer> ret = new HashMap<>();
		ret.put(1, aliveTotal * 100 / calculateTotal());
		return ret;
	}

	@Override
	public void setNewParams(HashMap<Integer, Integer> params) {
		// TODO Auto-generated method stub
	}
}