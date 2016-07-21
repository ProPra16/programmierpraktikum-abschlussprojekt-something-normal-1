package tracking;

import gui.StatsController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

public class Analytics {
    private StatsController controller;
    private ArrayList<Entry> stats;
    private int current = 0;
    public Analytics(StatsController controller, Runnable back){
        this.controller = controller;
        controller.prev_phase.setDisable(true);
        controller.next_phase.setDisable(true);
        controller.btn_return.setOnAction(e -> back.run());
        controller.next_phase.setOnAction(e -> next());
        controller.prev_phase.setOnAction(e -> prev());
    }
    public void loadTracking(Tracking tracking){
        stats = tracking.timeLine;
        controller.next_phase.setDisable(true);
        controller.prev_phase.setDisable(true);
        if(stats.size() > 0) {
            current = 0;
            setCurrent();
            if(stats.size() > 1) controller.next_phase.setDisable(false);
        }
        drawChart();
    }

    public void next(){
        controller.prev_phase.setDisable(false);
        current++;
        setCurrent();
        if(stats.size() == current+1) controller.next_phase.setDisable(true);
        drawChart();
    }

    public void prev(){
        controller.next_phase.setDisable(false);
        current--;
        setCurrent();
        if(current == 0) controller.prev_phase.setDisable(true);
        drawChart();
    }

    private void setCurrent(){
        controller.code_before.setText(stats.get(current).code_before);
        controller.code_after.setText(stats.get(current).code_after);
        controller.test_before.setText(stats.get(current).test_before);
        controller.test_after.setText(stats.get(current).test_after);
        controller.phase_label.setText("Phase: "+stats.get(current).phase);
        controller.time_label.setText("Time: "+Integer.toString(stats.get(current).time)+"sec");
        switch (stats.get(current).phase) {
            case "RED":
                controller.phase_label.setTextFill(Paint.valueOf("red"));
                break;
            case "GREEN":
                controller.phase_label.setTextFill(Paint.valueOf("green"));
                break;
            default:
                controller.phase_label.setTextFill(Paint.valueOf("blue"));
                break;
        }
    }

    private void drawChart(){
        int steps = 800/stats.size();
        double max = 0;
        for(Entry t:stats){
            if (t.time > max) max = t.time;
        }
        GraphicsContext gc = controller.chart_canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.WHITE);
        gc.fillRect(0,0,900,250);
        double curTime;
        for(int i = 0; i<stats.size();i++){
            curTime = stats.get(i).time;
            gc.setFill(Color.BLUE);
            gc.setStroke(Color.BLUE);
            if(stats.get(i).phase.contains("RED")) {
                gc.setFill(Color.RED);
                gc.setStroke(Color.RED);
            }
            if(stats.get(i).phase.contains("GREEN")){
                gc.setFill(Color.GREEN);
                gc.setStroke(Color.GREEN);
            }
            gc.fillRect(i*steps+10,210.0-((curTime/max)*200.0),steps,(curTime/max)*200.0);
        }
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeLine(current*steps+10+steps/2,230,current*steps+10+steps/2,215);
        gc.strokeLine(current*steps+10+steps/2,215,current*steps+10+steps/2-3,218);
        gc.strokeLine(current*steps+10+steps/2,215,current*steps+10+steps/2+3,218);

        gc.strokeLine(10, 10, 10, 210);
        gc.strokeLine(10, 10, 15, 15);
        gc.strokeLine(10, 10, 5, 15);
        gc.strokeLine(10, 210, 810, 210);
        gc.strokeLine(810, 210, 805, 205);
        gc.strokeLine(810, 210, 805, 215);
    }
}
