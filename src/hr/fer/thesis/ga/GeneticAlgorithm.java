package hr.fer.thesis.ga;

import hr.fer.thesis.ga.crossover.ICrossover;
import hr.fer.thesis.ga.evaluation.IFunction;
import hr.fer.thesis.ga.initialpopulation.IInitialPopulation;
import hr.fer.thesis.ga.mutation.IMutation;
import hr.fer.thesis.ga.replacement.IReplacement;
import hr.fer.thesis.ga.selection.ISelection;
import hr.fer.thesis.ga.stoppingcriteria.IStoppingCriteria;

public class GeneticAlgorithm {

	private IFunction evalFunction;
	private IInitialPopulation initialPopulation;
	private ISelection selection;
	private ICrossover crossover;
	private IMutation mutation;
	private IReplacement replacement;
	private IStoppingCriteria stoppingCriteria;

	private int totalIterations;

	public GeneticAlgorithm(IFunction evalFunction,
			IInitialPopulation initialPopulation, ISelection selection,
			ICrossover crossover, IMutation mutation, IReplacement replacement,
			IStoppingCriteria stoppingCriteria) {
		this.evalFunction = evalFunction;
		this.initialPopulation = initialPopulation;
		this.selection = selection;
		this.crossover = crossover;
		this.mutation = mutation;
		this.replacement = replacement;
		this.stoppingCriteria = stoppingCriteria;
	}

	public Chromosome run() {
		totalIterations = 0;
		Population population = initialPopulation.generate();
		evalFunction.evaluate(population);
		population.sortByFitness();

		Chromosome best = null;
		while (stoppingCriteria.check(population)) {
			population = replacement.replace(population, evalFunction,
					selection, crossover, mutation);
			evalFunction.evaluate(population);
			population.sortByFitness();
			best = population.getChromosome(0);
			totalIterations++;
		}

		return best;

	}

	public int getTotalIterations() {
		return totalIterations;
	}

	public IInitialPopulation getInitialPopulation() {
		return initialPopulation;
	}

	public ISelection getSelection() {
		return selection;
	}

	public ICrossover getCrossover() {
		return crossover;
	}

	public IReplacement getReplacement() {
		return replacement;
	}

	public IStoppingCriteria getStoppingCriteria() {
		return stoppingCriteria;
	}

}
