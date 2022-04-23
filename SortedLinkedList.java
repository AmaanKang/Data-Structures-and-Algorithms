
/**
 * Generic Linked List class that always keeps the elements in order 
 * @author mark.yendt
 */
public class SortedLinkedList<T extends Comparable>
{
  /**
   * The Node class stores a list element and a reference to the next node.
   */
  private final class Node<T extends Comparable>
  {
    T value;
    Node next;

    /**
     * Constructor.
     * @param val The element to store in the node.
     * @param n The reference to the successor node.
     */
    Node(T val, Node n)
    {
      value = val;
      next = n;
    }

    /**
     * Constructor.
     *
     * @param val The element to store in the node.
     */
    Node(T val)
    {
      // Call the other (sister) constructor.
      this(val, null);
    }
    @Override
    public boolean equals(Object obj) {
      if(obj == null){
        return false;
      }
      if(obj instanceof Node) {
        Node element = (Node) obj;
        return this.value == element.value;
      }
      return false;
    }

  }
  private Node first;  // list head

  /**
   * Constructor.
   */
  public SortedLinkedList()
  {
    first = null;
  }

  /**
   * The isEmpty method checks to see if the list is empty.
   *
   * @return true if list is empty, false otherwise.
   */
  public boolean isEmpty()
  {
    return first == null;
  }

  /**
   * The size method returns the length of the list.
   * @return The number of elements in the list.
   */
  public int size()
  {
    int count = 0;
    Node p = first;
    while (p != null) {
      // There is an element at p
      count++;
      p = p.next;
    }
    return count;
  }

  /**
   * The add method adds an element at a position.
   * @param element The element to add to the list in sorted order.
   */
  public void add(T element) {
    Node newElement = new Node(element);
    /**
     * IF the list is empty or the new value is smaller than the first value of list, then add the new value in first place.
     */
    if (isEmpty() || first.value.compareTo(newElement.value) >= 0) {
      newElement.next = first;
      first = newElement;
    }
    else {
      /**
       * Start looping from the first value of the list and iterate if the list is not ended AND current list value is smaller than the new value. When any greater than value
       * is seen, stop the loop
       */
      for (Node current = first; current != null && current.value.compareTo(newElement.value) < 0; current = current.next){
        if(current.next != null){
          if(current.value.compareTo(newElement.value) <= 0 && newElement.value.compareTo(current.next.value) <= 0){
            newElement.next = current.next;
            current.next = newElement;
          }
        }
        else{
          current.next = newElement;
        }
      }
    }
  }

  /**
   * The toString method computes the string representation of the list.
   * @return The string form of the list.
   */
  public String toString(){
    String listOfItems = "[";
    
    // TODO: Iterate through the list and append items to end of listOfItems
    Node current = first;
    while (current != null) {
      listOfItems += current.value;
      current = current.next;
      if (current != null){
        listOfItems += ", ";
      }
    }
    return listOfItems + "]";
  }
  /**
   * Retrive an item from the list * @return the item or exception if index is out of range
   */
  public T get(int index) {
    if (index < 0 || index >= size()) throw new IndexOutOfBoundsException(index + " is out of range for list");

    Node<T> current = first;
    int i = 0;
    while (i++ < index) {
      current = current.next;
    }
    return current.value;
  }
  /**
   * The remove method removes an element.
   * @param element The element to remove.
   * @return true if the remove succeeded, false otherwise.
   */
  public boolean remove(T element)
  {
    Node current = first;
    // Traverse the list
    while (current != null) {
      if (current.value.equals(element)){
        if(current.next != null){
          current.value = current.next.value;
          current.next = current.next.next;
        }
        else{
          current.value = null;
        }
        return true;
      }
      current = current.next;
    }
    return false;

  }

}