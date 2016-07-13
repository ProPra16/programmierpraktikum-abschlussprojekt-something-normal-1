import org.junit.Assert;
import org.junit.Test;

import xml.ExerciseList;
import xml.XmlParser;

public class XmlTest {

	
	@Test
	public void getExerciseList() {
		XmlParser xmlParser = new XmlParser("exercises.xml");
		ExerciseList exerciseList = xmlParser.getList();
	
		Assert.assertNotNull(exerciseList);
	}
	
	@Test
	public void getExerciseName() {
		XmlParser xmlParser = new XmlParser("exercises.xml");
		ExerciseList exerciseList = xmlParser.getList();
		
		Assert.assertEquals("Römische Zahlen", exerciseList.get(0).getName());
	}
	
	@Test
	public void getClassContent() {
		XmlParser xmlParser = new XmlParser("exercises.xml");
		ExerciseList exerciseList = xmlParser.getList();
		
		Assert.assertEquals("RomanNumberConverter", exerciseList.get(0).getClassList().getClass(0).getName());
	}
	@Test
	public void checkTimeTracking() {
		XmlParser xmlParser = new XmlParser("exercises.xml");
		ExerciseList exerciseList = xmlParser.getList();
		
		Assert.assertTrue( exerciseList.get(0).getConfig().isTimetracking());
	}
	@Test
	public void getTime() {
		XmlParser xmlParser = new XmlParser("exercises.xml");
		ExerciseList exerciseList = xmlParser.getList();
		
		Assert.assertEquals(120,exerciseList.get(1).getConfig().getTime());
	}
	
}
