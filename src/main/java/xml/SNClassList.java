package xml;

import java.util.ArrayList;

public class SNClassList {

	private ArrayList<SNClass> classes = new ArrayList<>();

	public void addClass(SNClass snClass) {
		
		classes.add(snClass);
		
	}
	
	public SNClass get(int i)
	{
		return classes.get(i);
	}
	public SNClass get()
	{
		return classes.get(0);
	}
}
