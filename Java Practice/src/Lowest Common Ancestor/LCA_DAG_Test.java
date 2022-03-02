import static org.junit.Assert.*;
import org.junit.Test;

public class LCA_DAG_Test {
	LCA_DAG dagGraph = new LCA_DAG(5);
	public void createDAGGraph(){
		dagGraph.addEdge(0, 1);
		dagGraph.addEdge(0, 2);
		dagGraph.addEdge(0, 3);
		dagGraph.addEdge(0, 4);
		dagGraph.addEdge(1, 3);
		dagGraph.addEdge(1, 4);
		dagGraph.addEdge(2, 3);
		dagGraph.addEdge(2, 4);
		dagGraph.addEdge(3, 4);
	}
	public void createDAGGraph2(){
		dagGraph.addEdge(0, 1);
		dagGraph.addEdge(0, 2);
		dagGraph.addEdge(0, 3);
		dagGraph.addEdge(0, 4);
		dagGraph.addEdge(1, 0);
		dagGraph.addEdge(1, 3);
		dagGraph.addEdge(2, 3);
		dagGraph.addEdge(2, 4);
		dagGraph.addEdge(3, 4);
	}
	@Test
	public void testOutdegree(){
		createDAGGraph();
		assertEquals("root node test", 4, dagGraph.outdegree(0));
		assertEquals("final node", 0, dagGraph.outdegree(4));	
	}
	@Test
	public void testIndegree(){
		createDAGGraph();
		assertEquals("root node test", 0, dagGraph.indegree(0));
		assertEquals("vertex 1", 1, dagGraph.indegree(1));
	}
	@Test
	public void testAdj(){
		createDAGGraph();
		assertArrayEquals(new int[]{1,2,3,4}, dagGraph.adj(0));
		assertArrayEquals(new int[]{3,4}, dagGraph.adj(1));
		assertArrayEquals(new int[]{3,4}, dagGraph.adj(2));
		assertArrayEquals(new int[]{4}, dagGraph.adj(3));
		assertArrayEquals(new int[] {}, dagGraph.adj(4));

	}
	@Test
	public void testVertex(){
		createDAGGraph();
		assertEquals("vertex count", 5, dagGraph.vertex());
	}
	@Test
	public void testEdge(){
		createDAGGraph();
		assertEquals("edges count", 9, dagGraph.edge());
	}
	@Test(expected = IllegalArgumentException.class)
	public void testValidateVertex(){
		createDAGGraph();
		dagGraph.addEdge(0, 1);
		dagGraph.validateVertex(0);
		dagGraph.validateVertex(-1);
	}
	@Test
	public void testAddEdge(){
		createDAGGraph();
		assertEquals("LCA", 4, dagGraph.findLCA(0, 4));
		dagGraph.addEdge(1, 2);
		assertEquals("Should add an edge between b and c", 10, dagGraph.edge());
		dagGraph.addEdge(1, 4);
		assertEquals("Should add an edge between b and e", 11, dagGraph.edge());
		dagGraph.addEdge(4, 1);
		assertEquals("Adding edge back", 12, dagGraph.edge());
	}
	@Test
	public void testRemoveEdge(){
		createDAGGraph();
		dagGraph.removeEdge(1, 2);
		assertEquals("should be one less edge", 8, dagGraph.edge());
	}
	@Test
	public void testHasCycle(){
		createDAGGraph();
		assertFalse(dagGraph.hasCycle());
		dagGraph.addEdge(1, 0);
		assertTrue(dagGraph.hasCycle());
	}
	@Test
	public void testLCA(){
		createDAGGraph();
		assertEquals("connected", 1, dagGraph.findLCA(0, 1));
	}
	@Test
	public void testLCA2(){
		createDAGGraph();
		assertEquals("connected", 4, dagGraph.findLCA(1, 4));
	}
	@Test(expected = IllegalArgumentException.class) 
	public void testLCAWithCycle(){
		createDAGGraph2();
		assertEquals("connected", 4, dagGraph.findLCA(2, 4));
	}
}