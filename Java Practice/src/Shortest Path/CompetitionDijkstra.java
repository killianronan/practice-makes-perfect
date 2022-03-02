import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CompetitionDijkstra {
	int sA;
	int sB;
	int sC;
	public double distLimit;
	public String file;
	public boolean isValid;
	public int slowest;
	static EdgeWeightedDigraph graph;

    /**
     * @param file: A file containing the details of the city road network
     * @param sA, sB, sC: speeds for 3 contestants
     * @throws FileNotFoundException 
    */
    CompetitionDijkstra (String file, int sA, int sB, int sC){
    	this.sA = sA;
    	this.sB = sB;
    	this.sC = sC;
		this.distLimit = 0;
		this.slowest = Math.min(Math.min(sA,sB),sC);
		try{
			this.file = file;
			File input = new File(file);
			Scanner scanner = new Scanner(input);
			this.graph = new EdgeWeightedDigraph(scanner);
		} 
		catch (FileNotFoundException| NullPointerException e){
			return;
		}
		if(this.graph != null && this.graph.V()>2){
			this.isValid = true;
			DijkstraSP graph = new DijkstraSP(this.graph, 0);
			for(int vertex=0; vertex<this.graph.V(); vertex++) {
				graph = new DijkstraSP(this.graph, vertex);
				for(int vertex2=0; vertex2<this.graph.V(); vertex2++){
					if(graph.hasPathTo(vertex2))
						if(graph.distTo(vertex2)>this.distLimit) 
							this.distLimit = graph.distTo(vertex2);
				}
			}
		}
    }
    /**
    * @return int: minimum minutes that will pass before the three contestants can meet
     */
	public int timeRequiredforCompetition() {
		if(this.file == null||this.distLimit < 1 || this.slowest < 1||this.slowest<50)
			return -1;
		int result = (int) Math.ceil((this.distLimit*1000)/this.slowest);
		return result;
	}
}
