package functions.meta;

import functions.Function;

public class Power implements Function {
    Function function;
    double power;
    public Power(Function f, double d){
        this.function = f;
        this.power = d;
    }

    @Override
    public double getLeftDomainBorder() {
        return function.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return function.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return Math.pow( function.getFunctionValue(x), power);
    }
}
