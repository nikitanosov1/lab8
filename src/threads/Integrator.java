package threads;

import functions.Functions;


public class Integrator extends Thread{
    private Task task;
    private Semaphore semaphore;

    public Integrator(Task task, Semaphore semaphore){
        this.task = task;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < task.getCountRunningTasks(); i++) {
                if (!isInterrupted()) {
                    semaphore.beginRead();
                    double result = Functions.integrate(task.getFunction(), task.getLeft(), task.getRight(), task.getStep());
                    System.out.println("Result " + task.getLeft() + " " + task.getRight() + " " + task.getStep() + " " + result);
                    semaphore.endRead();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
