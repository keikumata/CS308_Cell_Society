package simulations;

import java.util.ArrayList;
import java.util.HashMap;

import utility.Pair;

public class SlimeMold {
	
	public class SlimeBox {
		Pair coord;
		boolean containsCell;
		int pheromone;
		int evaporationRate;
		int diffusionRate;
		
		public SlimeBox(Pair coord, boolean containsCell, int pheromone,int evaporationRate, int diffusionRate) {
			this.coord = coord;
			this.containsCell = containsCell;
			this.pheromone = pheromone;
			this.evaporationRate = evaporationRate;
			this.diffusionRate = diffusionRate;
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

		HashMap<Pair, SlimeCell> cellsNext = updateCells(cells, terrain);
		
		
		HashMap<Pair, SlimeBox> terrainNext = updateTerrain(cellsNext, terrain);
	}
	
	public HashMap<Pair, SlimeBox> updateTerrain(HashMap<Pair, SlimeCell> cellsNext, HashMap<Pair, SlimeBox> terrain) {
		
		
		
		
		
		return terrain;
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
				cell.coord = whereNext(cell, terrain);
				cellsNext.put(cell.coord, cell);
			}
		}
		
		return cellsNext;
	}
	
	public Pair whereNext(SlimeCell cell, HashMap<Pair, SlimeBox> terrainXY) {
		
		Pair forward = new Pair(cell.facing.r + cell.coord.r, cell.facing.c + cell.coord.c);
		SlimeBox bForward = terrainXY.get(forward);
		SlimeBox bAdj1, bAdj2;
		
		if (cell.facing.r == 0) {
			bAdj1 = terrainXY.get(new Pair(forward.r - 1, forward.c));
			bAdj2 = terrainXY.get(new Pair(forward.r + 1, forward.c));
		}
		else {
			bAdj1 = terrainXY.get(new Pair(forward.r, forward.c + 1));
			bAdj2 = terrainXY.get(new Pair(forward.r, forward.c - 1));
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
			bNext = terrainXY.get(cell.coord);
		}
		
		return bNext.coord;
	}

}









