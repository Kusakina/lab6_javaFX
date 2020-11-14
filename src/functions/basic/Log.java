package functions.basic;

import functions.Function;

public class Log implements Function {
    double base;
    public Log (double x) throws IllegalArgumentException{
        if (x>0&&x!=1) {
            base = x;
        }
        else throw new IllegalArgumentException("Incorrect base of logarithm");
    }
   public double getLeftDomainBorder(){
        return 0;
    }
   public double getRightDomainBorder(){
        return Double.POSITIVE_INFINITY;
    }
   public double getFunctionValue(double x){
        return Math.log(x)/Math.log(base);
    }
}
