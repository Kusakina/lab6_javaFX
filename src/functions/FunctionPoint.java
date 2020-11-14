package functions;

import java.io.*;

public class FunctionPoint implements Serializable, Externalizable, Cloneable {
   private double abs;
   private double ord;
   public FunctionPoint(double x, double y){
        this.abs = x;
        this.ord = y;

    }
   public FunctionPoint(FunctionPoint point){
        this.abs = point.abs;
        this.ord = point.ord;

    }
    public FunctionPoint(){
        this.abs = 0;
        this.ord = 0;
    }
    @Override
    public String toString(){
        return String.format("(%.4f;%.4f)", abs, ord);
    }
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof FunctionPoint)) return false;

        FunctionPoint other = (FunctionPoint)o;

        if (this.hashCode() != other.hashCode()) return false;

        return this.abs == other.abs && this.ord == other.ord;
    }
    private long coordHashCode(double coord) {
        long bits = Double.doubleToLongBits(coord);
        long high = (bits >>> 32);

        long lowMask = ((1L << 32) - 1);
        long low = (bits & lowMask);

        return high ^ low;
    }
    @Override
    public int hashCode() {
        return (int)(coordHashCode(abs) ^ coordHashCode(ord));
    }
    @Override
    public FunctionPoint clone() {
        FunctionPoint copy = null;
        try {
            copy = (FunctionPoint) super.clone();
        } catch (CloneNotSupportedException e){
            throw new RuntimeException("impossible fail of clone", e);
        }
        double x = this.getAbs();
        double y = this.getOrd();
        copy.setAbs(x);
        copy.setOrd(y);
        return copy;
    }


    public double getAbs(){
        return abs;
    }
    public double getOrd(){
        return ord;
    }
    void setAbs(double x){
        this.abs =x;
    }
    void setOrd(double y){
        this.ord =y;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.abs);
        out.writeObject(this.ord);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
       abs = (double)in.readObject();
       ord = (double) in.readObject();

    }
}
