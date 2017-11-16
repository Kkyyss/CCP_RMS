/*
 * Clock Consumer.
 */
package rms.Consumer;

import java.util.concurrent.TimeUnit;
import static rms.MyUtils.MyConfig.conf;
import static rms.MyUtils.MyUtils.errorLog;

public class ClockConsumer implements Runnable {
    private String name;
    
    public ClockConsumer(String name) {
        this.name = name;
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                
                if (conf.getLastOrder()) {
                    
                }
                
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException ex) {
            errorLog(ex);
        }
    }
}
