package xml;

import java.util.ArrayList;

public class SNTestList {

	private ArrayList<SNTest> tests = new ArrayList<>();

	public void addClass(SNTest snTest) {
		
		tests.add(snTest);
		
	}
	public void addTest(SNTest snTest) {
		
		tests.add(snTest);
		
	}
	
	public SNTest get(int i)
	{
		return tests.get(i);
	}
	public SNTest get()
	{
		return tests.get(0);
	}
}
