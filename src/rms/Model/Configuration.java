/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rms.Model;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author kys
 */
public class Configuration {
    // Number of customer entering...
    private AtomicInteger numberOfCustomerEntering = new AtomicInteger(0);
    private AtomicInteger drinkingTime = new AtomicInteger(0);
    
    public Configuration() {}
    
    public int getNumberOfCustomerEntering() {
        return numberOfCustomerEntering.get();
    }
    
    public int getDrinkingTime() {
        return drinkingTime.get();
    }
    
    public void setNumberOfCustomerEntering(int newNum) {
        while (true) {
            int existingNum = this.getNumberOfCustomerEntering();
            if (this.numberOfCustomerEntering.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }
    
    public void decrementNumberOfCustomerEntering() {
        while (true) {
            int existingNum = this.getNumberOfCustomerEntering();
            int newNum = existingNum - 1;
            if (this.numberOfCustomerEntering.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }
    
    public void setdrinkingTime(int newNum) {
        while (true) {
            int existingNum = this.getDrinkingTime();
            if (this.drinkingTime.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }
}
