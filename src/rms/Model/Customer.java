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
    private int numOfDrinks = 0;
    private boolean served = false;
    
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
    public void setNumOfDrinks(int numOfDrinks) {
        this.numOfDrinks = numOfDrinks;
    }
    public void setServed(boolean served) {
        this.served = served;
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
    public int getNumOfDrinks() {
        return numOfDrinks;
    }
    public void decreaseNumOfDrinks() {
        this.numOfDrinks--;
    }
    public boolean isServed() {
        return served;
    }
}
