package functions.meta;

import functions.Function;

public class Shift implements Function {
    Function function;
    double shiftX;
    double shiftY;
    public Shift(Function f, double a, double b){
        this.function = f;
        this.shiftX=a;
        this.shiftY=b;
    }
    @Override
    public double getLeftDomainBorder() {
        return function.getLeftDomainBorder()+shiftX;
    }

    @Override
    public double getRightDomainBorder() {
        return function.getRightDomainBorder()+shiftX ;
    }

    @Override
    public double getFunctionValue(double x) {
        return function.getFunctionValue(x)+shiftY;
    }
}
