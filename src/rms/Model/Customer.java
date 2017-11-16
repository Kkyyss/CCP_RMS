/*
 * Customer.
 */
package rms.Model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Customer {
    private String name;
    private Order.Beverage order = null;
    private Order.Beverage drinkerType = null;
    
    public static BlockingQueue<Customer> customerQueue = 
            new LinkedBlockingQueue<Customer>();
    
    public void setName(String name) {
        this.name = name;
    }
    public void setOrder(Order.Beverage order) {
        this.order = order;
    }
    public void setDrinkerType(Order.Beverage drinkerType) {
        this.drinkerType = drinkerType;
    }
    
    public Order.Beverage getOrder() {
        return this.order;
    }
    public Order.Beverage getDrinkerType() {
        return drinkerType;
    }
    public String getName() {
        return name;
    }
}
