package functions.meta;

import functions.Function;

public class Mult implements Function {
    Function function1;
    Function function2;
    public Mult (Function a, Function b){
        this.function1=a;
        this.function2=b;
    }
    @Override
    public double getLeftDomainBorder() {
        return Math.max(function1.getLeftDomainBorder(), function2.getLeftDomainBorder());
    }

    @Override
    public double getRightDomainBorder() {
        return Math.min(function1.getRightDomainBorder(), function2.getRightDomainBorder());
    }

    @Override
    public double getFunctionValue(double x) {
        return function1.getFunctionValue(x)*function2.getFunctionValue(x);
    }
}
