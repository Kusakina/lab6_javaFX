package functions.meta;

import functions.Function;

public class Composition implements Function {
    Function function1;
    Function function2;
    public Composition (Function a, Function b){
        this.function1=a;
        this.function2=b;
    }

    @Override
    public double getLeftDomainBorder() {
        return function2.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return function2.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return function1.getFunctionValue(function2.getFunctionValue(x));
    }
}
