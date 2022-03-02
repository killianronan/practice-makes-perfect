import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

//-------------------------------------------------------------------------
/**
 *  Test class for Doubly Linked List
 *
 *  @version 3.1 09/11/15 11:32:15
 *
 *  @author  TODO
 */

@RunWith(JUnit4.class)
public class BSTTest
{
    //~ Constructor ........................................................
    @Test
    public void testConstructor()
    {
      new BST<Integer,Integer>();
    }
    @Test
    public void testIsEmpty() {
		BST<Integer, Integer> bst = new BST<Integer, Integer>();
		assertTrue("Checking isEmpty() on empty BST", bst.isEmpty());
		bst.put(1, 1);
		assertFalse("Checking isEmpty() on non-empty BST", bst.isEmpty());
    }
    @Test
    public void testSize() {
		BST<Integer, Integer> bst = new BST<Integer, Integer>();
		assertEquals("Checking size() on empty BST", 0, bst.size());
		bst.put(1, 1);
		bst.put(2, 1);
		bst.put(3, 1);
		assertEquals("Checking size() on non-empty BST", 3, bst.size());
    }
    @Test
    public void testContains() {
		BST<Integer, Integer> bst = new BST<Integer, Integer>();
		assertFalse("Checking contains() on empty BST", bst.contains(1));
		bst.put(1, 1);
		bst.put(2, 1);
		bst.put(3, 1);
		assertTrue("Checking contains() on non-empty BST", bst.contains(1));
		assertFalse("Checking contains() on non-empty BST", bst.contains(4));
		assertTrue("Checking contains() on non-empty BST", bst.contains(3));
    }
    @Test
    public void testGet() {
     	BST<Integer, Integer> bst = new BST<Integer, Integer>();
    	assertEquals("Checking get() on an empty BST", null, bst.get(1));
    	bst.put(1, 5);
    	assertEquals("Checking get() on 1 element BST", (Integer) 5, bst.get(1));
    	bst.put(4, 3);
    	bst.put(6, 8);
    	assertEquals("Checking get() for an element not in non-empty BST", null, bst.get(10));
    	assertEquals("Checking get() on 3 element BST(left)", (Integer) 3, bst.get(4));
    	assertEquals("Checking get() on 3 element BST(right)", (Integer) 8, bst.get(6));
    }
    @Test
    public void testPut() {
    	BST<Integer, Integer> bst = new BST<Integer, Integer>();
//    	bst.put(4, 8);
//    	bst.put(4, null);
//    	boolean result1 = bst.contains(4);
//    	assertFalse("Testing put(Key, null) on a bst of 1 element, should delete element", result1);
//    	
    	bst = new BST<Integer, Integer>();
    	bst.put(9, 2);
    	assertTrue("Checking put() on empty BST", bst.contains(9));
    	assertEquals("Checking value on empty BST",(Integer) 2, bst.get(9));
    	
    	bst.put(10, 1);
    	assertTrue("Testing put() on a 1 element BST(right)", bst.contains(10));
    	assertEquals("Checking value on 1 element BST",(Integer) 1, bst.get(10));
    	
    	bst.put(3, 7);
    	assertTrue("Testing put() on a 2 element BST(left)", bst.contains(3));
    	assertEquals("Checking value on 2 element BST",(Integer) 7, bst.get(3));    	
    	
    	bst.put(3, 17);
    	assertEquals("Testing put() on 3 element BST(should replace)", (Integer) 17, bst.get(3));
    	
    }
	@Test
	public void testHeight()
	{
		BST<Integer, Integer> bst = new BST<Integer, Integer>();
		assertEquals("Checking height() on empty BST", -1, bst.height());
		bst.put(2, 7);
		bst.put(3, 4);
		bst.put(1, 0);
		assertEquals("Checking height() on 3 element BST", 1, bst.height());
		bst.put(4, 2);
		assertEquals("Checking height() on 4 element BST(right-subtree)", 2, bst.height());
		bst.put(-1, 8);
		bst.put(0, 12);
		assertEquals("Checking height() on 6 element BST(left-subree)", 3, bst.height());
	}
	@Test
    public void testMedian()
    {
    	BST<Integer, Integer> bst = new BST<Integer, Integer>();
    	assertEquals("Checking median() on empty bst", null, bst.median());
		bst.put(8, 9);
		assertEquals("Checking median() on 1 element BST", (Integer) 8, bst.median());
		bst.put(7, 3);
		assertEquals("Checking median() on 2 element BST", (Integer) 7, bst.median());
		bst.put(6,4);
		bst.put(9,4);
		bst.put(1,2);
		bst.put(10,7);
		bst.put(0,7);
		assertEquals("Checking median() on 7 element BST", (Integer) 7,bst.median());
	}
	@Test
    public void testPrintKeysInOrder()
    {
    	BST<Integer, Integer> bst = new BST<Integer, Integer>();
    	assertEquals("Checking printKeysInOrder() on empty BST", "()", bst.printKeysInOrder());
    	bst.put(10,6);
    	assertEquals("Checking printKeysInOrder() on 1 element BST", "(()10())", bst.printKeysInOrder());
    	bst.put(20, 3);
    	assertEquals("Checking printKeysInOrder() on 2 element BST", "(()10(()20()))", bst.printKeysInOrder());
    	bst.put(5, 2);
    	assertEquals("Checking printKeysInOrder() on 3 element BST", "((()5())10(()20()))", bst.printKeysInOrder());
    	bst.put(7, 1);
    	assertEquals("Checking printKeysInOrder() on 4 element BST", "((()5(()7()))10(()20()))", bst.printKeysInOrder());
    }
	 /** <p>Test {@link BST#prettyPrintKeys()}.</p> */
    
	 @Test
	 public void testPrettyPrint() {
	     BST<Integer, Integer> bst = new BST<Integer, Integer>();
	     assertEquals("Checking pretty printing of empty tree",
	             "-null\n", bst.prettyPrintKeys());
	      
	                          //  -7
	                          //   |-3
	                          //   | |-1
	                          //   | | |-null
	     bst.put(7, 7);       //   | |  -2
	     bst.put(8, 8);       //   | |   |-null
	     bst.put(3, 3);       //   | |    -null
	     bst.put(1, 1);       //   |  -6
	     bst.put(2, 2);       //   |   |-4
	     bst.put(6, 6);       //   |   | |-null
	     bst.put(4, 4);       //   |   |  -5
	     bst.put(5, 5);       //   |   |   |-null
	                          //   |   |    -null
	                          //   |    -null
	                          //    -8
	                          //     |-null
	                          //      -null
	     
	     String result = 
	      "-7\n" +
	      " |-3\n" + 
	      " | |-1\n" +
	      " | | |-null\n" + 
	      " | |  -2\n" +
	      " | |   |-null\n" +
	      " | |    -null\n" +
	      " |  -6\n" +
	      " |   |-4\n" +
	      " |   | |-null\n" +
	      " |   |  -5\n" +
	      " |   |   |-null\n" +
	      " |   |    -null\n" +
	      " |    -null\n" +
	      "  -8\n" +
	      "   |-null\n" +
	      "    -null\n";
	     assertEquals("Checking pretty printing of non-empty tree", result, bst.prettyPrintKeys());
	     }

	  
	     /** <p>Test {@link BST#delete(Comparable)}.</p> */
	     @Test
	     public void testDelete() {
	         BST<Integer, Integer> bst = new BST<Integer, Integer>();
	         bst.delete(1);
	         assertEquals("Deleting from empty tree", "()", bst.printKeysInOrder());
	         
	         bst.put(7, 7);   //        _7_
	         bst.put(8, 8);   //      /     \
	         bst.put(3, 3);   //    _3_      8
	         bst.put(1, 1);   //  /     \
	         bst.put(2, 2);   // 1       6
	         bst.put(6, 6);   //  \     /
	         bst.put(4, 4);   //   2   4
	         bst.put(5, 5);   //        \
	                          //         5
	         
	         assertEquals("Checking order of constructed tree",
	                 "(((()1(()2()))3((()4(()5()))6()))7(()8()))", bst.printKeysInOrder());
	         
	         bst.delete(9);
	         assertEquals("Deleting non-existent key",
	                 "(((()1(()2()))3((()4(()5()))6()))7(()8()))", bst.printKeysInOrder());
	 
	         bst.delete(8);
	         assertEquals("Deleting leaf", "(((()1(()2()))3((()4(()5()))6()))7())", bst.printKeysInOrder());
	 
	         bst.delete(6);
	         assertEquals("Deleting node with single child",
	                 "(((()1(()2()))3(()4(()5())))7())", bst.printKeysInOrder());
	 
	         bst.delete(3);
	         assertEquals("Deleting node with two children",
	                 "(((()1())2(()4(()5())))7())", bst.printKeysInOrder());
	     }
	     

}
