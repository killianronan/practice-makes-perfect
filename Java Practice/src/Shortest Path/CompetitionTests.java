import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import org.junit.Test;

public class CompetitionTests {

    @Test
    public void testDijkstraConstructor() {
    	CompetitionDijkstra D = new CompetitionDijkstra("tinyEWD.txt", 20, 15, 30);
        assertEquals("Should be 50", 15, D.slowest);
        assertEquals("Should be 8 V",8, D.graph.V());
    }
    @Test
    public void testInputC() {

        CompetitionDijkstra D = new CompetitionDijkstra("input-C.txt", 50,100,100);
        assertEquals("Testing input-C.txt", -1, D.timeRequiredforCompetition());
//        D = new CompetitionDijkstra("input-I.txt", 72,70,65);
//        assertEquals("Testing input-I.txt", 185, D.timeRequiredforCompetition());

    }

    @Test
    public void testInputI() {

        CompetitionDijkstra D = new CompetitionDijkstra("input-I.txt", 60,70,84);
//        CompetitionFloydWarshall F = new CompetitionFloydWarshall("/ShortestPathAlgorithms/input-I.txt", 4,7,1);
//        assertEquals("Testing input-I.txt for Floyd", 10000, F.timeRequiredforCompetition());
        assertEquals("Testing input-I.txt", 200, D.timeRequiredforCompetition());
        D = new CompetitionDijkstra("input-I.txt", 72,70,65);
        assertEquals("Testing input-I.txt", 185, D.timeRequiredforCompetition());

    }
    @Test
    public void testInputK(){
        CompetitionDijkstra D = new CompetitionDijkstra("input-K.txt", 51,70,88);
        CompetitionFloydWarshall F = new CompetitionFloydWarshall("input-K.txt", 51,70,88);
        assertEquals("Testing input-K.txt", 314, F.timeRequiredforCompetition());
        assertEquals("Testing input-K.txt", 314, D.timeRequiredforCompetition());
    }

//input too small
    @Test
    public void testInputA() {
        CompetitionDijkstra D = new CompetitionDijkstra("input-A.txt", 50,50,50);
        CompetitionFloydWarshall F = new CompetitionFloydWarshall("input-A.txt", 60,50,75);
        assertEquals("Testing input-A.txt", -1, F.timeRequiredforCompetition());
        assertEquals("Testing input-A.txt", -1, D.timeRequiredforCompetition());
    }
    @Test
    public void testInputB(){
        CompetitionDijkstra D = new CompetitionDijkstra("input-B.txt", 60,50,75);
//        CompetitionFloydWarshall F = new CompetitionFloydWarshall("input-B.txt", 60,50,75);
//        assertEquals("Testing input-B.txt", -1, F.timeRequiredforCompetition());
        assertEquals("Testing input-B.txt", -1, D.timeRequiredforCompetition());
    }


    @Test
    public void testDijkstraNegSpeed(){
        CompetitionDijkstra D = new CompetitionDijkstra("tinyEWD.txt",-20,2,3);
        assertEquals("Should be -1", -1, D.timeRequiredforCompetition());
    }

    @Test 
    public void testTinyEWD() {
    	CompetitionDijkstra D = new CompetitionDijkstra("tinyEWD.txt", 50, 75, 100);
    	assertEquals("Should be 1.86km", 1.86, D.distLimit, 0.001);
        assertEquals("Should be 38 mins", 38, D.timeRequiredforCompetition());
    	CompetitionFloydWarshall F = new CompetitionFloydWarshall("tinyEWD.txt", 50, 75, 100);
    	assertEquals("Should be 1.86km", 1.86, F.distLimit, 0.001);
        assertEquals("Should be 38 mins", 38, F.timeRequiredforCompetition());
    }
//
    @Test 
    public void testDijk1000EWD(){

    	CompetitionDijkstra D = new CompetitionDijkstra("1000EWD.txt", 50, 75, 100);
    	CompetitionFloydWarshall F = new CompetitionFloydWarshall("1000EWD.txt", 50, 75, 100);

    	assertEquals("Should have 1000", 1000, D.graph.V());
    	assertEquals("Should be 1.39863", 1.39863, D.distLimit, 0.001);
        assertEquals("Should be 28 mins", 28, D.timeRequiredforCompetition());
        assertEquals("Graph should have 1000 vertices", 1000, F.graph.V());
    	assertEquals("Should be 1.39863", 1.39863, F.distLimit, 0.001);
        assertEquals("Should be 28 mins", 28, F.timeRequiredforCompetition());
    }
//
    @Test
    public void testDijkEmptyGraph()  {
    	CompetitionDijkstra D = new CompetitionDijkstra("input-J.txt", 10, 12, 22);
        assertEquals("Should be 0.0km", 0, D.distLimit, 0.001);
        assertEquals("Should be -1 mins", -1, D.timeRequiredforCompetition());
        assertTrue("Should have 0 vertices", D.graph.V()==0);
      	CompetitionFloydWarshall F = new CompetitionFloydWarshall("input-J.txt", 50, 75, 100);
        assertEquals("Should be 0.0km", 0, F.distLimit, 0.001);
        assertEquals("Should be -1 mins", -1, F.timeRequiredforCompetition());
        assertEquals("Should have 0 vertices",0, F.graph.V());
    }

    @Test
    public void testFWConstructor() {
        CompetitionFloydWarshall F = new CompetitionFloydWarshall("tinyEWD.txt", 30, 6,50);
        assertEquals("Should be 6", 6, F.slowest);
    }
  @Test
  public void testFWEmptyEWD() {

  }

}