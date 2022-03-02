

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 *  Test class for SortComparison.java
 *
 *  @author
 *  @version HT 2020		Insert	  Selection	 Merge Recursive Merge Iterative   Quick
 *  10 random            : 0.009ms	: 0.0186ms	:	0.0144ms	:	0.1482ms	: 0.0177ms  :
 *  100 random 			 : 0.1308ms : 0.2002ms	:	0.1ms		:	0.0891ms	: 0.4789ms	:
 *  1000 random 		 : 8.6522ms : 6.2137ms	:	0.3713ms	:	0.4763ms	: 4.5136ms  :
 *  1000 few unique		 : 1.9905ms : 0.6297ms	:	0.1158ms	:	0.343ms		: 4.8885ms  :
 *  1000 nearly ordered  : 0.0532ms : 0.3666ms  :	0.0903ms	:	0.1733ms	: 0.3955ms	:
 *  1000 reverse order   : 0.5052ms : 0.4ms 	:	0.0771ms	:	0.16ms	    : 0.4306ms  :
 *  1000 sorted			 : 0.0054ms : 0.4064ms  :	0.0767ms	:	0.1705ms	: 0.4382ms  :
 *  
 *  a.  Which of the sorting algorithms does the order of input have an impact on? Why?
 *		The order of input affects all of the sorting algorithms, some worse than others. Insertion Sort can work extremely well with inputs which are sorted.
 *	 	Selection Sort does not work well with large inputs that are sorted, conversely to Insertion Sort. 
 *
 *  b.  Which algorithm has the biggest difference between the best and worst performance, based on the type of input, for the input of size 1000? Why?
 *  	Insertion Sort had the largest difference of note for these inputs. It took the most amount of time with 8.6522ms sorting a random input of this size compared to the other algorithms.
 *  	It takes 0.0054ms to sort an already sorted list which was the best performer of all algorithms tested. This is because it's best case runtime of n where it does not do any swaps. If the input is random it will be likely to have a worst case runtime n^2.
 *  
 *  c.  Which algorithm has the best/worst scalability, i.e.the difference in performance timebased on the input size? Please consider only input files with random order for this answer.
 *  	Insertion Sort has the worst scalability in terms of the size of input. It moved from 0.09ms with an input size of 10 to 8.6522ms with an input size of 1000, which is a tremendous difference.
 *  	Merge Sort Recursive seemed to be the best scaling algorithm with a low average time. It moved from 0.0144ms with an input size of 10 to 0.3713ms with an input size of 1000 which shows it scales well with larger inputs.
 * 
 *  d.  Did you observe any difference between iterative and recursive implementations of mergesort?
 *  	Merge Sort Recursive worked a lot faster than Merge Sort iterative for all of the 1000 input sizes. Merge sort Iterative performed better in only one instance: input of size 100.
 *  	The recursive version of this algorithms is simpler to understand and works better over larger inputs compared to the bottom up version.
 *  
 *  e.  Which algorithm is the fastest for each of the 7 input files?
 *  	For 10 random the fastest was Insertion Sort on average.
 *  	For 100 random the fastest was Merge Sort Iterative on average.
 *  	For 1000 random the fastest was Merge Sort Recursive on average.
 *  	For 1000 few unique the fastest was Merge Sort Recursive on average.
 *  	For 1000 nearly ordered the fastest was Insertion Sort on average.
 *  	For 1000 reverse order the fastest was Merge Sort Recursive on average.
 *  	For 1000 sorted the fastest was Insertion Sort on average.
 */

@RunWith(JUnit4.class)
public class SortingComparisonTest
{
    //~ Constructor ........................................................
    @Test
    public void testConstructor()
    {
        new SortComparison();
    }
    //~ Public Methods ........................................................
    // ----------------------------------------------------------
    /**
     * Check that the methods work for empty arrays
     */
    @Test
    public void testEmpty()
    {
    	double a[] = {};  
    	SortComparison.insertionSort(a);
    	assertEquals("Checking insertionSort on an empty array, returned array should also be empty", 0, 0);
    	SortComparison.selectionSort(a);
    	assertEquals("Checking selectionSort on an empty array, returned array should also be empty", 0, 0);
    	SortComparison.quickSort(a,0,a.length-1);
    	assertEquals("Checking quickSort on an empty array, returned array should also be empty", 0, 0);
    }
    @Test
    public void testInsertionSort()
    {
    	double one[] = {1};
    	double oneResult[] = {1};
    	SortComparison.insertionSort(one);
        assertTrue(Arrays.equals(one,oneResult));
        double two[] = {5,4};
    	double twoResult[] = {4,5};
    	SortComparison.insertionSort(two);
        assertTrue(Arrays.equals(two,twoResult));
    	double a[] = {3,7,2,1,9};
    	double aresult[] = {1,2,3,7,9};
    	SortComparison.insertionSort(a);
        assertTrue(Arrays.equals(a,aresult));
    }
    @Test
    public void testSelectionSort()
    {
    	double one[] = {1};
    	double oneResult[] = {1};
    	SortComparison.selectionSort(one);
        assertTrue(Arrays.equals(one,oneResult));
        double two[] = {5,4};
    	double twoResult[] = {4,5};
    	SortComparison.selectionSort(two);
        assertTrue(Arrays.equals(two,twoResult));
    	double a[] = {3,7,2,1,9};
    	double aresult[] = {1,2,3,7,9};
    	SortComparison.selectionSort(a);
        assertTrue(Arrays.equals(a,aresult));
    }
    @Test
    public void testQuickSort()
    {
    	double one[] = {1};
    	double oneResult[] = {1};
    	SortComparison.quickSort(one,0,one.length-1);
        assertTrue(Arrays.equals(one,oneResult));
        double two[] = {5,4};
    	double twoResult[] = {4,5};
    	SortComparison.quickSort(two,0,two.length-1);
        assertTrue(Arrays.equals(two,twoResult));
    	double a[] = {3,7,2,1,9};
    	double aresult[] = {1,2,3,7,9};
    	SortComparison.quickSort(a,0,a.length-1);
        assertTrue(Arrays.equals(a,aresult));  
    }
    @Test
    public void testMergeSortIterative()
    {
    	double one[] = {1};
    	double oneResult[] = {1};
    	SortComparison.mergeSortIterative(one);
        assertTrue(Arrays.equals(one,oneResult));
        double two[] = {5,4};
    	double twoResult[] = {4,5};
    	SortComparison.mergeSortIterative(two);
        assertTrue(Arrays.equals(two,twoResult));
    	double a[] = {3,7,2,1,9};
    	double aresult[] = {1,2,3,7,9};
    	SortComparison.mergeSortIterative(a);
        assertTrue(Arrays.equals(a,aresult));  
    }
    @Test
    public void testMergeSortRecursive()
    {
    	double one[] = {1};
    	double oneResult[] = {1};
    	double tmp[] = new double[one.length];
    	SortComparison.mergeSortRecursive(one,tmp,0,one.length-1);
        assertTrue(Arrays.equals(one,oneResult));
        double two[] = {5,4};
    	double twoResult[] = {4,5};
    	tmp = new double[two.length];
    	SortComparison.mergeSortRecursive(two,tmp,0,two.length-1);
        assertTrue(Arrays.equals(two,twoResult));
    	double a[] = {3,7,2,1,9};
    	double aresult[] = {1,2,3,7,9};
    	tmp = new double[a.length];
    	SortComparison.mergeSortRecursive(a,tmp,0,a.length-1);
        assertTrue(Arrays.equals(a,aresult));  
    }
    public static double[] fillArray(File file, int n) {
    	double[] a = new double[n+1];
    	try {
            File arrayInput = new File("/SortingAlgorithns/numbers10.txt");
            Scanner in = new Scanner(arrayInput);
            int i = 0;
            while (in.hasNextLine()) {
                a[i++] = in.nextDouble();
            	System.out.println(a[i]);

            }
            in.close();
        }
        catch (FileNotFoundException e) {
            System.exit(1);
        }
    	return a;
    }
    /**
     *  Main Method.
     *  Use this main method to create the experiments needed to answer the experimental performance questions of this assignment.
     *
     */
    public static void main(String[] args)
    {
    	File file = new File("numbers10.txt");
        double[] a10 = SortComparison.fillArray(file,10);
        double[] tmp = a10;
        double startTime = System.nanoTime();
        SortComparison.insertionSort(tmp);
        double endTime = System.nanoTime();
        double duration = (endTime - startTime);  
        System.out.println("insertionSort took: " + duration/1000000+ "ms on numbers10");//divide by 1000000 to get milliseconds

        tmp = a10;
        startTime = System.nanoTime();
        SortComparison.selectionSort(tmp);
        endTime = System.nanoTime();
        duration = (endTime - startTime);  
        System.out.println("selectionSort took: " + duration/1000000 + "ms on numbers10");//divide by 1000000 to get milliseconds

        tmp = a10;
        startTime = System.nanoTime();
        SortComparison.quickSort(tmp,0,tmp.length-1);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("quickSort took: " + duration/1000000 + "ms on numbers10");//divide by 1000000 to get milliseconds
        
        tmp = a10;
        startTime = System.nanoTime();
        SortComparison.mergeSortIterative(tmp);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("mergeSortIterative took: " + duration/1000000 + "ms on numbers10");//divide by 1000000 to get milliseconds
        
        tmp = a10;
        startTime = System.nanoTime();
        double aux[] = new double[tmp.length];
        SortComparison.mergeSortRecursive(tmp,aux,0,tmp.length-1);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("mergeSortRecursive took: " + duration /1000000+ "ms on numbers10");//divide by 1000000 to get milliseconds
        System.out.println();
        
    	File file2 = new File("numbers100.txt");
        double[] a100 = SortComparison.fillArray(file2,100);
        tmp = a100;
        startTime = System.nanoTime();
        SortComparison.insertionSort(tmp);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Insertion took: " + duration/1000000 + "ms on numbers100");//divide by 1000000 to get milliseconds

        tmp = a100;
        startTime = System.nanoTime();
        SortComparison.selectionSort(tmp);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Selection took: " + duration/1000000 + "ms on numbers100");//divide by 1000000 to get milliseconds

        tmp = a100;
        startTime = System.nanoTime();
        SortComparison.quickSort(tmp,0,tmp.length-1);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Quick took: " + duration/1000000 + "ms on numbers100");//divide by 1000000 to get milliseconds
        
        tmp = a100;
        startTime = System.nanoTime();
        SortComparison.mergeSortIterative(tmp);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("mergeSortIterative took: " + duration/1000000 + "ms on numbers100");//divide by 1000000 to get milliseconds
        
        tmp = a100;
        startTime = System.nanoTime();
        aux = new double[tmp.length];
        SortComparison.mergeSortRecursive(tmp,aux,0,tmp.length-1);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("mergeSortRecursive took: " + duration/1000000 + "ms on numbers100");//divide by 1000000 to get milliseconds
        System.out.println();
        
    	File file3 = new File("numbers1000.txt");
        double[] a1000 = SortComparison.fillArray(file3,1000);
        tmp = a1000;
        startTime = System.nanoTime();
        SortComparison.insertionSort(tmp);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Insertion took: " + duration/1000000 + "ms on numbers1000");//divide by 1000000 to get milliseconds

        tmp = a1000;
        startTime = System.nanoTime();
        SortComparison.selectionSort(tmp);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Selection took: " + duration/1000000 + "ms on numbers1000");//divide by 1000000 to get milliseconds

        tmp = a1000;
        startTime = System.nanoTime();
        SortComparison.quickSort(tmp,0,tmp.length-1);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Quick took: " + duration/1000000 + "ms on numbers1000");//divide by 1000000 to get milliseconds
        
        tmp = a1000;
        startTime = System.nanoTime();
        SortComparison.mergeSortIterative(tmp);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("mergeSortIterative took: " + duration/1000000 + "ms on numbers1000");//divide by 1000000 to get milliseconds
        
        tmp = a1000;
        startTime = System.nanoTime();
        aux = new double[tmp.length];
        SortComparison.mergeSortRecursive(tmp,aux,0,tmp.length-1);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("mergeSortRecursive took: " + duration/1000000 + "ms on numbers1000");//divide by 1000000 to get milliseconds
        System.out.println();
        
    	File file4 = new File("numbers1000Duplicates.txt");
        double[] a1000Duplicates = SortComparison.fillArray(file4,1000);
        tmp = a1000Duplicates;
        
        startTime = System.nanoTime();
        SortComparison.insertionSort(tmp);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Insertion took: " + duration/1000000 + "ms on numbers1000Duplicates");//divide by 1000000 to get milliseconds

        tmp = a1000Duplicates;
        startTime = System.nanoTime();
        SortComparison.selectionSort(tmp);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Selection took: " + duration/1000000 + "ms on numbers1000Duplicates");//divide by 1000000 to get milliseconds

        tmp = a1000Duplicates;
        startTime = System.nanoTime();
        SortComparison.quickSort(tmp,0,tmp.length-1);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Quick took: " + duration/1000000 + "ms on numbers1000Duplicates");//divide by 1000000 to get milliseconds
      
        tmp = a1000Duplicates;
        startTime = System.nanoTime();
        SortComparison.mergeSortIterative(tmp);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("mergeSortIterative took: " + duration/1000000 + "ms on numbers1000Duplicates");//divide by 1000000 to get milliseconds
        
        tmp = a1000Duplicates;
        startTime = System.nanoTime();
        aux = new double[tmp.length];
        SortComparison.mergeSortRecursive(tmp,aux,0,tmp.length-1);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("mergeSortRecursive took: " + duration/1000000 + "ms on numbers1000Duplicates");//divide by 1000000 to get milliseconds
        System.out.println();
        
    	File file5 = new File("numbersNearlyOrdered1000.txt");
        double[] aNearlySorted1000 = SortComparison.fillArray(file5,1000);
        tmp = aNearlySorted1000;
        startTime = System.nanoTime();
        SortComparison.insertionSort(tmp);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Insertion took: " + duration/1000000 + "ms on numbers1000NearlyOrdered");//divide by 1000000 to get milliseconds

        tmp = aNearlySorted1000;
        startTime = System.nanoTime();
        SortComparison.selectionSort(tmp);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Selection took: " + duration/1000000 + "ms on numbers1000NearlyOrdered");//divide by 1000000 to get milliseconds

        tmp = aNearlySorted1000;
        startTime = System.nanoTime();
        SortComparison.quickSort(tmp,0,tmp.length-1);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Quick took: " + duration/1000000 + "ms on numbers1000NearlyOrdered");//divide by 1000000 to get milliseconds
        
        tmp = aNearlySorted1000;
        startTime = System.nanoTime();
        SortComparison.mergeSortIterative(tmp);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("mergeSortIterative took: " + duration/1000000 + "ms on numbers1000NearlyOrdered");//divide by 1000000 to get milliseconds
        
        tmp = aNearlySorted1000;
        startTime = System.nanoTime();
        aux = new double[tmp.length];
        SortComparison.mergeSortRecursive(tmp,aux,0,tmp.length-1);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("mergeSortRecursive took: " + duration/1000000 + "ms on numbers1000NearlyOrdered");//divide by 1000000 to get milliseconds
        System.out.println();
        
        File file6 = new File("numbersReverse1000.txt");
        double[] aReverse1000 = SortComparison.fillArray(file6,1000);
        tmp = aReverse1000;
        startTime = System.nanoTime();
        SortComparison.insertionSort(tmp);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Insertion took: " + duration/1000000 + "ms on numbers1000Reverse");//divide by 1000000 to get milliseconds

        tmp = aReverse1000;
        startTime = System.nanoTime();
        SortComparison.selectionSort(tmp);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Selection took: " + duration/1000000 + "ms on numbers1000Reverse");//divide by 1000000 to get milliseconds

        tmp = aReverse1000;
        startTime = System.nanoTime();
        SortComparison.quickSort(tmp,0,tmp.length-1);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Quick took: " + duration/1000000 + "ms on numbers1000Reverse");//divide by 1000000 to get milliseconds
        
        tmp = aReverse1000;
        startTime = System.nanoTime();
        SortComparison.mergeSortIterative(tmp);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("mergeSortIterative took: " + duration/1000000 + "ms on numbers1000Reverse");//divide by 1000000 to get milliseconds
        
        tmp = aReverse1000;
        startTime = System.nanoTime();
        aux = new double[tmp.length];
        SortComparison.mergeSortRecursive(tmp,aux,0,tmp.length-1);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("mergeSortRecursive took: " + duration/1000000 + "ms on numbers1000Reverse");//divide by 1000000 to get milliseconds
        System.out.println();
        
        File file7 = new File("numbersSorted1000.txt");
        double[] aSorted1000 = SortComparison.fillArray(file7,1000);
        tmp = aSorted1000;
        startTime = System.nanoTime();
        SortComparison.insertionSort(tmp);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Insertion took: " + duration/1000000 + "ms on numbers1000Sorted");//divide by 1000000 to get milliseconds

        tmp = aSorted1000;
        startTime = System.nanoTime();
        SortComparison.selectionSort(tmp);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Selection took: " + duration/1000000 + "ms on numbers1000Sorted");//divide by 1000000 to get milliseconds

        tmp = aSorted1000;
        startTime = System.nanoTime();
        SortComparison.quickSort(tmp,0,tmp.length-1);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Quick took: " + duration/1000000 + "ms on numbers1000Sorted");//divide by 1000000 to get milliseconds
        
        tmp = aSorted1000;
        startTime = System.nanoTime();
        SortComparison.mergeSortIterative(tmp);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("mergeSortIterative took: " + duration/1000000 + "ms on numbers1000Sorted");//divide by 1000000 to get milliseconds
        
        tmp = aSorted1000;
        startTime = System.nanoTime();
        aux = new double[tmp.length];
        SortComparison.mergeSortRecursive(tmp,aux,0,tmp.length-1);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("mergeSortRecursive took: " + duration/1000000 + "ms on numbers1000Sorted");//divide by 1000000 to get milliseconds
        System.out.println();
    }
}