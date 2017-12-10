/*
 * Helper functions.
 */
package rms.MyUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import static rms.MyUtils.MyConfig.myErrorLog;
import static rms.MyUtils.MyConfig.mySanityLog;
import static rms.MyUtils.MyConfig.myTimeLog;

public class MyUtils {
    // Logging.
    public static <T> void log(T t) {
        if (MyConfig.DEBUG) {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            String time = dateFormat.format(date);
            String content = "Time " + time + "---> " + t;

            System.out.println(content);
            myTimeLog.add(content + "\n");
        }
    }
    
    public static <T> void sanityLog(T t) {
        if (MyConfig.DEBUG) {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            String time = dateFormat.format(date);
            String content = "Time " + time + "---> " + t;

            // System.out.println(content);
            mySanityLog.add(content + "\n");
        }
    }
    
    public static <T> void sanityLog(T t, boolean isTime) {
        if (MyConfig.DEBUG) {
            String content = t + "";
            if (isTime) {
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Date date = new Date();
                String time = dateFormat.format(date);
                content = "Time " + time + "---> " + content;
            }

            // System.out.println(content);
            mySanityLog.add(content + "\n");
        }
    }    
    
    public static <T> void errorLog(T t) {
        if (MyConfig.DEBUG) {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            
            StackTraceElement classStack = Thread.currentThread().getStackTrace()[2];
            String fullClassName = classStack.getClassName();
            String methodName = classStack.getMethodName();
            int lineNumber = classStack.getLineNumber();

            String time = dateFormat.format(date);
            String content = "Time " + time + 
                    ", file \"" + fullClassName + ".java\"" +
                    ", method => " + methodName + 
                    ", line " + lineNumber + 
                    "\n--->" + t;
            System.out.println(content);
            myErrorLog.add(content + "\n");
        }
    }
    
    public static boolean isValidInt(String value) {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
}
