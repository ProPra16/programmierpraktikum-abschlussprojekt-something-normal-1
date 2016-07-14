package logic;

import javafx.animation.AnimationTimer;

public class Babysteps extends AnimationTimer{
    private long Babystepstime,starttime;
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
        if(pastTime < Babystepstime){
            try{
                wait(1000);
            }catch (Exception e){

            }
            whiletask.run();
        }else{
            endtask.run();
            stop();
        }
    }
    public void reset(){
        starttime = System.currentTimeMillis();
    }
}
