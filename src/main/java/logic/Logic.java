package logic;

import gui.GUIController;

public class Logic {
    public Phase current = Phase.RED;
    public gui.GUIController controller;
    public Babysteps timer;
    //public ExerciseList exerciseList;
    //public Exercise currentExercise;

    public Logic(GUIController controller){
        this.controller = controller;
    }

    public void startExercise(){
        //loads xml... getting test, code, title, babystepsflag and babystepstime
        //set test to textfield
        //set code to textfield2
        //set title to a label..
        //if(babysteps) set babystepstime
        //set phase to red
        //start (if babysteps start timer)
    }

    public void goToNextPhase(){
        if(current == Phase.RED) {
            //compile test and code
            //if the test doesnt compile change to green
            //if the test compile, and 1 test fails change to green
            //else stay
        }
        if(current == Phase.GREEN || current == Phase.REFRACTOR){
            //compile tests and code
            //if it compiles and tests dont fail then change to (green -> refracor) (refractor -> red)
        }
    }


}
