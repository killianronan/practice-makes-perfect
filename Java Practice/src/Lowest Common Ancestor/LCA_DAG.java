
public class LCA_DAG {
	
    public int[] indegree;
    public int[] outdegree;
    public int edge;
    public int vertice;
    public int[] visited;
	public boolean hasCycle;
    public int[][] adj;
       
    public LCA_DAG(int V){
        if(V<0){
            throw new IllegalArgumentException("Number of vertices in the DAG must be greater than 0.");
        }
        else{
            this.vertice = V;
            this.edge = 0;
	        indegree = new int[V];
		    outdegree = new int[V];
		    visited = new int[V];
		    adj = new int[V][V];
	    for(int i = 0; i<V; i++){
		for(int j=0;j<V;j++){
		    adj[i][j] = 0;
		}
	    }
        }
    }
    
    public int vertex(){
        return vertice;
    }
    
    public int edge(){
        return edge;
    }
    
    public void validateVertex(int v){
        if((v<0)||(v>=vertice)){
            throw new IllegalArgumentException("Edgesless than one means no verices are joined, and if there is a cycle it cannot be moved");
        }
    }
    
    public void addEdge(int v, int w){
        validateVertex(v);
		validateVertex(w);
		adj[v][w]=1;
		indegree[w]++;
		outdegree[v]++;
		edge++;
    }
    
    public void removeEdge(int v, int w){
        validateVertex(v);
		validateVertex(w);
		edge--;
		outdegree[v]--;
		indegree[w]--;
		adj[v][w]=0;
    }
    
    public int outdegree(int v){
    	validateVertex(v);
    	return outdegree[v];
    }
    
    public int indegree(int v){
	   	validateVertex(v);
		return indegree[v];
    }
    
    public int[] adj(int v){
    	validateVertex(v);
		int[] temp = new int[outdegree[v]];
		int count =0;
		for(int i=0;i<vertice;i++){
		    if(adj[v][i]==1){
				temp[count]=i;
				count++;
	    	}
		}
		return temp;
    }
    
    public boolean hasCycle(){
		hasCycle=false;
		int count = 0;
		for(int i =0;i<vertice;i++){
			visited[count]=i;
			for(int j = 0; j<vertice;j++){
				for(int k=0;k<vertice;k++){
					if(visited[k]==j && adj[i][j]==1){
						hasCycle=true;
						return hasCycle;
					}
				}	
			}
			count++;
		}
		return hasCycle;
	}
    
    public int findLCA(int v, int w){
		validateVertex(v);
		validateVertex(w);
		hasCycle();
		if(edge>0 && !hasCycle){
			return LCAUtil(v,w);
		}
		else{
			throw new IllegalArgumentException("This graph is not an acyclic graph.");
		}
    }

    public int LCAUtil(int v, int w){
		int[] vArr = new int[edge];
		int[] wArr = new int[edge];
		boolean[] vMarked = new boolean[vertice];
		boolean[] wMarked = new boolean[vertice];
		int vCount =0;
		int wCount = 0;
		vArr[vCount]=v;
		wArr[wCount]=w;
		for(int j=0; j<vertice;j++){
			vMarked[j]=false;
			wMarked[j]=false;
		}
		for(int i =0;i<vertice;i++){
			vMarked[v] =true;
			wMarked[w] =true;
			for(int j = 0; j<vertice;j++){
				if(adj[i][j]==1 && vMarked[i]){
					vCount++;
					vArr[vCount]=j;
					vMarked[j]=true;
				}
				if(adj[i][j]==1 && wMarked[i]){
					wCount++;
					wArr[wCount]=j;
					wMarked[j]=true;
				}
				if(wArr[wCount]==vArr[vCount]){
					return wArr[wCount];
				}
			}
		}
		return -1;
	}  
}
