package hr.fer.thesis.ga.selection;

import hr.fer.thesis.ga.Chromosome;
import hr.fer.thesis.ga.Population;

public interface ISelection {

	Chromosome selectParent(Population population);

	String getName();

}
