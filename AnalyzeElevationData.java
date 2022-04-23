import java.io.File;
import java.util.Scanner;

/**
 * Class contains four methods which perform calculations by using a data set.
 *
 * @author Amandeep Kaur
 * Date Created: Jan 25, 2021
 *
 */
public class AnalyzeElevationData {
    /**
     * main method runs all four methods by passing parameters and calculates the time taken to run the whole program
     * @param args unused
     */
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        /**
         * array containing the number of rows, number of columns and radius value by reading the first row of the data set
         */
        int[] dimensionsArray = new int[3];
        int[][] arrayOfValues = null;
        File file = new File("ELEVATIONS.TXT");
        try {
            Scanner textFile = new Scanner(file);
            for (int i = 0; i < dimensionsArray.length; i++) {
                dimensionsArray[i] = textFile.nextInt();
            }
            arrayOfValues = new int[dimensionsArray[0]][dimensionsArray[1]];//array containing all the values present in the data set
            for (int row = 0; row < arrayOfValues.length; row++) {
                for (int col = 0; col < arrayOfValues[row].length; col++) {
                    arrayOfValues[row][col] = textFile.nextInt();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        int[] lowestValue = lowestElevationValue(arrayOfValues,99000);
        System.out.println("The lowest value is " + lowestValue[0] + " and it appears " + lowestValue[1] + " times.");

        int[][] peaksArray = findLocalPeaks(arrayOfValues,dimensionsArray[2],98480);

        double distance = findDistance(peaksArray);
        System.out.printf("The distance between these two closest peaks is: %.2f m",distance);
        System.out.println();

        findCommonValue(arrayOfValues,99000);
        long stopTime1 = System.nanoTime();
        System.out.printf(" [Time = %d us]\n", (stopTime1 - startTime) / 1000);

    }

    /**
     * Calculates the lowest integer value in the data set and how many times it appears
     * @param array array of data set values
     * @param limit maximum value possible in the data set
     * @return array containing lowest value and its frequency
     */
    public static int[] lowestElevationValue(int[][] array, int limit) {
        int lowestValue = array[0][0];
        /**
         * array containing the frequency of each value in the set
         */
        int[] frequencyArray = new int[limit];
        for (int row = 0; row < array.length; row++) {
            for (int col = 0; col < array[row].length; col++) {
                if (array[row][col] <= lowestValue) {
                    lowestValue = array[row][col];
                    frequencyArray[lowestValue]++;
                }
            }
        }
        return new int[]{lowestValue, frequencyArray[lowestValue]};
    }

    /**
     * Finds out all the local peaks present in the data set
     * @param array array of data set values
     * @param radius the radius of the elevation blocks
     * @param limit the minimum elevation value given
     * @return array of local peaks
     */
    public static int[][] findLocalPeaks(int[][] array, int radius, int limit) {
        int[] localPeaks = new int[array.length*array[0].length];
        int index = 0;
        int numberOfPeaks = 0;
        /**
         * loop through the two dimensional array
         */
        for (int row = radius; row < array.length - radius; row++) {
            for (int col = radius; col < array[row].length - radius; col++) {
                int counter = 0;
                /**
                 * If any value is greater than or equal to the limit, then proceed further
                 */
                if(array[row][col] >= limit){
                    /**
                     * To find out the number of rows included inside the elevation block
                     */
                    int rowDiff = ((row+radius) - (row-radius))+1;
                    /**
                     * To find out the number of columns included in the elevation block
                     */
                    int colDiff = ((col+radius) - (col-radius))+1;
                    /**
                     * total number of elements present inside the single elevation block
                     */
                    int numOfValues = rowDiff*colDiff;
                    /**
                     * loop through the elevation block/square
                     */
                    for(int newRow = row-radius; newRow <= row+radius; newRow++){
                        for (int newCol = col - radius; newCol <= col + radius; newCol++) {
                            /**
                             * if the current block element is not the center most value/elevation value, then proceed
                             */
                            if (newRow != row || newCol != col || array[newRow][newCol] != array[row][col]) {
                                /**
                                 * if the current value is less than elevation value, then increase the counter
                                 * (The elevation value or centre most value should be the largest value in its radius scope)
                                 */
                                if (array[newRow][newCol] < array[row][col]) {
                                    counter++;
                                }
                            }
                        }
                    }
                    /**
                     * when all the values present in the block has passed the check and it is confirmed that the given array[row][col] value can be considered
                     * as an elevation value, then add the value, row, column to the new array of local peaks
                     */
                    if(counter == numOfValues - 1){
                        localPeaks[index] = array[row][col];
                        index++;
                        localPeaks[index] = row;
                        index++;
                        localPeaks[index] = col;
                        index++;
                        numberOfPeaks++;
                    }
                }
            }
        }
        localPeaks[index] = numberOfPeaks;
        index++;
        System.out.println("The number of peaks is "+ numberOfPeaks);
        /**
         * peaksArray have 3 columns and number of rows depend on the number of peaks found. It contains elevation value in first column, its row number in second column,
         * its column number in third column.
         */
        int[][] peaksArray = new int[numberOfPeaks][3];
        int peak = 0;
        for(int i = 0; i < peaksArray.length; i++){
            for(int j = 0; j < peaksArray[i].length; j++){
                peaksArray[i][j] = localPeaks[peak];
                peak++;
            }
            System.out.println("["+peaksArray[i][1]+","+peaksArray[i][2]+" elevation = "+peaksArray[i][0]+"]");
        }
        return peaksArray;
    }

    /**
     * Finds out the distance between two closest peaks
     * @param array array of data set values
     * @return minimum distance between two elevation values
     */
    public static double findDistance(int[][] array){
        int elevation1 = 0;
        int elevation2 = 0;
        double minDistance = 0;
        int row1 = 0;
        int row2 = 0;
        int col1 = 0;
        int col2 = 0;
        double distance = array[0][0];
        for (int row = 0; row < array.length; row++) {
            for (int r = 0; r < array.length; r++) {
                /**
                 * if one elevation's row is same as the other elevation, don't proceed because the distance will come out to be 0
                 */
                if(row == r)
                {
                    break;
                }
                minDistance = Math.sqrt(Math.pow(array[row][1]-array[r][1],2) + Math.pow(array[row][2]-array[r][2],2));//distance formula
                if(minDistance < distance){
                    distance = minDistance;
                    row1 = array[row][1];
                    row2 = array[r][1];
                    col1 = array[row][2];
                    col2 = array[r][2];
                    elevation1 = array[row][0];
                    elevation2 = array[r][0];
                }
            }

            }
        System.out.println("The two closest peaks are located at ["+row1+","+col1+" elevation = "+elevation1+"] and ["+row2+","+col2+" elevation = "+elevation2+"]");
        return distance;
    }

    /**
     * Finds out the data set value that occurs maximum number of times in the set
     * @param array array of data set values
     * @param maxLimit maximum value possible in the data set
     * @return the common value in the data set
     */
    public static int findCommonValue(int[][] array, int maxLimit){
        int commonValue = 0;
        int maxCount = 0;
        int[] frequencyArray = new int[maxLimit];
        for (int row = 0; row < array.length; row++) {
            for (int col = 0; col < array[row].length; col++) {
                frequencyArray[array[row][col]]++;
                if(frequencyArray[array[row][col]] > maxCount){
                    maxCount = frequencyArray[array[row][col]];
                    commonValue = array[row][col];
                }
            }
        }
        System.out.println("The most common height in the terrain is "+commonValue+" and it occurs "+maxCount+" times.");
        return commonValue;
    }
}

