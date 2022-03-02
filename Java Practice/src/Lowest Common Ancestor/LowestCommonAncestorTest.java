import static org.junit.Assert.*;

import org.junit.Test;

public class LowestCommonAncestorTest {

	@Test
	public void findLCATest() {
		LowestCommonAncestor tree = new LowestCommonAncestor(); 
		tree.root = new Node(1); 
		tree.root.left = new Node(2); 
		tree.root.right = new Node(3); 
		tree.root.left.left = new Node(4); 
		tree.root.left.right = new Node(5); 
		tree.root.right.left = new Node(6); 
		tree.root.right.right = new Node(7); 
        assertEquals("Testing LCA(4, 5)", 2, tree.findLCA(4,5));
        assertEquals("Testing LCA(4, 6)", 1, tree.findLCA(4,6));
        assertEquals("Testing LCA(4, 6)", 1, tree.findLCA(3,4));
        assertEquals("Testing LCA(4, 6)", 2, tree.findLCA(2,4));
	}
	@Test
	public void findLCAInvalidInputTest() {
		LowestCommonAncestor tree = new LowestCommonAncestor(); 
		tree.root = new Node(1); 
		tree.root.left = new Node(2); 
		tree.root.right = new Node(3); 
		tree.root.left.left = new Node(4); 
		tree.root.left.right = new Node(5); 
		tree.root.right.left = new Node(6); 
		tree.root.right.right = new Node(7); 
        assertEquals("Testing LCA(4, 9)", -1, tree.findLCA(4,9));
        assertEquals("Testing LCA(-1, 2)", -1, tree.findLCA(-1,2));
	}
	@Test
	public void findLCANullRootTest() {
		LowestCommonAncestor tree = new LowestCommonAncestor(); 
		tree.root = new Node(1); 
		tree.root.left = new Node(2); 
		tree.root.right = new Node(3); 
		tree.root.left.left = new Node(4); 
		tree.root.left.right = new Node(5); 
		tree.root.right.left = new Node(6); 
		tree.root.right.right = new Node(7); 
		tree.root = null; 
        assertEquals("Testing LCA(9, 4) with null root", -1, tree.findLCA(9,4));
	}
}
