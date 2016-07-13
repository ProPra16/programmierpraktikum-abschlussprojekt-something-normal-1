package logic;

import javafx.scene.control.Label;

public class Babysteps extends Thread{
    private long startingTime;
    private double babystepsTime;
    private Runnable task;
    private Label label;
    public void start(double seconds, Label label, Runnable endtask){
        babystepsTime = seconds;
        startingTime = System.currentTimeMillis();
        this.label = label;
        task = endtask;
        run();
    }

    public void run(){
        long pastTime = System.currentTimeMillis()-startingTime;
        while(pastTime<babystepsTime){
            try {
                label.setText(Double.toString(babystepsTime-pastTime));
                wait(1000);
            }
            catch (InterruptedException e){
                System.out.println("Error in Babysteps Thread");
            }
        }
        task.run();
    }
}
