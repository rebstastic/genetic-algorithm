package hr.fer.thesis.ga;

import java.util.Arrays;
import java.util.Collections;

public class Population {

	private Chromosome[] chromosomes;

	public Population(int size) {
		chromosomes = new Chromosome[size];
	}

	public int size() {
		return chromosomes.length;
	}

	public Chromosome[] getChromosomes() {
		return chromosomes;
	}

	public Chromosome getChromosome(int index) {
		return chromosomes[index];
	}

	public void setChromosome(int index, Chromosome chromosome) {
		chromosomes[index] = chromosome;
	}

	public double getFitnessValue() {
		double totalValue = 0;
		for (Chromosome chromosome : chromosomes) {
			totalValue += chromosome.getFitness();
		}
		return totalValue / chromosomes.length;
	}

	public void sortByFitness() {
		Arrays.sort(chromosomes, Collections.reverseOrder());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chromosomes.length; i++) {
			sb.append(chromosomes[i]);
			if (i != chromosomes.length - 1) {
				sb.append(" | ");
			}
		}
		return sb.toString();
	}

}
