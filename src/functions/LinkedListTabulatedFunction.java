  package functions;

import java.io.*;

  public class LinkedListTabulatedFunction implements TabulatedFunction, Serializable, Externalizable {
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.head);
        out.writeObject(this.Size);
        out.writeObject(this.it);
        out.writeObject(this.myindex);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        head = (FunctionNode) in.readObject();
        Size = (int) in.readObject();
        it = (FunctionNode) in.readObject();
        myindex = (int) in.readObject();

    }
    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        for (int i =0; i< Size; ++i) {
            if (result.length() > 0) result.append(" ");
            result.append("(").append(getNodeByIndex(i).myPoint).append(")");
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

        boolean isOtherLinkedList = (other instanceof LinkedListTabulatedFunction);
        LinkedListTabulatedFunction linkedListOther = (
                isOtherLinkedList
                        ? (LinkedListTabulatedFunction) other
                        : null
        );

        FunctionNode thisCur = this.head.pNext;
        FunctionNode otherCur = (isOtherLinkedList ? linkedListOther.head.pNext : null);

        for (int i = 0; i < Size; ++i) {
            FunctionPoint thisPoint = thisCur.myPoint;
            FunctionPoint otherPoint = (
                    isOtherLinkedList
                            ? otherCur.myPoint
                            : other.getPoint(i)
            );

            if (!thisPoint.equals(otherPoint)) {
                return false;
            }

            thisCur = thisCur.pNext;
            if (isOtherLinkedList) {
                otherCur = otherCur.pNext;
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
        LinkedListTabulatedFunction copy = null;
        try {
            copy = (LinkedListTabulatedFunction) super.clone();
        } catch (CloneNotSupportedException e){
            throw new RuntimeException("impossible fail of clone", e);
        }

        copy.Size = this.Size;
        copy.head = new FunctionNode();

        FunctionNode thisCur = this.head;
        FunctionNode copyCur = copy.head;
        for(int i = 0; i < Size;++i){
            FunctionNode thisNext = thisCur.pNext;

            FunctionNode copyNext = new FunctionNode(thisNext.myPoint.clone());

            copyCur.pNext = copyNext;
            copyNext.pPrev = copyCur;

            thisCur = thisNext;
            copyCur = copyNext;
        }

        copyCur.pNext = copy.head;
        copy.head.pPrev = copyCur;

        return copy;
    }

    public static class FunctionNode implements Serializable, Externalizable{
        FunctionNode pNext;
        FunctionNode pPrev;
        FunctionPoint myPoint;

        public FunctionNode() {
        }

        FunctionNode(double x, double y) {
            this.myPoint = new FunctionPoint(x,y);
        }
        FunctionNode(FunctionPoint a) {
            this.myPoint = a;
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
           out.writeObject(this.pNext);
            out.writeObject(this.pPrev);
            out.writeObject(this.myPoint);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            pNext = (FunctionNode) in.readObject();
            pPrev= (FunctionNode) in.readObject();
            myPoint = (FunctionPoint) in.readObject();
        }
    }

    FunctionNode head;
    int Size;
    int myindex;
    FunctionNode it;

    public LinkedListTabulatedFunction() {
        this.head = new FunctionNode(Double.NaN,Double.NaN);
        head.pNext = head;
        head.pPrev = head;
        Size = 0;
        it = head;
        myindex = -1;
    }
    boolean Check_scope_not_correct(double leftX, double rightX)throws IllegalArgumentException{
        if ((leftX > rightX) || (Math.abs(leftX - rightX) < Double.MIN_VALUE)){
            return true;
        } else return false;
    }
    public LinkedListTabulatedFunction (double leftX, double rightX, int pointsCount)throws IllegalArgumentException {
        this();
        if (Check_scope_not_correct(leftX,rightX)) {
            throw new IllegalArgumentException("Incorrect range of values");
        }
        if (pointsCount < 2) throw new IllegalArgumentException("Incorrect point's number");
        Size = 0;
        double alpha = (rightX - leftX) / (pointsCount - 1);
        double b = leftX;
        for (int i = 0; i < pointsCount; b += alpha, ++i) {
            addNodeToTail().myPoint = new FunctionPoint(b,0);
            /*FunctionNode a = new FunctionNode(b, 0);
            it.pNext =a;
            a.pPrev = it;
            a.pNext=head;
            it = a;*/
        }

        myindex = Size-1;
    }
    public LinkedListTabulatedFunction(FunctionPoint [] values) throws IllegalArgumentException{
        this();
        if (values.length < 2) throw new IllegalArgumentException("Incorrect point's number");
        double last = values[0].getAbs();
        for(int i =1; i< values.length;++i){
            if(values[i].getAbs()<= last) throw new IllegalArgumentException("Incorrect abscissa's value");
            addNodeToTail().myPoint = values[i];
            last = values[i].getAbs();
        }
        Size = values.length;
    }
    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalArgumentException {
        this();
        if (Check_scope_not_correct(leftX,rightX))
            throw new IllegalArgumentException("Incorrect range of values");
        if (values.length < 2) throw new IllegalArgumentException("Incorrect point's number");
        Size = 0;
        double alpha = (rightX - leftX) / (values.length - 1);
        double b = leftX;
        for (int i = 0; i < values.length; ++i, b += alpha) {
            addNodeToTail().myPoint = new FunctionPoint(b,values[i]);
            /*it.pNext =a;
            a.pPrev = it;
            a.pNext=head;
            it = a;*/}

    }

    FunctionNode addNodeToTail() {
        FunctionNode a = new FunctionNode();

        FunctionNode b = this.head.pPrev;
        a.pPrev = b;
        a.pNext = head;
        b.pNext = a;
        head.pPrev = a;
        it = a;
        myindex = Size;
        Size++;
        return it;
    }

    FunctionNode getNodeByIndex(int index) {
        if (index > myindex) {
            if ((Size - 1 - index) > (index - myindex)) {
                for (; myindex < index; ++myindex) {
                    it = it.pNext;
                }
                //return it;// вернуть т в конце?
            } else {
                myindex = Size;
                it = head;
                for (; myindex > index; --myindex) {
                    it = it.pPrev;
                }
                //return it;
            }
        } else {
            if (index < myindex - index) {
                it = head;
                myindex = -1;
                for (; myindex < index; ++myindex) {
                    it = it.pNext;
                }
            } else {
                for (; myindex > index; --myindex) {
                    it = it.pPrev;
                }
            }
        }
        return it;

    }
    public double getLeftDomainBorder(){
        return (head.pNext.myPoint.getAbs());
    }
    public double getRightDomainBorder(){
        return(head.pPrev.myPoint.getAbs());
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
                return (this.getNodeByIndex(index2).myPoint.getOrd());
            } else {
                double k = (this.getNodeByIndex(index + 1).myPoint.getOrd() - this.getNodeByIndex(index).myPoint.getOrd()) / (this.getNodeByIndex(index + 1).myPoint.getAbs() - this.getNodeByIndex(index).myPoint.getAbs());
                double b = (this.getNodeByIndex(index).myPoint.getOrd() - k * this.getNodeByIndex(index).myPoint.getAbs());
                return (k * x + b);
            }

        } else return (Double.NaN);

    }



    public int getPointCount() {
        return (Size);
    }

    FunctionNode addNodeByIndex(int index) {
        FunctionNode next = getNodeByIndex(index);
        FunctionNode prev = next.pPrev;
        FunctionNode a = new FunctionNode();
        a.pPrev = prev;
        prev.pNext = a;
        a.pNext = next;
        next.pPrev = a;
        it = a;
        ++Size;
        return it;

    }
    void Check_out_of_range(int index)throws FunctionPointIndexOutOfBoundsException{
        if ((index < 0) || (index > Size - 1)) throw new FunctionPointIndexOutOfBoundsException();
    }
    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException {
      Check_out_of_range(index);
      return (this.getNodeByIndex(index).myPoint);
    }
    void Check_for_set(int index, FunctionPoint point)throws IllegalArgumentException, InappropriateFunctionPointException{
        if (((index == 0) && !(this.getNodeByIndex(1).myPoint.getAbs() > point.getAbs())) || ((index == getPointCount() - 1) && !(this.getNodeByIndex(Size - 2).myPoint.getAbs() < point.getAbs())) || (((index!=0)&&(index!=Size-1))&&((this.getNodeByIndex(index - 1).myPoint.getAbs() > point.getAbs()) || (this.getNodeByIndex(index + 1).myPoint.getAbs() < point.getAbs()))))
            throw new InappropriateFunctionPointException();
        if (index <= (Size - 2) && (index > 0) && ((Math.abs(point.getAbs() - this.getNodeByIndex(index + 1).myPoint.getAbs()) < Double.MIN_VALUE) || (Math.abs(point.getAbs() - this.getNodeByIndex(index - 1).myPoint.getAbs()) < Double.MIN_VALUE)))
            throw new InappropriateFunctionPointException();
        if (index > (Size - 2) && (Math.abs(point.getAbs() - this.getNodeByIndex(index - 1).myPoint.getAbs()) < Double.MIN_VALUE))
            throw new InappropriateFunctionPointException();
        if ((index < 1) && (Math.abs(point.getAbs() - getNodeByIndex(index + 1).myPoint.getAbs()) < Double.MIN_VALUE))
            throw new InappropriateFunctionPointException();
    }
    public void setPoint(int index, FunctionPoint point) throws IllegalArgumentException, InappropriateFunctionPointException {
        Check_out_of_range(index);
        Check_for_set(index, point);
        this.getNodeByIndex(index).myPoint = point;
    }
    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException {
        Check_out_of_range(index);
        return (getNodeByIndex(index).myPoint.getAbs());
    }
    void Check_for_setX(int index, double x)throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException{
        if ((index < 0) || (index > Size)) throw new FunctionPointIndexOutOfBoundsException();
        if (((index == 0) && !(getNodeByIndex(1).myPoint.getAbs() > x)) || ((index == getPointCount() - 1) && !(getNodeByIndex(Size - 2).myPoint.getAbs() < x)) ||(((index!=0)&&(index!=Size-1))&&((getNodeByIndex(index - 1).myPoint.getAbs() > x) || (getNodeByIndex(index + 1).myPoint.getAbs() < x))))
            throw new InappropriateFunctionPointException();
        if (index <= (Size - 2) && (index > 0) && ((Math.abs(x - getNodeByIndex(index + 1).myPoint.getAbs()) < Double.MIN_VALUE) || (Math.abs(x - getNodeByIndex(index - 1).myPoint.getAbs()) < Double.MIN_VALUE)))
            throw new InappropriateFunctionPointException();
        if (index > (Size - 2) && (Math.abs(x - getNodeByIndex(index - 1).myPoint.getAbs()) < Double.MIN_VALUE))
            throw new InappropriateFunctionPointException();
        if ((index < 1) && (Math.abs(x - getNodeByIndex(index + 1).myPoint.getAbs()) < Double.MIN_VALUE))
            throw new InappropriateFunctionPointException();
    }
    public void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        Check_for_setX(index,x);
        getNodeByIndex(index).myPoint.setAbs(x);
    }
    public double getPointY (int index) throws FunctionPointIndexOutOfBoundsException{
        Check_out_of_range(index);

        return (getNodeByIndex(index).myPoint.getOrd());

        //return(Double.NaN);
    }
    public void setPointY(int index, double y)throws FunctionPointIndexOutOfBoundsException{
        Check_out_of_range(index);
        // if ((index>=0)&& (index<getPointCount())) {
        getNodeByIndex(index).myPoint.setOrd(y);
        // }
    }
    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException, IllegalStateException{
        Check_out_of_range(index);
        if (Size<3)throw new IllegalStateException("Points' amount less then 3");
        deleteNodeByIndex(index);
    }
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException{
        int index = 0;
        double xPoint = point.getAbs();//чекнуть порядок передачи ссылок
        //чекнуть совпадение
        for(; (index<Size)&&(xPoint > getNodeByIndex(index).myPoint.getAbs());++index);
        int index2 = index;
        if (index>0&&(Math.abs(xPoint - getNodeByIndex(index2 - 1).myPoint.getAbs()) < Double.MIN_VALUE))
            throw new InappropriateFunctionPointException();
        if ((index<Size)&&(Math.abs(point.getAbs() - getNodeByIndex(index2).myPoint.getAbs()) < Double.MIN_VALUE))
            throw new InappropriateFunctionPointException();
        addNodeByIndex(index).myPoint=point;
      /*FunctionNode a = new FunctionNode(point);
      this.getNodeByIndex(index).pPrev.pNext =a;
        a.pNext = getNodeByIndex(index);
       a.pPrev= getNodeByIndex(index).pPrev;
       this.getNodeByIndex(index).pPrev =a;
       ++Size;*/
    }
    FunctionNode deleteNodeByIndex(int index){
        it=getNodeByIndex(index);
        it.pPrev.pNext = it.pNext;
        it.pNext.pPrev=it.pPrev;
        FunctionNode a = new FunctionNode();
        a = it;
        it = a.pNext;
        --Size;
        return a;
    }

}
