/*
 * Order type.
 */
package rms.Model;

public class Order {
    public enum Beverage {
        FRUIT_JUICE,
        CAPPUCCINO,
        CHOCOLATE,
    }
    
    public static String getBeverageValue(Beverage beverage) {
        switch (beverage) {
            case CAPPUCCINO:
                return "CAPPUCCINO";
                
            case CHOCOLATE:
                return "CHOCOLATE";
                
            case FRUIT_JUICE:
                return "FRUIT JUICE";
            
            default:
                return "NO SUCH ORDER! PLS FIXED IT!";
        }
    }
}

