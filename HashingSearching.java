
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 *
 * PERFORMANCE ANALYSIS:
 * LINEAR SEARCH: All values in the collection are searched to look for a specific value. Thus, it takes longer time in case of large data sets and its Big O notation
 * for speed is O(n)
 *
 * BINARY SEARCH: This takes less time as compared to binary search as it doesn't include looking over all the values. For this search to be performed, data
 * should be sorted. On every iteration, its number of elements to be searched becomes almost half of the previous. Hence, its Big O notation is O(log n)
 *
 * HASHSET SEARCH: The data is sorted in different buckets on the basis of the hash value of its elements. To look for a specific value, search is performed
 * inside a single bucket of the hashset. A single bucket doesn't contain many elements and thus it takes very less time. In most of cases, its Big O complexity
 * is O(1) which is nearly constant and isn't much affected by size of dataset.
 *
 *  This class reads text from two files and perform some calculations on the basis of that.
 *
 */
public class HashingSearching{

    /**
     * The starting point of the application
     */
    public static void main(String[] args)
    {
        List<String> nameArray = Arrays.asList("frodo", "sam", "bilbo", "gandalf", "boromir", "aragorn", "legolas", "gollum", "pippin", "merry",
                                               "gimli", "sauron", "saruman", "faramir", "denethor", "treebeard", "elrond", "galadriel","ring");
        ArrayList<String> nameList = new ArrayList<>(nameArray);
        ArrayList<WordPosition> charactersList = new ArrayList<>();
        final String filename = "TheLordOfTheRIngs.txt";

        ArrayList<BookWord> uniqueList = new ArrayList<>();//stores a set of all words found in file
        try {
            int count = 0;
            Scanner fin = new Scanner(new File(filename));
            fin.useDelimiter("\\s|\"|\\(|\\)|\\.|\\,|\\?|\\!|\\_|\\-|\\:|\\;|\\n");
            while (fin.hasNext()) {
                String fileWord = fin.next().toLowerCase();
                if (fileWord.length() > 0)
                {
                   BookWord word = new BookWord(fileWord);

                   if(nameList.contains(fileWord)){
                       WordPosition characterName = new WordPosition(fileWord);
                       /**
                        * Populates the charactersList and if the name is already in the list, then just add its position to arraylist
                        */
                       if(charactersList.contains(characterName)){
                           charactersList.get(charactersList.indexOf(characterName)).getPosition().add(count);
                       }
                       else{
                           characterName.getPosition().add(count);
                           charactersList.add(characterName);
                       }

                   }
                    /**
                     * If a word is already present in the uniqueList, then increase its count instead of adding a new word to list
                     */
                   if(uniqueList.contains(word)){
                       int index = uniqueList.indexOf(word);
                       uniqueList.get(index).incrementCount();
                   }
                   else {
                       uniqueList.add(word);
                   }
                   count++;
                }
            }
            fin.close();

        } catch (FileNotFoundException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
        System.out.println("==============================================================");
        System.out.println("There are " + uniqueList.size() + " unique words in this file ");
        System.out.println("==============================================================");
        /**
         * Sorts the uniqueList, first on basis of the word count and then alphabetically
         */
        Collections.sort(uniqueList,(BookWord w1,BookWord w2) -> {
            if(w1.getCount() == w2.getCount()){
                return w1.getText().compareTo(w2.getText());
            }
            return w1.compareTo(w2);
        });
        int printCount = 0;
        int totalWords = 0;
        ArrayList<BookWord> wordsCount64 = new ArrayList<>();
        System.out.println("Most Frequent words: ");
        for (BookWord b:uniqueList) {
            if(printCount < 10) {
                System.out.println(b);//prints only top 10
            }
            if(b.getCount() == 64){
                wordsCount64.add(b);
            }
            totalWords += b.getCount();
            printCount++;
        }
        System.out.println("==============================================================");
        System.out.println("There are " + totalWords + " words in this file ");

        System.out.println("==============================================================");
        System.out.println("Following words appear 64 times in the file: ");
        for (BookWord b:wordsCount64) {
            System.out.println(b);
        }
        long startTime = System.nanoTime();
        WordPosition ring = charactersList.get(charactersList.indexOf(new WordPosition("ring")));
        if(charactersList.contains(new WordPosition("ring"))){
            charactersList.set(0,ring);
        }
        int difference = 0;
        int charPos = 0;
        boolean stop = false;
        /**
         * Iterate through charactersList
         */
        for(WordPosition w:charactersList){
            /**
             * First element is ring, so start iterating through all the ring positions(position arraylist) one by one
             */
            for(int ringPos:ring.getPosition()){
                stop = false;
                /**
                 * Go to each other characters position arraylist and check if their positions are close enough to the ring position, if so, increment the closeness count.
                 */
                for(int i = 0; i < w.getPosition().size() && !stop;i++){
                    if(w.equals(ring)){
                        stop = true;
                    }else{
                        charPos = w.getPosition().get(i);
                        difference = (ringPos - charPos);
                        if(difference <= 42 && difference >= -42){
                            w.incrementClose();
                        }
                        else if(difference < -42){
                            stop = true;
                        }
                    }

                }
            }
        }
        charactersList.remove(new WordPosition("ring"));
        System.out.println("==============================================================");
        System.out.println("WHO REALLY WANTS THE RING: ");
        System.out.println("--------------------------------------------------------------");
        Collections.sort(charactersList);

        for(WordPosition w:charactersList){
            System.out.println(w);
        }
        long endTime = System.nanoTime();
        System.out.println("--------------------------------------------------------------");
        System.out.printf("Time taken to find the character that really wants the ring: %.2f ms",(double)(endTime-startTime)/1000000);
        System.out.println();


        final String filename2 = "US.txt";
        ArrayList<BookWord> dictionary = new ArrayList<>();
        SimpleHashSet<BookWord> hashDictionary = new SimpleHashSet<>();

        try {
            Scanner fin = new Scanner(new File(filename2));
            while (fin.hasNext()) {
                String fileWord = fin.next().toLowerCase();
                if (fileWord.length() > 0)
                {
                    BookWord word = new BookWord(fileWord);
                    dictionary.add(word);
                    hashDictionary.insert(word);
                }
            }
            fin.close();
        } catch (FileNotFoundException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }

        int wordsNotContained1 = 0;
        int wordsNotContained2 = 0;
        int wordsNotContained3 = 0;
        /** HASHSET SEARCH */
        long begin = System.nanoTime();
        for(BookWord b:uniqueList){
            if(!hashDictionary.contains(b)){
                wordsNotContained3++;
            }
        }
        long end = System.nanoTime();
        System.out.println("==============================================================");
        String time = String.format("%.2f",(double)(end-begin)/1000000);
        System.out.println("HASHSET SEARCH: " + wordsNotContained3 + " words not present in the dictionary. Time: "+time);

        /** LINEAR SEARCH */
        begin = System.nanoTime();
        for (BookWord b:uniqueList) {
            if(!dictionary.contains(b)){
                wordsNotContained1++;
            }
        }
        end = System.nanoTime();

        time = String.format("%.2f",(double)(end-begin)/1000000);
        System.out.println("LINEAR SEARCH: " + wordsNotContained1 + " words not present in the dictionary. Time :"+time);

        /** BINARY SEARCH */
        begin = System.nanoTime();
        Collections.sort(dictionary,(w1,w2) -> w1.getText().compareTo(w2.getText()));
        for (BookWord b:uniqueList) {
            if(Collections.binarySearch(dictionary,b,(w1,w2) -> w1.getText().compareTo(w2.getText())) < 0){
                wordsNotContained2++;
            }
        }
        end = System.nanoTime();
        time = String.format("%.2f",(double)(end-begin)/1000000);
        System.out.println("BINARY SEARCH: " + wordsNotContained2 + " words not present in the dictionary. Time: "+time);

        System.out.println("==============================================================");
        System.out.println("Total Buckets: "+hashDictionary.getNumberofBuckets());
        System.out.println("Largest Bucket size: "+hashDictionary.getLargestBucketSize());
        System.out.println("Empty Buckets: "+hashDictionary.getNumberofEmptyBuckets());

        System.out.printf("Percentage of empty buckets: %.2f",((double)hashDictionary.getNumberofEmptyBuckets()/(double)hashDictionary.getNumberofBuckets())*(double)100);
        System.out.println(" %");


    }
}

