package hr.fer.thesis.ga.initialpopulation;

import hr.fer.thesis.ga.Population;

public interface IInitialPopulation {

	Population generate();

	String getName();
	
}
