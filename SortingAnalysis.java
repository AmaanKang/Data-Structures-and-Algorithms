import java.util.Arrays;

/* 

 Some of the sort code from courseWare textbook - Copyright, All rights reserved.
 1. aSort = Quick Sort
    bSort = Selection Sort
    cSort = Insertion Sort
    dSort = Merge Sort
    eSort = Bubble Sort

 2. Fastest to slowest algorithm for array of size 30
    cSort -> bSort -> aSort -> dSort -> eSort

 3. Fastest to slowest algorithm for array of size 30000
    aSort -> dSort -> cSort -> bSort -> eSort

 4. The Big-O notation time complexity for an average case is given below:
    aSort : Quick Sort : O(n.log(n))
    bSort : Selection sort : O(n^2)
    cSort : Insertion sort : O(n^2)
    dSort : Merge Sort : O(n.log(n))
    eSort : Bubble Sort : O(n^2)
    The estimated time complexity of these five algorithms seems true in case of 30000 elements.

 5. bSort(selection sort) has the best performance of the basic step for array of size 30.
    While sorting an array of 30000 elements, cSort(insertion sort) has best performance of basic step.
    Although, the fastest algorithm in case of 30000 elements is aSort(quick sort), but it covers less number of steps in more time, whereas, cSort
    covers more number of steps in less time.
    Basic step depends on the number of comparisons and time taken by the algorithm. In case of 30000 elements, number of comparisons are too much
    that its value becomes even greater than the time taken, hence the value of basic step comes out to be very low, that makes it better on basis of
    performance.

 6. In case of 30 elements, Arrays.sort takes more time as compared to other algorithms, but it is close to eSort(Bubble sort)
    In case of 300 elements, it resembles aSort(Quick sort) and in 30000 elements, Arrays.sort's taken time is close to dSort(Merge Sort)


 _________________________________________________

*/

public class SortingAnalysis {
    static int sortDCompare = 0;

    /**
     * The swap method swaps the contents of two elements in an int array.
     *
     * @param array containing the two elements.
     * @param a     The subscript of the first element.
     * @param b     The subscript of the second element.
     */
    private static void swap(int[] array, int a, int b) {
        int temp;
        temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    /**
     * The QUICK sort - manages first call
     *
     * @param array an unsorted array that will be sorted upon method completion
     * @return number of compares
     */
    public static int aSort(int array[], int counter) {
        int counts = doASort(array, 0, array.length - 1, counter);
        return counts;
    }

    /**
     * The doASort method uses the ____________________ algorithm to sort
     *
     * @param array The array to sort.
     * @param start The starting subscript of the list to sort
     * @param end   The ending subscript of the list to sort
     * @return number of comparisons
     */
    private static int doASort(int array[], int start, int end, int counter) {
        int pivotPoint;
        int count1 = counter;
        int count2 = counter;
        int count3 = counter;
        if (start < end) {
            counter += (end-start);
            // Get the pivot point.
            pivotPoint = part(array, start, end);
            // Note - only one +/=
            // Sort the first sub list.
            count1 = counter;
            count2 = doASort(array, start, pivotPoint - 1, count1);
            // Sort the second sub list.
            count3 = doASort(array, pivotPoint + 1, end, count2);

        }
        return count3;
    }

    /**
     * The partition method selects a pivot value in an array and arranges the
     * array into two sub lists. All the values less than the pivot will be
     * stored in the left sub list and all the values greater than or equal to
     * the pivot will be stored in the right sub list.
     *
     * @param array The array to partition.
     * @param start The starting subscript of the area to partition.
     * @param end   The ending subscript of the area to partition.
     * @return The subscript of the pivot value.
     */
    private static int part(int array[], int start, int end) {
        int pivotValue;    // To hold the pivot value
        int endOfLeftList; // Last element in the left sub list.
        int mid;           // To hold the mid-point subscript

        // see http://www.cs.cmu.edu/~fp/courses/15122-s11/lectures/08-qsort.pdf
        // for discussion of middle point
        // Find the subscript of the middle element.
        // This will be our pivot value.
        mid = (start + end) / 2;

        // Swap the middle element with the first element.
        // This moves the pivot value to the start of
        // the list.
        swap(array, start, mid);

        // Save the pivot value for comparisons.
        pivotValue = array[start];

        // For now, the end of the left sub list is
        // the first element.
        endOfLeftList = start;

        // Scan the entire list and move any values that
        // are less than the pivot value to the left
        // sub list.
        for (int scan = start + 1; scan <= end; scan++) {
            if (array[scan] < pivotValue) {
                endOfLeftList++;
                swap(array, endOfLeftList, scan);
            }
        }

        // Move the pivot value to end of the
        // left sub list.
        swap(array, start, endOfLeftList);
        pivotValue = endOfLeftList;
        // Return the subscript of the pivot value.
        return pivotValue;
    }

    /**
     * An implementation of the SELECTION Sort Algorithm
     *
     * @param array an unsorted array that will be sorted upon method completion
     * @return number of compares
     */

    public static int bSort(int[] array) {
        int startScan;   // Starting position of the scan
        int index;       // To hold a subscript value
        int minIndex;    // Element with smallest value in the scan
        int minValue;    // The smallest value found in the scan
        int counter = 0;

        // The outer loop iterates once for each element in the
        // array. The startScan variable marks the position where
        // the scan should begin.
        for (startScan = 0; startScan < (array.length - 1); startScan++) {
            // Assume the first element in the scannable area
            // is the smallest value.
            minIndex = startScan;
            minValue = array[startScan];

            // Scan the array, starting at the 2nd element in
            // the scannable area. We are looking for the smallest
            // value in the scannable area.
            for (index = startScan + 1; index < array.length; index++) {
                counter++;
                if (array[index] < minValue) {
                    minValue = array[index];
                    minIndex = index;
                }
            }

            // Swap the element with the smallest value
            // with the first element in the scannable area.
            array[minIndex] = array[startScan];
            array[startScan] = minValue;
        }
        return counter;
    }

    /**
     * An implementation of the INSERTION Sort algorithm
     *
     * @param array an unsorted array that will be sorted upon method completion
     * @return number of compares
     */
    public static int cSort(int[] array) {
        int unsortedValue;  // The first unsorted value
        int scan;           // Used to scan the array
        int counter = 0;    //counts the number of total compares
        int count = 0;      //counts the number of compares in one iteration of for loop
        // The outer loop steps the index variable through
        // each subscript in the array, starting at 1. The portion of
        // the array containing element 0  by itself is already sorted.
        for (int index = 1; index < array.length; index++) {
            count = 1;
            // The first element outside the sorted portion is
            // array[index]. Store the value of this element
            // in unsortedValue.
            unsortedValue = array[index];

            // Start scan at the subscript of the first element
            // outside the sorted part.
            scan = index;

            // Move the first element in the still unsorted part
            // into its proper position within the sorted part.
            while (scan > 0 && array[scan - 1] > unsortedValue) {
                count++;
                array[scan] = array[scan - 1];
                scan--;
            }
            counter += count;
            // Insert the unsorted value in its proper position
            // within the sorted subset.
            array[scan] = unsortedValue;
        }
        return counter;
    }


    /**
     * completes a MERGE sort on an array
     *
     * @param array the unsorted elements - will be sorted on completion
     */
    public static void dSort(int array[]) {
        int length = array.length;
        doDSort(array, 0, length - 1);
    }

    /**
     * private recursive method that splits array until we start at array sizes of 1
     *
     * @param array       starting array
     * @param lowerIndex  lower bound of array to sort
     * @param higherIndex upper bound of array to sort
     */

    private static void doDSort(int[] array, int lowerIndex, int higherIndex) {
        if (lowerIndex < higherIndex) {
            int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
            // Below step sorts the left side of the array
            doDSort(array, lowerIndex, middle);
            // Below step sorts the right side of the array
            doDSort(array, middle + 1, higherIndex);
            // Now put both parts together
            part1(array, lowerIndex, middle, higherIndex);
        }
    }

    /**
     * Puts two smaller sorted arrays into one sorted array
     *
     * @param array       provided in two sorted parts (1,4,9,2,3,11)
     * @param lowerIndex  the location of the first index
     * @param middle      - the middle element
     * @param higherIndex - the upper bound of the sorted elements
     */
    private static void part1(int[] array, int lowerIndex, int middle, int higherIndex) {

        int[] mArray = new int[higherIndex - lowerIndex + 1];
        for (int i = lowerIndex; i <= higherIndex; i++) {
            mArray[i - lowerIndex] = array[i];
        }
        // Part A - from lowerIndex to middle
        // Part B - from middle + 1 to higherIndex
        int partAIndex = lowerIndex;
        int partBIndex = middle + 1;
        int m = lowerIndex;
        while (partAIndex <= middle && partBIndex <= higherIndex) {
            // Place items back into Array in order
            // Select the lowest of the two elements
            sortDCompare++;
            if (mArray[partAIndex - lowerIndex] <= mArray[partBIndex - lowerIndex]) {
                array[m] = mArray[partAIndex - lowerIndex];
                partAIndex++;
            } else {
                array[m] = mArray[partBIndex - lowerIndex];
                partBIndex++;
            }
            m++;
        }
        // Copy the remainder of PartA array (if required)
        while (partAIndex <= middle) {
            array[m] = mArray[partAIndex - lowerIndex];
            m++;
            partAIndex++;
        }
    }

    /**
     * Sorting using the BUBBLE Sort algorithm
     *
     * @param array an unsorted array that will be sorted upon method completion
     */
    public static int eSort(int[] array) {
        int lastPos;     // Position of last element to compare
        int index;       // Index of an element to compare
        int counter = 0;
        // The outer loop positions lastPos at the last element
        // to compare during each pass through the array. Initially
        // lastPos is the index of the last element in the array.
        // During each iteration, it is decreased by one.
        for (lastPos = array.length - 1; lastPos >= 0; lastPos--) {
            // The inner loop steps through the array, comparing
            // each element with its neighbor. All of the elements
            // from index 0 through lastPos are involved in the
            // comparison. If two elements are out of order, they
            // are swapped.
            for (index = 0; index <= lastPos - 1; index++) {
                // Compare an element with its neighbor.
                counter++;
                if (array[index] > array[index + 1]) {
                    // Swap the two elements.
                    swap(array, index, index + 1);
                }
            }
        }
        return counter;
    }

    /**
     * Print an array to the Console
     *
     * @param A array to be printed
     */
    public static void printArray(int[] A) {
        for (int i = 0; i < A.length; i++) {
            System.out.printf("%5d ", A[i]);
        }
        System.out.println();
    }

    public static void createAndSortArrays(int size){
        int[] A = new int[size];


        // Create random array with elements in the range of 0 to SIZE - 1;
        for (int i = 0; i < size; i++) {
            A[i] = (int) (Math.random() * size);
        }

        System.out.printf("\nComparison for array size of %d\n\n", size);

        int[] B = Arrays.copyOf(A, A.length);
        long startTime = System.nanoTime();
        int sortACompare = aSort(B,0);
        long elapsedTime = System.nanoTime() - startTime;

        System.out.printf("Number of compares for aSort     = %10d\n", sortACompare);
        System.out.printf("Time required for aSort          = %10d ns\n", elapsedTime);
        System.out.printf("Basic Step = %6.1f ns\n", (double) elapsedTime / sortACompare);


        B = Arrays.copyOf(A, A.length);
        startTime = System.nanoTime();
        int sortBCompares = bSort(B);
        elapsedTime = System.nanoTime() - startTime;

        System.out.printf("Number of compares for bSort     = %10d\n", sortBCompares);
        System.out.printf("Time required for bSort          = %10d ns\n", elapsedTime);
        System.out.printf("Basic Step = %6.1f ns\n", (double) elapsedTime / sortBCompares);

        B = Arrays.copyOf(A, A.length);
        startTime = System.nanoTime();
        int sortCCompares = cSort(B);
        elapsedTime = System.nanoTime() - startTime;

        System.out.printf("Number of compares for cSort     = %10d\n", sortCCompares);
        System.out.printf("Time required for cSort          = %10d ns\n", elapsedTime);
        System.out.printf("Basic Step = %6.1f ns\n", (double) elapsedTime / sortCCompares);

        B = Arrays.copyOf(A, A.length);
        startTime = System.nanoTime();
        dSort(B);
        elapsedTime = System.nanoTime() - startTime;

        System.out.printf("Number of compares for dSort     = %10d\n", sortDCompare);
        System.out.printf("Time required for dSort          = %10d ns\n", elapsedTime);
        System.out.printf("Basic Step = %6.1f ns\n", (double) elapsedTime / sortDCompare);

        B = Arrays.copyOf(A, A.length);
        startTime = System.nanoTime();
        int sortECompare = eSort(B);
        elapsedTime = System.nanoTime() - startTime;

        System.out.printf("Number of compares for eSort     = %10d\n", sortECompare);
        System.out.printf("Time required for eSort          = %10d ns\n", elapsedTime);
        System.out.printf("Basic Step = %6.1f ns\n", (double) elapsedTime / sortECompare);

        System.out.println();
        B = Arrays.copyOf(A, A.length);
        startTime = System.nanoTime();
        Arrays.sort(B);
        elapsedTime = System.nanoTime() - startTime;
        System.out.printf("Time required for Arrays.sort    = %10d ns\n", elapsedTime);

    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.printf("Assignment#2 Sorting and Performance Analysis\n");
        System.out.printf("Completed by Amandeep Kaur (000822179)\n");

        createAndSortArrays(30);
        createAndSortArrays(300);
        createAndSortArrays(30000);



    }
}

