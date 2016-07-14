import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import xml.Exercise;

public class Controller implements Initializable { 

    @FXML 
    private Label label_time;
    @FXML 
    private Button btn_nextPhase; 
    @FXML 
    private Button btn_compileTest; 
    @FXML 
    private ComboBox<Exercise> combo_exercises; 
    @FXML 
    private TextArea textArea_test; 
    @FXML 
    private TextArea textArea_code;
    @FXML 
    private TextArea textArea_console;

    private Model model; 

    
    public void initialize(URL location, ResourceBundle resources) { 
        model = new Model(); 
        combo_exercises.setItems(model.comboBoxData);
        combo_exercises.setOnAction((event) -> {
        	
        		// open a dialog and ask for if do you really want to change the exercise?
        	
        	 System.out.println("Item Selected");
             });
       
    } 

    public void compileAndTest() { 
       // button func
    } 
    public void nextPhase() { 
       //  button func
    } 
} 