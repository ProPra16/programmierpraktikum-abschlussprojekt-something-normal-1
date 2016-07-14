package xml;

import java.util.ArrayList;

public class ExerciseList {

	ArrayList<Exercise> exercises = new ArrayList<>();
	
	
	public Exercise get(int i) {
		return exercises.get(i);
	}
	public int getSize()
	{
		return exercises.size();
	}
	public void addExercise(Exercise exercise) {
		exercises.add(exercise);
	}
}
