package hr.fer.thesis.ga.replacement;

import hr.fer.thesis.ga.Chromosome;
import hr.fer.thesis.ga.GAData;
import hr.fer.thesis.ga.Population;
import hr.fer.thesis.ga.crossover.ICrossover;
import hr.fer.thesis.ga.evaluation.IFunction;
import hr.fer.thesis.ga.mutation.IMutation;
import hr.fer.thesis.ga.selection.ISelection;

public class GenerationalReplacement implements IReplacement {

	@Override
	public Population replace(Population generation, IFunction evalFunction,
			ISelection selection, ICrossover crossover, IMutation mutation) {

		Population nextGeneration = new Population(GAData.populationSize);
		int numOfOffsprings = 0;
		if (GAData.elitism) {
			generation.sortByFitness();
			nextGeneration.setChromosome(0, generation.getChromosome(0));
			numOfOffsprings++;
		}

		int parentsPairs = (int) Math
				.ceil((GAData.populationSize - numOfOffsprings) / 2.);
		Population pool = new Population(2 * parentsPairs);
		for (int i = 0; i < parentsPairs; i++) {
			Chromosome parent1 = selection.selectParent(generation);
			Chromosome parent2 = null;
			int avoidInfiniteLoop = 5;
			while (true) {
				parent2 = selection.selectParent(generation);
				if (!parent1.equals(parent2) || avoidInfiniteLoop == 0) {
					break;
				}
				avoidInfiniteLoop--;
			}
			Chromosome[] offsprings = crossover.performCrossover(parent1,
					parent2);
			mutation.mutate(offsprings[0]);
			mutation.mutate(offsprings[1]);
			pool.setChromosome(2 * i, offsprings[0]);
			pool.setChromosome(2 * i + 1, offsprings[1]);
		}
		evalFunction.evaluate(pool);

		while (numOfOffsprings < GAData.populationSize) {
			int index = (GAData.elitism) ? numOfOffsprings - 1
					: numOfOffsprings;
			nextGeneration.setChromosome(numOfOffsprings,
					pool.getChromosome(index));
			numOfOffsprings++;
		}
		return nextGeneration;
	}

	@Override
	public String getName() {
		return "Generational replacement";
	}

}
