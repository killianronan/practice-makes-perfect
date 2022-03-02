import java.util.Arrays;


public class Collinear
{
	static int y1 = 1;
	static int y2 = 2;
	static int y3 = 3;
 
	
   // ----------------------------------------------------------
    /**
     * Counts for the number of non-hoizontal lines that go through 3 points in arrays a1, a2, a3.
     * This method is static, thus it can be called as Collinear.countCollinear(a1,a2,a3)
     * @param a1: An UNSORTED array of integers. Each integer a1[i] represents the point (a1[i], 1) on the plain.
     * @param a2: An UNSORTED array of integers. Each integer a2[i] represents the point (a2[i], 2) on the plain.
     * @param a3: An UNSORTED array of integers. Each integer a3[i] represents the point (a3[i], 3) on the plain.
     * @return the number of points which are collinear and do not lie on a horizontal line.
     *
     * Array a1, a2 and a3 contain points on the horizontal line y=1, y=2 and y=3, respectively.
     * A non-horizontal line will have to cross all three of these lines. Thus
     * we are looking for 3 points, each in a1, a2, a3 which lie on the same
     * line.
     *
     * Three points (x1, y1), (x2, y2), (x3, y3) are collinear (i.e., they are on the same line) if
     *
     * x1(y2−y3)+x2(y3−y1)+x3(y1−y2)=0
     *
     * In our case y1=1, y2=2, y3=3.
     *
     * You should implement this using a BRUTE FORCE approach (check all possible combinations of numbers from a1, a2, a3)
     *
     * ----------------------------------------------------------
     *
     *
     *  Order of Growth
     *  -------------------------
     *
     *  Caclulate and write down the order of growth of your algorithm. You can use the asymptotic notation.
     *  You should adequately explain your answer. Answers without adequate explanation will not be counted.
     *
     *  Order of growth: N^3
     *
     *  Explanation: Three nested for-loops
     *  
     *  I use three nested for loops to loop through each of the 3 arrays. I pass the element values
     *  to the equation for finding collinear points and if the result is 0, I increase the collinearCount by 1.
     *  I return the result at the end.
     *  
     */
    static int countCollinear(int[] a1, int[] a2, int[] a3)
    {
	   int collinearCount = 0;
	   
	   for(int i = 0; i < a1.length; i++) {
		   for(int j = 0; j < a2.length; j++) {
			   for(int k = 0; k < a3.length; k++) {
				   if(a1[i]*(y2-y3) + a2[j]*(y3-y1) + a3[k]*(y1-y2)==0) {
					   collinearCount++;
				   }
			   }
		   }
	   }  
	   	return collinearCount;
    }

    /**
     * Counts for the number of non-hoizontal lines that go through 3 points in arrays a1, a2, a3.
     * This method is static, thus it can be called as Collinear.countCollinearFast(a1,a2,a3)
     * @param a1: An UNSORTED array of integers. Each integer a1[i] represents the point (a1[i], 1) on the plain.
     * @param a2: An UNSORTED array of integers. Each integer a2[i] represents the point (a2[i], 2) on the plain.
     * @param a3: An UNSORTED array of integers. Each integer a3[i] represents the point (a3[i], 3) on the plain.
     * @return the number of points which are collinear and do not lie on a horizontal line.
     *
     * In this implementation you should make non-trivial use of InsertionSort and Binary Search.
     * The performance of this method should be much better than that of the above method.
     *
     *
     *  Order of Growth
     *  -------------------------
     *
     *  Caclulate and write down the order of growth of your algorithm. You can use the asymptotic notation.
     *  You should adequately explain your answer. Answers without adequate explanation will not be counted.
     *
     *  Order of Growth: N^2
     *
     *  Explanation: A nested for loop is implemented.
     *  
     *   My function begins by setting an initial value of 0 for the success count. I then call the sort
     *  method and pass each array because they must be sorted before using binarySearch. I nested a for loop to find the
     *  first two values in the equation to find collinear points. I then call the binarySearch method passing it the 
     *  negative of the number needed which satisfies the equation. If the result is true, I increase the collinearCount.
     *  I return the result at the end. 
     *  
     */

    static int countCollinearFast(int[] a1, int[] a2, int[] a3)
    {
	   int collinearCount = 0;
	   Collinear.sort(a1);
	   Collinear.sort(a2);
	   Collinear.sort(a3);
	   for(int i = 0; i < a1.length; i++) {
		    for(int j = 0; j < a2.length; j++) {
			    //if(a1[i]*(y2-y3) + a2[j]*(y3-y1) + a3[k]*(-1)==0)
			    int valNeeded = a1[i]*(y2-y3) + a2[j]*(y3-y1);
			    if(Collinear.binarySearch(a3, valNeeded)) {
			    	collinearCount++;
			    }
		    }
	   }  
	   return collinearCount;
    }

    // ----------------------------------------------------------
    /**
     * Sorts an array of integers according to InsertionSort.
     * This method is static, thus it can be called as Collinear.sort(a)
     * @param a: An UNSORTED array of integers.
     * @return after the method returns, the array must be in ascending sorted order.
     *
     * ----------------------------------------------------------
     *
     *  Order of Growth
     *  -------------------------
     *
     *  Caclulate and write down the order of growth of your algorithm. You can use the asymptotic notation.
     *  You should adequately explain your answer. Answers without adequate explanation will not be counted.
     *
     *  Order of Growth: N^2
     *
     *  Explanation: Two linear for-loops.
     *
     */
    static void sort(int[] a)
    {
      for ( int j = 1; j<a.length; j++)
      {
        int i = j - 1;
        while(i>=0 && a[i]>a[i+1])
        {
          int temp = a[i];
          a[i] = a[i+1];
          a[i+1] = temp;
          i--;
        }
      }
    }

    // ----------------------------------------------------------
    /**
     * Searches for an integer inside an array of integers.
     * This method is static, thus it can be called as Collinear.binarySearch(a,x)
     * @param a: A array of integers SORTED in ascending order.
     * @param x: An integer.
     * @return true if 'x' is contained in 'a'; false otherwise.
     *
     * ----------------------------------------------------------
     *
     *  Order of Growth
     *  -------------------------
     *
     *  Caclulate and write down the order of growth of your algorithm. You can use the asymptotic notation.
     *  You should adequately explain your answer. Answers without adequate explanation will not be counted.
     *
     *  Order of Growth: logN
     *
     *  Explanation: binarySearch method is implemented.
     *  
     *  My function uses the Arrays class by importing it at the beginning of the program. I implemented 
     *  the binarySearch function from the import and assigned the result to a variable called 'index'. If the binary
     *  search is a success it returns >=0 so I find out whether is is a success or not and return true or false.
     *  
     */
    static boolean binarySearch(int[] a, int x)
    {
	    int index = Arrays.binarySearch(a, x);
	    if(index >= 0) {
	            return true;
	    }
	    return false;
	}
}
	
	
	
