package simulations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import cellsociety_team05.SceneUpdater;
import utility.MapCopier;

public class SlimeMold extends Sim{

	public final int boardLength = 100;

	public class SlimeBox {
		int coord;
		boolean containsCell;
		int pheromone;
		int evaporationRate;

		public SlimeBox(int coord, boolean containsCell, int pheromone, int evaporationRate) {
			this.coord = coord;
			this.containsCell = containsCell;
			this.pheromone = pheromone;
			this.evaporationRate = evaporationRate;
		}
	}

	public class SlimeCell {
		int coord;
		int sniffThreshhold;
		int sniffAngle;
		int depositAmt;
		int facing;

		public SlimeCell(int coord, int sniffThreshhold, int sniffAngle, int depositAmt, int facing) {
			this.coord = coord;
			this.sniffThreshhold = sniffThreshhold;
			this.sniffAngle = sniffAngle;
			this.depositAmt = depositAmt;
			this.facing = facing; 
		}

	}

	private HashMap<Integer, SlimeBox> terrain = new HashMap<Integer, SlimeMold.SlimeBox>();
	private HashMap<Integer, SlimeCell> cells = new HashMap<Integer, SlimeMold.SlimeCell>();
	private int evaporationRate;

	public SlimeMold(int game, int cellTypes, int size, int fps, int cellSides, List<Integer> params) {
		super(game, cellTypes, size, fps, cellSides, params);
		evaporationRate = params.get(2);
	}
	
	private void prepGrid(int[][] tempMap) {
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map.length; col++) {
				int pp = row*map.length + col;
				
				if (tempMap[row][col]==1) {
					terrain.put(pp, new SlimeBox(pp, tempMap[row][col]==1, 10, evaporationRate));
				}
				else {
					int pp2 = (row+1)*map.length + col+1;
					cells.put(pp, new SlimeCell(pp, 50, 50, 50, pp2));
				}
			}
		}
	}
	@Override
	public void nextGen(SceneUpdater updater) {
		int[][] tempMap = MapCopier.copyOfArray(map);
		prepGrid(tempMap);
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map.length; col++) {
				cells = updateCells(cells, terrain);
				terrain = updateTerrain(cells, terrain,row,col,tempMap);

				updater.updateScene(row,col, tempMap[row][col]);
			}
		}
		this.map = tempMap;
	}

	public HashMap<Integer,SlimeBox> updateTerrain(HashMap<Integer, SlimeCell> cellsNext, HashMap<Integer, SlimeBox> terrain, int row, int col, int[][] tempMap) {

		HashMap<Integer, SlimeBox> terrainNext = new HashMap<Integer, SlimeMold.SlimeBox>();

		for (int boxCoord : terrain.keySet()) {
			SlimeBox box = terrain.get(boxCoord);
			box.pheromone *= box.evaporationRate;
			int cB = boxCoord % boardLength;
			int rB = (boxCoord-cB) / boardLength;
			if (rB==row && cB==col && cellsNext.get(boxCoord) != null) {
				tempMap[row][col] = 1;
				box.containsCell = true;
				System.out.format("Row %d and Col %d has a slimecell\n", row, col);
			}
			else {
				tempMap[row][col] = 0;
				box.containsCell = false;
			}

			terrainNext.put(boxCoord, box);
		}
		return terrainNext;

	}


	public HashMap<Integer, SlimeCell> updateCells(HashMap<Integer, SlimeCell> cells, HashMap<Integer, SlimeBox> terrain) {

		HashMap<Integer, SlimeCell>  cellsNext = new HashMap<Integer, SlimeCell>();

		for (int cellCoord : cells.keySet()) {

			SlimeCell cell = cells.get(cellCoord);

			SlimeBox box = terrain.get(cell);
			if (box.pheromone >= cell.sniffThreshhold) {
				cellsNext.put(cell.coord, cell);
			}
			else {
				SlimeCell tempCell = new SlimeCell(cell.coord, cell.sniffThreshhold, cell.sniffAngle, cell.depositAmt, cell.facing);
				cell.coord = whereNext(cell, terrain);
				cellsNext.put(cell.coord, cell);

				if (tempCell.coord != cell.coord) {
					SlimeBox tempBox = terrain.get(tempCell.coord);
					tempBox.pheromone += tempCell.depositAmt;
				}
			}
		}


		return cellsNext;
	}

	public int whereNext(SlimeCell cell, HashMap<Integer, SlimeBox> terrainXY) {

		SlimeBox bCur = terrainXY.get(cell.coord);

		int cF = cell.facing % boardLength;
		int rF = (cell.facing-cF) / boardLength;
		SlimeBox bForward = terrainXY.get(rF*boardLength+cF);
		SlimeBox bAdj1, bAdj2;

		if (rF == 0) {
			if (!(rF - 1 < 0)) {
				bAdj1 = terrainXY.get((rF - 1)*boardLength + cF);
			}
			else {
				bAdj1 = bCur;
			}
			if (!(rF + 1 > boardLength)) {
				bAdj2 = terrainXY.get((rF + 1)*boardLength+ cF);
			}
			else {
				bAdj2 = bCur;
			}
		}
		else {
			if (!(cF + 1 > boardLength)) {
				bAdj1 = terrainXY.get(rF*boardLength+ cF + 1);
			}
			else {
				bAdj1 = bCur;
			}
			if (!(cF - 1 < 0)) {
				bAdj2 = terrainXY.get(rF*boardLength + cF - 1);
			}
			else {
				bAdj2 = bCur;
			}
		}

		ArrayList<SlimeBox> terrainAdj = new ArrayList<SlimeMold.SlimeBox>();
		terrainAdj.add(bForward);
		terrainAdj.add(bAdj1);
		terrainAdj.add(bAdj2);

		SlimeBox bNext = bForward;
		boolean flag = false;
		if (bForward.containsCell == true) {
			flag = true;
		}
		for (SlimeBox b: terrainAdj) {
			if (b.pheromone > bNext.pheromone && b.containsCell == false) {
				bNext = b;
			}
		}
		if (flag == true && bNext == bForward) {
			bNext = bCur;
		}
		else if (bForward.pheromone == bAdj1.pheromone && bForward.pheromone == bAdj2.pheromone) {
			Random random = new Random();
			bNext = terrainAdj.get(random.nextInt(terrainAdj.size()));
		}
		return bNext.coord;
	}


	@Override
	public String simTitle() {
		return "Slime Mold";
	}
	@Override
	public HashMap<Integer, Integer> cellProportions() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setNewParams(HashMap<Integer, Integer> params) {
		// TODO Auto-generated method stub
		
	}
}









