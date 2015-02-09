package utility;

import java.util.Random;

public class Ant {
	private int age = 0;
	private int life;
	public int dir;
	private final int[][] directions = { { -1, 0 }, { 0, 1 }, { 1, 0 },
			{ 0, -1 } };
	public int[] coor;
	public boolean hasFood = false;
	private int foraged = 0;

	public Ant(Random rand, int life, int[] nestCoor) {
		coor = nestCoor;
		this.life = life;
		dir = rand.nextInt(4);
	}

	public void changeDir(int[] next) {
		int rowMove = next[0] - coor[0];
		int colMove = next[1] - coor[1];
		if (dir % 2 == 1 && rowMove < 0) {
			dir = 0;
		} else if (dir % 2 == 1 && rowMove > 0) {
			dir = 2;
		} else if (dir % 2 == 0 && colMove < 0) {
			dir = 3;
		} else if (dir % 2 == 0 && colMove > 0) {
			dir = 1;
		}
	}

	public int[][] getFrontNeighbors() {
		int[][] front = new int[3][2];
		if (dir % 2 == 1) {
			front[0][0] = coor[0];
			front[1][0] = coor[0] + 1;
			front[2][0] = coor[0] - 1;
			front[0][1] = coor[1] + directions[dir][1];
			front[1][1] = coor[1] + directions[dir][1];
			front[2][1] = coor[1] + directions[dir][1];
		} else {
			front[0][0] = coor[0] + directions[dir][0];
			front[1][0] = coor[0] + directions[dir][0];
			front[2][0] = coor[0] + directions[dir][0];
			front[0][1] = coor[1];
			front[1][1] = coor[1] + 1;
			front[2][1] = coor[1] - 1;
		}
		return front;
	}

	public boolean grow() {
		age++;
		if (age > life) {
			return true;
		} else {
			return false;
		}
	}

	public int[][] getSideNeighbors() {
		int[][] side = new int[5][2];
		if (dir % 2 == 1) {
			side[0][0] = coor[0] + 1;
			side[0][1] = coor[1];
			side[1][0] = coor[0] - 1;
			side[1][1] = coor[1];
			side[2][0] = coor[0] + 1;
			side[2][1] = coor[1] + directions[dir][1] * (-1);
			side[3][0] = coor[0];
			side[3][1] = coor[1] + directions[dir][1] * (-1);
			side[4][0] = coor[0] - 1;
			side[4][1] = coor[1] + directions[dir][1] * (-1);
		} else {
			side[0][0] = coor[0];
			side[0][1] = coor[1] + 1;
			side[1][0] = coor[0];
			side[1][1] = coor[1] - 1;
			side[2][0] = coor[0] + directions[dir][0] * (-1);
			side[2][1] = coor[1] + 1;
			side[3][0] = coor[0] + directions[dir][0] * (-1);
			side[3][1] = coor[1];
			side[4][0] = coor[0] + directions[dir][0] * (-1);
			side[4][1] = coor[1] - 1;
		}
		return side;
	}

	public void moveTo(int[] coor) {
		this.coor = coor;
	}

	public int atHome() {
		if (hasFood) {
			foraged++;
			return 1;
		}
		hasFood = false;
		return 0;
	}

	public void atFood() {
		hasFood = true;
	}

}
