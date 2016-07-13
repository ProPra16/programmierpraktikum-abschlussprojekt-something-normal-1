
import static org.junit.Assert.assertEquals;

import logic.Babysteps;
import org.junit.Test;

public class BabystepsTest {
    int k = 1;
    int p = 1;
    @Test
    public void endTaskAndWhileTaskTest() {
        Babysteps babysteps = new Babysteps(100, new Runnable() {
            @Override
            public void run() {
                p++;
            }
        }, new Runnable() {
            @Override
            public void run() {
                k = 0;
            }
        });
        assertEquals(p,1);
        assertEquals(k,1);
    }
}