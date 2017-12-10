/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rms.Consumer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import static rms.Model.Cupboard.cupboard;
import rms.Model.Customer;
import static rms.Model.JuiceFountainTap.juiceFountainTap;
import rms.Model.Order;
import rms.Model.ServeCustomerPosition;
import rms.Model.Tables;
import static rms.Model.Tables.tables;
import static rms.MyUtils.MyConfig.conf;
import static rms.MyUtils.MyUtils.errorLog;
import static rms.MyUtils.MyUtils.log;


public class ServeConsumer implements Runnable {
    // private final ReentrantLock lock = new ReentrantLock();
    private String name;
    private boolean closingTime = false;
    private boolean lastOrder = false;
    private boolean leaving = false;
    private ServeConsumer barmaid;
    private BlockingQueue<Customer> customerQueue;
    private ServeCustomerPosition scp;
    private AssistantConsumer ac;
    private Order.Beverage[] order = new Order.Beverage[] {
        Order.Beverage.FRUIT_JUICE,
        Order.Beverage.CAPPUCCINO,
        Order.Beverage.CHOCOLATE,
    };
    private Random rand = new Random();
    
    public ServeConsumer(
            String name, ServeCustomerPosition scp, 
            BlockingQueue<Customer> customerQueue) {
        this.name = name;
        this.scp = scp;
        this.customerQueue = customerQueue;
    }
    
    public ServeConsumer(
            String name, ServeCustomerPosition scp, 
            ServeConsumer barmaid,
            AssistantConsumer ac,
            BlockingQueue<Customer> customerQueue) {
        this.name = name;
        this.scp = scp;
        this.ac = ac;
        this.barmaid = barmaid;
        this.customerQueue = customerQueue;
    }
    
    public void notifyLastOrder() {
        this.lastOrder = true;
    }
    
    public void notifyClosingTime() {
        this.closingTime = true;
    }
    
    public boolean isLeaving() {
        return this.leaving;
    }
    
    @Override
    public void run() {
        // Serving Customer
        while (!lastOrder) {
            if (closingTime) {
                log(this.name + " leave restaurant...");
                leaving = true;
                return;
            }
            takeCustomer();
        }
        
        // Attending last order
        log(this.name + " shout: LAST ORDER!!!");
        conf.setLastOrder(true);

        // Serving last order until closing time.
        while (!closingTime) {
            takeCustomer();
        }
        try {
            // After closing time, if there still have customers then serve...
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServeConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (!customerQueue.isEmpty() || !barmaid.isLeaving() || !ac.isLeaving()) {
            takeCustomer();
        }
        log(this.name + " leaving restaurant...");
    }

    private void takeCustomer() {
        try {
            if (!customerQueue.isEmpty()) {

                Customer customer = customerQueue.take();
                if (customer.isServed() && closingTime) {
                    log(this.name + " refused to serve " + customer.getName() + " due to the closing time!");
                    log(customer.getName() + " leaving restaurant...");
                    return;
                }                
                serveCustomer(customer);
            } else {
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException ex) {
            errorLog(ex);
        }
    }
    
    private void serveCustomer(Customer customer) {
        try {
                log(this.name + " serve Customer " + customer.getName() + 
                        "...");

                // Customer Ordering...
                log(customer.getName() + " ordering for 1 seconds...");
                TimeUnit.SECONDS.sleep(1);

                if (customer.isServed() && conf.getLastOrder() && customer.getDrinkerType() == Order.Beverage.FRUIT_JUICE) {
                    customer.setOrder(Order.Beverage.FRUIT_JUICE);
                } else {
                    customer.setOrder(order[rand.nextInt(order.length)]);
                }
                log(customer.getName() + " order " + customer.getOrder());

                // Go to cupboard obtain items -- 2 seconds.
                boolean isFruitJuice = customer.getOrder() == Order.Beverage.FRUIT_JUICE;
                if (isFruitJuice) {
                    fruitRoutine();
                } else if (customer.getOrder() == Order.Beverage.CAPPUCCINO) {
                    milkCoffeeRoutine();
                } else {
                    chocolateRoutine();
                }

                // Finally increment customer served.
                if (!customer.isServed()) {
                    this.scp.addServed();
                    customer.setServed(true);
                }
                log(this.name + " served " + this.scp.getServed() + " customer(s).");
                
                new Thread(() -> {
                    try {
                        log(customer.getName() + " finding table...");
                        while (conf.getNumberOfTables() <= 0) {
                            TimeUnit.SECONDS.sleep(1);
                        }
                        Tables t = tables.get(rand.nextInt(conf.getNumberOfTables()));
                        // Add customer to the table.
                        log(customer.getName() + " at table " + t.getName());
                        t.addCustomer(customer);
                    } catch (InterruptedException ex) {
                        errorLog(ex);
                    }
                }).start();
                scp.getRest(4);
            }
        catch (InterruptedException ex) {
            errorLog(ex);
        }
    }
    
    private void fruitRoutine() {
        log(this.name + " go cupboard obtain glass for " + conf.getTimeFetchGlass() + " seconds");
        waitForGlass();
        log(this.name + " obtained the glass.");
        log(this.name + " obtained the juice fountain tap for " + conf.getTimeFetchJuice() + " seconds.");
        juiceFountainTap.getFountainTap(conf.getTimeFetchJuice());
        log(this.name + " get fill for " + conf.getMakeFruitJuiceTime() + " seconds.");
        juiceFountainTap.getFill(conf.getMakeFruitJuiceTime());
        log(this.name + " complete filled.");
    }
    
    private void milkCoffeeRoutine() {
        log(this.name + " go cupboard obtain cup for " + conf.getTimeFetchCup()+ " seconds");
        waitForCup();
        log(this.name + " obtained the cup.");
        log(this.name + " obtained milk for " + conf.getTimeFetchMilk() + " seconds.");
        cupboard.getMilk(conf.getTimeFetchMilk());
        log(this.name + " obtained coffee for " + conf.getTimeFetchCoffee() + " seconds.");
        cupboard.getCoffee(conf.getTimeFetchCoffee());
        log(this.name + " obtained mix drinks for " + conf.getMakeCappuccinoTime() + " seconds.");
        cupboard.mixDrinks(conf.getMakeCappuccinoTime());
        log(this.name + " complete mixed.");
    }
    
    private void chocolateRoutine() {
        log(this.name + " go cupboard obtain cup for " + conf.getTimeFetchCup()+ " seconds");
        waitForCup();
        log(this.name + " obtained the cup.");
        log(this.name + " obtained chocolate for " + conf.getMakeChocolateTime()+ " seconds.");
        cupboard.getChocolate(conf.getTimeFetchChocolate());
        log(this.name + " obtained the chocolate drinks for " + conf.getMakeChocolateTime()+ " seconds.");
        cupboard.makeChocolate(conf.getMakeChocolateTime());
        log(this.name + " complete.");
    }    
    
    private void waitForCup() {
        try {
            while (true) {
                if (cupboard.getCup() <= 0) {
                    log(this.name + ": No cup available...");

                } else {
                    cupboard.obtainCup(conf.getTimeFetchCup());
                    break;
                }
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException ex) {
            errorLog(ex);
        }
    }
    
    private void waitForGlass() {
        try {
            while (true) {

                if (cupboard.getGlass() <= 0) {
                    log(this.name + ": No glass available...");
                } else {
                    cupboard.obtainGlass(conf.getTimeFetchGlass());
                    break;
                }
                TimeUnit.SECONDS.sleep(1);
            }   
        } catch (InterruptedException ex) {
            errorLog(ex);
        }
    }
}
