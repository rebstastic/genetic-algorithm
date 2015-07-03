package hr.fer.thesis.ga.stoppingcriteria;

import hr.fer.thesis.ga.GAData;
import hr.fer.thesis.ga.Population;

public class AdaptiveStoppingCriteria implements IStoppingCriteria {

	private int remaining;
	private double lastFitness;

	public AdaptiveStoppingCriteria() {
		remaining = GAData.iterations;
		lastFitness = -1;
	}

	@Override
	public boolean check(Population population) {
		if (lastFitness < 0) {
			lastFitness = population.getFitnessValue();
		} else {
			if (Math.abs(lastFitness - population.getFitnessValue()) < 1e-6) {
				remaining--;
			} else {
				remaining = GAData.iterations;
				lastFitness = population.getFitnessValue();
			}
		}
		if (remaining > 0) {
			return true;
		} else {
			remaining = GAData.iterations;
			lastFitness = -1;
			return false;
		}
	}

	@Override
	public String getName() {
		return "Adaptive stopping criteria";
	}
}
