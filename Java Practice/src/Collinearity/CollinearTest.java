import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;

//-------------------------------------------------------------------------
/**
 *  Test class for Collinear.java
 *
 *  @author  
 *  @version 18/09/18 12:21:26
 */
@RunWith(JUnit4.class)
public class CollinearTest
{
    //~ Constructor ........................................................
    @Test
    public void testConstructor()
    {
      new Collinear();
    }

    //~ Public Methods ........................................................

    // ----------------------------------------------------------
    /**
     * Check that the two methods work for empty arrays
     */
    @Test
    public void testEmpty()
    {
        int expectedResult = 0;

        assertEquals("countCollinear failed with 3 empty arrays",       expectedResult, Collinear.countCollinear(new int[0], new int[0], new int[0]));
        assertEquals("countCollinearFast failed with 3 empty arrays", expectedResult, Collinear.countCollinearFast(new int[0], new int[0], new int[0]));
    }

    // ----------------------------------------------------------
    /**
     * Check for no false positives in a single-element array
     */
    @Test
    public void testSingleFalse()
    {
        int[] a3 = { 15 };
        int[] a2 = { 5 };
        int[] a1 = { 10 };

        int expectedResult = 0;

        assertEquals("countCollinear({10}, {5}, {15})",       expectedResult, Collinear.countCollinear(a1, a2, a3) );
        assertEquals("countCollinearFast({10}, {5}, {15})", expectedResult, Collinear.countCollinearFast(a1, a2, a3) );
    }

    // ----------------------------------------------------------
    /**
     * Check for no false positives in a single-element array
     */
    @Test
    public void testSingleTrue()
    {
        int[] a3 = { 15, 5 };       int[] a2 = { 5 };       int[] a1 = { 10, 15, 5 };

        int expectedResult = 1;

        assertEquals("countCollinear(" + Arrays.toString(a1) + "," + Arrays.toString(a2) + "," + Arrays.toString(a3) + ")",     expectedResult, Collinear.countCollinear(a1, a2, a3));
        assertEquals("countCollinearFast(" + Arrays.toString(a1) + "," + Arrays.toString(a2) + "," + Arrays.toString(a3) + ")", expectedResult, Collinear.countCollinearFast(a1, a2, a3));
    }
    
    @Test
    public void testDoubleTrue()
    {
        int[] a3 = { 5, 10 };       int[] a2 = { 10, 5 };       int[] a1 = { 10, 5 };

        int expectedResult = 2;

        assertEquals("countCollinear(" + Arrays.toString(a1) + "," + Arrays.toString(a2) + "," + Arrays.toString(a3) + ")",     expectedResult, Collinear.countCollinear(a1, a2, a3));
        assertEquals("countCollinearFast(" + Arrays.toString(a1) + "," + Arrays.toString(a2) + "," + Arrays.toString(a3) + ")", expectedResult, Collinear.countCollinearFast(a1, a2, a3));
    }
    @Test
    public void testTripleTrue()
    {
        int[] a3 = { 5, 10, 20 };       int[] a2 = { 10, 5, 20 };       int[] a1 = { 10, 5, 20};

        int expectedResult = 3;

        assertEquals("countCollinear(" + Arrays.toString(a1) + "," + Arrays.toString(a2) + "," + Arrays.toString(a3) + ")",     expectedResult, Collinear.countCollinear(a1, a2, a3));
        assertEquals("countCollinearFast(" + Arrays.toString(a1) + "," + Arrays.toString(a2) + "," + Arrays.toString(a3) + ")", expectedResult, Collinear.countCollinearFast(a1, a2, a3));
    }
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testArrayIndexOver()
    {
        int[] a3 = { 5, 10, 20 };       int[] a2 = { 10, 5, 20 };       int[] a1 = { 10, 5, 20};
        
        int y1 = 1;
        int y2 = 2;
        int y3 = 3;
        
        int i = 3;
        int j = 3;
        int k = 3;
        
        boolean found = false;
        if(a1[i]*(y2-y3) + a2[j]*(y3-y1) + a3[k]*(y1-y2)==0) {
        	found = true;
        }
    }
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testArrayIndexUnder()
    {
        int[] a3 = { 5, 10, 20 };       int[] a2 = { 10, 5, 20 };       int[] a1 = { 10, 5, 20};
        
        int y1 = 1;
        int y2 = 2;
        int y3 = 3;
        
        int i = -1;
        int j = -1;
        int k = -1;
        
        boolean found = false;
        
        if(a1[i]*(y2-y3) + a2[j]*(y3-y1) + a3[k]*(y1-y2)==0) {
        	found = true;
        }
    }
    @Test
    public void testBinarySearchTrue()
    {
        int[] elements = { 15, 5, 20, 15 };       int key = 5;       

        assertTrue("binarySearch(" + Arrays.toString(elements) + "," + key + "," ,Collinear.binarySearch(elements, key));
    }
    @Test
    public void testBinarySearchFalse()
    {
        int[] elements = { 16, 1, 29, 13 };       int key = 8;       
        
        assertFalse("binarySearch(" + Arrays.toString(elements) + "," + key + "," ,Collinear.binarySearch(elements, key));
    }
    @Test
    public void testBinarySearchEmpty()
    {
        int[] elements = {};       int key = 8;       
        
        assertFalse("binarySearch(" + Arrays.toString(elements) + "," + key + "," ,Collinear.binarySearch(elements, key));
    }
    
    //100000N
    @Test
    public void testBinarySearchMediumSize()
    {
        int[] elements = new int[100000];       int key = 8;       
        
        assertFalse("binarySearch(" + Arrays.toString(elements) + "," + key + "," ,Collinear.binarySearch(elements, key));
    }//0.031seconds
    
    //10000000N
    @Test
    public void testBinarySearchLargeSize()
    {
        int[] elements = new int[10000000];       int key = 8;       
        
        assertFalse("binarySearch(" + Arrays.toString(elements) + "," + key + "," ,Collinear.binarySearch(elements, key));
    }//0.531seconds crashed with another 0
    
    
//    @Test
//    public void testSortEquals()
//    {
//        int[] elements = {6,4,7,3,9,8,11,2};
//        int[] expectedResult = {2,3,4,6,7,8,9,11};
//
//        Collinear.sort(elements);
//
//        assertTrue("sort(" + Arrays.toString(elements), elements.equals(expectedResult));
//    }//0.531seconds crashed with another 0

//    Each method in Collinear.java is tested at least once,
//    Each decision (that is, every branch of if-then-else, for, and other kinds of choices) in Collinear.java is tested at least once,
//    Each line of code in Collinear.java is executed at least once from the tests.

}

