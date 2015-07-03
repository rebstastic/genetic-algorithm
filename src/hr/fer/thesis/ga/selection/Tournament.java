package hr.fer.thesis.ga.selection;

import hr.fer.thesis.ga.Chromosome;
import hr.fer.thesis.ga.GAData;
import hr.fer.thesis.ga.Population;

public class Tournament implements ISelection {

	@Override
	public Chromosome selectParent(Population population) {
		int s = population.size() / 3;
		Chromosome[] random = new Chromosome[s];
		Chromosome best = null;
		for (int i = 0; i < s; i++) {
			int index = GAData.random.nextInt(population.size());
			random[i] = population.getChromosome(index);
			if (i == 0 || best.getFitness() < random[i].getFitness()) {
				best = random[i];
			}
		}
		return best;
	}

	@Override
	public String getName() {
		return "Tournament selection";
	}

	
	
}
