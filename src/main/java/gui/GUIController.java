package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import xml.Exercise;

public class GUIController implements Initializable {

    @FXML
    public Label label_time;
    @FXML
    public Button btn_nextPhase;
    @FXML
    public Button btn_compileTest;
    @FXML
    public ComboBox<Exercise> combo_exercises;
    @FXML
    public TextArea textArea_test;
    @FXML
    public TextArea textArea_code;
    @FXML
    public TextArea textArea_console;

    public Model model;


    public void initialize(URL location, ResourceBundle resources) {
        model = new Model();
        combo_exercises.setItems(model.comboBoxData);
        combo_exercises.setOnAction((event) -> {

            // open a dialog and ask for if do you really want to change the exercise?

            System.out.println("Item Selected");
        });

    }

}