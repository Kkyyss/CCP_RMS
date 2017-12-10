/*
 * Juice Foundtain Tap
 */
package rms.Model;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import static rms.MyUtils.MyUtils.log;

public class JuiceFountainTap extends ServingArea {
    private final ReentrantLock lockFill = new ReentrantLock();
    private final ReentrantLock lockTap = new ReentrantLock();
    
    public static JuiceFountainTap juiceFountainTap = new JuiceFountainTap();
    
    public JuiceFountainTap() {}
    
    public void getFountainTap(int seconds) {
        lockTap.lock();
        
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException ex) {
            log(ex);
        } finally {
            lockTap.unlock();
        }
    }
    
    public void getFill(int seconds) {
        lockFill.lock();
        
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException ex) {
            log(ex);
        } finally {
            lockFill.unlock();
        }
    }
}
