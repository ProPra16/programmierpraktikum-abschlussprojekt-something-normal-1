package logic;

import javafx.animation.AnimationTimer;

public class Babysteps extends AnimationTimer{
    public long Babystepstime,starttime;
    private Runnable whiletask;
    private Runnable endtask;
    public Babysteps(int seconds, Runnable whiletask, Runnable endtask){
        Babystepstime = seconds;
        starttime = System.currentTimeMillis();
        this.whiletask = whiletask;
        this.endtask = endtask;
    }
    @Override
    public void handle(long now) {
        long pastTime = System.currentTimeMillis()-starttime;
        if(pastTime/1000 < Babystepstime){
            whiletask.run();
        }else{
            endtask.run();
            stop();
        }
    }
    public void reset(){
        stop();
        starttime = System.currentTimeMillis();
        start();
    }
}
