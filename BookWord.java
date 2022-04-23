import java.util.Objects;

/**
 * This class holds a single book word along with its total count
 */
public class BookWord implements Comparable<BookWord>{
    private String text;
    private int count = 1;

    /**
     * Sets the text of word to passed string argument
     * @param wordText
     */
    public BookWord(String wordText){
        this.text = wordText;
    }

    /**
     * Gets the text of the word
     * @return word text
     */
    public String getText() {
        return text;
    }

    /**
     * Gets the count of word
     * @return word count
     */
    public int getCount() {
        return count;
    }

    /**
     * Increment the count of word by 1
     */
    public void incrementCount(){
        count++;
    }

    /**
     * Checks if the passed argument is equal to the word
     * @param wordToCompare another word
     * @return whether the given wordToCompare is equal to the word or not
     */
    @Override
    public boolean equals(Object wordToCompare) {
        if(wordToCompare == null){
            return false;
        }
        if(wordToCompare instanceof BookWord){
            BookWord bookWord = (BookWord) wordToCompare;
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
     * Creates a string representation of the object
     * @return a formatted string representation
     */
    @Override
    public String toString() {
        return "[" + getText() + ": " + getCount() +"]";
    }
    /**
     * Compares two BookWord objects
     * @param o BookWord object
     * @return an integer indicating if the two BookWord objects are equal, smaller or larger than each other.
     */
    @Override
    public int compareTo(BookWord o) {
        if(this.getCount() < o.getCount()){
            return 1;
        }
        else if(this.getCount() == o.getCount()){
            return 0;
        }
        else{
            return -1;
        }
    }
}
