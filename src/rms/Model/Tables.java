/*
 * Tables
 */
package rms.Model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import static rms.MyUtils.MyUtils.errorLog;

public class Tables {
    // private final ReentrantLock lock = new ReentrantLock();
    private AtomicInteger unit = new AtomicInteger(3);
    private AtomicInteger glasses = new AtomicInteger(0);
    private AtomicInteger cups = new AtomicInteger(0);

    public BlockingQueue<Customer> tableCustomers = 
        new LinkedBlockingQueue<>();
    
    public Tables() {}

    public void setUnit(AtomicInteger unit) {
        this.unit = unit;
    }

    public int getUnit() {
        return unit.get();
    }

    public int getGlasses() {
        return glasses.get();
    }

    public int getCups() {
        return cups.get();
    }
    
    public void incrementGlasses() {
        while (true) {
            int existingGlasses = this.getGlasses();
            int newGlasses = existingGlasses + 1;
            if (this.glasses.compareAndSet(existingGlasses, newGlasses)) {
                return;
            }
        }
    }
    
    public void incrementCups() {
        while (true) {
            int existingCups= this.getCups();
            int newCups = existingCups + 1;
            if (this.cups.compareAndSet(existingCups, newCups)) {
                return;
            }
        }
    }
    
    public void decrementGlasses() {
        while (true) {
            int existingGlasses = this.getGlasses();
            int newGlasses = existingGlasses - 1;
            if (this.glasses.compareAndSet(existingGlasses, newGlasses)) {
                return;
            }
        }
    }
    
    public void decrementCups() {
        while (true) {
            int existingCups= this.getCups();
            int newCups = existingCups - 1;
            if (this.cups.compareAndSet(existingCups, newCups)) {
                return;
            }
        }
    }
    
    public void addUnit(int unit) {
        while(true) {
            int existingUnit = this.getUnit();
            int newUnit = existingUnit + unit;
            if (this.unit.compareAndSet(existingUnit, newUnit)) {
                return;
            }
        }
    }
    
    public void minusUnit(int unit) {
        while(true) {
            int existingUnit = this.getUnit();
            int newUnit = existingUnit - unit;
            if(this.unit.compareAndSet(existingUnit, newUnit)) {
                return;
            }
        }
    }
    
    public void addCustomer(Customer customer) {
        try {
            this.tableCustomers.put(customer);
        } catch (InterruptedException ex) {
            errorLog(ex);
        }
    }

}
