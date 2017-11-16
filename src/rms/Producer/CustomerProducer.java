/*
 * Customer Producer
 */
package rms.Producer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import rms.Model.Customer;
import rms.Model.Order;
import static rms.MyUtils.MyConfig.closingTimeNotify;
import static rms.MyUtils.MyConfig.conf;
import static rms.MyUtils.MyConfig.customerPauseSpawn;
import static rms.MyUtils.MyUtils.log;

public class CustomerProducer implements Runnable {
    private String name;
    private long counter = 0;
    private BlockingQueue<Customer> customerQueue;
    private Order.Beverage[] chocoCappuOrder = new Order.Beverage[] {
        Order.Beverage.CAPPUCCINO,
        Order.Beverage.CHOCOLATE,
    };
    private Order.Beverage[] chocoJuiceOrder = new Order.Beverage[] {
        Order.Beverage.FRUIT_JUICE,
        Order.Beverage.CHOCOLATE,
    };
    private Order.Beverage[] cappuJuiceOrder = new Order.Beverage[] {
        Order.Beverage.FRUIT_JUICE,
        Order.Beverage.CAPPUCCINO,
    };
    
    private Random rand = new Random();
    
    public CustomerProducer(String name, BlockingQueue<Customer> customerQueue) {
        this.name = name;
        this.customerQueue = customerQueue;
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                if (!closingTimeNotify && 
                        !customerPauseSpawn &&
                        conf.getNumberOfCustomerEntering() > 0) {
                    int drinkType = 0;
                    Customer customer = new Customer();
                    customer.setName("C" + (counter + 1));
                    
                    // Number Of Drinks
                    customer.setNumOfDrinks(conf.getNumberOfDrinks());
                    
                    // Drinker type
                    int chocoRatio = conf.getChocolateType();
                    int cappucinoRatio = conf.getCappuccinoType();
                    int juiceRatio = conf.getJuiceType();
                    int totalRatio = chocoRatio + cappucinoRatio + juiceRatio;
                    int percentage = (totalRatio > 0) ? rand.nextInt(chocoRatio + cappucinoRatio + juiceRatio) + 1 : 0;
                    
                    // Choco case
                    if (chocoRatio != 0) {
                        // Choco & cappu same percentage...
                        if (chocoRatio <= percentage && cappucinoRatio == chocoRatio && percentage > juiceRatio) {
                            drinkType = rand.nextInt(2);
                            customer.setDrinkerType(chocoCappuOrder[drinkType]);
                        }
                        // Choco & juice same percentage...
                        if (chocoRatio <= percentage && juiceRatio == chocoRatio && percentage > cappucinoRatio) {
                            drinkType = rand.nextInt(2);
                            customer.setDrinkerType(chocoJuiceOrder[drinkType]);
                        }
                        
                        // Choco
                        if (chocoRatio <= percentage && percentage > cappucinoRatio && percentage > juiceRatio) {
                            customer.setDrinkerType(Order.Beverage.CHOCOLATE);
                        }
                    }
                    
                    // Juice case
                    if (juiceRatio != 0) {
                        // Juice
                        if (juiceRatio <= percentage && percentage > chocoRatio && percentage > cappucinoRatio) {
                            customer.setDrinkerType(Order.Beverage.FRUIT_JUICE);
                        }
                    }
                    
                    // Cappu case
                    if (cappucinoRatio != 0) {
                        // Cappu & juice same percentage...
                        if (cappucinoRatio <= percentage && juiceRatio == cappucinoRatio && percentage > chocoRatio) {
                            drinkType = rand.nextInt(2);
                            customer.setDrinkerType(cappuJuiceOrder[drinkType]);
                        }
                        
                        // Cappu
                        if (cappucinoRatio <= percentage && percentage > chocoRatio && percentage > juiceRatio) {
                            customer.setDrinkerType(Order.Beverage.CAPPUCCINO);
                        }
                    }                    
                    
                    counter++;
                    conf.decrementNumberOfCustomerEntering();
                    log(
                            ((customer.getDrinkerType() != null) ? (customer.getDrinkerType()) : "Default") + 
                                    " drinker " + customer.getName() + " entered...");
                    customerQueue.put(customer);
                }
                TimeUnit.SECONDS.sleep(5);
            }
        } catch (InterruptedException ex) {
            log(ex);
        }
    }
}
