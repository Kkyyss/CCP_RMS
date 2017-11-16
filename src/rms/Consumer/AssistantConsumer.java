/*
 * Assistant Consumer.
 */
package rms.Consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import rms.Model.Assistant;
import rms.Model.Customer;
import rms.Model.Tables;
import static rms.Model.Tables.tables;
import static rms.MyUtils.MyConfig.conf;
import static rms.MyUtils.MyConfig.cupboard;
import static rms.MyUtils.MyUtils.errorLog;
import static rms.MyUtils.MyUtils.log;

public class AssistantConsumer implements Runnable {
    private String name;
    private int cups = 0;
    private int glasses = 0;
    private BlockingQueue<Customer> tableCustomer;
    private Assistant assistant;
    
    public AssistantConsumer(String name, Assistant assistant, BlockingQueue<Customer> tableCustomer) {
        this.name = name;
        this.assistant= assistant;
        this.tableCustomer = tableCustomer;
    }
    // Collect, Wash, Replace glasses & cups
    @Override
    public void run() {
        try {
            while (true) {
                // When assistant is not in idle mode...
                if (!assistant.isIdle()) {
                    // Collecting...
                    log(this.name + " collecting...");
                    for (int i = 0; i < conf.getNumberOfTables(); i++) {
                        Tables t = tables.get(i);

                        // Collect glasses
                        for (int j = 0; j < t.getGlasses(); j++) {
                            glasses++;
                            if ((t.getUnit() + 1) < conf.getTableCapacity()) {
                                t.addUnit(1);
                            }
                            t.decrementGlasses();
                            // Collect for 1 seconds
                            TimeUnit.SECONDS.sleep(1);
                        }
                        // Collect cups
                        for (int j = 0; j < t.getCups(); j++) {
                            cups++;
                            if ((t.getUnit()) + 2 < conf.getTableCapacity()) {
                                t.addUnit(2);
                            }
                            t.decrementCups();
                            // Collect for 1 seconds
                            TimeUnit.SECONDS.sleep(1);
                        }
                        // Go to next tables if available.
                        TimeUnit.SECONDS.sleep(2);
                    }
                    // Washing
                   log(this.name + 
                            " washing...cups " + 
                            conf.getWashCupTime() + " seconds and glasses " +
                            conf.getWashGlassTime() + " seconds...");
                    
                    TimeUnit.SECONDS.sleep((cups * conf.getWashCupTime()) + 
                            (glasses * conf.getWashGlassTime()));

                    // Replace
                    // Go to cupboard
                    log(this.name + " moving to cupboard.");
                    TimeUnit.SECONDS.sleep(1);

                    log(this.name + 
                            " replacing...cups " + 
                            conf.getTimeReplaceCup() + " seconds and glasses " +
                            conf.getTimeReplaceGlass() + " seconds...");
                    TimeUnit.SECONDS.sleep((cups * conf.getTimeReplaceCup()) + 
                                                (glasses * conf.getTimeReplaceGlass()));
                    cupboard.returnCup(cups);
                    cupboard.returnGlass(glasses);
                    
                    cups = 0;
                    glasses = 0;

                    log(this.name + " completed collect-wash-replace cycle.");
                    log(this.name + " taking rest for " + conf.getWashRoundsTime() + " seconds...");
                    assistant.getRest(conf.getWashRoundsTime());
                }
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException ex) {
            errorLog(ex);
        }    
    }
    
    
}
