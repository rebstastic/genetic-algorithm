package hr.fer.thesis.ga;

import java.util.Arrays;

public class Chromosome implements Comparable<Chromosome> {

	private boolean[] genes;
	private double fitness;
	private double rate;

	public Chromosome(int size) {
		genes = new boolean[size];
	}

	public Chromosome(Chromosome other) {
		this.genes = Arrays.copyOf(other.genes, other.size());
		this.fitness = other.fitness;
		this.rate = other.rate;
	}

	public void setGene(int index, boolean gene) {
		genes[index] = gene;
	}

	public boolean[] getGenes() {
		return genes;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public boolean getGene(int index) {
		return genes[index];
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public int size() {
		return genes.length;
	}

	@Override
	public int compareTo(Chromosome other) {
		if (this.fitness < other.fitness) {
			return -1;
		} else if (this.fitness > other.fitness) {
			return 1;
		}
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Chromosome)) {
			return false;
		}
		Chromosome other = (Chromosome) obj;
		if (this.genes.equals(other.genes)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.genes.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < genes.length; i++) {
			if (genes[i]) {
				sb.append(1);
			} else {
				sb.append(0);
			}
		}
		return sb.toString() + ", fitness=" + fitness;
	}

}
