package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import xml.Exercise;
import xml.ExerciseList;
import xml.XmlParser;

public class Model {

	public ObservableList<Exercise> comboBoxData = FXCollections.observableArrayList();
	ExerciseList exerciseList;
	public Model()
	{
		XmlParser xmlParser = new XmlParser("src/main/resources/exercises.xml");
		exerciseList = xmlParser.getList();
		addData();
		
	}
	private void addData()
	{
		for(int i = 0 ; i < exerciseList.getSize(); i++)
		{
		comboBoxData.addAll(exerciseList.get(i));
		}
	}
}
