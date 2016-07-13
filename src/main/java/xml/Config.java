package xml;

public class Config {

	
	private boolean babysteps;
	private int time;
	private boolean timetracking;
	
	public Config(String babysteps, String timetracking) {
		time = 0;
		if(babysteps.equalsIgnoreCase("false"))
		{
			this.babysteps = false;
		}
		else
			this.babysteps = true;
		if(timetracking.equalsIgnoreCase("false"))
		{
			this.timetracking = false;
		}
		else
			this.timetracking = true;
	}
	
	
	
	public void setTime(String string) {
		// saving the time as seconds;
		String[] str = string.split(":");
		int time = Integer.parseInt(str[0]) * 60;
		time += Integer.parseInt(str[1]);
		this.time = time;
		
	}

	public int getTime()
	{
		return time;
	}

	public boolean isBabysteps() {
		return babysteps;
	}
	
	
	public boolean isTimetracking() {
		return timetracking;
	}

	
	
}
