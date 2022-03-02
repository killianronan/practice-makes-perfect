import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import org.junit.Test;
//import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

//-------------------------------------------------------------------------
/**
 *  Test class for Doubly Linked List
 *
 *  @author  
 *  @version 13/10/16 18:15
 */
@RunWith(JUnit4.class)
public class DoublyLinkedListTest
{
    //~ Constructor ........................................................
    @Test
    public void testConstructor()
    {
      new DoublyLinkedList<Integer>();
    }

    //~ Public Methods ........................................................

    // ----------------------------------------------------------
    /**
     * Check if the insertBefore works
     */
    @Test
    public void testInsertBefore()
    {
         DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
         testDLL.insertBefore(0,1);
         testDLL.insertBefore(1,2);
         assertEquals( "Checking insertBefore to a list containing 2 elements at position 1", "1,2", testDLL.toString() );

         testDLL.insertBefore(1,3);
         assertEquals( "Checking insertBefore to a list containing 3 elements at position 0", "1,3,2", testDLL.toString() );

        // test non-empty list
        testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertBefore(0,1);
        testDLL.insertBefore(1,2);
        testDLL.insertBefore(2,3);

        testDLL.insertBefore(0,4);
        assertEquals( "Checking insertBefore to a list containing 3 elements at position 0", "4,1,2,3", testDLL.toString() );
        testDLL.insertBefore(1,5);
        assertEquals( "Checking insertBefore to a list containing 4 elements at position 1", "4,5,1,2,3", testDLL.toString() );
        testDLL.insertBefore(2,6);       
        assertEquals( "Checking insertBefore to a list containing 5 elements at position 2", "4,5,6,1,2,3", testDLL.toString() );
        testDLL.insertBefore(-1,7);        
        assertEquals( "Checking insertBefore to a list containing 6 elements at position -1 - expected the element at the head of the list", "7,4,5,6,1,2,3", testDLL.toString() );
        testDLL.insertBefore(7,8);        
        assertEquals( "Checking insertBefore to a list containing 7 elemenets at position 8 - expected the element at the tail of the list", "7,4,5,6,1,2,3,8", testDLL.toString() );
        testDLL.insertBefore(700,9);        
        assertEquals( "Checking insertBefore to a list containing 8 elements at position 700 - expected the element at the tail of the list", "7,4,5,6,1,2,3,8,9", testDLL.toString() );

        // test empty list
        testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertBefore(0,1);        
        assertEquals( "Checking insertBefore to an empty list at position 0 - expected the element at the head of the list", "1", testDLL.toString() );
        testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertBefore(10,1);        
        assertEquals( "Checking insertBefore to an empty list at position 10 - expected the element at the head of the list", "1", testDLL.toString() );
        testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertBefore(-10,1);        
        assertEquals( "Checking insertBefore to an empty list at position -10 - expected the element at the head of the list", "1", testDLL.toString() );
        testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertBefore(0,1);
        testDLL.insertBefore(2,3);
        assertEquals( "Checking insertBefore to a list containing 3 elements at position 0", "1,3", testDLL.toString());
    	testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertBefore(0,1);
        testDLL.insertBefore(1,2);
        testDLL.insertBefore(2,3);
        testDLL.insertBefore(2,4);
        assertEquals( "Checking insertBefore to a list containing 3 elements at position 0", "1,2,4,3", testDLL.toString());


     }
    @Test
    public void testGet() {
        DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
        assertNull(testDLL.get(0));
        testDLL.insertBefore(0,1);
        testDLL.insertBefore(1,2);
        assertTrue(testDLL.get(1)==2);
        testDLL.insertBefore(2,3);
        testDLL.insertBefore(0,4);
        assertTrue(testDLL.get(0)==4);
        assertTrue(testDLL.get(1)==1);
        assertTrue(testDLL.get(16)==null);
        testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertBefore(0,1);
        assertTrue(testDLL.get(1)==null);
        assertNull(testDLL.get(-2));
        testDLL = new DoublyLinkedList<Integer>();
        testDLL.push(0);
        testDLL.push(1);
        assertTrue(testDLL.get(1)==0);
        testDLL = new DoublyLinkedList<Integer>();
        testDLL.enqueue(0);
        testDLL.enqueue(1);
        assertTrue(testDLL.get(1)==0);
    }
    @Test
    public void testEmpty() {
        DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
        assertTrue(testDLL.isEmpty());
        testDLL.insertBefore(0,1);
        assertFalse(testDLL.isEmpty());
    }
    @Test
    public void testDeleteAt() {
        DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
        assertFalse(testDLL.deleteAt(0));
        testDLL.insertBefore(0,1);
        testDLL.insertBefore(1,2);
        testDLL.deleteAt(0);
        assertEquals( "Checking deleteAt to a list containing 2 elements at position 0", "2", testDLL.toString());
        assertTrue(testDLL.deleteAt(0));
        testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertBefore(0,1);
        testDLL.insertBefore(1,2);
        testDLL.insertBefore(2,3);
        testDLL.deleteAt(0);
        assertEquals( "Checking deleteAt to a list containing 3 elements at position 0", "2,3", testDLL.toString());

        testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertBefore(0,1);
        testDLL.insertBefore(1,2);
        testDLL.insertBefore(2,3);
        assertTrue(testDLL.deleteAt(2));
        assertEquals( "Checking deleteAt to a list containing 2 elements at tail", "1,2", testDLL.toString());
        testDLL.insertBefore(2,3);
        assertTrue(testDLL.deleteAt(1));
        testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertBefore(0,1);
        testDLL.insertBefore(1,2);
        testDLL.insertBefore(2,3);
        assertFalse(testDLL.deleteAt(10));
        assertFalse(testDLL.deleteAt(-10));
        testDLL = new DoublyLinkedList<Integer>();
        testDLL.push(0);
        assertFalse(testDLL.deleteAt(1));
        testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertBefore(0,1);
        testDLL.insertBefore(1,2);
        testDLL.insertBefore(2,3);
        testDLL.insertBefore(3,1);
        testDLL.insertBefore(4,2);
        testDLL.insertBefore(5,3);
        testDLL.insertBefore(6,4);

        testDLL.deleteAt(4);
        assertEquals( "Checking deleteAt to a list containing 2 elements at tail", "1,2,3,1,3,4", testDLL.toString());
        
    }
    @Test
    public void testReverse() {
        DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
        testDLL.push(0);
        testDLL.push(1);
        testDLL.push(2);
        testDLL.push(3);
        testDLL.push(4);
        testDLL.push(4);
        testDLL.reverse();
        assertEquals( "Checking push", "0,1,2,3,4,4", testDLL.toString());
        testDLL = new DoublyLinkedList<Integer>();
        testDLL.reverse();
        testDLL.push(0);
        testDLL.reverse();
        
    }
    @Test
    public void testMakeUnique() {
        DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
        testDLL.push(0);
        testDLL.push(1);
        testDLL.push(2);
        testDLL.push(3);
        testDLL.push(4);
        testDLL.push(4);
        testDLL.makeUnique();
        assertEquals( "Checking push", "4,3,2,1,0", testDLL.toString());
        testDLL = new DoublyLinkedList<Integer>();
        testDLL.makeUnique();
        testDLL.push(0);
        testDLL.makeUnique();
    	testDLL = new DoublyLinkedList<Integer>();
        testDLL.push(3);
        testDLL.push(4);
        testDLL.push(6);
        testDLL.push(6);
        testDLL.push(2);
        testDLL.push(6);
        testDLL.makeUnique();
        assertEquals( "Checking makeUniq", "6,2,4,3", testDLL.toString());
        testDLL = new DoublyLinkedList<Integer>();
        testDLL.push(4);
        testDLL.push(4);
        testDLL.push(4);
        testDLL.push(3);
        testDLL.push(4);
        testDLL.push(6);
        testDLL.push(6);
        testDLL.push(6);
        testDLL.push(6);
        testDLL.push(2);
        testDLL.push(6);
        testDLL.push(4);
        testDLL.push(4);
        testDLL.push(4);

        testDLL.makeUnique();
        assertEquals( "Checking makeUniq", "4,6,2,3", testDLL.toString());
        testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertBefore(0,2);
        testDLL.insertBefore(1,2);
        testDLL.insertBefore(2,2);
        testDLL.insertBefore(3,1);
        testDLL.insertBefore(4,1);
        testDLL.insertBefore(5,1);
        testDLL.insertBefore(6,1);
        testDLL.makeUnique();
        assertEquals( "Checking insertBefore to a list containing 3 elements at position 0", "2,1", testDLL.toString());
    }
    @Test
    public void testPush(){
        DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
        testDLL.push(0);
        testDLL.push(1);
        testDLL.push(2);
        assertEquals( "Checking push", "2,1,0", testDLL.toString());
    }
    @Test
    public void testPop(){
        DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
        assertTrue(testDLL.pop()==null);
        testDLL.push(0);
        assertTrue(testDLL.pop()==0);
        testDLL.push(1);
        testDLL.push(2);
        assertTrue(testDLL.pop()==2);
    }
    @Test
    public void testEnqueue() {
        DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
        testDLL.enqueue(0);
        testDLL.enqueue(1);
        testDLL.enqueue(2);
        assertEquals( "Checking enqueue", "2,1,0", testDLL.toString());
    }
    @Test
    public void testDequeue() {
        DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
        assertTrue(testDLL.dequeue()==null);
        testDLL.enqueue(0);
        assertTrue(testDLL.dequeue()==0);
        testDLL.enqueue(1);
        testDLL.enqueue(2);
        assertTrue(testDLL.dequeue()==1);

        testDLL = new DoublyLinkedList<Integer>();
        String msg = "Testing dequeue on an empty queue";
        Integer result = testDLL.dequeue();
        assertNull(msg + " - should return: null - received: " + result, result);
        assertEquals(msg + " - list should be: [] - received: [" + testDLL.toString() + "]", "", testDLL.toString());

        testDLL.enqueue(100);

        msg = "Testing dequeue on a 1-element queue [" + testDLL.toString() + "]";
        result = testDLL.dequeue();
        assertEquals(msg + " - should return: 100 - received: " + result, (long)100, (long)result);
        assertEquals(msg + " - list should become: [] - received: [" + testDLL.toString() + "]", "", testDLL.toString());

        msg = "Testing dequeue after a enqueue-dequeue";
        result = testDLL.dequeue();
        assertNull(msg + " - should return: null - received: " + result, result);
        assertEquals(msg + " - list should be: [] - received: [" + testDLL.toString() + "]", "", testDLL.toString());

        testDLL.enqueue(100);
        testDLL.enqueue(200);
        testDLL.enqueue(300);
        
        msg = "Testing dequeue on a 3-element queue [" + testDLL.toString() + "]";
        result = testDLL.dequeue();
        assertEquals(msg + " - should return: 100 - received: " + result,(long)100, (long)result);

        msg = "Testing dequeue on a 2-element queue [" + testDLL.toString() + "]";
        result = testDLL.dequeue();
        assertEquals(msg + " - should return: 200 - received: " + result, (long)200, (long)result);
        assertEquals(msg + " - list should be: [300] - received: [" + testDLL.toString() + "]", "300", testDLL.toString());
    }
    // TODO: add more tests here. Each line of code in DoublyLinkedList.java should
    // be executed at least once from at least one test.

}



