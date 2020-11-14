package functions;

import java.io.*;
import java.util.Locale;
import java.util.Scanner;

public class TabulatedFunctions {
    private TabulatedFunctions(){

    }
    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) throws IllegalArgumentException{
        if(leftX< function.getLeftDomainBorder()||rightX>function.getRightDomainBorder())
            throw new IllegalArgumentException("Incorrect domain's value");
        //if (leftX>rightX) throw new IllegalArgumentException("Incorrect domain's value");
        LinkedListTabulatedFunction a = new LinkedListTabulatedFunction(leftX,rightX,pointsCount);
        for(int i =0; i< pointsCount;++i) {
            a.setPointY(i, function.getFunctionValue(a.getPointX(i)));
        }
        return a;
    }
    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) throws IOException {
        DataOutputStream a = new DataOutputStream(out);
        a.writeInt(function.getPointCount());
        for(int i=0;i<function.getPointCount();++i){
            a.writeDouble(function.getPointX(i));
            a.writeDouble(function.getPointY(i));
        }
    }
    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException, InappropriateFunctionPointException {
        DataInputStream a= new DataInputStream(in);
        int count = a.readInt();
        TabulatedFunction b = new LinkedListTabulatedFunction();
        for(int i=0;i<count;++i){
            FunctionPoint point = new FunctionPoint(a.readDouble(), a.readDouble());
            b.addPoint(point);
        }
        return b;
    }
    public static void writeTabulatedFunction(TabulatedFunction function, Writer out){
        PrintWriter a = new PrintWriter(out);
        a.println(function.getPointCount());
        for (int i=0; i<function.getPointCount(); ++i){
            a.print(function.getPointX(i));
            a.print(" ");
            a.println(function.getPointY(i));
        }
    }
    public static TabulatedFunction readTabulatedFunction(Reader in) throws InappropriateFunctionPointException {
        Scanner a= new Scanner(in);
        a.useLocale(Locale.US);
        int count = a.nextInt();
        TabulatedFunction b = new LinkedListTabulatedFunction();
        for(int i=0;i<count;++i){
            FunctionPoint point = new FunctionPoint(a.nextDouble(), a.nextDouble());
            b.addPoint(point);
        }
        return b;

    }

}
