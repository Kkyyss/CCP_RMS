/*
 * Configuration
 */
package rms.Model;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Configuration {
    // Restaurant number of tables
    private AtomicInteger numberOfTables = new AtomicInteger(5);
    
    // Cupboard time to fetch/replace a glass/cup, time to fetch the different ingredients
    private AtomicInteger timeFetchGlass = new AtomicInteger(1);
    private AtomicInteger timeFetchCup = new AtomicInteger(1);
    private AtomicInteger timeReplaceGlass = new AtomicInteger(0);
    private AtomicInteger timeReplaceCup = new AtomicInteger(0);
    private AtomicInteger timeFetchCoffee = new AtomicInteger(0);
    private AtomicInteger timeFetchMilk = new AtomicInteger(0);
    private AtomicInteger timeFetchChocolate = new AtomicInteger(0);
    private AtomicInteger timeFetchJuice = new AtomicInteger(0);
    
    // Number of customer entering, drinking time and number of drinks, drinking type
    private AtomicInteger numberOfCustomerEntering = new AtomicInteger(10);
    private AtomicInteger drinkingTime = new AtomicInteger(1);
    private AtomicInteger numberOfDrinks = new AtomicInteger(4);
    private AtomicInteger chocolateType = new AtomicInteger(0);
    private AtomicInteger cappuccinoType = new AtomicInteger(0);
    private AtomicInteger juiceType = new AtomicInteger(1);
    
    // Landlord/Barmaid make drinks time
    private AtomicInteger makeChocolateTime = new AtomicInteger(0);
    private AtomicInteger makeCappuccinoTime = new AtomicInteger(0);
    private AtomicInteger makeFruitJuiceTime = new AtomicInteger(0);
    
    // Assistant wash glass/cup and washing rounds
    private AtomicInteger washGlassTime = new AtomicInteger(0);
    private AtomicInteger washCupTime = new AtomicInteger(0);
    private AtomicInteger washRoundsTime = new AtomicInteger(0);
    
    // Table capacity, time to put down/pick up a glass or a cup
    private AtomicInteger tableCapacity = new AtomicInteger(10);
    private AtomicInteger putDownGlassTime = new AtomicInteger(0);
    private AtomicInteger putDownCupTime = new AtomicInteger(0);
    private AtomicInteger pickUpGlassTime = new AtomicInteger(0);
    private AtomicInteger pickUpCupTime = new AtomicInteger(0);
    
    // Clock
    private AtomicBoolean lastOrderNotify = new AtomicBoolean(false);
    private AtomicBoolean lastOrder = new AtomicBoolean(false);
    
    public Configuration() {}
    
    public int getNumberOfCustomerEntering() {
        return numberOfCustomerEntering.get();
    }
    
    public int getDrinkingTime() {
        return drinkingTime.get();
    }

    public int getNumberOfDrinks() {
        return numberOfDrinks.get();
    }

    public int getChocolateType() {
        return chocolateType.get();
    }

    public int getCappuccinoType() {
        return cappuccinoType.get();
    }

    public int getJuiceType() {
        return juiceType.get();
    }

    public int getMakeCappuccinoTime() {
        return makeCappuccinoTime.get();
    }

    public int getMakeChocolateTime() {
        return makeChocolateTime.get();
    }

    public int getMakeFruitJuiceTime() {
        return makeFruitJuiceTime.get();
    }

    public int getWashGlassTime() {
        return washGlassTime.get();
    }

    public int getWashCupTime() {
        return washCupTime.get();
    }

    public int getWashRoundsTime() {
        return washRoundsTime.get();
    }

    public int getTableCapacity() {
        return tableCapacity.get();
    }

    public int getNumberOfTables() {
        return numberOfTables.get();
    }

    public int getTimeFetchCoffee() {
        return timeFetchCoffee.get();
    }

    public int getTimeFetchMilk() {
        return timeFetchMilk.get();
    }

    public int getPutDownGlassTime() {
        return putDownGlassTime.get();
    }

    public int getPutDownCupTime() {
        return putDownCupTime.get();
    }

    public int getPickUpGlassTime() {
        return pickUpGlassTime.get();
    }

    public int getPickUpCupTime() {
        return pickUpCupTime.get();
    }

    public int getTimeFetchGlass() {
        return timeFetchGlass.get();
    }

    public int getTimeFetchCup() {
        return timeFetchCup.get();
    }

    public int getTimeReplaceGlass() {
        return timeReplaceGlass.get();
    }

    public int getTimeReplaceCup() {
        return timeReplaceCup.get();
    }

    public int getTimeFetchChocolate() {
        return timeFetchChocolate.get();
    }

    public int getTimeFetchJuice() {
        return timeFetchJuice.get();
    }

    public boolean getLastOrderNotify() {
        return lastOrderNotify.get();
    }

    public boolean getLastOrder() {
        return lastOrder.get();
    }
        
    public void setNumberOfCustomerEntering(int newNum) {
        while (true) {
            int existingNum = this.getNumberOfCustomerEntering();
            if (this.numberOfCustomerEntering.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }
    
    public void decrementNumberOfCustomerEntering() {
        while (true) {
            int existingNum = this.getNumberOfCustomerEntering();
            int newNum = existingNum - 1;
            if (this.numberOfCustomerEntering.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }
    
    public void setDrinkingTime(int newNum) {
        while (true) {
            int existingNum = this.getDrinkingTime();
            if (this.drinkingTime.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }

    public void setNumberOfDrinks(int newNum) {
        while (true) {
            int existingNum = this.getNumberOfDrinks();
            if (this.numberOfDrinks.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }

    public void setChocolateType(int newNum) {
        while (true) {
            int existingNum = this.getChocolateType();
            if (this.chocolateType.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }

    public void setCappuccinoType(int newNum) {
        while (true) {
            int existingNum = this.getCappuccinoType();
            if (this.cappuccinoType.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }

    public void setJuiceType(int newNum) {
        while (true) {
            int existingNum = this.getJuiceType();
            if (this.juiceType.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }

    public void setMakeChocolateTime(int newNum) {
        while (true) {
            int existingNum = this.getMakeChocolateTime();
            if (this.makeChocolateTime.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }

    public void setMakeCappuccinoTime(int newNum) {
        while (true) {
            int existingNum = this.getMakeCappuccinoTime();
            if (this.makeCappuccinoTime.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }

    public void setMakeFruitJuiceTime(int newNum) {
        while (true) {
            int existingNum = this.getMakeFruitJuiceTime();
            if (this.makeFruitJuiceTime.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }

    public void setWashGlassTime(int newNum) {
        while (true) {
            int existingNum = this.getWashGlassTime();
            if (this.washGlassTime.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }

    public void setWashCupTime(int newNum) {
        while (true) {
            int existingNum = this.getWashCupTime();
            if (this.washCupTime.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }

    public void setWashRoundsTime(int newNum) {
        while (true) {
            int existingNum = this.getWashRoundsTime();
            if (this.washRoundsTime.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }

    public void setTableCapacity(int newNum) {
        while (true) {
            int existingNum = this.getTableCapacity();
            if (this.tableCapacity.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }

    public void setNumberOfTables(int newNum) {
        while (true) {
            int existingNum = this.getNumberOfTables();
            if (this.numberOfTables.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }

    public void setTimeFetchGlass(int newNum) {
        while (true) {
            int existingNum = this.getTimeFetchGlass();
            if (this.timeFetchGlass.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }

    public void setTimeFetchCup(int newNum) {
        while (true) {
            int existingNum = this.getTimeFetchCup();
            if (this.timeFetchCup.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }

    public void setTimeReplaceGlass(int newNum) {
        while (true) {
            int existingNum = this.getTimeReplaceGlass();
            if (this.timeReplaceGlass.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }

    public void setTimeReplaceCup(int newNum) {
        while (true) {
            int existingNum = this.getTimeReplaceCup();
            if (this.timeReplaceCup.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }

    public void setTimeFetchCoffee(int newNum) {
        while (true) {
            int existingNum = this.getTimeFetchCoffee();
            if (this.timeFetchCoffee.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }

    public void setTimeFetchMilk(int newNum) {
        while (true) {
            int existingNum = this.getTimeFetchMilk();
            if (this.timeFetchMilk.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }

    public void setPutDownGlassTime(int newNum) {
        while (true) {
            int existingNum = this.getPutDownGlassTime();
            if (this.putDownGlassTime.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }

    public void setPutDownCupTime(int newNum) {
        while (true) {
            int existingNum = this.getPutDownCupTime();
            if (this.putDownCupTime.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }

    public void setPickUpGlassTime(int newNum) {
        while (true) {
            int existingNum = this.getPickUpGlassTime();
            if (this.pickUpGlassTime.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }

    public void setPickUpCupTime(int newNum) {
        while (true) {
            int existingNum = this.getPickUpCupTime();
            if (this.pickUpCupTime.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }

    public void setTimeFetchChocolate(int newNum) {
        while (true) {
            int existingNum = this.getTimeFetchChocolate();
            if (this.timeFetchChocolate.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }

    public void setTimeFetchJuice(int newNum) {
        while (true) {
            int existingNum = this.getTimeFetchJuice();
            if (this.timeFetchJuice.
                    compareAndSet(existingNum, newNum)) {
                return;
            }
        }
    }

    public void setLastOrderNotify(boolean newBool) {
        while (true) {
            boolean existingBool = this.getLastOrderNotify();
            if (this.lastOrderNotify.
                    compareAndSet(existingBool, newBool)) {
                return;
            }
        }
    }
    
    public void setLastOrder(boolean newBool) {
        while (true) {
            boolean existingBool = this.getLastOrder();
            if (this.lastOrder.
                    compareAndSet(existingBool, newBool)) {
                return;
            }
        }
    }
}
