/*
 * Clock Consumer.
 */
package rms.Consumer;

import java.util.concurrent.TimeUnit;
import static rms.MyUtils.MyUtils.errorLog;
import static rms.MyUtils.MyUtils.log;
import rms.Producer.CustomerProducer;

public class ClockConsumer implements Runnable {
    private String name;
    private CustomerProducer cp;
    private AssistantConsumer ac;
    private ServeConsumer landlord;
    private ServeConsumer barmaid;
    
    public ClockConsumer(
            String name,
            CustomerProducer cp,
            AssistantConsumer ac,
            ServeConsumer landlord,
            ServeConsumer barmaid
    ) {
        this.name = name;
        this.ac = ac;
        this.cp = cp;
        this.landlord = landlord;
        this.barmaid = barmaid;
    }
    
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(5);
            landlord.notifyLastOrder();
            TimeUnit.SECONDS.sleep(10);
            cp.notifyClosingTime();
            ac.notifyClosingTime();
            landlord.notifyClosingTime();
            barmaid.notifyClosingTime();
            log("Restaurant closed!");
        } catch (InterruptedException ex) {
            errorLog(ex);
        }
    }
}
