package tracking;

public class Entry {
    public int time;
    public String code_before,test_before,code_after,test_after,phase;

    public Entry(int time, String code_before, String test_before, String code_after, String test_after, String phase){
        this.time = time;
        this.code_before = code_before;
        this.test_after = test_after;
        this.code_after = code_after;
        this.test_before = test_before;
        this.phase = phase;
    }
}
