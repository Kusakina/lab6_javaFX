package functions.meta;

import functions.Function;

public class Scale implements Function {
    Function function;
    double scaleX;
    double scaleY;
    public Scale(Function f, double a, double b){
        this.function = f;
        this.scaleX=a;
        this.scaleY=b;
    }

    @Override
    public double getLeftDomainBorder() {
        return function.getLeftDomainBorder()*scaleX;
    }

    @Override
    public double getRightDomainBorder() {
        return function.getRightDomainBorder()*scaleX;
    }

    @Override
    public double getFunctionValue(double x) {
        return function.getFunctionValue(x)*scaleY;
    }
}
