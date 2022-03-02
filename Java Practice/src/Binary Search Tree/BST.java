/*************************************************************************
 *  Binary Search Tree class.
 *  Adapted from Sedgewick and Wayne.
 *
 *  @version 3.0 1/11/15 16:49:42
 *
 *  @author TODO
 *
 *************************************************************************/

import static org.junit.Assert.assertEquals;

import java.util.NoSuchElementException;

public class BST<Key extends Comparable<Key>, Value> {
    private Node root;             // root of BST

    /**
     * Private node class.
     */
    private class Node {
        private Key key;           // sorted by key
        private Value val;         // associated data
        private Node left, right;  // left and right subtrees
        private int N;             // number of nodes in subtree

        public Node(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }

    // is the symbol table empty?
    public boolean isEmpty() { return size() == 0; }

    // return number of key-value pairs in BST
    public int size() { return size(root); }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }

    /**
     *  Search BST for given key.
     *  Does there exist a key-value pair with given key?
     *
     *  @param key the search key
     *  @return true if key is found and false otherwise
     */
    public boolean contains(Key key) {
        return get(key) != null;
    }

    /**
     *  Search BST for given key.
     *  What is the value associated with given key?
     *
     *  @param key the search key
     *  @return value associated with the given key if found, or null if no such key exists.
     */
    public Value get(Key key) { return get(root, key); }

    private Value get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else              return x.val;
    }

    /**
     *  Insert key-value pair into BST.
     *  If key already exists, update with new value.
     *
     *  @param key the key to insert
     *  @param val the value associated with key
     */
    public void put(Key key, Value val) {
        if (val == null) { delete(key); return; }
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = put(x.left,  key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else              x.val   = val;
        x.N = 1 + size(x.left) + size(x.right);
        return x;
    }

    /**
     * Tree height.
     *
     * Asymptotic worst-case running time using Theta notation: TODO
     *
     * @return the number of links from the root to the deepest leaf.
     *
     * Example 1: for an empty tree this should return -1.
     * Example 2: for a tree with only one node it should return 0.
     * Example 3: for the following tree it should return 2.
     *   B
     *  / \
     * A   C
     *      \
     *       D
     */
    public int height() {
    	if(isEmpty()){
        	return -1;
    	}
    	else {
    		return height(root);
    	}
    }

    /**
     * returns height of node.
     * @param x node to find height of
     * 
     * @return height the height of node x
     */
    public int height(Node x){
    	Node tmp = x;
    	int leftHeight = 0;
    	int rightHeight = 0;
    	if(x == null){
        	return -1;
    	}
    	else {
    		 leftHeight = height(tmp.left);  
    	     rightHeight = height(tmp.right); 
    	     if(leftHeight>rightHeight) {
    	    	 return leftHeight+1;
    	     }
    	     else {
    	    	 return rightHeight+1;
    	     }
    	}
    }	
    /**
     * Median key.
     * If the tree has N keys k1 < k2 < k3 < ... < kN, then their median key 
     * is the element at position (N-1)/2 (where "/" here is integer division)
     * Note that the median is always a key, not the average of keys. 
     * If there is an even number of keys. 
     * For example if the keys in the tree are the A, C, U, W then the median is the key at position (4-1)/2=1, which is key C -- position numbers start at zero.
     * This operation needs to run in worst-case 0(h) time, where h is the height of the tree.
     * Hint: To implement a 0(h) median method you need to study and understand the rank and select methods from the lectures  and the book.
     *
     * @return the median key, or null if the tree is empty.
     * Theta(h) runtime.
     */
	public Key median() {
	    if (isEmpty()) return null;  
	    else return median(root);
	}
	private Key median (Node x) {
	    if (isEmpty()) return null;
	    int median = (size()-1)/2;
	    return getKey(median);
	}
	public Key getKey(int x) { 
		return getKey(root, x); 
	}
	private Key getKey(Node x, int pos) {
		if (x == null) return null;
		int rank = size(x.left);
		if(rank > pos) return getKey(x.left, pos);			    //if smaller than current node
		else if(rank < pos) return getKey(x.right, pos-rank--);	//if greater than current node
		else return x.key;
	}
    /**
     * Print all keys of the tree in a sequence, in-order.
     * That is, for each node, the keys in the left subtree should appear before the key in the node.
     * Also, for each node, the keys in the right subtree should appear before the key in the node.
     * For each subtree, its keys should appear within a parenthesis.
     *
     * Example 1: Empty tree -- output: "()"
     * Example 2: Tree containing only "A" -- output: "(()A())"
     * Example 3: Tree:
     *   B
     *  / \
     * A   C
     *      \
     *       D
     *
     * output: "((()A())B(()C(()D())))"
     *
     * output of example in the assignment: (((()A(()C()))E((()H(()M()))R()))S(()X()))
     *
     * @return a String with all keys in the tree, in order, parenthesized.
     */
    public String printKeysInOrder() {
        if (isEmpty())  {
        	return "()";
        }
        else { 
        	return printKeysInOrder(root); //print from root
        }
    }
    public String printKeysInOrder(Node node) {
    	String result = "";
    	if(node==null)
    		return "()";
    	//Iterate recursively through subtrees + print parentheses
    	else {
    		result += "(" + printKeysInOrder(node.left) + node.key + printKeysInOrder(node.right) + ")";
    	}
    	return result;
    }
    
    /**
     * Pretty Printing the tree. Each node is on one line -- see assignment for details.
     *
     * @return a multi-line string with the pretty ascii picture of the tree.
     */
    public String prettyPrintKeys() {
   	 return prettyPrintKeys(root, "");
   }
   
   private String prettyPrintKeys(Node node, String prefix) {
   	String resultString = "";
   	
   	if(node == null) 
   		return (prefix + "-null\n");
   	
   	else {
   		//Print first value of tree/subtree and pretty characters (-)
   		resultString += prefix + "-" + node.val + "\n";
   		resultString += prettyPrintKeys(node.left, (prefix+" |"));   		//Recursively print left subtree and pretty characters ( |)
   		resultString += prettyPrintKeys(node.right, (prefix+"  "));			//Recursively print right subtree and pretty characters
   	}
   	
   	return resultString;
   }

    /**
     * Deteles a key from a tree (if the key is in the tree).
     * Note that this method works symmetrically from the Hibbard deletion:
     * If the node to be deleted has two child nodes, then it needs to be
     * replaced with its predecessor (not its successor) node.
     *
     * @param key the key to delete
     */
    public void delete(Key key) {
      //TODO fill in the correct implementation.
    }
    public static void main(String[] args) {
    	BST<Integer, Integer> bst = new BST<Integer, Integer>();    	
    	bst.put(5, 1);
    	Integer result2 = bst.median();

    }
}
