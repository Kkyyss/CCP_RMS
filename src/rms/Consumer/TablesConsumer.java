/*
 * Tables Consumer
 */
package rms.Consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import rms.Model.Customer;
import static rms.Model.Customer.customerQueue;
import rms.Model.Order;
import rms.Model.Tables;
import static rms.MyUtils.MyConfig.conf;
import static rms.MyUtils.MyUtils.errorLog;
import static rms.MyUtils.MyUtils.log;

public class TablesConsumer implements Runnable {
    private String name;
    private Tables t;
    private BlockingQueue<Customer> tableCustomers;
    
    public TablesConsumer(String name, Tables t) {
        this.name = name;
        this.t = t;
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                tableCustomers = t.tableCustomers;
                Customer customer = tableCustomers.take();
                new Thread(() -> {
                    try {
                        boolean isFruitJuice = customer.getOrder() == Order.Beverage.FRUIT_JUICE;
                        
                        // Customer pick up glass/cup
                        log(customer.getName() + " pick up " + 
                                ((isFruitJuice) ? "glass" : "cup") + " for " + 
                                ((isFruitJuice) ? conf.getPickUpGlassTime() : conf.getPickUpCupTime()) + " seconds...");
                        TimeUnit.SECONDS.sleep(((isFruitJuice) ? conf.getPickUpGlassTime() : conf.getPickUpCupTime()));
         
                        int drinkingTime = (conf.getLastOrder() && customer.getDrinkerType() == Order.Beverage.FRUIT_JUICE) ? 0 : conf.getDrinkingTime();
                        log(customer.getName() + " drinking " + customer.getOrder() + " for " + drinkingTime + " seconds...");
                        TimeUnit.SECONDS.sleep(drinkingTime);
                        
                        
                        // Increment cup or glass for that table.
                        int unit = (isFruitJuice) ? 1 : 2;
                        while (true) {
                            if (t.getUnit() - unit >= 0) {
                                if (isFruitJuice) {
                                    t.minusUnit(1);
                                    t.incrementGlasses();
                                }
                                else {
                                    t.minusUnit(2);
                                    t.incrementCups();
                                }
                                log(customer.getName() + " put down " + 
                                        ((isFruitJuice) ? "glass" : "cup") + " for " +
                                        ((isFruitJuice) ? conf.getPutDownGlassTime(): conf.getPutDownCupTime()) + " seconds...");
                                TimeUnit.SECONDS.sleep(((isFruitJuice) ? conf.getPutDownGlassTime(): conf.getPutDownCupTime()));
                                break;
                            }
                            log(customer.getName() + " waiting to put down the " + ((isFruitJuice) ? "glass" : "cup"));
                            TimeUnit.SECONDS.sleep(1);
                        }
                        // May go back queue to order again base on the number of drinks
                        if (customer.getNumOfDrinks() > 0) {
                            if (conf.getLastOrder() && customer.getDrinkerType() == Order.Beverage.FRUIT_JUICE) {
                                customer.setNumOfDrinks(0);
                            } else {
                                customer.decreaseNumOfDrinks();
                            }
                            customerQueue.put(customer);
                            log(customer.getName() + " back to the queue.");
                        }
                    } catch (InterruptedException ex) {
                        errorLog(ex);
                    }
                }).start();
            }
        } catch (InterruptedException ex) {
            log(ex);
        }
    }
}
