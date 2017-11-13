/*
 * Juice Foundtain Tap
 */
package rms.Model;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import static rms.MyUtils.MyUtils.log;

public class JuiceFountainTap extends ServingArea {
    private ReentrantLock lock = new ReentrantLock();
    
    public JuiceFountainTap() {}
    
    public void getFill(Integer seconds) {
        lock.lock();
        
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException ex) {
            log(ex);
        } finally {
            lock.unlock();
        }
    }
}
