/**
 *
 * Customer Class represents a customer waiting for checkout in a store line
 */
public class Customer {
    private int numberOfItems;
    private int timeTaken;

    /**
     * Constructor sets the number of items in cart of the customer to the argument passed
     * @param numberOfItems number of items in cart
     */
    public Customer(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    /**
     * Gets the number of items in cart
     * @return number of cart items
     */
    public int getNumberOfItems() {
        return numberOfItems;
    }

    /**
     * Sets the time taken for the checkout of the current customer by adding the argument time passed to the method
     * @param time total time taken by the customers of the queue, who came before the current customer
     * @return time taken
     */
    public int setTimeTaken(int time){
        return this.timeTaken += time;

    }

    /**
     * Gets the time taken for one customer
     * @return time taken for the checkout of one customer
     */
    public int getTimeTaken(){
        return this.timeTaken = 45+(5*numberOfItems);
    }

    /**
     * String representation of one customer
     * @return string representation
     */
    @Override
    public String toString() {
        return "{" + numberOfItems + " ("+ timeTaken+" s)}";
    }
}
