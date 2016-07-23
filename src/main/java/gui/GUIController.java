package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import sun.rmi.runtime.Log;
import xml.Exercise;

public class GUIController implements Initializable {

    @FXML
    public Label label_time,label_phase;
    public Button btn_nextPhase;
    public Button btn_compileTest;
    public Button save;
   // public Button load;
    public ComboBox<Exercise> combo_exercises;
    public TextArea textArea_test;
    public TextArea textArea_code;
    public TextArea textArea_console;
    public Button btn_backToRed;

    public Model model;


    public void initialize(URL location, ResourceBundle resources) {
        model = new Model();
        combo_exercises.setItems(model.comboBoxData);

        textArea_console.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                textArea_console.setScrollTop(Double.MIN_VALUE);
            }
        });

    }

    public void textareaChanged(){
        btn_nextPhase.setDisable(true);
    }

}
