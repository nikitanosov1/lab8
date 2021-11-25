package threads;

import functions.Functions;

public class SimpleIntegrator implements Runnable{
    private Task task;

    public SimpleIntegrator(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        for (int i = 0; i < task.getCountRunningTasks(); i++) {
            synchronized (task){
                double result = Functions.integrate(task.getFunction(), task.getLeft(), task.getRight(), task.getStep());
                System.out.println("Result " + task.getLeft() + " " + task.getRight() + " " + task.getStep() + " " + result);
            }
        }
    }
}
