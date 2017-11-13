/*
 * Configuration.
 */
package rms.MyUtils;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import rms.Model.Configuration;
import rms.Model.Cupboard;
import rms.Model.JuiceFountainTap;
import rms.Model.Tables;

public class MyConfig {
    // Configuration of Customer, Table, Assistant...
    public static Configuration conf = new Configuration();
    
    public static final boolean DEBUG = true; // default = true
    
    // Fill
    public static boolean isFilling = false;

    // Closing time...
    public static boolean lastOrder = false;
    public static boolean closingTimeNotify = false;
    public static boolean isClosingTime = false;
    
    // Leaving...
    public static boolean barmaidLeaved = false;
    
    // Customer...
    public static ExecutorService customerExecutor;
    public static boolean customerPauseSpawn = false;
    
    // Threads...
    public static ExecutorService staffExecutor;
    public static ExecutorService tablesExecutor;
    
    // Cupboard
    public static Cupboard cupboard = new Cupboard();
    public static JuiceFountainTap juiceFountainTap = new JuiceFountainTap();
    
    // Tables
    public static int tableNumbers = 5;
    public static ArrayList<Tables> tables = new ArrayList<Tables>();
    
    // Logs
    public static ArrayList<String> myTimeLog = new ArrayList<String>();
    public static ArrayList<String> mySanityLog = new ArrayList<String>();
    public static ArrayList<String> myErrorLog = new ArrayList<String>();
}
