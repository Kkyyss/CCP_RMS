/*
 * Staff.
 */
package rms.Model;

import java.util.concurrent.TimeUnit;
import static rms.MyUtils.MyUtils.log;

public class Staff {
    private JobPosition.Position position = null;
    private String name;
    private boolean idle = true;
    
    public Staff() {}
    
    public Staff(JobPosition.Position position) {
        this.setPosition(position);
    }

    public JobPosition.Position getPosition() {
        return position;
    }
    public String getName() {
        return name;
    }
    
    public void setPosition(JobPosition.Position position) {
        this.position = position;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setIdle(boolean idle) {
        this.idle = idle;
    }
    
    public void getRest(Integer seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException ex) {
            log(ex);
        }
    }
    
    public boolean isIdle() {
        return idle;
    }
}
