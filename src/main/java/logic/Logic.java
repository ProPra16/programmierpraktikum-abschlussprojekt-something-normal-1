package logic;

import gui.GUIController;
import javafx.scene.control.*;
import vk.core.api.TestResult;
import vk.core.internal.InternalCompiler;
import xml.Exercise;
import xml.ExerciseList;
import xml.XmlParser;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Logic {
    public Phase currentPhase;
    public gui.GUIController controller;
    public Babysteps timer;
    public ExerciseList exerciseList;
    public Exercise currentExercise;
    public String startTest,startCode;
    private XmlParser xml = new XmlParser("src/main/resources/exercises.xml");

    public Logic(GUIController controller){
        currentPhase = Phase.STOP;
        this.controller = controller;
        exerciseList = xml.getList();
        controller.combo_exercises.setOnAction(e -> {
            if(currentPhase != Phase.STOP) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Are you sure?");
                String s = "You are about to change Exercise";
                alert.setHeaderText(s);
                Optional<ButtonType> result = alert.showAndWait();
                if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                    currentExercise = controller.combo_exercises.getValue().getClone();
                    startTest = currentExercise.getTestList().get().getTestContent();
                    startCode = currentExercise.getClassList().get().getClassContent();
                    startExercise();
                }
            }else {
                currentExercise = controller.combo_exercises.getValue().getClone();
                startTest = currentExercise.getTestList().get().getTestContent();
                startCode = currentExercise.getClassList().get().getClassContent();
                startExercise();
            }

        });
        controller.btn_compileTest.setOnAction(e -> {
            currentExercise.getTestList().get().setTestContent(controller.textArea_test.getText());
            currentExercise.getClassList().get().setClassContent(controller.textArea_code.getText());
            compileAndTest();
        } );
        controller.btn_nextPhase.setOnAction(event -> changePhase());
    }


    public void startExercise(){
        controller.textArea_code.setText(currentExercise.getClassList().get().getClassContent());
        controller.textArea_test.setText(currentExercise.getTestList().get().getTestContent());
        controller.textArea_test.setDisable(false);
        controller.btn_nextPhase.setDisable(true);
        controller.label_phase.setText("PHASE=RED");
        currentPhase = Phase.RED;
        if(currentExercise.getConfig().isBabysteps()) {
            timer = new Babysteps(currentExercise.getConfig().getTime(), () -> controller.label_time.setText(Long.toString(timer.Babystepstime-(System.currentTimeMillis()-timer.starttime)/1000)) ,
                    () -> {
                        timer.reset();
                        controller.textArea_code.setText(startCode);
                        controller.textArea_test.setText(startTest);

                    });
            timer.start();
        }
        controller.textArea_test.setOnKeyTyped( e -> {
            controller.btn_nextPhase.setDisable(true);
        });
    }

    public void compileAndTest(){
        String compilation = "code compilation successful";
        String testing = "";
        int result = -1;
        if(!Compiler.isCompileable(currentExercise)) {
            compilation = "code compilation failed";
        }
        else {
            TestResult testResult = Compiler.compileAndRunTests(currentExercise);
            if(testResult != null) {
                testing = testResult.getNumberOfFailedTests() + " test(s) failed";
                result = testResult.getNumberOfFailedTests();
            }else {
                testing = "test compilation failed";
                result = -2;
            }
        }
        controller.textArea_console.setText(controller.textArea_console.getText()+"\n"+compilation+"\n"+testing+result);
        updatePhaseChanging(result);
    }

    public void updatePhaseChanging(int result){
        if(currentPhase == Phase.RED){
            if(result < 0 || result == 1){
                controller.textArea_console.setText(controller.textArea_console.getText()+"\nYou can change Phase now");
                controller.btn_nextPhase.setDisable(false);
            }
        }
        if(currentPhase == Phase.GREEN){
            if(result == 0){
                controller.textArea_console.setText(controller.textArea_console.getText()+"\nYou can change Phase now");
                controller.btn_nextPhase.setDisable(false);
            }
        }
        if(currentPhase == Phase.REFRACTOR){
            if(result == 0){
                controller.textArea_console.setText(controller.textArea_console.getText()+"\nYou can change Phase now");
                controller.btn_nextPhase.setDisable(false);
            }
        }
    }

    public void changePhase(){
        switch (currentPhase) {
            case RED:
                changeToGreen();
                break;
            case GREEN:
                changeToRefractor();
                break;
            case REFRACTOR:
                changeToRed();
        }
        controller.btn_nextPhase.setDisable(true);
        currentExercise.getTestList().get().setTestContent(controller.textArea_test.getText());
        currentExercise.getClassList().get().setClassContent(controller.textArea_code.getText());
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
        controller.textArea_test.setDisable(false);
        controller.textArea_code.setDisable(true);
        controller.label_phase.setText("PHASE=RED");
    }
    private void changeToRefractor(){
        currentPhase = Phase.REFRACTOR;
        if(currentExercise.getConfig().isBabysteps()) timer.reset();
        controller.label_phase.setText("PHASE=REFRACTOR");
        String[] s = {"edit code","edit test"};
        List<String> dialogData = Arrays.asList(s);
        ChoiceDialog<String> dialog = new ChoiceDialog<>(dialogData.get(0),dialogData);
        dialog.setTitle("Refractor");
        dialog.setHeaderText("Choose wisely:");
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()) {
            if(result.get().equals("edit code")) {
                controller.textArea_test.setDisable(true);
                controller.textArea_code.setDisable(false);
                controller.label_phase.setText(controller.label_phase.getText()+"(edit code)");
            }
            else{
                controller.textArea_test.setDisable(false);
                controller.textArea_code.setDisable(true);
                controller.label_phase.setText(controller.label_phase.getText()+"(edit test)");
            }
        }
    }


}
