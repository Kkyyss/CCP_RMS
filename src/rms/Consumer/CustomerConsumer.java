/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rms.Consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import rms.Model.Customer;
import rms.Model.Order;
import rms.Model.ServeCustomerPosition;
import rms.Model.Tables;
import static rms.MyUtils.MyConfig.closingTimeNotify;
import static rms.MyUtils.MyConfig.cupboard;
import static rms.MyUtils.MyConfig.juiceFountainTap;
import static rms.MyUtils.MyConfig.lastOrder;
import static rms.MyUtils.MyConfig.tables;
import static rms.MyUtils.MyUtils.errorLog;
import static rms.MyUtils.MyUtils.log;


public class CustomerConsumer implements Runnable {
    // private final ReentrantLock lock = new ReentrantLock();
    private String name;
    private BlockingQueue<Customer> customerQueue;
    private ServeCustomerPosition scp;
    
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
                serveCustomer();
            }

            if (lastOrder && this.name == "Landlord") {
                log(this.name + " shout: LAST ORDER!!!");
                serveCustomer();
                break;
            }
        }
//        if (isClosingTime) {
//            log(this.name + " " + this.scp.getName() + 
//                    " is leaving...");
//            barmaidLeaved = true;
//            return;
//        }        
        log(this.name + " leaving.");
    }
    
    private void serveCustomer() {
        try {
            // Pop customer FIFO
            Customer customer = customerQueue.take();
            log(this.name + " serve Customer " + customer.getName() + 
                    ", order " + Order.getBeverageValue(customer.getOrder()));

            // Go to cupboard obtain items -- 2 seconds.
            boolean isFruitJuice = customer.getOrder() == Order.Beverage.FRUIT_JUICE;
            if (isFruitJuice) {
                fruitRoutine();
            } else {
                milkCoffeeRoutine();
            }

            // Finally increment customer served.
            this.scp.addServed();
            log(this.name + " served " + this.scp.getServed() + " customer(s).");
            // Fill in the tables (may occurs deadlock)

            log(customer.getName() + " finding table...");
            // TimeUnit.SECONDS.sleep(1);
            Tables t = tables.get(0);
            // Add customer to the table.
            log(customer.getName() + " at table ");
            t.addCustomer(customer);

            scp.getRest(4);
        } catch (InterruptedException ex) {
            errorLog(ex);
        }
    }
    
    private void fruitRoutine() {
        log(this.name + " go cupboard obtain glass for 2 seconds");
        while (cupboard.getGlass() <= 0) {
            // TODO waiting assistant return glasses
            log("No glass available...");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {
                errorLog(ex);
            }
        }
        cupboard.obtainGlass(2);
        log(this.name + " obtained the glass.");
        log(this.name + " obtained the juice fountain tap.");
        log(this.name + " get fill for 3 seconds.");
        juiceFountainTap.getFill(3);
        log(this.name + " complete filled.");
    }
    
    private void milkCoffeeRoutine() {
        log(this.name + " go cupboard obtain cup for 2 seconds");
        while (cupboard.getCup() <= 0) {
            // TODO waiting assistant return cups
            log("No cup available...");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {
                errorLog(ex);
            }
        }
        cupboard.obtainCup(2);
        log(this.name + " obtained the cup.");
        log(this.name + " obtained the ingredients and mix drinks for 3 seconds.");
        cupboard.getIngredientsAndMixDrinks(3);
        log(this.name + " complete mixed.");
    }
}
