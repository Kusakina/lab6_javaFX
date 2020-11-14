package functions;

import java.io.*;

public class ArrayTabulatedFunction implements TabulatedFunction, Serializable, Externalizable{
    private FunctionPoint[] mas;
    private int Size;
    void Check_scope_not_correct(double leftX, double rightX){
        if ((leftX > rightX) || (Math.abs(leftX - rightX) < Double.MIN_VALUE))
            throw new IllegalArgumentException("Incorrect range of values");
    }
    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalArgumentException {
        Check_scope_not_correct(leftX, rightX);
        if (pointsCount < 2) throw new IllegalArgumentException("Incorrect point's number");
        this.mas = new FunctionPoint[pointsCount * 3];
        Size = pointsCount;
        double alpha = (rightX - leftX) / (pointsCount - 1);
        double b = leftX;
        for (int i = 0; i < Size; b += alpha, ++i) {
            mas[i] = new FunctionPoint(b, 0);
        }
    }
    public ArrayTabulatedFunction(FunctionPoint[]values)throws IllegalArgumentException{
        this.mas = new FunctionPoint[values.length*3];
        if (values.length < 2) throw new IllegalArgumentException("Incorrect point's number");
        double last = values[0].getAbs();
        for(int i=1;i<values.length;++i){
            if(values[i].getAbs()<=last) throw new IllegalArgumentException("Incorrect abscissa's value");
            last=values[i].getAbs();
        }
        Size = values.length;
        System.arraycopy(values, 0, mas,0, values.length);
    }
    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalArgumentException {
        Check_scope_not_correct(leftX,rightX);
        if (values.length < 2) throw new IllegalArgumentException("Incorrect point's number");
        Size = values.length;
        this.mas = new FunctionPoint[values.length * 3];
        double alpha = (rightX - leftX) / (values.length - 1);
        double b = leftX;
        for (int i = 0; i < Size; ++i, b += alpha) {
            mas[i] = new FunctionPoint(b, values[i]);
        }
    }
    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i< Size; ++i) {
            if (result.length() > 0) result.append(" ");
            result.append("(").append(getPoint(i)).append(")");
        }
        return result.toString();
    }
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof TabulatedFunction)) return false;

        TabulatedFunction other = (TabulatedFunction) o;

        if (this.hashCode() != other.hashCode()) return false;
        if (this.getPointCount() != other.getPointCount()) return false;

        boolean isOtherArray = (other instanceof ArrayTabulatedFunction);
        ArrayTabulatedFunction arrayOther = (
                isOtherArray
                        ? (ArrayTabulatedFunction) other
                        : null
        );

        for (int i = 0; i < Size; ++i) {
            FunctionPoint thisPoint = this.mas[i];
            FunctionPoint otherPoint = (
                    isOtherArray
                            ? arrayOther.mas[i]
                            : other.getPoint(i)
            );

            if (!thisPoint.equals(otherPoint)) {
                return false;
            }
        }

        return true;
    }
    @Override
    public int hashCode() {
        int hash = Size;
        for (int i=0; i<Size; ++i ) {
            hash ^= getPoint(i).hashCode();
        }

        return hash;
    }
    @Override
    public TabulatedFunction clone(){
        ArrayTabulatedFunction copy = null;
        try {
            copy = (ArrayTabulatedFunction) super.clone();
        } catch (CloneNotSupportedException e){
            throw new RuntimeException("impossible fail of clone", e);
        }

        copy.Size = this.Size;
        copy.mas = new FunctionPoint[this.mas.length];
        for(int i = 0; i < Size;++i){
            copy.mas[i] =  this.mas[i].clone();
        }

        return copy;
    }
    public double getLeftDomainBorder() {
        return (mas[0].getAbs());
    }

    public double getRightDomainBorder() {
        return (mas[Size - 1].getAbs());
    }

    public double getFunctionValue(double x) {
        double left = this.getLeftDomainBorder();
        double right = this.getRightDomainBorder();
        double left1 = this.getLeftDomainBorder();
        int index = -1;
        int index2 = -1;
        int size = Size;
        double alpha = (right - left) / (size - 1);
        if (left <= x && x <= right) {
            int i = 0;
            while (index < 0) {
                if (left1 <= x && x <= left1 + alpha) {
                    if ((Math.abs(x - left1)) < Double.MIN_VALUE) {
                        index2 = i;
                    }
                    if ((left1 + alpha - x) < Double.MIN_VALUE) {
                        index2 = i + 1;
                    }
                    index = i;
                } else {
                    left1 += alpha;
                    ++i;
                }
            }
            if (index2 != -1) {
                return (mas[index2].getOrd());
            } else {
                double k = (mas[index + 1].getOrd() - mas[index].getOrd()) / (mas[index + 1].getAbs() - mas[index].getAbs());
                double b = (mas[index].getOrd() - k * mas[index].getAbs());
                return (k * x + b);
            }

        } else return (Double.NaN);

    }



    public int getPointCount() {
        return (Size);
    }
    void Check_out_of_range(int index)throws FunctionPointIndexOutOfBoundsException{
        if ((index < 0) || (index > Size - 1)) throw new FunctionPointIndexOutOfBoundsException();
    }

    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException {
       Check_out_of_range(index);
        return (mas[index]);
    }
    void Check_for_set(int index, FunctionPoint point)throws IllegalArgumentException, InappropriateFunctionPointException{
        if (((index == 0) && !(mas[1].getAbs() > point.getAbs())) || ((index == getPointCount() - 1) && !(mas[Size - 2].getAbs() < point.getAbs())) || (((index!=0)&&(index!=Size-1))&&((mas[index - 1].getAbs() > point.getAbs()) || (mas[index + 1].getAbs() < point.getAbs()))))
            throw new InappropriateFunctionPointException();
        if (index <= (Size - 2) && (index > 0) && ((Math.abs(point.getAbs() - mas[index + 1].getAbs()) < Double.MIN_VALUE) || (Math.abs(point.getAbs() - mas[index - 1].getAbs()) < Double.MIN_VALUE)))
            throw new InappropriateFunctionPointException();
        if (index > (Size - 2) && (Math.abs(point.getAbs() - mas[index - 1].getAbs()) < Double.MIN_VALUE))
            throw new InappropriateFunctionPointException();
        if ((index < 1) && (Math.abs(point.getAbs() - mas[index + 1].getAbs()) < Double.MIN_VALUE))
            throw new InappropriateFunctionPointException();
    }
    public void setPoint(int index, FunctionPoint point) throws IllegalArgumentException, InappropriateFunctionPointException {
        Check_out_of_range(index);
        Check_for_set(index,point);
        mas[index] = point;

    }

    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException {
        Check_out_of_range(index);
        //if ((index>=0) && (index < getPointCount())) {
        return (mas[index].getAbs());
        //}
        //return(Double.NaN);
    }
    void Check_for_setX(int index, double x)throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException{
        if ((index < 0) || (index > Size)) throw new FunctionPointIndexOutOfBoundsException();
        if (((index == 0) && !(mas[1].getAbs() > x)) || ((index == getPointCount() - 1) && !(mas[Size - 2].getAbs() < x)) || (((index!=0)&&(index!=Size-1)) &&((mas[index - 1].getAbs() > x) || (mas[index + 1].getAbs() < x))))
            throw new InappropriateFunctionPointException();
        if (index <= (Size - 2) && (index > 0) && ((Math.abs(x - mas[index + 1].getAbs()) < Double.MIN_VALUE) || (Math.abs(x - mas[index - 1].getAbs()) < Double.MIN_VALUE)))
            throw new InappropriateFunctionPointException();
        if (index > (Size - 2) && (Math.abs(x - mas[index - 1].getAbs()) < Double.MIN_VALUE))
            throw new InappropriateFunctionPointException();
        if ((index < 1) && (Math.abs(x - mas[index + 1].getAbs()) < Double.MIN_VALUE))
            throw new InappropriateFunctionPointException();
    }
    public void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        Check_for_setX(index,x);
        //if ((index == 0)&&(mas[1].getAbs()>x)) {
        mas[index].setAbs(x);
        //return;
    //}
        /*if ((index == getPointCount()-1)&&(mas[Size-2].getAbs() < x)) {
            mas[index].setAbs(x);
            return;
        }

        if ((mas[index-1].getAbs()< x )&& (mas[index+1].getAbs()> x )){
            mas[index].setAbs(x);
        }*/

    }
    public double getPointY (int index) throws FunctionPointIndexOutOfBoundsException{
        if ((index<0)||(index>Size-1))throw new FunctionPointIndexOutOfBoundsException();

            return (mas[index].getOrd());

        //return(Double.NaN);
    }
    public void setPointY(int index, double y)throws FunctionPointIndexOutOfBoundsException{
      Check_out_of_range(index);
           // if ((index>=0)&& (index<getPointCount())) {
                mas[index].setOrd(y);
           // }
    }
    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException, IllegalStateException{
        Check_out_of_range(index);
        if (Size<3)throw new IllegalStateException("Points' amount less then 3");
        System.arraycopy(mas, index + 1, mas, index, Size - index);
        --Size;
    }
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException{
        if (Size==mas.length){
            FunctionPoint[] newMas = new FunctionPoint[Size*3];
            for (int i =0; i<Size;++i){
                newMas[i]=mas[i];
            }
            this.mas = newMas;
        }
        int index = 0;
        double xPoint = point.getAbs();
        //чекнуть совпадение
        for(; index<Size && xPoint > mas[index].getAbs();++index);
        int index2 = index;
        if ((index>0&&Math.abs(point.getAbs() - mas[index2 - 1].getAbs()) < Double.MIN_VALUE) )
            throw new InappropriateFunctionPointException();
        if ((index<Size)&&(Math.abs(point.getAbs() - mas[index2].getAbs()) < Double.MIN_VALUE))
            throw new InappropriateFunctionPointException();
        System.arraycopy(mas, index , mas, index+1, Size - index);
        mas[index2] = point;
        ++Size;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.Size);
        out.writeObject(this.mas);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        Size = (int) in.readObject();
        mas = (FunctionPoint[]) in.readObject();
    }
}
