package functions;

import java.io.*;
import java.lang.reflect.Constructor;

public abstract class TabulatedFunctions {
    private static TabulatedFunctionFactory tabulatedFunctionFactory = new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory();

    public static void setTabulatedFunctionFactory(TabulatedFunctionFactory functionFactory){
        tabulatedFunctionFactory = functionFactory;
    }

    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] points){
        return tabulatedFunctionFactory.createTabulatedFunction(points);
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount){
        return tabulatedFunctionFactory.createTabulatedFunction(leftX, rightX, pointsCount);
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values){
        return tabulatedFunctionFactory.createTabulatedFunction(leftX, rightX, values);
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> classFunction, FunctionPoint[] points){
        Constructor[] constructors = classFunction.getConstructors();
        try {
            for (Constructor<?> constructor : constructors) {
                Class[] types = constructor.getParameterTypes();
                if (types.length == 1) {
                    return (TabulatedFunction) constructor.newInstance(new Object[] {points});
                }
            }
        }
        catch (Exception e){
            throw new IllegalArgumentException(e);
        }
        throw new IllegalArgumentException();
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> classFunction, double leftX, double rightX, int pointsCount){
        Constructor[] constructors = classFunction.getConstructors();
        try {
            for (Constructor<?> constructor : constructors) {
                Class[] types = constructor.getParameterTypes();
                if (types.length == 3 && types[2].equals(Integer.TYPE)) {
                    return (TabulatedFunction) constructor.newInstance(leftX, rightX, pointsCount);
                }
            }
        }
        catch (Exception e){
            throw new IllegalArgumentException(e);
        }
        throw new IllegalArgumentException();
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> classFunction, double leftX, double rightX, double[] values){
        Constructor[] constructors = classFunction.getConstructors();
        try {
            for (Constructor<?> constructor : constructors) {
                Class[] types = constructor.getParameterTypes();
                if (types.length == 3 &&  types[2].equals(values.getClass())) {
                    return (TabulatedFunction) constructor.newInstance(leftX, rightX, values);
                }
            }
        }
        catch (Exception e){
            throw new IllegalArgumentException(e);
        }
        throw new IllegalArgumentException();
    }

    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) throws IllegalArgumentException {
        if ((function.getLeftDomainBorder() > leftX) || (function.getRightDomainBorder() < rightX)) {
            throw new IllegalArgumentException();
        }
        TabulatedFunction tabulatedFunction = createTabulatedFunction(leftX, rightX, pointsCount);
        for (int i = 0; i < pointsCount; i++) {
            tabulatedFunction.setPointY(i, function.getFunctionValue(tabulatedFunction.getPointX(i)));
        }
        return tabulatedFunction;
    }

    public static TabulatedFunction tabulate(Class<? extends TabulatedFunction> classFunction, Function function, double leftX, double rightX, int pointsCount) throws IllegalArgumentException {
        if ((function.getLeftDomainBorder() > leftX) || (function.getRightDomainBorder() < rightX)) {
            throw new IllegalArgumentException();
        }
        TabulatedFunction tabulatedFunction = createTabulatedFunction(classFunction, leftX, rightX, pointsCount);
        for (int i = 0; i < pointsCount; i++) {
            tabulatedFunction.setPointY(i, function.getFunctionValue(tabulatedFunction.getPointX(i)));
        }
        return tabulatedFunction;
    }

    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) {
        try (DataOutputStream dataOutputStream = new DataOutputStream(out)){
            dataOutputStream.writeInt(function.getPointsCount());
            for (int i = 0; i < function.getPointsCount(); i++) {
                dataOutputStream.writeDouble(function.getPointX(i));
                dataOutputStream.writeDouble(function.getPointY(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in){
        TabulatedFunction tabulatedFunction = null;
        try (DataInputStream dataInputStream = new DataInputStream(in)){
            int countPoints = dataInputStream.readInt();
            FunctionPoint[] arrayFunctionPoint = new FunctionPoint[countPoints];
            for (int i = 0; i < countPoints; i++) {
                arrayFunctionPoint[i].setX(dataInputStream.readDouble());
                arrayFunctionPoint[i].setY(dataInputStream.readDouble());
            }
            tabulatedFunction = createTabulatedFunction(arrayFunctionPoint);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tabulatedFunction;
    }

    public static TabulatedFunction inputTabulatedFunction(Class<? extends TabulatedFunction> classFunction, InputStream in){
        TabulatedFunction tabulatedFunction = null;
        try (DataInputStream dataInputStream = new DataInputStream(in)){
            int countPoints = dataInputStream.readInt();
            FunctionPoint[] arrayFunctionPoint = new FunctionPoint[countPoints];
            for (int i = 0; i < countPoints; i++) {
                arrayFunctionPoint[i].setX(dataInputStream.readDouble());
                arrayFunctionPoint[i].setY(dataInputStream.readDouble());
            }
            tabulatedFunction = createTabulatedFunction(classFunction, arrayFunctionPoint);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tabulatedFunction;
    }

    public static void writeTabulatedFunction(TabulatedFunction function, Writer out){
        PrintWriter printWriter = new PrintWriter(out);
        printWriter.print(function.getPointsCount());
        printWriter.print(" ");
        for (int i = 0; i < function.getPointsCount(); i++) {
            printWriter.print(function.getPointX(i));
            printWriter.print(" ");
            printWriter.print(function.getPointY(i));
            printWriter.print(" ");
        }
        printWriter.close();
    }

    public static TabulatedFunction readTabulatedFunction(Reader in){
        StreamTokenizer streamTokenizer = new StreamTokenizer(in);
        TabulatedFunction tabulatedFunction = null;
        try {
            streamTokenizer.nextToken();
            int countsPoints = (int) streamTokenizer.nval;
            FunctionPoint[] arrayFunctionPoint = new FunctionPoint[countsPoints];
            for (int i = 0; i < countsPoints; i++) {
                FunctionPoint functionPoint = new FunctionPoint();
                streamTokenizer.nextToken();
                arrayFunctionPoint[i].setX(streamTokenizer.nval);
                streamTokenizer.nextToken();
                arrayFunctionPoint[i].setY(streamTokenizer.nval);
            }
            tabulatedFunction = createTabulatedFunction(arrayFunctionPoint);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tabulatedFunction;
    }

    public static TabulatedFunction readTabulatedFunction(Class<? extends TabulatedFunction> classFunction, Reader in){
        StreamTokenizer streamTokenizer = new StreamTokenizer(in);
        TabulatedFunction tabulatedFunction = null;
        try {
            streamTokenizer.nextToken();
            int countsPoints = (int) streamTokenizer.nval;
            FunctionPoint[] arrayFunctionPoint = new FunctionPoint[countsPoints];
            for (int i = 0; i < countsPoints; i++) {
                FunctionPoint functionPoint = new FunctionPoint();
                streamTokenizer.nextToken();
                arrayFunctionPoint[i].setX(streamTokenizer.nval);
                streamTokenizer.nextToken();
                arrayFunctionPoint[i].setY(streamTokenizer.nval);
            }
            tabulatedFunction = createTabulatedFunction(classFunction, arrayFunctionPoint);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tabulatedFunction;
    }
}
