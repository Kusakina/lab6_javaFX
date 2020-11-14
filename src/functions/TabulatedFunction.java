package functions;

public interface TabulatedFunction extends Function, Cloneable {
    //double getLeftDomainBorder();
    //double getRightDomainBorder();
    //double getFunctionValue(double x);
    TabulatedFunction clone();
    int getPointCount();
    FunctionPoint getPoint(int index);
    void setPoint(int index, FunctionPoint point) throws IllegalArgumentException, InappropriateFunctionPointException;
    double getPointX(int index) throws FunctionPointIndexOutOfBoundsException;
    void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException;
    double getPointY (int index) throws FunctionPointIndexOutOfBoundsException;
    void setPointY(int index, double y)throws FunctionPointIndexOutOfBoundsException;
    void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException, IllegalStateException;
    void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;



}
