package simulations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import utility.Pair;

public class SlimeMold {
	
	public final int boardLength = 100;
	
	public class SlimeBox {
		Pair coord;
		boolean containsCell;
		int pheromone;
		int evaporationRate;
		
		public SlimeBox(Pair coord, boolean containsCell, int pheromone, int evaporationRate) {
			this.coord = coord;
			this.containsCell = containsCell;
			this.pheromone = pheromone;
			this.evaporationRate = evaporationRate;
		}
	}
	
	public class SlimeCell {
		Pair coord;
		int sniffThreshhold;
		int sniffAngle;
		int depositAmt;
		Pair facing;
		
		public SlimeCell(Pair coord, int sniffThreshhold, int sniffAngle, int depositAmt, Pair facing) {
			this.coord = coord;
			this.sniffThreshhold = sniffThreshhold;
			this.sniffAngle = sniffAngle;
			this.depositAmt = depositAmt;
			this.facing = facing; 
		}
		
	}

	public SlimeMold() {
		HashMap<Pair, SlimeBox> terrain = new HashMap<Pair, SlimeMold.SlimeBox>();
		HashMap<Pair, SlimeCell> cells = new HashMap<Pair, SlimeMold.SlimeCell>();

		cells = updateCells(cells, terrain);
		terrain = updateTerrain(cells, terrain);
		
		
	}
	
	public HashMap<Pair, SlimeBox> updateTerrain(HashMap<Pair, SlimeCell> cellsNext, HashMap<Pair, SlimeBox> terrain) {

		HashMap<Pair, SlimeBox> terrainNext = new HashMap<Pair, SlimeMold.SlimeBox>();
		
		for (Pair boxCoord : terrain.keySet()) {
			SlimeBox box = terrain.get(boxCoord);
			box.pheromone *= box.evaporationRate;
			
			if (cellsNext.get(boxCoord) != null) {
				box.containsCell = true;
			}
			else {
				box.containsCell = false;
			}
			
			terrainNext.put(boxCoord, box);
		}
		
		return terrainNext;
	}
	
	public HashMap<Pair, SlimeCell> updateCells(HashMap<Pair, SlimeCell> cells, HashMap<Pair, SlimeBox> terrain) {

		HashMap<Pair, SlimeCell>  cellsNext = new HashMap<Pair, SlimeCell>();
		
		for (Pair cellCoord : cells.keySet()) {
			
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
	
	public Pair whereNext(SlimeCell cell, HashMap<Pair, SlimeBox> terrainXY) {

		SlimeBox bCur = terrainXY.get(cell.coord);
		
		Pair forward = new Pair(cell.facing.r + cell.coord.r, cell.facing.c + cell.coord.c);
		SlimeBox bForward = terrainXY.get(forward);
		SlimeBox bAdj1, bAdj2;
		
		if (cell.facing.r == 0) {
			if (!(forward.r - 1 < 0)) {
				bAdj1 = terrainXY.get(new Pair(forward.r - 1, forward.c));
			}
			else {
				bAdj1 = bCur;
			}
			if (!(forward.r + 1 > boardLength)) {
				bAdj2 = terrainXY.get(new Pair(forward.r + 1, forward.c));
			}
			else {
				bAdj2 = bCur;
			}
		}
		else {
			if (!(forward.c + 1 > boardLength)) {
				bAdj1 = terrainXY.get(new Pair(forward.r, forward.c + 1));
			}
			else {
				bAdj1 = bCur;
			}
			if (!(forward.c - 1 < 0)) {
				bAdj2 = terrainXY.get(new Pair(forward.r, forward.c - 1));
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
}









