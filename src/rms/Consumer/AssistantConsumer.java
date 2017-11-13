/*
 * Assistant Consumer.
 */
package rms.Consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import static java.util.concurrent.TimeUnit.SECONDS;
import rms.Model.Assistant;
import rms.Model.Customer;
import rms.Model.Tables;
import static rms.MyUtils.MyConfig.cupboard;
import static rms.MyUtils.MyConfig.tableNumbers;
import static rms.MyUtils.MyConfig.tables;
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
                    for (int i = 0; i < tableNumbers; i++) {
                        Tables t = tables.get(i);

                        // Collect glasses
                        for (int j = 0; j < t.getGlasses(); j++) {
                            glasses++;
                            t.addUnit(1);
                            t.decrementGlasses();
                            // Collect for 1 seconds
                            TimeUnit.SECONDS.sleep(1);
                        }
                        // Collect cups
                        for (int j = 0; j < t.getCups(); j++) {
                            cups++;
                            t.addUnit(2);
                            t.decrementCups();
                            // Collect for 1 seconds
                            TimeUnit.SECONDS.sleep(1);
                        }
                        // Go to next tables if available.
                        TimeUnit.SECONDS.sleep(2);
                    }
                    int totalCollected = cups + glasses;
                    // Washing
                    log(this.name + " washing...");
                    TimeUnit.SECONDS.sleep(totalCollected);

                    // Replace
                    // Go to cupboard
                    log(this.name + " moving to cupboard.");
                    TimeUnit.SECONDS.sleep(2);

                    log(this.name + " replacing...");
                    for (int i = 0; i < cups; i++) {
                        cupboard.returnCup(1);
                    }
                    
                    for (int i = 0; i < glasses; i++) {
                        cupboard.returnGlass(1);
                    }
                    
                    cups = 0;
                    glasses = 0;

                    log(this.name + " completed collect-wash-replace cycle.");
                    log(this.name + " taking rest for 20 seconds...");
                    assistant.getRest(5);
                }
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException ex) {
            log(ex);
        }    
    }
    
    
}
