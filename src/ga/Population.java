package ga;

import ga.replacement.SurvivalOfTheFittest;

import java.util.Arrays;

import p3.Expression;

public class Population {
	//copied elite creatures (they are also in people).
	Individual elite[] = null;
	
	//Fitness used to evaluate individuals
	Fitness fitnessFunction;

	//current full population
	public Individual[] people;

	//position in people of the best individual
	private int best;
	
	private Individual bestOfAll;

	//agv of all population fitness
	protected double fitAverage;

	//inbreading of population
	protected double inbreading;
	
	
	public Population(Fitness fitness, int numOfIndividuals, boolean elite){
		fitnessFunction= fitness;
		
		people = new Individual[numOfIndividuals];
		
		if(elite) this.elite = new Individual[numOfIndividuals/50];
			//elite is 2% of total size of population
		
		best = numOfIndividuals-1;
		
		Individual.setStaticFitness(fitness);
		
		for (int i=0; i<numOfIndividuals; i++) 
			try {
				if(fitness.specie().isAssignableFrom(Expression.class))
					people[i]= Expression.generateGoodTree(3);
				else 
					people[i]= fitness.specie().newInstance();
			}catch (Exception e) {
				System.out.println(e);
			}
		findElite();
		bestOfAll = people[best].clone();
		recalculate();
	}
	
	public Population() {}

	public Fitness getFitnessFunction(){
		return fitnessFunction;
	}

	public double average(){
		return fitAverage;
	}

	public Individual getBest() {
		return people[best];
	}
	
	public Individual getBestOfAll(){
		return bestOfAll;
	}

	public double inbreading() {
		inbreading=0;
		int length = people.length;

		for(int i=0; i<length-1; i++)
			inbreading+=Math.abs(people[i].kinship(people[i+1])/people.length);
		
		return Math.min(inbreading, 1);
	}

	public void recalculate() {
		inbreading= 0;
		
		//orders all population by fitness
		restoreElite();
		findElite();
		
		Arrays.sort(people);//FIXME
		if(people[best].compareTo(bestOfAll)>0)
			bestOfAll = people[best].clone();
		
		fitAverage= 0;
		//calculating fitAverage
		for(int i=0; i<people.length; i++){
			fitAverage += people[i].fitness()/people.length;
		}
		
	}
	
	/**
	 * Copies the best individuals in elite (and orders the people).
	 */
	public void findElite(){
		if(elite==null)return;
		Arrays.sort(people);
		for(int i=0; i<elite.length; i++)
			elite[elite.length-i-1]= people[best-i].clone();
	}
	
	/**
	 * Overwrite worst individuals with elite.
	 */
	public void restoreElite(){
		if(elite==null)return;
		Arrays.sort(people);
		SurvivalOfTheFittest.insert(elite, people);
	}

}
