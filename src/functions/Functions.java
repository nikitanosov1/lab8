package functions;

import functions.meta.*;

public abstract class Functions {
    public static Function shift(Function f, double shiftX, double shiftY){
        return new Shift(f, shiftX, shiftY);
    }

    public static Function scale(Function f, double scaleX, double scaleY){
        return new Scale(f, scaleX, scaleY);
    }

    public static Function power(Function f, double power){
        return new Power(f, power);
    }

    public static Function sum(Function f1, Function f2){
        return new Sum(f1, f2);
    }

    public static Function mult(Function f1, Function f2){
        return new Mult(f1, f2);
    }

    public static Function composition(Function f1, Function f2){
        return new Composition(f1, f2);
    }

    public static double integrate(Function function, double left, double right, double step){
        if ((left < function.getLeftDomainBorder()) || (function.getRightDomainBorder() < right)){
            throw new RuntimeException("out of range integrate");
        }
        double x = left;
        double result = 0;
        double prevFunctionValue = function.getFunctionValue(left);
        double functionValue;
        while (x < right){
            if (x + step < right){
                x = x + step;
            }else{
                x = right;
            }
            functionValue = function.getFunctionValue(x);
            result = result + (prevFunctionValue + (functionValue - prevFunctionValue) / 2) * step;
            prevFunctionValue = functionValue;
        }
        return result;
    }
}
