package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class StatsController {
    @FXML
    public TextArea code_before, code_after, test_before, test_after;
    public Button next_phase,prev_phase,btn_return;
    public Label phase_label,time_label;
}
