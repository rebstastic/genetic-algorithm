package hr.fer.thesis.ga.initialpopulation;

import hr.fer.thesis.ga.Chromosome;
import hr.fer.thesis.ga.GAData;
import hr.fer.thesis.ga.Population;
import hr.fer.thesis.kp.Item;
import hr.fer.thesis.kp.KPData;
import hr.fer.thesis.kp.Knapsack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GreedyInitialPopulation implements IInitialPopulation {

	/**
	 * Size of the restricted candidate list. It is always equal to the
	 * population size to provide diversity of chromosomes.
	 */
	private static int RLC_SIZE;

	private List<Item> items;

	private Chromosome generateChromosome() {
		Chromosome chromosome = new Chromosome(KPData.size());
		Knapsack knapsack = new Knapsack(KPData.capacity);
		double oldWeight = 0;
		while (true) {

			/*
			 * Find the items with highest value that would fit the knapsack.
			 */
			List<Item> RLC = new ArrayList<>();
			for (Item item : items) {
				if (item.getWeight() <= knapsack.getFreeSpace()) {
					RLC.add(item);
					if (RLC.size() == RLC_SIZE) {
						break;
					}
				}
			}

			/*
			 * There were no items that could fit the knapsack.
			 */
			if (RLC.size() == 0) {
				break;
			}

			/*
			 * Randomly choose one item from the restricted candidate list.
			 */
			int index = GAData.random.nextInt(RLC.size());
			Item item = RLC.get(index);

			/*
			 * Add the chosen item to the knapsack if it isn't already there.
			 */
			if (!chromosome.getGene(item.getIndex())) {
				chromosome.setGene(item.getIndex(), true);
				knapsack.addItem(item);
				if (knapsack.getFreeSpace() <= 0) {
					break;
				}
			}

			if (Math.abs(oldWeight - knapsack.getTotalWeight()) < 1e-9) {
				break;
			}

			oldWeight = knapsack.getTotalWeight();
		}
		return chromosome;
	}

	@Override
	public Population generate() {
		this.items = new ArrayList<>(KPData.items);
		/*
		 * Sort the copy of the items. Otherwise, the original ordering of the
		 * items would be compromised.
		 */
		Collections.sort(this.items, Collections.reverseOrder());

		RLC_SIZE = GAData.populationSize;
		Population population = new Population(GAData.populationSize);
		for (int i = 0; i < GAData.populationSize; i++) {
			population.setChromosome(i, generateChromosome());
		}
		return population;
	}

	@Override
	public String getName() {
		return "Greedy";
	}
	
	

}
