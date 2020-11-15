package sample;

import functions.*;

import java.io.*;

import static functions.TabulatedFunctions.writeTabulatedFunction;

public class FileTF {
    String filename = "1.txt";
    TabulatedFunction myTF;
    boolean edit;

    TabulatedFunction newFunction(double leftX, double rightX, int pointsCount) {
        return (myTF = new LinkedListTabulatedFunction(leftX, rightX, pointsCount));

    }

    void saveFunction() {
        try (Writer out = new FileWriter(filename)) {
            TabulatedFunctions.writeTabulatedFunction(myTF, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void saveFunctionAs(String fileName) {
        try (Writer out = new FileWriter(fileName)) {
            TabulatedFunctions.writeTabulatedFunction(myTF, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void loadFunction(String fileName) {
        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
            TabulatedFunction Ex2 = TabulatedFunctions.readTabulatedFunction(in);
        } catch (InappropriateFunctionPointException | IOException e) {
            e.printStackTrace();
        }
    }
    void tabulateFunction(Function function, double leftX, double rightX, int pointsCount){

    }
}