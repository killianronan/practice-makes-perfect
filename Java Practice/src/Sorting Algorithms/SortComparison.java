import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

// -------------------------------------------------------------------------

/**
 *  This class contains static methods that implementing sorting of an array of numbers
 *  using different sort algorithms.
 *
 *  @author
 *  @version HT 2020
 */

 class SortComparison {

    /**
     * Sorts an array of doubles using InsertionSort.
     * This method is static, thus it can be called as SortComparison.sort(a)
     * @param a: An unsorted array of doubles.
     * @return array sorted in ascending order.
     *
     */
    static double [] insertionSort (double a[]) {
    	int j;
    	for(int i = 1; i<a.length; i++) {//i = key
            double key = a[i];    
    		j = i-1;
    		while(j>-1&&a[j]>key) {//check lower // make sure j>-1 check is done first, otherwise Out of bounds exception
    			a[j+1]=a[j];//swap
        		j--;
    		}
    		a[j+1]=key;//move to key pos
    	}
    	return a;
    }
    static void swap(double a[],int pos1, int pos2){
    	double tmp = a[pos1];
    	a[pos1] = a[pos2];
    	a[pos2] = tmp;
    	return;
    }
	    /**
     * Sorts an array of doubles using Selection Sort.
     * This method is static, thus it can be called as SortComparison.sort(a)
     * @param a: An unsorted array of doubles.
     * @return array sorted in ascending order
     *
     */
    static double [] selectionSort (double a[]){
    	int smallest;
        for (int i = 0; i < a.length - 1; i++) {
            smallest = i; //begin at a[i]
            for (int j = i + 1; j < a.length; j++) {//select lowest element
                if (a[smallest] > a[j]) {
                    smallest = j; //save new smallest index
                }
            }
            swap(a,i,smallest); //Swapping
        }
        return a;
    }//end selectionsort
    /**
     * Sorts an array of doubles using Quick Sort.
     * This method is static, thus it can be called as SortComparison.sort(a)
     * @param a: An unsorted array of doubles.
     * @return array sorted in ascending order
     *
     */
    static double [] quickSort (double a[], int leftmost, int rightmost){
    	if (a.length == 0){
            return null;
        }
        if (rightmost>leftmost)  
        {
            int pivot = partition(a, leftmost, rightmost); 
            quickSort(a, leftmost, pivot-1); //Recursively continue
            quickSort(a, pivot+1, rightmost); 
        } //else finished
    	return a;
    }//end quicksort
    
    static int partition(double arr[], int begin, int end) { //partition using end of array as pivot
        double pivot = arr[end];
        int i = (begin-1);
        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr,i,j);
            }
        }
        swap(arr,i+1,end);
        i++;
        return i;
    }
    
    /**
     * Sorts an array of doubles using Merge Sort.
     * This method is static, thus it can be called as SortComparison.sort(a)
     * @param a: An unsorted array of doubles.
     * @return array sorted in ascending order
     *
     */
    /**
     * Sorts an array of doubles using iterative implementation of Merge Sort.
     * This method is static, thus it can be called as SortComparison.sort(a)
     *
     * @param a: An unsorted array of doubles.
     * @return after the method returns, the array must be in ascending sorted order.
     */    
 	static void merge(double[] a, double[] tmp, int begin, int mid, int end) {
        for (int x = begin; x <= end; x++){//copy to tmp array
            tmp[x] = a[x]; 
        }
        // merge back to a[]
        int i = begin;
        int j = mid+1;
        for (int h = begin; h <= end; h++) {
            if (i > mid) {			//This part only needed for mergeSortRecursive and not mergeSortIterative
            	a[h] = tmp[j];		//
            	j++;				//
            }else					//
            if (j > end) {
            	a[h] = tmp[i];
            	i++;
            }
            else if (tmp[j]<tmp[i]) {
            	a[h] = tmp[j];
            	j++;
            }
            else{
            	a[h] = tmp[i];
            	i++;
            }
        }
    }
    static double[] mergeSortIterative (double a[]) {
 		int begin = 0;
 		int end = a.length - 1;
 		double[] temp = Arrays.copyOf(a, a.length);//make temp for sorted array
 		for (int multiples2 = 1; multiples2 <= end - begin; multiples2 = 2*multiples2) //Divide array into powers/multiples of 2
 		{
 			for (int i = begin; i < end; i += 2*multiples2)//loop to merge sub-arrays
 			{
 				int start = i;
 				int mid = i + multiples2 - 1;
 				int to = Math.min(2*multiples2-1+i,end);
 				merge(a, temp, start, mid, to);
 			}
 		}
 		return a;
    }//end mergesortIterative
    
    /**
     * Sorts an array of doubles using recursive implementation of Merge Sort.
     * This method is static, thus it can be called as SortComparison.sort(a)
     *
     * @param a: An unsorted array of doubles.
     * @return after the method returns, the array must be in ascending sorted order.
     */
    static double[] mergeSortRecursive (double[] a, double[] tmp, int begin, int end) {
        if (begin >= end) {//if(finished)
        	return a;
        }
        int mid = (end - begin)/2 + begin;
        mergeSortRecursive(a, tmp, begin, mid);
        mergeSortRecursive(a, tmp, mid+1, end);
        merge(a, tmp, begin, mid, end);
        return null;
   }//end mergeSortRecursive
    
    public static double[] fillArray(File arrayInput, int n) {
    	double[] a = new double[n];
    	try {
            Scanner in = new Scanner(arrayInput);
            int i = 0;
            while (in.hasNextLine()&&i<n) {
                a[i++] = in.nextDouble();
            }
            in.close();
        }
        catch (FileNotFoundException e) {
            System.exit(1);
        }
    	return a;
    }
    public static void main(String a[]){ 
        File file = new File("numbers10.txt");
    	fillArray(file, 10);
    	
        double[] arr1 = {9,14,3,2,43,11,58,22};    
        System.out.println("Before Insertion Sort");    
        for(double i:arr1){    
            System.out.print(i+" ");    
        }    
        System.out.println();    
            
        insertionSort(arr1);//sorting array using insertion sort    
           
        System.out.print("After Insertion Sort");    
        for(double i:arr1){    
            System.out.print(i+" ");    
        }    
        double[] arr2 = {9,14,3,2,43,11,58,22};    

        selectionSort(arr2);//sorting array using insertion sort    
        double[] arr3 = {9,14,3,2,43,11,58,22};    

        selectionSort(arr3);//sorting array using insertion sort    
        double[] arr4 = {9,14,3,2,43,11,58,22};    

        quickSort(arr4,0,arr4.length-1);//sorting array using insertion sort 
        double[] arr5 = {9,14,3,2,43,11,58,22};    

        mergeSortIterative(arr5);//sorting array using insertion sort 

    }
 }//end class

