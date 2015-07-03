package hr.fer.thesis.ga.evaluation;

import hr.fer.thesis.ga.Chromosome;
import hr.fer.thesis.ga.Population;
import hr.fer.thesis.kp.KPData;
import hr.fer.thesis.kp.Knapsack;

public class EvaluationFunction implements IFunction {

	@Override
	public void evaluate(Population population) {
		for (Chromosome chromosome : population.getChromosomes()) {
			Knapsack knapsack = new Knapsack(KPData.capacity);
			for (int i = 0; i < chromosome.size(); i++) {
				if (chromosome.getGene(i)) {
					knapsack.addItem(KPData.items.get(i));
				}
			}
			if (knapsack.isOverWeighted()) {
				chromosome.setFitness(0);
			} else {
				chromosome.setFitness(knapsack.getTotalValue());
			}
		}
	}

}
