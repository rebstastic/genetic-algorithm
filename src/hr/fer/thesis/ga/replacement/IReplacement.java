package hr.fer.thesis.ga.replacement;

import hr.fer.thesis.ga.Population;
import hr.fer.thesis.ga.crossover.ICrossover;
import hr.fer.thesis.ga.evaluation.IFunction;
import hr.fer.thesis.ga.mutation.IMutation;
import hr.fer.thesis.ga.selection.ISelection;

public interface IReplacement {
	
	Population replace(Population generation, IFunction evalFunction,
			ISelection selection, ICrossover crossover, IMutation mutation);

	String getName();
}
