package p2;

import java.util.Random;

import ga.Individual;
import ga.Mutation;
/**
 * This mutation tries 'numOfTries' times to change two positions of the same row.
 * returns the best one of them.
 */
public class Reverse extends Mutation {
	
	public static int numOfTries= 3;

	public Reverse(double baseProb, double inbreedingControl, double annealingControl) {
		super(baseProb, inbreedingControl, annealingControl);
	}

	@Override
	protected void mutate(Individual indiv) {
		Sudoku sudoku = (Sudoku)indiv;
		Random r = new Random();
		
		for(int i=0; i<numOfTries; i++){
			//choose a row
			int row = r.nextInt(9);
			
			//choose a begin point of cut
			int cut1 = r.nextInt(9);
			//choose a end point of cut
			int cut2 = r.nextInt(9);
			
			if (cut2<cut1){
				int temp = cut1;
				cut1=cut2;
				cut2=temp;
			}
			
			Sudoku newOne  = (Sudoku)sudoku.clone();
			
			int a1=0,a2=0;
			while (cut1 < cut2){
				while (sudoku.rows[row][cut1]<0){
					a1++;
				}
				while (sudoku.rows[row][cut2]<0){
					a2--;
				}
				int p = newOne.rows[row][cut1];
				newOne.rows[row][cut1]= newOne.rows[row][cut2];
				newOne.rows[row][cut2]= p;
				a1++; a2--;
			}
			
			newOne.recalce();
			
			if(newOne.compareTo(sudoku)>0)//choose the best one
				sudoku= newOne;
		}

	}

}
