package threads;

import functions.basic.Log;


public class Generator extends Thread{
    private Task task;
    private Semaphore semaphore;

    public Generator(Task task, Semaphore semaphore){
        this.task = task;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < task.getCountRunningTasks(); i++) {
                if (!isInterrupted()) {
                    semaphore.beginWrite();
                    task.setFunction(new Log(Math.random() * 9 + 1));
                    task.setLeft(Math.random() * 100);
                    task.setRight(Math.random() * 100 + 100);
                    task.setStep(Math.random());
                    System.out.println("Source " + task.getLeft() + " " + task.getRight() + " " + task.getStep());
                    semaphore.endWrite();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
