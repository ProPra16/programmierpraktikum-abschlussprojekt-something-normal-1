package tracking;

import gui.StatsController;
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
    }

    public void next(){
        controller.prev_phase.setDisable(false);
        current++;
        setCurrent();
        if(stats.size() == current+1) controller.next_phase.setDisable(true);
    }

    public void prev(){
        controller.next_phase.setDisable(false);
        current--;
        setCurrent();
        if(current == 0) controller.prev_phase.setDisable(true);
    }

    private void setCurrent(){
        controller.code_before.setText(stats.get(current).code_before);
        controller.code_after.setText(stats.get(current).code_after);
        controller.test_before.setText(stats.get(current).test_before);
        controller.test_after.setText(stats.get(current).test_after);
        controller.phase_label.setText(stats.get(current).phase);
        controller.time_label.setText(Integer.toString(stats.get(current).time));
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
}
