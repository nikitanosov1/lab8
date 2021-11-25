package functions.meta;

import functions.Function;

public class Scale implements Function {
    private Function function;
    private double scaleX;
    private double scaleY;

    public Scale(Function function, double scaleX, double scaleY) {
        this.function = function;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public double getLeftDomainBorder() {
        return (scaleX > 0) ? function.getLeftDomainBorder() * scaleX : function.getRightDomainBorder() * scaleX;
    }

    @Override
    public double getRightDomainBorder() {
        return (scaleX > 0) ? function.getRightDomainBorder() * scaleX : function.getLeftDomainBorder() * scaleX;
    }

    @Override
    public double getFunctionValue(double x) {
        return function.getFunctionValue(x) * scaleY;
    }
}
