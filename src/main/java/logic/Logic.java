package logic;

import gui.GUIController;
import vk.core.internal.InternalResult;
import xml.Exercise;
import xml.ExerciseList;
import xml.XmlParser;

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
        //show list to user
    }

    public void startExercise(Exercise exercise){
        currentExercise = exercise;
        //set test to textfield
        //set code to textfield2
        //set title to a label..

        if(exercise.getConfig().isBabysteps()) {
            timer = new Babysteps(exercise.getConfig().getTime(), () -> { /* change time-label */} , () -> { /* reset phase */});
            timer.start();
        }
    }

    public void goToNextPhase(){
        if(currentExercise != null) {
            InternalResult[] cp = Compiler.compile(currentExercise);
            if (currentPhase == Phase.RED) {
                if (cp[0].hasCompileErrors() || cp[1].getNumberOfFailedTests() == 1) {
                    changeToGreen();
                }
            }
            if (currentPhase == Phase.GREEN || currentPhase == Phase.REFRACTOR) {
                //compile tests and code
                //if it compiles and tests dont fail then change to (green -> refracor) (refractor -> red)
                if (!cp[0].hasCompileErrors() || cp[1].getNumberOfFailedTests() == 0){
                    if(currentPhase == Phase.GREEN) changeToRefractor();
                    if(currentPhase == Phase.REFRACTOR) changeToRed();
                }

            }
        }
    }
    private void changeToGreen(){
        currentPhase = Phase.GREEN;
        if(currentExercise.getConfig().isBabysteps()) timer.reset();
        //block test-textarea
        //unblock code-textarea
        //change phase-label
    }
    private void changeToRed(){
        currentPhase = Phase.RED;
        if(currentExercise.getConfig().isBabysteps()) timer.reset();
        //unblock test-textarea
        //block code-textarea
        //change phase-label
    }
    private void changeToRefractor(){
        currentPhase = Phase.REFRACTOR;
        if(currentExercise.getConfig().isBabysteps()) timer.reset();
        //ask what the user wants to do (change code or change test) and unblock/block right the textareas
        //change phase-label
    }


}
