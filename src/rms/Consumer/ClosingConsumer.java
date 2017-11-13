/*
 * Closing
 */
package rms.Consumer;

import java.util.concurrent.TimeUnit;
import static rms.MyUtils.MyConfig.isClosingTime;
import static rms.MyUtils.MyUtils.log;
public class ClosingConsumer implements Runnable {
    
    
    @Override
    public void run() {
        
        try {
            TimeUnit.SECONDS.sleep(2);
            log("close!");
            isClosingTime = true;
        } catch (InterruptedException ex) {
            log(ex);
        }
        
    }
}
