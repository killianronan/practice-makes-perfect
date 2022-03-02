import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.NullPointerException;
import java.lang.UnsupportedOperationException;

public class CompetitionFloydWarshall{
    public boolean isValidGraph;
    public int slowest;
    public String file;
    public AdjMatrixEdgeWeightedDigraph graph;
    public FloydWarshall table;
    public double distLimit;

    CompetitionFloydWarshall (String file, int sA, int sB, int sC){
        this.distLimit = 0;
        this.slowest = Math.min(Math.min(sA,sB),sC);
        try{
			this.file = file;
			File input = new File(file);
			Scanner scanner = new Scanner(input);
            this.graph = new AdjMatrixEdgeWeightedDigraph(scanner);
            this.table = new FloydWarshall(this.graph);
        } 
		catch (FileNotFoundException| NullPointerException e){
        	return;
        } 
        if(!this.table.hasNegativeCycle() && this.table != null&&this.graph.V()>2){
        	int v = this.graph.V();
            this.isValidGraph = true;
            for (int i = 0; i < v; i++) {
                for (int j = 0; j < v; j++) {
                    if (table.hasPath(i, j))
                        if(table.dist(i, j)>this.distLimit ) 
                            this.distLimit = table.dist(i, j);  
                }
            }
        }
    }
	public int timeRequiredforCompetition() {
		if(this.file == null||this.distLimit < 1 || this.slowest < 1 )
			return -1;
		int result = (int) Math.ceil((this.distLimit*1000)/this.slowest);
		return result;
	}
}