/*
 * Customer.
 */
package rms.Model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Customer {
    private String name;
    private Order.Beverage order = null;
    
    public static BlockingQueue<Customer> customerQueue = 
            new LinkedBlockingQueue<Customer>();
    
    public void setName(String name) {
        this.name = name;
    }
    public void setOrder(Order.Beverage order) {
        this.order = order;
    }
    
    public Order.Beverage getOrder() {
        return this.order;
    }
    public String getName() {
        return name;
    }
}
