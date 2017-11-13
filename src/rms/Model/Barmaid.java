/*
 * Barmaid
 */
package rms.Model;

public class Barmaid extends ServeCustomerPosition {
    public static Barmaid barmaid = new Barmaid();
    
    public Barmaid() {
        super(JobPosition.Position.BARMAID);
    }
}
