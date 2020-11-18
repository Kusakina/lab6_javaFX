package sample;

import functions.*;

import java.io.*;

class FileTabulatedFunction implements TabulatedFunction {

    private TabulatedFunction tabulatedFunction;
    private boolean modified;
    
    private boolean fileNameAssigned;
    private String selectedFileName;
    
    FileTabulatedFunction() {
	setTabulatedFunction(null);
    	setFileName(null);
    }	
    
    boolean isModified() {
    	return modified;
    }
    
    private void setTabulatedFunction(TabulatedFunction tabulatedFunction) {
    	this.tabulatedFunction = tabulatedFunction;
    	modified = false;
    }
    
    boolean isFileNameAssigned() {
    	return fileNameAssigned;
    }
    
    private void setFileName(String fileName) {
    	this.selectedFileName = fileName;
    	this.fileNameAssigned = (null != fileName);
    }
    
    void newFunction(double leftX, double rightX, int pointsCount) {
    	TabulatedFunction newFunction = new ArrayTabulatedFunction(leftX, rightX, pointsCount);
    	
    	setTabulatedFunction(newFunction);
    	setFileName(null);
    	
    	modified = true;
    }
        
    void tabulateFunction(Function function, double leftX, double rightX, int pointsCount){
        TabulatedFunction newFunction = TabulatedFunctions.tabulate(function, leftX, rightX, pointsCount);
        setTabulatedFunction(newFunction);
    }
    
    void saveFunction() throws IOException {
   	    if (!modified) return;
    
    	if (!isFileNameAssigned()) {
    		throw new IOException("Файл не выбран!");
    	}
    
    	try (Writer out = new FileWriter(selectedFileName)) {
            TabulatedFunctions.writeTabulatedFunction(tabulatedFunction, out);
            modified = false;
        }
    }

    void saveFunctionAs(String fileName) throws IOException {
        setFileName(fileName);
        saveFunction();
    }

    void loadFunction(String fileName) throws InappropriateFunctionPointException, IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
            TabulatedFunction loadedFunction = TabulatedFunctions.readTabulatedFunction(in);
            setTabulatedFunction(loadedFunction);
            setFileName(fileName);
        }
    }

    @Override
    public double getLeftDomainBorder() {
        return tabulatedFunction.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return tabulatedFunction.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return tabulatedFunction.getFunctionValue(x);
    }

    @Override
    public int getPointCount() {
    	return tabulatedFunction.getPointCount();
    }
    
    @Override
    public FunctionPoint getPoint(int index) {
    	return tabulatedFunction.getPoint(index);
    }
    
    @Override
    public void setPoint(int index, FunctionPoint point) throws IllegalArgumentException, InappropriateFunctionPointException {
    	tabulatedFunction.setPoint(index, point);
    	modified = true;
    }
    
    @Override
    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException {
    	return tabulatedFunction.getPointX(index);
    }
    
    @Override
    public void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
	tabulatedFunction.setPointX(index, x);
    	modified = true;
    }
    
    @Override
    public double getPointY (int index) throws FunctionPointIndexOutOfBoundsException {
    	return tabulatedFunction.getPointY(index);
    }
    
    @Override
    public void setPointY(int index, double y) throws FunctionPointIndexOutOfBoundsException {
    	tabulatedFunction.setPointY(index, y);
    	modified = true;
    }
    
    @Override
    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException, IllegalStateException {
    	tabulatedFunction.deletePoint(index);
    	modified = true;
    }
    
    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
    	tabulatedFunction.addPoint(point);
    	modified = true;	
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (null == tabulatedFunction) return null == obj;
    	return tabulatedFunction.equals(obj);
    }
    	
    	
    @Override
    public int hashCode() {
    	return tabulatedFunction.hashCode();
    }
    
    @Override    
    public TabulatedFunction clone() {
    	FileTabulatedFunction copy;
    	try {
    		copy = (FileTabulatedFunction) super.clone();
    	} catch (CloneNotSupportedException e) {
    		throw new RuntimeException("impossible fail of clone", e);
    	}
    	
    	copy.tabulatedFunction = this.tabulatedFunction.clone();
    	
    	copy.modified = this.modified;
    	copy.fileNameAssigned = this.fileNameAssigned;
    	copy.selectedFileName = this.selectedFileName;
    	
    	return copy;
    }
}
