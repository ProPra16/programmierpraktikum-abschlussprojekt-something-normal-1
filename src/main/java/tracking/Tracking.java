package tracking;

import java.util.ArrayList;

public class Tracking {
    public ArrayList<Entry> timeLine = new ArrayList<>();
    private long starttime;

    public void addEntryWithCurrentTime(String code, String test, String phase) {
        timeLine.add(new Entry((int)((System.currentTimeMillis()-starttime)/1000), code, test, phase));
    }

    public void startPhase(){
        starttime = System.currentTimeMillis();
        for(Entry t:timeLine){
            System.out.println(t.phase+t.time);
        }
    }
}
