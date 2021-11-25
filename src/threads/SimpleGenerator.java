package threads;

import functions.basic.Log;

public class SimpleGenerator implements Runnable{
    private Task task;

    public SimpleGenerator(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        for (int i = 0; i < task.getCountRunningTasks(); i++) {
            synchronized (task) {
                task.setFunction(new Log(Math.random() * 9 + 1));
                task.setLeft(Math.random() * 100);
                task.setRight(Math.random() * 100 + 100);
                task.setStep(Math.random());
                System.out.println("Source " + task.getLeft() + " " + task.getRight() + " " + task.getStep());
            }
        }
    }
}
