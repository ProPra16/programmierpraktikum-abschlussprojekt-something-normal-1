package logic;


import gui.GUIController;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import tracking.Tracking;
import vk.core.api.TestResult;
import xml.*;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Logic {
    public Phase currentPhase;
    public gui.GUIController controller;
    public Babysteps timer;
    public ExerciseList exerciseList;
    public Exercise currentExercise;
    public Tracking timeLine;
    public Runnable showStatistics;
    private XmlParser xml = new XmlParser("src/main/resources/exercises.xml");
    FileManager fileManager;

    public Logic(GUIController controller, Runnable showStatistics){
        fileManager = new FileManager();
        currentPhase = Phase.STOP;
        this.showStatistics = showStatistics;
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
                    startExercise();
                }
            }else {
                currentExercise = controller.combo_exercises.getValue().getClone();
                startExercise();
            }

        });
        controller.btn_compileTest.setOnAction(e -> {
            compileAndTest();
        } );
        controller.btn_nextPhase.setOnAction(event -> changePhase());
        controller.btn_backToRed.setOnAction(event -> {
            String p = "";
            switch (currentPhase){
                case GREEN:
                    p= "GREEN";
                    break;
                case RED:
                    p= "RED";
                    break;
                case REFRACTOR:
                    p= controller.label_phase.getText().contains("test") ? "REFRACTOR TEST": "REFRACTOR CODE";
            }
            if(currentExercise.getConfig().isTimetracking()) {
                timeLine.addEntryWithCurrentTime(currentExercise.getClassList().get().getClassContent(), currentExercise.getTestList().get().getTestContent(),controller.textArea_code.getText(),controller.textArea_test.getText(), p+" (back to red)");
                timeLine.startPhase();
            }
            changeToRed();
            if(currentExercise.getConfig().isBabysteps()) startNewBabysteps();
         // controller.textArea_code.setText(currentExercise.getClassList().get().getClassContent());
            controller.textArea_test.setText(currentExercise.getTestList().get().getTestContent());
        });

         controller.save.setOnAction(event -> {
             fileManager.save(controller.textArea_test.getText(),controller.textArea_code.getText(),currentExercise.getName());
         });
    }


    public void startExercise(){
        controller.textArea_code.setText(currentExercise.getClassList().get().getClassContent());
        controller.textArea_test.setText(currentExercise.getTestList().get().getTestContent());
        controller.textArea_test.setDisable(false);
        controller.btn_nextPhase.setDisable(true);
        controller.btn_compileTest.setDisable(false);
        if(currentExercise.getConfig().isBabysteps()) { startNewBabysteps(); }
        if(currentExercise.getConfig().isTimetracking()) {
            timeLine = new Tracking();
            timeLine.startPhase();
        }
        changeToRed();
    }

    public void compileAndTest(){
        Exercise testExercise = new Exercise();
        testExercise.getClassList().addClass(new SNClass(currentExercise.getClassList().get().getName(),controller.textArea_code.getText()));
        testExercise.getTestList().addTest(new SNTest(currentExercise.getTestList().get().getName(),controller.textArea_test.getText()));
        String compilation = "code compilation successful";
        String testing = "";
        int result = -1;
        if(!Compiler.isCompileable(testExercise)) {
            compilation = "code compilation failed";
        }
        else {
            TestResult testResult = Compiler.compileAndRunTests(testExercise);
            if(testResult != null) {
                testing = testResult.getNumberOfFailedTests() + " test(s) failed";
                result = testResult.getNumberOfFailedTests();
            }else {
                testing = "test compilation failed";
                result = -2;
            }
        }
        controller.textArea_console.appendText(compilation+"\n"+testing+"\n");

        updatePhaseChanging(result);
    }

    public void updatePhaseChanging(int result){
        if(currentPhase == Phase.RED){
            if(result < 0 || result == 1){
                controller.textArea_console.appendText("You can change Phase now\n");
                controller.btn_nextPhase.setDisable(false);
            }
        }
        if(currentPhase == Phase.GREEN){
            if(result == 0){
                controller.textArea_console.appendText("You can change Phase now\n");
                controller.btn_nextPhase.setDisable(false);
            }
        }
        if(currentPhase == Phase.REFRACTOR){
            if(result == 0){
                controller.textArea_console.appendText("You can change Phase now\n");
                controller.btn_nextPhase.setDisable(false);
            }
        }
    }

    public void changePhase() {
        String p = "";
        switch (currentPhase){
            case GREEN:
                p= "GREEN";
                break;
            case RED:
                p= "RED";
                break;
            case REFRACTOR:
                p= controller.label_phase.getText().contains("test") ? "REFRACTOR TEST": "REFRACTOR CODE";
        }
        if(currentExercise.getConfig().isTimetracking()) {
            timeLine.addEntryWithCurrentTime(currentExercise.getClassList().get().getClassContent(), currentExercise.getTestList().get().getTestContent(), controller.textArea_code.getText(), controller.textArea_test.getText(), p);
            timeLine.startPhase();
        }
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
        if(currentExercise.getConfig().isBabysteps()) startNewBabysteps();
        controller.textArea_test.setDisable(true);
        controller.textArea_code.setDisable(false);
        controller.label_phase.setText("PHASE=GREEN");
        controller.label_phase.setTextFill(Paint.valueOf("green"));
        controller.btn_backToRed.setDisable(false);
    }
    private void changeToRed(){
        currentPhase = Phase.RED;
        controller.btn_backToRed.setDisable(true);
        if(currentExercise.getConfig().isBabysteps()) startNewBabysteps();
        controller.textArea_test.setDisable(false);
        controller.textArea_code.setDisable(true);
        controller.label_phase.setText("PHASE=RED");
        controller.label_phase.setTextFill(Paint.valueOf("red"));
    }
    private void changeToRefractor(){
        currentPhase = Phase.REFRACTOR;
        controller.btn_backToRed.setDisable(true);
        if(currentExercise.getConfig().isBabysteps()) startNewBabysteps();
        controller.label_phase.setText("PHASE=REFRACTOR");
        String[] s;
        if(currentExercise.getConfig().isTimetracking()) {
            s = new String[3];
            s[0] = "edit code";
            s[1] = "edit test";
            s[2] = "finish project and show statistics";
        }else {
            s = new String[3];
            s[0] = "edit code";
            s[1] = "edit test";
            s[2] = "finish project and save it";
        }
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
                if(result.get().equals("edit test")) {
                    controller.textArea_test.setDisable(false);
                    controller.textArea_code.setDisable(true);
                    controller.label_phase.setText(controller.label_phase.getText() + "(edit test)");
                }else{
                    if(result.get().equals("finish project and save it")){
                        //save it...
                    }else showStatistics.run();
                }
            }
        }
        controller.label_phase.setTextFill(Paint.valueOf("blue"));
    }

    public void startNewBabysteps(){
        if(timer != null) timer.stop();
        timer = new Babysteps(currentExercise.getConfig().getTime(), () -> controller.label_time.setText(Long.toString(timer.Babystepstime-(System.currentTimeMillis()-timer.starttime)/1000)) ,
                () -> {
                    startNewBabysteps();
                    controller.textArea_code.setText(currentExercise.getClassList().get().getClassContent());
                    controller.textArea_test.setText(currentExercise.getTestList().get().getTestContent());

                });
        timer.start();
    }


}
