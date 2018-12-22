package A3;

import java.util.*;

public class BellmanFord{

	
	/**
	 * Utility class. Don't use.
	 */
	public class BellmanFordException extends Exception{
		private static final long serialVersionUID = -4302041380938489291L;
		public BellmanFordException() {super();}
		public BellmanFordException(String message) {
			super(message);
		}
	}
	
	/**
	 * Custom exception class for BellmanFord algorithm
	 * 
	 * Use this to specify a negative cycle has been found 
	 */
	public class NegativeWeightException extends BellmanFordException{
		private static final long serialVersionUID = -7144618211100573822L;
		public NegativeWeightException() {super();}
		public NegativeWeightException(String message) {
			super(message);
		}
	}
	
	/**
	 * Custom exception class for BellmanFord algorithm
	 *
	 * Use this to specify that a path does not exist
	 */
	public class PathDoesNotExistException extends BellmanFordException{
		private static final long serialVersionUID = 547323414762935276L;
		public PathDoesNotExistException() { super();} 
		public PathDoesNotExistException(String message) {
			super(message);
		}
	}
	
    private int[] distances = null;
    private int[] predecessors = null;
    private int source;

    BellmanFord(WGraph g, int source) throws BellmanFordException{
        /* Constructor, input a graph and a source
         * Computes the Bellman Ford algorithm to populate the
         * attributes 
         *  distances - at position "n" the distance of node "n" to the source is kept
         *  predecessors - at position "n" the predecessor of node "n" on the path
         *                 to the source is kept
         *  source - the source node
         *
         *  If the node is not reachable from the source, the
         *  distance value must be Integer.MAX_VALUE
         *  
         *  When throwing an exception, choose an appropriate one from the ones given above
         */
        
        /* YOUR CODE GOES HERE */
    	int numberofNodes = g.getNbNodes();     
        distances = new int [numberofNodes]; 
        predecessors  = new int [numberofNodes];
       
       
         ArrayList<Edge> listofEdges = g.getEdges();
         for (int i = 0; i < numberofNodes; i++){

             distances[i]=Integer.MAX_VALUE;
             predecessors[i]= -1; 
         }   
        
      
         distances[source] = 0; 
         int i, j;
         

         for (i = 0; i<numberofNodes-1; ++i){
             for (j= 0; j<listofEdges.size(); j++){ 
                 
                 int u = g.getEdges().get(j).nodes[0];
                 int v = g.getEdges().get(j).nodes[1];
                 int weight = g.getEdges().get(j).weight;
                 
                 if (distances[u] + weight < distances[v]){
                 distances[v] = distances[u] + weight;
                 predecessors[v] = u;
               }
             }
         }
      
       
         for (j= 0; j<listofEdges.size(); j++){ 
               int u = g.getEdges().get(j).nodes[0];
                 int v = g.getEdges().get(j).nodes[1];
                 int weight = g.getEdges().get(j).weight;
                 if (distances[u] + weight  < distances[v]) {
                     try {
						throw new Exception("Negative cycle Found!");
					} catch (Exception e) {
						e.printStackTrace();
					}

                 
                 }
                 
     
             }
    }

    public int[] shortestPath(int destination) throws BellmanFordException{
        /*Returns the list of nodes along the shortest path from 
         * the object source to the input destination
         * If not path exists an Exception is thrown
         * Choose appropriate Exception from the ones given 
         */

        /* YOUR CODE GOES HERE (update the return statement as well!) */
        
    	ArrayList<Integer> reversePath = new ArrayList<Integer>();
        reversePath.add(destination);
        int predecessor = this.predecessors[destination];
        int [] zero = {0};
        int counter = 0;
        while ( predecessor != source) {
            reversePath.add(predecessor);
            
            if(predecessor == -1) {
                try {
					throw new Exception("There is no path");
				} catch (Exception e) {
					e.printStackTrace();
				}
               return zero;
            }
            predecessor = this.predecessors[predecessor];
        }
        
        reversePath.add(predecessor);
        int [] shortPathestPath = new int [reversePath.size()];
        int j = 0;
        
        for (int i = reversePath.size()-1; i>=0; i--){

          shortPathestPath[j] = reversePath.get(i);
          j++;

        }

       
    return shortPathestPath;
       
    }

    public void printPath(int destination){
        /*Print the path in the format s->n1->n2->destination
         *if the path exists, else catch the Error and 
         *prints it
         */
        try {
            int[] path = this.shortestPath(destination);
            for (int i = 0; i < path.length; i++){
                int next = path[i];
                if (next == destination){
                    System.out.println(destination);
                }
                else {
                    System.out.print(next + "-->");
                }
            }
        }
        catch (BellmanFordException e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){

        String file = "bf2.txt";
        WGraph g = new WGraph(file);
        try{
            BellmanFord bf = new BellmanFord(g, g.getSource());
            bf.printPath(g.getDestination());
        }
        catch (BellmanFordException e){
            System.out.println(e);
        }

   } 
}
