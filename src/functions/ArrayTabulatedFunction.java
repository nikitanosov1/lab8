package functions;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ArrayTabulatedFunction implements TabulatedFunction{
    protected FunctionPoint[] points;
    protected int countPoints;

    public ArrayTabulatedFunction(){
        points = null;
        countPoints = 0;
    }

    public ArrayTabulatedFunction(FunctionPoint[] points) throws IllegalArgumentException {
        if (points.length < 2){
            throw new IllegalArgumentException();
        }
        boolean isSorted = true;
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].getX() > points[i + 1].getX()){
                isSorted = false;
                break;
            }
        }
        if (!isSorted){
            throw new IllegalArgumentException();
        }
        this.countPoints = points.length;
        this.points = points;
    }

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalArgumentException{ // Перенёс
        if ((pointsCount < 2) || (leftX >= rightX)) throw new IllegalArgumentException();
        countPoints = pointsCount;
        points = new FunctionPoint[pointsCount];
        double interval = (rightX - leftX)/(pointsCount - 1);
        for (int i = 0; i < pointsCount; ++i) {
            points[i] = new FunctionPoint(leftX + i * interval, 0);
        }
    }
    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalArgumentException{// Перенёс
        if ((values.length < 2) || (leftX >= rightX)) throw new IllegalArgumentException();
        countPoints = values.length;
        points = new FunctionPoint[values.length];
        double interval = (rightX - leftX)/(values.length - 1);
        for (int i = 0; i < values.length; ++i) {
            points[i] = new FunctionPoint(leftX + i * interval, values[i]);
        }
    }

    public int maxCountElem(){return points.length;} //Сколько с запасом влезет элементов в данный массив (не по заданию, для отладки)

    public double getLeftDomainBorder(){ return points[0].getX();}// Перенёс
    public double getRightDomainBorder(){ return points[countPoints - 1].getX();}// Перенёс
    public double getFunctionValue(double x){// Перенёс
        if ((x < points[0].getX()) || (x > points[countPoints - 1].getX())){ return Double.NaN;}
        int index = -1;
        for (int i = 0; i < countPoints - 1; ++i){
            if ((x >= points[i].getX()) && (x <= points[i + 1].getX())){
                index = i;
                break;
            }
        }
        return points[index].getY() + (points[index + 1].getY() - points[index].getY())*(x - points[index].getX())/(points[index + 1].getX() - points[index].getX());
    }
    public int getPointsCount(){ return countPoints;}// Перенёс
    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException{// Перенёс
        if ((index < 0) || (index >= countPoints)) { throw new FunctionPointIndexOutOfBoundsException();}
        return points[index];
    }
    public void setPoint(int index, FunctionPoint point) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException{
        if ((index < 0) || (index >= countPoints)) { throw new FunctionPointIndexOutOfBoundsException();}

        if ((index == 0) && (point.getX() < points[1].getX())){
            points[0] = point;
        }else if ((index == countPoints - 1) && (point.getX() > points[countPoints - 2].getX())){
            points[countPoints - 1] = point;
        }else{
            if ((point.getX() > points[index - 1].getX()) && (point.getX() < points[index + 1].getX())) { throw new InappropriateFunctionPointException();}
            points[index] = point;
        }
    }
    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException{
        if ((index < 0) || (index >= countPoints)) { throw new FunctionPointIndexOutOfBoundsException();}
        return points[index].getX();
    }
    public void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException{
        if ((index < 0) || (index >= countPoints)) { throw new FunctionPointIndexOutOfBoundsException();}

        if ((index == 0) && (x < points[1].getX())){
            points[0].setX(x);
        }else if ((index == countPoints - 1) && (x > points[countPoints - 2].getX())){
            points[countPoints - 1].setX(x);
        }else{
            if ((x > points[index - 1].getX()) && (x < points[index + 1].getX())) { throw new InappropriateFunctionPointException();}
            points[index].setX(x);
        }
    }
    public double getPointY(int index) throws FunctionPointIndexOutOfBoundsException{
        if ((index < 0) || (index >= countPoints)) { throw new FunctionPointIndexOutOfBoundsException();}
        return points[index].getY();
    }
    public void setPointY(int index, double y) throws FunctionPointIndexOutOfBoundsException{
        if ((index < 0) || (index >= countPoints)) { throw new FunctionPointIndexOutOfBoundsException();}
        points[index].setY(y);
    }
    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException{
        if ((index < 0) || (index >= countPoints)) { throw new FunctionPointIndexOutOfBoundsException();}
        if (countPoints == 3){ throw new InappropriateFunctionPointException();}

        --countPoints;
        for (int i = index; i < countPoints; ++i) points[i] = points[i + 1]; // Сдвиг правой части на массива на место удалённого
        if (countPoints == points.length/2){
            FunctionPoint[] temp = new FunctionPoint[countPoints];
            System.arraycopy(points, 0, temp, 0, countPoints);
            points = temp;
        }
    }
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException{
        if (points == null){
            points = new FunctionPoint[1];
            points[0] = point;
            countPoints = 1;
        }else {
            int indexForAdd = 0;
            for (int i = 0; i < countPoints; ++i)
                if (points[i].getX() < point.getX()) indexForAdd = i + 1; // Куда вставить элемент?

            if ((indexForAdd != countPoints) && (points[indexForAdd].getX() == point.getX())) {
                throw new InappropriateFunctionPointException();
            }

            ++countPoints;
            if (countPoints > points.length) {
                FunctionPoint[] temp = new FunctionPoint[2 * points.length];
                System.arraycopy(points, 0, temp, 0, indexForAdd);
                temp[indexForAdd] = point;
                System.arraycopy(points, indexForAdd, temp, indexForAdd + 1, points.length - indexForAdd);
                points = temp;
            } else {
                for (int i = countPoints - 1; i > indexForAdd; --i) points[i] = points[i - 1];
                points[indexForAdd] = point;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        for (int i = 0; i < countPoints; i++) {
            stringBuilder.append(this.getPoint(i).toString() + ", ");
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TabulatedFunction){
            TabulatedFunction tabulatedFunction = (TabulatedFunction) obj;
            if (tabulatedFunction.getPointsCount() != this.getPointsCount()){
                return false;
            }else{
                boolean equal = true;
                for (int i = 0; i < this.getPointsCount(); i++) {
                    if (!tabulatedFunction.getPoint(i).equals(this.getPoint(i))){
                        equal = false;
                        break;
                    }
                }
                return equal;
            }
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = countPoints;
        for (int i = 0; i < countPoints; i++) {
            hash = hash ^ this.getPoint(i).hashCode();
        }
        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ArrayTabulatedFunction arrayTabulatedFunction = new ArrayTabulatedFunction(this.getLeftDomainBorder(), this.getRightDomainBorder(), countPoints);
        for (int i = 0; i < countPoints; i++) {
            arrayTabulatedFunction.setPointY(i, getPointY(i));
        }
        return arrayTabulatedFunction;
    }

    @Override
    public Iterator<FunctionPoint> iterator() {
        return new Iterator<FunctionPoint>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < countPoints;
            }

            @Override
            public FunctionPoint next() {
                if (!hasNext()){
                    throw new NoSuchElementException();
                }
                return points[index++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static class ArrayTabulatedFunctionFactory implements TabulatedFunctionFactory{
        @Override
        public TabulatedFunction createTabulatedFunction(FunctionPoint[] points) {
            return new ArrayTabulatedFunction(points);
        }

        @Override
        public TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount) {
            return new ArrayTabulatedFunction(leftX, rightX, pointsCount);
        }

        @Override
        public TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values) {
            return new ArrayTabulatedFunction(leftX, rightX, values);
        }
    }

}