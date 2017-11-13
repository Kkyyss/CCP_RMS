/*
 * Landlord
 */
package rms.Model;

public class Landlord extends ServeCustomerPosition {
    public static Landlord landlord = new Landlord();
    
    public Landlord() {
        super(JobPosition.Position.LANDLORD);
    }
}
