package functions.basic;

public class Sin extends  TrigonometricFunction{
    public double getLeftDomainBorder(){
        return super.getLeftDomainBorder();
    }
    public double getRightDomainBorder(){
        return super.getRightDomainBorder();
    }
    @Override
    public double getFunctionValue(double x){
        return Math.sin(x);
    }
}
