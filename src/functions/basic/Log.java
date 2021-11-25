package functions.basic;

import functions.Function;

public class Log implements Function {
    private double base;

    public Log(double base) {
        if ((base < 0)||(Math.abs(base - 1) < 0.001)){
            throw new RuntimeException("base incorrect"); // Ошибку тут свою надо сделать
        }
        this.base = base;
    }

    @Override
    public double getLeftDomainBorder() {
        return 0;
    }

    @Override
    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double getFunctionValue(double x) {
        return Math.log(x)/Math.log(base);
    }
}
