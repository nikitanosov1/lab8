package functions.meta;

import functions.Function;

public class Mult implements Function {
    private Function function1;
    private Function function2;

    public Mult(Function function1, Function function2) {
        this.function1 = function1;
        this.function2 = function2;
    }

    @Override
    public double getLeftDomainBorder() {
        return Math.max(function1.getLeftDomainBorder(), function2.getLeftDomainBorder());
    }

    @Override
    public double getRightDomainBorder() {
        return Math.min(function1.getRightDomainBorder(), function2.getRightDomainBorder());
    }

    @Override
    public double getFunctionValue(double x) {
        return function1.getFunctionValue(x) * function2.getFunctionValue(x);
    }
}
