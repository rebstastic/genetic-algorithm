package hr.fer.thesis.ga.selection;

import hr.fer.thesis.ga.Chromosome;
import hr.fer.thesis.ga.GAData;
import hr.fer.thesis.ga.Population;

public class RouletteWheel implements ISelection {

	@Override
	public Chromosome selectParent(Population population) {

		double totalFitness = 0;
		for (Chromosome chromosome : population.getChromosomes()) {
			totalFitness += chromosome.getFitness();
		}
		for (Chromosome chromosome : population.getChromosomes()) {
			if (totalFitness != 0) {
				chromosome.setRate(chromosome.getFitness() / totalFitness);
			}
		}

		double p = GAData.random.nextDouble();
		double accumulatedSum = 0;
		for (Chromosome chromosome : population.getChromosomes()) {
			accumulatedSum += chromosome.getRate();
			if (p < accumulatedSum) {
				return chromosome;
			}
		}

		return population.getChromosome(0);

	}

	@Override
	public String getName() {
		return "Roulette wheel selection";
	}

}
