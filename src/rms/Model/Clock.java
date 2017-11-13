/*
 * Clock
 */
package rms.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

public class Clock {
    private String time;
    private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss a");
    
    public Clock() {
        time = dateFormat.format(new Date());
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
