package logic;

import gui.GUIController;
import javafx.scene.control.*;
import vk.core.internal.InternalCompiler;
import xml.Exercise;
import xml.ExerciseList;
import xml.XmlParser;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Logic {
    public Phase currentPhase = Phase.STOP;
    public gui.GUIController controller;
    public Babysteps timer;
    public ExerciseList exerciseList;
    public Exercise currentExercise;
    private XmlParser xml = new XmlParser("src/main/resources/exercises.xml");

    public Logic(GUIController controller){
        this.controller = controller;
        exerciseList = xml.getList();
        controller.combo_exercises.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You are about to start a new Exercise!", ButtonType.OK, ButtonType.CANCEL);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    currentExercise = controller.combo_exercises.getValue().getClone();
                    startExercise();
                }

        });
        controller.btn_compileTest.setOnAction(e -> {
            currentExercise.getTestList().get().setTestContent(controller.textArea_test.getText());
            currentExercise.getClassList().get().setClassContent(controller.textArea_code.getText());
            compileAndTest();
        } );
        //show list to user
    }


    public void startExercise(){
        controller.textArea_code.setText(currentExercise.getClassList().get().getClassContent());
        controller.textArea_test.setText(currentExercise.getTestList().get().getTestContent());
        controller.textArea_test.setDisable(false);
        controller.label_phase.setText("PHASE=RED");
        if(timer != null) { timer.reset(); timer.stop(); controller.label_time.setText("00"); }
        if(currentExercise.getConfig().isBabysteps()) {
            timer = new Babysteps(currentExercise.getConfig().getTime(), () -> controller.label_time.setText(Long.toString(timer.Babystepstime-(System.currentTimeMillis()-timer.starttime)/1000)) ,
                    () -> {
                        timer.reset();
                        //reset exercise to last phase
                    });
            timer.start();
        }
        controller.textArea_test.setOnKeyTyped( e -> {
            controller.btn_nextPhase.setDisable(true);
        });
    }

    public void compileAndTest(){
        String compilation = "compilation successful";
        String testing = "";
        if(!Compiler.isCompileable(currentExercise)) compilation = "compilation failed";
        else {
            testing = Compiler.compileAndRunTests(currentExercise)+" test(s) failed";
        }
        controller.textArea_console.setText(controller.textArea_console.getText()+"\n"+compilation+"\n"+testing);
        /*if(cp[1].getNumberOfFailedTests() > 0) testing = cp[1].getNumberOfFailedTests()+" test(s) failed";
        controller.textArea_console.setText(controller.textArea_console.getText()+"\n"+compilation+"\n"+testing);
        if(currentPhase == Phase.RED){
            if (cp[1].getNumberOfFailedTests() == 1) {
                controller.btn_nextPhase.setDisable(false);
            }
        }*/
    }

    public void goToNextPhase(){
        if(currentPhase == Phase.GREEN) changeToRefractor();
        if(currentPhase == Phase.RED) changeToGreen();
        if(currentPhase == Phase.REFRACTOR) changeToRed();
    }
    private void changeToGreen(){
        currentPhase = Phase.GREEN;
        if(currentExercise.getConfig().isBabysteps()) timer.reset();
        controller.textArea_test.setDisable(true);
        controller.textArea_code.setDisable(false);
        controller.label_phase.setText("PHASE=GREEN");
    }
    private void changeToRed(){
        currentPhase = Phase.RED;
        if(currentExercise.getConfig().isBabysteps()) timer.reset();
        //unblock test-textarea
        //block code-textarea
        //change phase-label
        controller.textArea_test.setDisable(false);
        controller.textArea_code.setDisable(true);
        controller.label_phase.setText("PHASE=RED");
    }
    private void changeToRefractor(){
        currentPhase = Phase.REFRACTOR;
        if(currentExercise.getConfig().isBabysteps()) timer.reset();
        controller.label_phase.setText("PHASE=REFRACTOR");
        //ask what the user wants to do (change code or change test) and unblock/block right the textareas
        String[] s = {"edit code","edit test"};
        List<String> dialogData = Arrays.asList(s);
        ChoiceDialog<String> dialog = new ChoiceDialog<>(dialogData.get(0),dialogData);
        dialog.setTitle("Refractor");
        dialog.setHeaderText("Choose wisely:");
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()) {
            if(result.get().equals("edit code")){
                controller.textArea_code.setDisable(false);
                controller.textArea_test.setDisable(true);
            }else{
                controller.textArea_code.setDisable(true);
                controller.textArea_test.setDisable(false);
            }

        }
        //change phase-label
    }


}
