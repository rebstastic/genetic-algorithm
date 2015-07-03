package hr.fer.thesis.ga.crossover;

import hr.fer.thesis.ga.Chromosome;

public interface ICrossover {

	Chromosome[] performCrossover(Chromosome parent1, Chromosome parent2);

	String getName();

}
