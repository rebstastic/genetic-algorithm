package hr.fer.thesis.ga.crossover;

import hr.fer.thesis.ga.Chromosome;
import hr.fer.thesis.ga.GAData;

public class UniformCrossover implements ICrossover {
	
	private final double p = 0.5;

	@Override
	public Chromosome[] performCrossover(Chromosome parent1, Chromosome parent2) {
		Chromosome offspring1 = new Chromosome(parent1);
		Chromosome offspring2 = new Chromosome(parent2);

		if (GAData.random.nextDouble() < GAData.pc) {
			for (int i = 0; i < parent1.size(); i++) {
				double pg = GAData.random.nextDouble();
				boolean gene1 = (pg < p) ? parent1.getGene(i):parent2.getGene(i);
				boolean gene2 = (pg < p) ? parent2.getGene(i):parent1.getGene(i);
				offspring1.setGene(i, gene1);
				offspring2.setGene(i, gene2);
			}
		}

		return new Chromosome[] { offspring1, offspring2 };
	}

	@Override
	public String getName() {
		return "Uniform crossover";
	}

}
