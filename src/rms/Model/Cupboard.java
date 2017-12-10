/*
 * Cupboard
 */
package rms.Model;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import static rms.MyUtils.MyUtils.log;

public class Cupboard extends ServingArea {
    public static Cupboard cupboard = new Cupboard();
    
    private int glass = 2;
    private int cup = 2;
    
    private final ReentrantLock lockCups = new ReentrantLock();
    private final ReentrantLock lockGlass = new ReentrantLock();
    private final ReentrantLock lockIngredients = new ReentrantLock();
    private final ReentrantLock lockChocolate = new ReentrantLock();
    private final ReentrantLock lockMilk = new ReentrantLock();
    private final ReentrantLock lockCoffee = new ReentrantLock();
    private final ReentrantLock lockMakeChocolate = new ReentrantLock();
    
    public Cupboard() {}
    
    public void setGlass(int glass) {
        this.glass = glass;
    }

    public void setCup(int cup) {
        this.cup = cup;
    }

    public int getGlass() {
        return glass;
    }

    public int getCup() {
        return cup;
    }
    
    public void mixDrinks(int seconds) {
        lockIngredients.lock();
        
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException ex) {
            log(ex);
        } finally {
            lockIngredients.unlock();
        }
    }
    
    public void getMilk(int seconds) {
        lockMilk.lock();
        
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException ex) {
            log(ex);
        } finally {
            lockMilk.unlock();
        }
    }
    
    public void getCoffee(int seconds) {
        lockCoffee.lock();
        
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException ex) {
            log(ex);
        } finally {
            lockCoffee.unlock();
        }
    }    
    
    public void getChocolate(int seconds) {
        lockChocolate.lock();
        
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException ex) {
            log(ex);
        } finally {
            lockChocolate.unlock();
        }
    }
    
    public void makeChocolate(int seconds) {
        lockMakeChocolate.lock();
        
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException ex) {
            log(ex);
        } finally {
            lockMakeChocolate.unlock();
        }
    }
    
    public void obtainGlass(int seconds) {
        lockGlass.lock();
        try {
            this.glass--;
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException ex) {
            log(ex);
        } finally {
            lockGlass.unlock();
        }
    }
    
    public void obtainCup(int seconds) {
        lockCups.lock();
        try {
            this.cup--;
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException ex) {
            log(ex);
        } finally {
            lockCups.unlock();
        }
    } 
    
    public void returnGlass(int value) {
        lockGlass.lock();
        try {
            this.glass += value;
        } finally {
            lockGlass.unlock();
        }
    }
    
    public void returnCup(int value) {
        lockCups.lock();

        try {
            this.cup += value;
        } finally {
            lockCups.unlock();
        }
    }
}
