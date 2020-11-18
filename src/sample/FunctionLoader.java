package sample;
import functions.Function;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class FunctionLoader extends ClassLoader {

    public static byte[] loadFileAsBytes(File file) throws IOException
    {
        byte[] result = new byte[(int)file.length()];
        FileInputStream f = new FileInputStream(file);
        try {
            f.read(result,0,result.length);
        } finally {
            try {
                f.close();
            } catch (Exception e) {
                // Игнорируем исключения, возникшие при
                // вызове close. Они крайне маловероятны и не
                // очень важны - файл уже прочитан. Но если
                // они все же возникнут, то они не должны
                // замаскировать действительно важные ошибки,
                // возникшие при вызове read.
            };
        }
        return result;
    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private File functionFile;
    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        try {
            byte[] classBytes= loadFileAsBytes(functionFile);
            return defineClass(className, classBytes, 0,
                    classBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(
                    "Cannot load class " + className + ": " + e);
        } catch (ClassFormatError e) {
            throw new ClassNotFoundException(
                    "Format of class file incorrect for class "
                            + className + " : " + e);
        }
    }
    public Function loadFunction(File file) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        functionFile = file;
        String functionName =file.getName().split(".class")[0];
        Class clazz = loadClass(functionName);
        return (Function) clazz.newInstance();
    }

}



