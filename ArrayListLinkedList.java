
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * DISCUSSION
 * ----There is not much difference seen in the time complexity of both Data Structures.
 *
 * ----ArrayList takes lesser amount of time while removing items as compared to LinkedList. This is because ArrayList has random access to its elements and thus, when it has
 * to remove any element at a specific index, it just gets the element at that specific index. But in case of LinkedList, get method involves iteration up to the required index,
 * and then after locating the element, it is removed through looping. ArrayList has O(1) complexity in case of get method but LinkedList has a complexity of O(n).
 *
 * ----When there is a need to search elements such as get method of LinkedList, ArrayList is better to use but in case of adding or removing, LinkedList is a better choice.
 */
public class ArrayListLinkedList
{
  public static void main(String[] args)
  {
    final int NUMBER_OF_NAMES = 18756;
    final String filename = "bnames.txt";
    final String[] names = new String[NUMBER_OF_NAMES];
    
     // May be useful for selecting random words to remove
    Random random = new Random(); 
    
    // Read in all of the names 
    try {
      Scanner fin = new Scanner(new File(filename));
      for (int i=0; i<NUMBER_OF_NAMES; i++)
          names[i] = fin.next();
      fin.close();
    } catch (FileNotFoundException e) {
      System.out.println("Exception caught: " + e.getMessage());
      System.exit(-1);
    }
 
    // Use the system to build the linked List
    
    // 1. Create the linkedList and add all items in Array
    System.out.println("=========================================================================================================");
    SortedLinkedList<String> linkedList_String = new SortedLinkedList<>();
    long start = System.nanoTime();
    for (int i=0; i<NUMBER_OF_NAMES;i++)
        linkedList_String.add(names[i]);
    long finish = System.nanoTime();
    System.out.println("STRING LINKED LIST");
    System.out.printf("The time required to add %d elements to a Linked List = %d us\n", NUMBER_OF_NAMES, (finish - start) / 1000);

    // 2. Remove half the items and time the code.
    start = System.nanoTime();
    int halfOfSize = linkedList_String.size()/2;
    for(int i = 0; i < halfOfSize; i++){
      int index = random.nextInt(linkedList_String.size()-1);
      linkedList_String.remove(linkedList_String.get(index));
    }
    finish = System.nanoTime();
    System.out.printf("The time required to remove %d elements from a Linked List = %d us\n", halfOfSize, (finish - start) / 1000);

    // 3. Create a SortedLinkedList of another data type and demonstrate
    System.out.println("=========================================================================================================");
    SortedLinkedList<Integer> linkedList_Integer = new SortedLinkedList<>();
    start = System.nanoTime();
    for (int i=0; i<2000;i++)
      linkedList_Integer.add(random.nextInt(10000));
    finish = System.nanoTime();
    System.out.println("INTEGER LINKED LIST");
    System.out.printf("The time required to add 2000 elements to a Linked List = %d us\n", (finish - start) / 1000);

    // 2. Remove half the items and time the code.
    start = System.nanoTime();
    halfOfSize = linkedList_Integer.size()/2;
    for(int i = 0; i < halfOfSize; i++){
      int index = random.nextInt(linkedList_Integer.size()-1);
      linkedList_Integer.remove(linkedList_Integer.get(index));
    }
    finish = System.nanoTime();
    System.out.printf("The time required to remove %d elements from a Linked List = %d us\n", halfOfSize, (finish - start) / 1000);

    // 4. Build a standard ArrayList of String - Remember to sort list after each element is added
    System.out.println("=========================================================================================================");
    ArrayList<String> arrayList_String = new ArrayList<>();
    start = System.nanoTime();
    for (int i=0; i<NUMBER_OF_NAMES;i++){
      arrayList_String.add(names[i]);
      Collections.sort(arrayList_String);
    }
    finish = System.nanoTime();
    System.out.println("STRING ARRAYLIST");
    System.out.printf("The time required to add %d elements to an Array List = %d us\n", NUMBER_OF_NAMES, (finish - start) / 1000);

    //    Time this code.
    //    Use this timing data to compare add against SortedLinkedList in discussion
    //    Remove the half the elements and time again.
    // 2. Remove half the items and time the code.
    start = System.nanoTime();
    halfOfSize = arrayList_String.size()/2;
    for(int i = 0; i < halfOfSize; i++){
      int index = random.nextInt(arrayList_String.size()-1);
      arrayList_String.remove(arrayList_String.get(index));
    }
    finish = System.nanoTime();
    System.out.printf("The time required to remove %d elements from an Array List = %d us\n", halfOfSize, (finish - start) / 1000);
    //    Use this timing data to compare remove against SortedLinkedList in discussion
    
  }   
}
