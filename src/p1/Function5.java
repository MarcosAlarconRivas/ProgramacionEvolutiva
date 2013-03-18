package p1;

public class Function5 extends DoubleFunction{
	
	protected static double lowLimit[]= {-10};
	protected static double highLimit[]={10};
	protected static int n = 1;
	protected static int numOfChromosomes = 2;
	
	public String toString(){
		return "f(xi, i=1..2) = sum(i=1..5)[(i*cos((i+1)*x1+i))]*sum(i=1..5)[(i*cos((i+1)*x2+i))]";
	}

	@Override
	protected double fitnessFun(double[] fenotypes) {
		double res = 0;
		for (int i=1; i<=5; i++){
			double f1 = fenotypes[0];
			double f2 = fenotypes[1];
			double r1 = i*Math.cos((i+1)*f1+i);
			double r2 = i*Math.cos((i+1)*f2+i);
			res += r1*r2;
		}
		return res;
	}
	
}