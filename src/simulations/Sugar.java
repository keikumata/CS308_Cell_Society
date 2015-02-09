package simulations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import utility.Agent;
import utility.Neighborhood;
import utility.RandomRange;
import cellsociety_team05.SceneUpdater;

public class Sugar extends Sim {
	private int preset;
	private Map<Integer, Agent> agents = new HashMap<Integer, Agent>();
	public int patchCap;
	private int[] initSugar;
	private int[] metabolism;
	private int[] vision;
	private int agentNum = 0;
	private int growBack;
	private int growInt;
	private int worldAge = 0;
	private int[] maxLife;
	private int[] fertile;

	public Sugar(int sim, int cellTypes, int size, int delay, int cellSides,
			List<Integer> params) {
		super(sim, cellTypes, size, delay, cellSides, params);
		double iniagents = (double) params.get(0);
		agentNum = (int) (calculateTotal() * iniagents / 100);
		preset = params.get(1);
		int[] maxInitSugar = { params.get(2), params.get(3) };
		initSugar = maxInitSugar;
		int[] maxMeta = { 1, params.get(4) };
		metabolism = maxMeta;
		int[] maxVision = { 1, params.get(5) };
		vision = maxVision;
		patchCap = params.get(6);
		growBack = params.get(7);
		growInt = params.get(8);
		int[] maxLife = { params.get(9), params.get(10) };
		int[] fertile = { params.get(11), params.get(12) };
		this.fertile = fertile;
		this.maxLife = maxLife;
	}

	public void initMap() {
		double unpopulated = Math.pow(size, 2);
		int[] population = new int[cellTypes];
		for (int i = 0; i < cellTypes; i++) {
			population[i] = (int) (unpopulated * rand.nextDouble());
			unpopulated -= population[i];
			populate(i + 1, population[i], size);
		}
		List<Integer> index = new ArrayList<Integer>();
		for (int i = 0; i < agentNum; i++) {
			fillAgents(index);
		}
	}

	private void fillAgents(List<Integer> index) {
		int agentRow = RandomRange.randInt(0, size - 1, rand);
		int agentCol = RandomRange.randInt(0, size - 1, rand);
		int agentIndex = agentRow * size + agentCol;
		if (!index.contains(agentIndex)) {
			index.add(agentIndex);
			int[] loc = { agentRow, agentCol };
			map[agentRow][agentCol] = 0;
			setCell(agentRow, agentCol, 5);
			if (preset == 1) {
				agents.put(agentIndex, new Agent(initSugar, vision, metabolism,
						rand, loc, preset, size));
			} else {
				agents.put(agentIndex, new Agent(initSugar, vision, metabolism,
						rand, loc, preset, size, maxLife, fertile));
			}
			return;
		} else {
			fillAgents(index);
		}
	}

	public void nextGen(SceneUpdater updater) {
		worldAge++;
		updateAgent(updater);
		if (worldAge % growInt == 0) {
			growSugar(updater);
		}
	}

	private void growSugar(SceneUpdater updater) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (map[i][j] < 4) {
					map[i][j] += growBack;
					updater.updateScene(i, j, map[i][j]);
				}
			}
		}
	}

	private void updateAgent(SceneUpdater updater) {
		int[] next = null;
		Iterator<Entry<Integer, Agent>> it = agents.entrySet().iterator();
		Map<Integer, Integer> changedAgents = new HashMap<Integer, Integer>();
		Map<Integer, Agent> babies = new HashMap<Integer, Agent>();
		while (it.hasNext()) {
			Entry<Integer, Agent> agentEntry = it.next();
			Agent agent = agentEntry.getValue();
			next = agent.findNext(map, rand);
			if (preset == 2) {
				agent.mated = new ArrayList<Integer>();
				int[][] neighbors = Neighborhood.getNeighbors(4, 1, 4);
				for (int[] neighbor : neighbors) {
					int mateIndex = (agent.loc[0] + neighbor[0]) * map.length
							+ agent.loc[1] + neighbor[1];
					if ((agent.loc[0] + neighbor[0] >= 0 && agent.loc[0]
							+ neighbor[0] < map.length)
							&& (agent.loc[1] + neighbor[1] >= 0 && agent.loc[1]
									+ neighbor[1] < map.length)
							&& map[agent.loc[0] + neighbor[0]][agent.loc[1]
									+ neighbor[1]] == 5
							&& agents.get(mateIndex) != null) {
						Agent thisAgent = agents.get(mateIndex);
						Integer babyIndex = agent.mate(thisAgent, map, rand,
								babies);
						if (babyIndex != null) {
							agent.mated.add(mateIndex);
							int babySugar = (int) (rand.nextDouble()
									* agent.sugar + rand.nextDouble()
									* thisAgent.sugar);
							int babyVision = (int) (rand.nextDouble()
									* agent.vision + rand.nextDouble()
									* thisAgent.vision);
							int babyMeta = (int) (rand.nextDouble()
									* agent.meta + rand.nextDouble()
									* thisAgent.meta);
							int babyLife = (int) (rand.nextDouble()
									* agent.life + rand.nextDouble()
									* thisAgent.life);
							int babyferSu = (int) (rand.nextDouble()
									* agent.ferSugar + rand.nextDouble()
									* thisAgent.ferSugar);
							int babyCol = babyIndex % size;
							int babyRow = (babyIndex - babyCol) / size;
							int[] baby = { babyRow, babyCol };
							babies.put(babyIndex, new Agent(babySugar,
									babyVision, babyMeta, 0, babyferSu, rand,
									baby, 2, size, babyLife, fertile));
							agentNum++;
						}
					}
				}
			}
			if (next == null) {
				next = agent.loc;
			} else {
				agent.eat(map[next[0]][next[1]]);
				changedAgents.put(agent.loc[0] * size + agent.loc[1], next[0]
						* map.length + next[1]);
			}
			if (agent.grow()) {
				changedAgents.put(agent.loc[0] * size + agent.loc[1], null);
				agentNum--;
			}
		}
		updateHash(changedAgents, babies, updater);
	}

	private void updateHash(Map<Integer, Integer> changedAgents,
			Map<Integer, Agent> babies, SceneUpdater updater) {
		Iterator<Entry<Integer, Integer>> it = changedAgents.entrySet()
				.iterator();
		while (it.hasNext()) {
			Entry<Integer, Integer> agentEntry = it.next();
			Integer oldAgent = agentEntry.getKey();
			Integer newAgent = agentEntry.getValue();
			Agent temp = agents.get(oldAgent);
			if (newAgent != null) {
				int newcol = newAgent % size;
				int newrow = (newAgent - newcol) / size;
				int[] loc = { newrow, newcol };
				setCell(newrow, newcol, 5);
				updater.updateScene(newrow, newcol, 5);
				if (preset == 1) {
					int[] fertile = { 1, 1 };
					agents.put(newAgent, new Agent(temp.sugar, temp.vision,
							temp.meta, temp.age, temp.ferSugar, rand, loc,
							preset, size, 1, fertile));
				} else {
					agents.put(newAgent, new Agent(temp.sugar, temp.vision,
							temp.meta, temp.age, temp.ferSugar, rand, loc,
							preset, size, temp.life, temp.fertile));
				}
			}
			int oldcol = oldAgent % size;
			int oldrow = (oldAgent - oldcol) / size;
			setCell(oldrow, oldcol, 0);
			updater.updateScene(oldrow, oldcol, 0);
			agents.remove(oldAgent);
			it.remove();
		}
		Iterator<Entry<Integer, Agent>> itB = babies.entrySet().iterator();
		while (itB.hasNext()) {
			Entry<Integer, Agent> agentEntry = itB.next();
			Integer babyIndex = agentEntry.getKey();
			Agent baby = agentEntry.getValue();
			int newcol = babyIndex % size;
			int newrow = (babyIndex - newcol) / size;
			agents.put(babyIndex, baby);
			setCell(newrow, newcol, 5);
			updater.updateScene(newrow, newcol, 5);
		}
		Iterator<Entry<Integer, Agent>> itA = agents.entrySet().iterator();
		while (itA.hasNext()) {
			Entry<Integer, Agent> agentEntry = itA.next();
			if (agentEntry.getValue() == null) {
				int oldAgent = agentEntry.getKey();
				int oldcol = oldAgent % size;
				int oldrow = (oldAgent - oldcol) / size;
				setCell(oldrow, oldcol, 0);
				updater.updateScene(oldrow, oldcol, 0);
				itA.remove();
			}
		}
	}

	public String simTitle() {
		return "Sugarscape";
	}

	@Override
	public HashMap<Integer, Integer> cellProportions() {
		HashMap<Integer, Integer> ret = new HashMap<>();
		ret.put(3, agents.entrySet().size() * 100 / calculateTotal());
		return ret;
	}

	@Override
	public void setNewParams(HashMap<Integer, Integer> params) {
		// TODO Auto-generated method stub

	}

}
