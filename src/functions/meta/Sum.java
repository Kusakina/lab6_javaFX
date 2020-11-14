package functions.meta;

import functions.Function;

public class Sum implements Function {
    Function function1;
    Function function2;
    public Sum(Function a, Function b){
        this.function1=a;
        this.function2=b;
    }
    public double getLeftDomainBorder() throws IllegalArgumentException{
        if (Math.max(function1.getLeftDomainBorder(), function2.getLeftDomainBorder())>Math.min(function1.getRightDomainBorder(), function2.getRightDomainBorder()))
            throw new IllegalArgumentException("Incorrect function");
        return Math.max(function1.getLeftDomainBorder(), function2.getLeftDomainBorder());
    }
    public double getRightDomainBorder(){
        if (Math.max(function1.getLeftDomainBorder(), function2.getLeftDomainBorder())>Math.min(function1.getRightDomainBorder(), function2.getRightDomainBorder()))
        throw new IllegalArgumentException("Incorrect function");
        return Math.min(function1.getRightDomainBorder(), function2.getRightDomainBorder());
    }
    public double getFunctionValue(double x){
        return function1.getFunctionValue(x)+function2.getFunctionValue(x);
    }
}
