package hr.fer.thesis.ga.replacement;

import hr.fer.thesis.ga.Chromosome;
import hr.fer.thesis.ga.GAData;
import hr.fer.thesis.ga.Population;
import hr.fer.thesis.ga.crossover.ICrossover;
import hr.fer.thesis.ga.evaluation.IFunction;
import hr.fer.thesis.ga.mutation.IMutation;
import hr.fer.thesis.ga.selection.ISelection;

public class SteadyStateReplacement implements IReplacement {

	private final double mortality = 4;

	@Override
	public Population replace(Population generation, IFunction evalFunction,
			ISelection selection, ICrossover crossover, IMutation mutation) {

		int numToReplace = (int) (generation.size() / mortality <= mortality ? 1
				: mortality);

		Chromosome[] worst = new Chromosome[numToReplace];
		generation.sortByFitness();
		for (int i = 0; i < numToReplace; i++) {
			worst[i] = generation.getChromosome(GAData.populationSize - i - 1);

		}

		int poolSize = (numToReplace % 2 == 0) ? numToReplace
				: numToReplace + 1;

		Population pool = new Population(poolSize);

		int numOfOffsprings = 0;
		while (numOfOffsprings < poolSize) {
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
			pool.setChromosome(numOfOffsprings++, offsprings[0]);
			mutation.mutate(offsprings[1]);
			pool.setChromosome(numOfOffsprings++, offsprings[1]);

		}

		evalFunction.evaluate(pool);
		pool.sortByFitness();

		for (int i = 0; i < numToReplace; i++) {
			if (pool.getChromosome(i).getFitness() > generation.getChromosome(
					GAData.populationSize - i - 1).getFitness()) {
				generation.setChromosome(GAData.populationSize - i - 1,
						pool.getChromosome(i));
			}
		}

		return generation;

	}

	@Override
	public String getName() {
		return "Steady-state replacement";
	}

}
