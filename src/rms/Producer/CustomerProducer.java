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
    private Order.Beverage[] order = new Order.Beverage[] {
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
                    Customer customer = new Customer();
                    customer.setName("C" + (counter + 1));
                    customer.setOrder(order[rand.nextInt(2)]);
                    customerQueue.put(customer);
                    counter++;
                    conf.decrementNumberOfCustomerEntering();
                    log(customer.getName() + " entered...");
                }
                TimeUnit.SECONDS.sleep(5);
            }
        } catch (InterruptedException ex) {
            log(ex);
        }
    }
}
