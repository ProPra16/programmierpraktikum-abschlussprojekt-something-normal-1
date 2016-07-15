package xml;

public class Exercise {

	private String name;
	private String desc;
	private SNClassList classList;
	private SNTestList testList;
	private Config config;
	
	public Exercise()
	{
		classList = new SNClassList();
		testList = new SNTestList();
		
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Config getConfig() {
		return config;
	}
	public void setConfig(Config config) {
		this.config = config;
	}

	public SNClassList getClassList() {
		return classList;
	}

	public void setClassList(SNClassList classList) {
			this.classList = classList;
	}

	public SNTestList getTestList() {
		return testList;
	}

	public void setTestList(SNTestList testList) {
		this.testList = testList;
	}
	
	@Override
	public String toString() {
	    return name;
	}

	public Exercise getClone(){
		Exercise result = new Exercise();
		result.setClassList(this.classList);
		result.setConfig(this.config);
		result.setDesc(this.getDesc());
		result.setName(this.name);
		result.setTestList(this.testList);
		return result;
	}
	
}
