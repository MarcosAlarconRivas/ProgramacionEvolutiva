package GA;

import GA.Individual.Individual;
import GA.Mutation.Mutation;

public class GeneticAlgorithm {
	protected Reproduccion repFun;
	protected Mutation mutFun;
	protected Population population;
	protected long currentGen;
	protected long generations;
	
	public GeneticAlgorithm(long numOfGenerations, Population pop, Reproduccion rep, Mutation mut){
		population = pop;
		repFun = rep;
		mutFun = mut;
		generations = numOfGenerations;
	}
	
	protected void advanceGeneration(){
		currentGen++;
		repFun.reproduce(population);
		mutFun.mutate(population);
		population.recalculate();
		
	}
	
	/**
	 * The main method of the GAs, it searches for the best individual
	 * for the fitness function.
	 */
	public Individual search(){
		currentGen = 0;
		
		while(currentGen<generations){
			advanceGeneration();
		}

		return population.getBest();
	}
}
