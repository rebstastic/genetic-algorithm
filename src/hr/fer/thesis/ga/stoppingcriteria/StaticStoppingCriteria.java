package hr.fer.thesis.ga.stoppingcriteria;

import hr.fer.thesis.ga.GAData;
import hr.fer.thesis.ga.Population;

public class StaticStoppingCriteria implements IStoppingCriteria {

	private int remaining;

	public StaticStoppingCriteria() {
		remaining = GAData.iterations;
	}

	@Override
	public boolean check(Population population) {
		if (remaining > 0) {
			remaining--;
			return true;
		}
		remaining = GAData.iterations;
		return false;
	}

	@Override
	public String getName() {
		return "Static stopping criteria";
	}

}
