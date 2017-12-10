/*
 * Configuration.
 */
package rms.MyUtils;

import java.util.ArrayList;
import rms.Model.Configuration;

public class MyConfig {
    // Configuration of Customer, Table, Assistant...
    public static Configuration conf = new Configuration();
    
    public static final boolean DEBUG = true; // default = true

    // Customer...
    public static boolean customerPauseSpawn = false;
    
    // Logs
    public static ArrayList<String> myTimeLog = new ArrayList<String>();
    public static ArrayList<String> mySanityLog = new ArrayList<String>();
    public static ArrayList<String> myErrorLog = new ArrayList<String>();
}
