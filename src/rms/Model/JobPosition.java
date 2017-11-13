/*
 * Job position.
 */
package rms.Model;

public class JobPosition {
    public enum Position {
        LANDLORD,
        BARMAID,
        ASSISTANT,
    }
        
    public static String getBeverageValue(Position position) {
        switch (position) {
            case LANDLORD:
                return "LANDLORD";
                
            case BARMAID:
                return "BARMAID";
                
            case ASSISTANT:
                return "ASSISTANT";
            
            default:
                return "NO SUCH POSITION! PLS FIXED IT!";
        }
    }
}
