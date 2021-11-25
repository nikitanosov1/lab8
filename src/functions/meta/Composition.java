package functions.meta;

import functions.Function;

public class Composition implements Function {
    private Function function1;
    private Function function2;

    public Composition(Function function1, Function function2) {
        this.function1 = function1;
        this.function2 = function2;
    }

    @Override
    public double getLeftDomainBorder() {
        return function1.getLeftDomainBorder(); // хотя это не так
    }

    @Override
    public double getRightDomainBorder() {
        return function1.getRightDomainBorder(); // хотя это не так
    }

    @Override
    public double getFunctionValue(double x) {
        return function1.getFunctionValue(function2.getFunctionValue(x));
    }
}
