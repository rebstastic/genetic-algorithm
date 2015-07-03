package hr.fer.thesis.ga.crossover;

import java.util.Arrays;

import hr.fer.thesis.ga.Chromosome;
import hr.fer.thesis.ga.GAData;

public class NPointCrossover implements ICrossover {

	@Override
	public Chromosome[] performCrossover(Chromosome parent1, Chromosome parent2) {
		Chromosome offspring1 = new Chromosome(parent1);
		Chromosome offspring2 = new Chromosome(parent2);

		if (GAData.N == 0) {
			GAData.N = 1;
		}
		if (GAData.random.nextDouble() < GAData.pc) {
			int[] points = new int[GAData.N];

			for (int i = 0; i < GAData.N; i++) {
				points[i] = GAData.random.nextInt(parent1.size() - 1) + 1;
			}

			Arrays.sort(points);

			for (int i = 0; i < GAData.N; i++) {

				int bound = (i == GAData.N - 1) ? parent1.size()
						: points[i + 1];

				if (i % 2 == 0) {
					for (int j = points[i]; j < bound; j++) {
						offspring1.setGene(j, parent2.getGene(j));
						offspring2.setGene(j, parent1.getGene(j));
					}
				} else {
					for (int j = points[i]; j < bound; j++) {
						offspring1.setGene(j, parent1.getGene(j));
						offspring2.setGene(j, parent2.getGene(j));
					}
				}

			}

		}

		return new Chromosome[] { offspring1, offspring2 };
	}

	@Override
	public String getName() {
		return "N-point crossover with " + GAData.N + " points";
	}

}
