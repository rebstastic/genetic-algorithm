package hr.fer.thesis.ga.mutation;

import hr.fer.thesis.ga.Chromosome;
import hr.fer.thesis.ga.GAData;

public class FlipMutation implements IMutation {

	@Override
	public void mutate(Chromosome chromosome) {
		for (int i = 0; i < chromosome.size(); i++) {
			if (GAData.random.nextDouble() < GAData.pm) {
				boolean gene = chromosome.getGene(i) ? false : true;
				chromosome.setGene(i, gene);
			}
		}
	}

}
