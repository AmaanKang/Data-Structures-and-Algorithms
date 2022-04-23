import java.util.ArrayList;
import java.util.Objects;

/**
 * This class represents a single word present in the file along with its various positions in the text.
 */
public class WordPosition implements Comparable<WordPosition> {
    private String text;
    private ArrayList<Integer> position;
    private int close = 0;
    private double closeFactor;

    /**
     * Sets the text of the word to the passed string argument
     * @param text text of the word
     */
    public WordPosition(String text) {
        this.text = text;
        position = new ArrayList<>();
    }

    /**
     * Gets the count of the times when the character word is close to a specific word
     * @return closeness count
     */
    public int getClose() {
        return close;
    }

    /**
     * Increment the count of closeness by 1
     * @return increased count
     */
    public int incrementClose(){
        return close++;
    }

    /**
     * Gets the closeness factor
     * @return closeness factor
     */
    public double getCloseFactor() {
        return (double)close/position.size();
    }

    /**
     * Gets the text of the word
     * @return word text
     */
    public String getText() {
        return text;
    }

    /**
     * Gets an arraylist of the word positions
     * @return an arraylist of positions
     */
    public ArrayList<Integer> getPosition() {
        return position;
    }

    /**
     * Checks if the passed argument is equal to the word
     * @param o another object
     * @return whether the given object is equal to the word or not
     */
    @Override
    public boolean equals(Object o) {
        if(o == null){
            return false;
        }
        if(o instanceof WordPosition){
            WordPosition bookWord = (WordPosition) o;
            return getText().equals(bookWord.getText());
        }
        return false;
    }

    /**
     * Creates hashcode for a single word
     * Reference: https://www.baeldung.com/java-hashcode
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int h = 7;
        for (int i = 0; i < text.length(); i++) {
            h = 31 * h + text.charAt(i);
        }
        return h;
    }

    /**
     * Compares two WordPosition objects
     * @param o WordPosition object
     * @return an integer indicating if the two WordPosition objects are equal, smaller or larger to each other.
     */
    @Override
    public int compareTo(WordPosition o) {
        if(this.getCloseFactor() < o.getCloseFactor()){
            return 1;
        }
        else if(this.getCloseFactor() == o.getCloseFactor()){
            return 0;
        }
        else{
            return -1;
        }
    }

    /**
     * Creates a string representation of the object
     * @return a formatted string representation
     */
    @Override
    public String toString() {
        String factor = String.format("%.4f",this.getCloseFactor());
        return "[" + text +
                ", " + position.size() + "] Close to Ring "+
                close + "  Closeness Factor: " + factor ;
    }
}
