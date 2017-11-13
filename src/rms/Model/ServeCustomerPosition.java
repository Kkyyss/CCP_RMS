/*
 * Serve customer.
 */
package rms.Model;

public class ServeCustomerPosition extends Staff {
    private long served = 0;
    
    public ServeCustomerPosition(JobPosition.Position position) {
        super(position);
    }
    
    public void addServed() {
        this.served++;
    }
    
    public long getServed() {
        return this.served;
    }
}
