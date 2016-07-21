package tracking;

import java.util.ArrayList;

public class Tracking {
    public ArrayList<Entry> timeLine = new ArrayList<>();
    private long starttime;

    public void addEntryWithCurrentTime(String code_before, String test_before, String code_after, String test_after, String phase) {
        timeLine.add(new Entry((int)((System.currentTimeMillis()-starttime)/1000), code_before, test_before, code_after, test_after, phase));
    }

    public void startPhase(){
        starttime = System.currentTimeMillis();
        for(Entry t:timeLine){
            System.out.println(t.phase+t.time);
        }
    }
}
