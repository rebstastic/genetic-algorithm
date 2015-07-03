package hr.fer.thesis.ga.initialpopulation;

import hr.fer.thesis.ga.Chromosome;
import hr.fer.thesis.ga.GAData;
import hr.fer.thesis.ga.Population;
import hr.fer.thesis.kp.KPData;
import hr.fer.thesis.kp.Knapsack;

public class RandomInitialPopulation implements IInitialPopulation {

	private Chromosome generateChromosome() {
		Chromosome chromosome = new Chromosome(KPData.size());
		Knapsack knapsack = new Knapsack(KPData.capacity);
		for (int i = 0; i < KPData.size(); i++) {
			boolean gene = GAData.random.nextBoolean();
			if (gene) {
				if (KPData.items.get(i).getWeight() > knapsack.getFreeSpace()) {
					continue;
				}
				chromosome.setGene(i, gene);
				knapsack.addItem(KPData.items.get(i));
			}
		}
		return chromosome;
	}

	@Override
	public Population generate() {
		Population population = new Population(GAData.populationSize);
		for (int i = 0; i < GAData.populationSize; i++) {
			population.setChromosome(i, generateChromosome());
		}
		return population;
	}

	@Override
	public String getName() {
		return "Random";
	}

}
