package xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlParser {

	private ExerciseList exerciseList;
	
	
	public XmlParser(String fileName) {
		exerciseList = new ExerciseList();
		File file = new File(fileName);
		
		DocumentBuilderFactory dbFactory  = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
	
		try {
		dBuilder = dbFactory.newDocumentBuilder();
		 Document doc = dBuilder.parse(file);
	     doc.getDocumentElement().normalize();
	     NodeList nList = doc.getElementsByTagName("exercise");
	     parse(nList);
	     
	     
			} catch (ParserConfigurationException e) {
		
				e.printStackTrace();
			} catch (SAXException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		    
			}

	public ExerciseList getList() {
		return exerciseList;
	}

	private void parse(NodeList nList)
	{
		for (int i = 0; i < nList.getLength(); i++) {
		     Exercise exercise = new Exercise();
	    	 Node nNode = nList.item(i);
	    	 Element e = ((Element)nNode);
	    	 exercise.setName(e.getAttribute("name"));
	    	 exercise.setDesc(e.getElementsByTagName("description").item(0).getTextContent());
	    	 
	    	 NodeList classNodeList = e.getElementsByTagName("class");
	    	 NodeList testNodeList = e.getElementsByTagName("test");
	    	 exercise.setClassList(getClassList(classNodeList));
	    	 exercise.setTestList(getTestList(testNodeList));
	    	 
	    	 
	    	 Element temp = (Element)e.getElementsByTagName("babysteps").item(0);
	    	 Element temp2 = (Element) e.getElementsByTagName("timetracking").item(0);
	    	 exercise.setConfig(new Config(temp.getAttribute("value"),temp2.getAttribute("value")));
	    	 if(exercise.getConfig().isBabysteps())
	    	 {
	    		 exercise.getConfig().setTime(((Element)e.getElementsByTagName("babysteps").item(0)).getAttribute("time"));
	    	 }
	    	 
	    	 
	    	 exerciseList.addExercise(exercise);

		
		
		}
	}
	
	private SNClassList getClassList(NodeList nodeList)
	{
		SNClassList classList = new SNClassList();
		for(int i = 0 ; i < nodeList.getLength() ; i++)
		{
			Element e = (Element )nodeList.item(i);
			SNClass snClass = new SNClass(e.getAttribute("name"),e.getTextContent());
			classList.addClass(snClass);
		}
		return classList;
	}
	private SNTestList getTestList(NodeList nodeList)
	{
		SNTestList testList = new SNTestList();
		for(int i = 0 ; i < nodeList.getLength() ; i++)
		{
			Element e = (Element )nodeList.item(i);
			SNTest snTest = new SNTest(e.getAttribute("name"),e.getTextContent());
			testList.addClass(snTest);
		}
		return testList;
	}
}
