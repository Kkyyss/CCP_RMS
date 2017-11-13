/*
 * Order type.
 */
package rms.Model;

public class Order {
    public enum Beverage {
        FRUIT_JUICE,
        CAPPUCCINO,
    }
    
    public static String getBeverageValue(Beverage beverage) {
        switch (beverage) {
            case CAPPUCCINO:
                return "CAPPUCCINO";
                
            case FRUIT_JUICE:
                return "FRUIT JUICE";
            
            default:
                return "NO ORDER! PLS FIXED IT!";
        }
    }
}

