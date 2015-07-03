package hr.fer.thesis.ga.stoppingcriteria;

import hr.fer.thesis.ga.Population;

public interface IStoppingCriteria {

	boolean check(Population population);

	String getName();
	
}
