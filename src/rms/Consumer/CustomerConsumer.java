/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rms.Consumer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import rms.Model.Customer;
import rms.Model.Order;
import rms.Model.ServeCustomerPosition;
import rms.Model.Tables;
import static rms.Model.Tables.tables;
import static rms.MyUtils.MyConfig.closingTimeNotify;
import static rms.MyUtils.MyConfig.conf;
import static rms.MyUtils.MyConfig.cupboard;
import static rms.MyUtils.MyConfig.juiceFountainTap;
import static rms.MyUtils.MyUtils.errorLog;
import static rms.MyUtils.MyUtils.log;


public class CustomerConsumer implements Runnable {
    // private final ReentrantLock lock = new ReentrantLock();
    private String name;
    private BlockingQueue<Customer> customerQueue;
    private ServeCustomerPosition scp;
    private Order.Beverage[] order = new Order.Beverage[] {
        Order.Beverage.FRUIT_JUICE,
        Order.Beverage.CAPPUCCINO,
        Order.Beverage.CHOCOLATE,
    };
    private Random rand = new Random();
    
    public CustomerConsumer(String name, ServeCustomerPosition scp, BlockingQueue<Customer> customerQueue) {
        this.name = name;
        this.scp = scp;
        this.customerQueue = customerQueue;
    }
    
    @Override
    public void run() {
        // Serving Customer
        while (true) {
            if (!closingTimeNotify) {
                if (conf.getLastOrderNotify() && this.name == "Landlord") {
                    log(this.name + " shout: LAST ORDER!!!");
                    conf.setLastOrder(true);
                    conf.setLastOrderNotify(false);
                }
                serveCustomer();
            } else {
                break;
            }
        }
       
        log(this.name + " leaving.");
    }
    
    private void serveCustomer() {
        try {
            // Pop customer FIFO
            Customer customer = customerQueue.take();
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
            this.scp.addServed();
            customer.setServed(true);
            log(this.name + " served " + this.scp.getServed() + " customer(s).");

            log(customer.getName() + " finding table...");
            Tables t = tables.get(rand.nextInt(tables.size()));
            // Add customer to the table.
            log(customer.getName() + " at table ");
            t.addCustomer(customer);

            scp.getRest(4);
        } catch (InterruptedException ex) {
            errorLog(ex);
        }
    }
    
    private void fruitRoutine() {
        log(this.name + " go cupboard obtain glass for " + conf.getTimeFetchGlass() + " seconds");
        while (cupboard.getGlass() <= 0) {
            // TODO waiting assistant return glasses
            log("No glass available...");
            try {
                TimeUnit.SECONDS.sleep(conf.getTimeFetchGlass());
            } catch (InterruptedException ex) {
                errorLog(ex);
            }
        }
        cupboard.obtainGlass(2);
        log(this.name + " obtained the glass.");
        log(this.name + " obtained the juice fountain tap for " + conf.getTimeFetchJuice() + " seconds.");
        juiceFountainTap.getFountainTap(conf.getTimeFetchJuice());
        log(this.name + " get fill for " + conf.getMakeFruitJuiceTime() + " seconds.");
        juiceFountainTap.getFill(conf.getMakeFruitJuiceTime());
        log(this.name + " complete filled.");
    }
    
    private void milkCoffeeRoutine() {
        log(this.name + " go cupboard obtain cup for " + conf.getTimeFetchCup()+ " seconds");
        while (cupboard.getCup() <= 0) {
            // TODO waiting assistant return cups
            log("No cup available...");
            try {
                TimeUnit.SECONDS.sleep(conf.getTimeFetchCup());
            } catch (InterruptedException ex) {
                errorLog(ex);
            }
        }
        cupboard.obtainCup(2);
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
        while (cupboard.getCup() <= 0) {
            // TODO waiting assistant return cups
            log("No cup available...");
            try {
                TimeUnit.SECONDS.sleep(conf.getTimeFetchCup());
            } catch (InterruptedException ex) {
                errorLog(ex);
            }
        }
        cupboard.obtainCup(2);
        log(this.name + " obtained the cup.");
        log(this.name + " obtained chocolate for " + conf.getMakeChocolateTime()+ " seconds.");
        cupboard.getChocolate(conf.getTimeFetchChocolate());
        log(this.name + " obtained the chocolate drinks for " + conf.getMakeChocolateTime()+ " seconds.");
        cupboard.makeChocolate(conf.getMakeChocolateTime());
        log(this.name + " complete.");
    }    
}
