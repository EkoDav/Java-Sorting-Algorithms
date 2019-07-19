/*
 * This program can test the run time of different sorting algorithms by using
 * large random number lists. 
 */
package sortingalgorithmruntime;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Erik Davis
 * @version 1.0
 */
public class SortingAlgorithmsRuntime {

    /**
     * The main method will create all arrays needed, initialize the arrays with
     * random numbers, and call on the other methods to sort, validate, and 
     * generate the report. 
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final int SIZE = 50000;
        final String OUTFILE_NAME = "output/sortResults.txt";
        
        java.util.Random random = new java.util.Random();
        
        long[][] results = new long[3][3];
        
        for (int i = 0; i < 3; i++) {
            int round = i + 1;
            System.out.println("Starting Sort #" + round + "...");
            
            //Create Lists
            Integer[] list1 = new Integer[SIZE];
            Integer[] list2 = new Integer[SIZE];
            Integer[] list3 = new Integer[SIZE];

            //Initialize List Values
            for (int j = 0; j < SIZE; j++) {
                list1[j] = list2[j] = list3[j] = random.nextInt(100000);
            }

            //Sort List 1 with Selection Sort
            long startTime1 = System.nanoTime();
            selectionSort(list1);
            long stopTime1 = System.nanoTime();
            long runTime1 = (stopTime1 - startTime1) / 1000000; 
            verifySort(list1, "Selection Sort");
            System.out.println("\tSelection Sort time " + runTime1);
            results[0][i] = runTime1;

            //Sort List 2 with Shell Sort
            long startTime2 = System.nanoTime();
            shellSort(list2);
            long stopTime2 = System.nanoTime();
            long runTime2 = (stopTime2 - startTime2) / 1000000; 
            verifySort(list2, "Shell Sort");
            System.out.println("\tShell Sort time " + runTime2);
            results[1][i] = runTime2;

            //Sort List 3 with Quick Sort
            long startTime3 = System.nanoTime();
            quickSort(list3, 0, SIZE - 1);
            long stopTime3 = System.nanoTime();
            long runTime3 = (stopTime3 - startTime3) / 1000000; 
            verifySort(list3, "Quick Sort");
            System.out.println("\tQuick Sort time " + runTime3);
            results[2][i] = runTime3;

            System.out.println("\t  Sorts validated");
        }
        generateReport(results, OUTFILE_NAME);
    }
    
    /**
     * The selectionSort method will sort list 1 using the selection sort algorithm.
     * 
     * @param numArray - The integer array of random numbers. 
     */
    public static void selectionSort(Integer[] numArray) {
        int n = numArray.length;
        for (int i = 0; i < n - 1; i++) {
            int posMin = i;
            for (int next = i + 1; next < n; next++) {
                if (numArray[next] < numArray[posMin]) {
                    posMin = next;
                }
            }
            int temp = numArray[i];
            numArray[i] = numArray[posMin];
            numArray[posMin] = temp;
        }
    }
    
    /**
     * Method to sort list 2 using the Shell sorting algorithm.
     * 
     * @param numArray - The integer array of random numbers. 
     */
    public static void shellSort(Integer[] numArray) {
        int n = numArray.length;
        int gap = n / 2;
        while (gap > 0) {
            for (int nextPos = gap; nextPos < n; nextPos++) {
                insertSort(numArray, nextPos, gap);
            }
            if (gap == 2) {
                gap = 1;
            } else {
                gap /= 2;
            }
        }
    }
    
    /**
     * Method to implement insert sort algorithm used inside the Shell sort. 
     * 
     * @param numArray - The integer array of random numbers. 
     * @param nextPos - The position of the next insertion element. 
     * @param gap - The gap size given from the Shell sort. 
     */
    public static void insertSort(Integer[] numArray, int nextPos, int gap) {
        int nextVal = numArray[nextPos];
        while ((nextPos > gap - 1) && nextVal < numArray[nextPos - gap]) {
            numArray[nextPos] = numArray[nextPos - gap];
            nextPos -= gap;
            }
        numArray[nextPos] = nextVal;
    }
    
    /**
     * Method to sort list 3 using the Quick Sort algorithm. 
     * 
     * @param numArray - The integer array of random numbers. 
     * @param first - The first index in the array
     * @param last - The last index in the array. 
     */
    public static void quickSort(Integer[] numArray, int first, int last) {
        if (first < last) {
            int pivIdx = partition(numArray, first, last);
            quickSort(numArray, first, pivIdx - 1);
            quickSort(numArray, pivIdx + 1, last);
        }
    }
    
    /**
     * Method to process the partitioned subarrays of the quick sort algorithm. 
     * 
     * @param numArray - The integer array of random numbers. 
     * @param first - The first index in the array.
     * @param last - The last index in the array.
     * @return int - The pivot index of the full array. 
     */
    private static int partition(Integer[] numArray, int first, int last) {
        bubbleSort(numArray, first, last);
        swap(numArray, first, (first + last) / 2);
        
        int pivot = numArray[first];
        int left = first;
        int right = last;
        do {
            while ((left < right) && (pivot >= numArray[left])) {
                left++;
            }
            while (pivot < numArray[right]) {
                right--;
            }
            if (left < right) {
                swap(numArray, left, right);
            } 
        } while (left < right);
        swap(numArray, first, right);
        return right;
    }
    
    /**
     * Method to use the bubble sort algorithm to find the median of three 
     * elements from a list to use as the pivot index in the quick sort. 
     * 
     * @param numArray - The integer array of random numbers. 
     * @param first - The first index in the subarray.
     * @param last - The last index in the subarray.
     */
    public static void bubbleSort(Integer[] numArray, int first, int last) {
        int middle = (first + last) / 2;
        if (numArray[middle] < numArray[first]) {
            swap(numArray, first, middle);
        }
        if (numArray[last] < numArray[middle]) {
            swap(numArray, middle, last);
        }
        if (numArray[middle] < numArray[first]) {
            swap(numArray, first, middle);
        }
    }
    
    /**
     * Method to simply swap two elements within an integer array. 
     * 
     * @param numArray - The integer array of random numbers. 
     * @param i - The first element
     * @param j - The second element
     */
    public static void swap(Integer[] numArray, int i, int j) {
        int temp = numArray[i];
        numArray[i] = numArray[j];
        numArray[j] = temp;
    }
    
    /**
     * Method to verify that the lists were sorted correctly. Will print out 
     * an error and exit program if any list is found to be incorrectly sorted. 
     * 
     * @param numArray - The integer array of random numbers. 
     * @param sortName - The algorithm used to sort the current list. 
     */
    public static void verifySort(Integer[] numArray, String sortName) {
        int j = 1;
        for (int i = 0; i < numArray.length - 1; i++) {
            if (numArray[i] > numArray[j]) {
                System.out.println("ERROR: The list performed by the " + sortName 
                        + " algorithm was not sorted correctly.");
                System.exit(1);
            }
            j++;
        }
    }
    
    /**
     * Method to anaylze the data and generate a report about the runtimes for
     * each sorting algorithm tested. 
     * 
     * @param results - The array containing the time results.
     * @param outputFilename - The output filename for the report. 
     */
    public static void generateReport(long[][] results, String outputFilename) {
        String blank = "";
        String run1 = "Run 1";
        String run2 = "Run 3";
        String run3 = "Run 3";
        String ms = "ms";
        String avg = "Average";
        String select = "Selection Sort";
        String shell = "Shell Sort";
        String quick = "Quick Sort";
        
        double selectAvg = (results[0][0] + results[0][1] + results[0][2]) / 3;
        double shellAvg = (results[1][0] + results[1][1] + results[1][2]) / 3;
        double quickAvg = (results[2][0] + results[2][1] + results[2][2]) / 3;
        
        try {
            File outFile = new File(outputFilename);
            PrintWriter fout = new PrintWriter(outFile);
            
            fout.println("SORTING RESULTS");
            fout.println("---------------");
            fout.printf("%-15s%15s%15s%15s%15s%n", blank, run1, run2, run3, avg);
            fout.printf("%-15s%13d%s%13d%s%13d%s%13.1f%s%n", select, results[0][0], 
                    ms, results[0][1], ms, results[0][2], ms, selectAvg, ms);
            fout.printf("%-15s%13d%s%13d%s%13d%s%13.1f%s%n", shell, results[1][0], 
                    ms, results[1][1], ms, results[1][2], ms, shellAvg, ms);
            fout.printf("%-15s%13d%s%13d%s%13d%s%13.1f%s%n", quick, results[2][0], 
                    ms, results[2][1], ms, results[2][2], ms, quickAvg, ms);
            fout.println();
            
            fout.close();
            System.out.println();
            System.out.println("Report is complete -- located in file: " 
                    + outputFilename);
            System.out.println();
            
        } catch (IOException ioex) {
            System.out.println("The file " + outputFilename 
                    + " could not be opened.");
        }
    }
}