package logic;

public class Babysteps extends Thread{
    private long startingTime;
    private double babystepsTime;
    private Runnable task;
    public void start(double seconds,Runnable task){
        babystepsTime = seconds;

    }
    public void run(){

    }
}
