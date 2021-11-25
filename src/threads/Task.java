package threads;

import functions.Function;
import functions.basic.Exp;

public class Task {
    private Function function;
    private double left;
    private double right;
    private double step;
    private int countRunningTasks;

    public Task(){
        /*function = new Exp();
        left = 0;
        right = 2;
        step = 1;
        countRunningTasks = 10;*/
    }

    public Task(Function function, double left, double right, double step, int countRunningTasks) {
        this.function = function;
        this.left = left;
        this.right = right;
        this.step = step;
        this.countRunningTasks = countRunningTasks;
    }

    public Function getFunction() {
        return function;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

    public double getStep() {
        return step;
    }

    public int getCountRunningTasks() {
        return countRunningTasks;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public void setRight(double right) {
        this.right = right;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public void setCountRunningTasks(int countRunningTasks) {
        this.countRunningTasks = countRunningTasks;
    }
}
