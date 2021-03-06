package p3;

import ga.Fitness;
import ga.Individual;

public abstract class Expression extends Individual {
	public static final Fitness fitness= new MUX4();
	protected static boolean enabledIf= true;
	protected static int maxDepth = 3;
	
	/**
	 * Returns a new Random Expression of the default maxDepth.
	 * You can change this depth using the static method 'setMaxDepth(int)'.
	 */
	public static Expression generateRandomTree(){
		Expression r= generateRandomTree(maxDepth, false);
		r.recalcule();
		return r;
	}
	
	/** Generate numOfTries trees and returns the best one*/
	public static Expression generateGoodTree(int numOfTries){
		Expression best = null;
		while(numOfTries-- >0){
			Expression newTry= generateRandomTree();
			if(best==null||best.compareTo(newTry)<0)
				best= newTry;
		}
		return best;
	}
	
	/**
	 * This creates a new Expression with a 'maxDepth' size.
	 * Some of its branches can be shorter.
	 */
	static Expression generateRandomTree(int maxDepths){
		return generateRandomTree(maxDepths, true);
	}
	
	/**
	 * This creates a new Expression with a 'maxDepth' size.
	 * If 'leafs' some intern nodes are randomly generated as leafs.
	 */
	static Expression generateRandomTree(int maxDepth, boolean leafs){
		if(maxDepth<=Leaf.depth||(leafs&&Math.random()<.75*(maxDepth/Expression.maxDepth)))
			return new Leaf();
		return Operator.generateRandomOp(maxDepth, leafs);
	}
	
	protected abstract boolean evaluate(boolean args[]);
	
	public abstract int depth();
	
	protected abstract int measureDepth();
	
	public boolean isLeaf(){
		 return this instanceof Leaf;
	}
	
	public static void setIfEnabled(boolean use_if){
		//if(enabledIf == use_if)return;
		enabledIf = use_if;
		//if(!use_if)maxDepth+=2;
		//else maxDepth-=2;
	}
	
	public static void setMaxDepth(int depth){
		maxDepth = depth;
	}
	
	@Override
	public double recalcule(){
		measureDepth();
		return super.recalcule();
	}
	
	public abstract int getArity();

	public double kinship(Individual individual) {
		if(this==individual) return 1;
		Expression other= (Expression) individual;
		double k=0;
		double mF= this.fitness();
		double hF= other.fitness();
		k=(64-Math.abs(mF-hF))*(mF+hF/2)/64;
		k= k/Math.abs(depth()-other.depth());
		if(this.isLeaf()^other.isLeaf()) k/= 3;
		else if(isLeaf()){
			if(((Leaf) this).equals((Leaf)other))k/= 2;
		}else if(getClass() != other.getClass()) k/= 1.5;
		
		return k;
	}
}
