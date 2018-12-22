package A3;

import java.io.*;
import java.util.*;




public class FordFulkerson {

	
	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> Stack = new ArrayList<Integer>();
		/* YOUR CODE GOES HERE
		//
		//
		//
		//
		//
		//
		//
		*/
	        ArrayList<Integer> Q = new ArrayList<Integer>();
	        Q.add(source);
	        Stack.add(source);
	        while (Q.size() > 0)
	        {
	            Integer v = Q.remove(0);
	            if (v == destination)
	            {
	                break;
	            }
	            Integer u;
	            for (Edge e : graph.listOfEdgesSorted())
	            {
	                if (v == e.nodes[0])
	                {
	                    u = e.nodes[1];
	                    if (!Stack.contains(u) && e.weight != 0)
	                    {
	                        Q.add(u);
	                        Stack.add(u);
	                        break;
	                    }
	                }
	            }
	        }
		return Stack;
	}
	//helper method
	static boolean augPath(ArrayList<Integer> Stack, Integer source, WGraph graph)
    {
        boolean augmentingPath = true;
        if (Stack.contains(graph.getSource()) && Stack.size() == 1)
        {
            augmentingPath = false;
        }
        return augmentingPath;
    }
	
	
	public static void fordfulkerson(Integer source, Integer destination, WGraph graph, String filePath){
		String answer="";
		String myMcGillID = "260706034"; //Please initialize this variable with your McGill ID
		int maxFlow = 0;
		
				/* YOUR CODE GOES HERE
		//
		//
		//
		//
		//
		//
		//
		*/
		WGraph residualGraph = new WGraph(graph);
        for (Edge e : graph.getEdges()){
            e.weight = 0;}

        ArrayList<Integer> paths = pathDFS(source, destination, residualGraph);

        while (augPath(paths, residualGraph.getSource(), residualGraph))
        {
            if (paths.contains(residualGraph.getDestination()))
            {
                int minimum = residualGraph.getEdge(paths.get(0), paths.get(1)).weight;
                for (int i = 0; i < paths.size() - 1; i++)
                {
                  if (residualGraph.getEdge(paths.get(i), paths.get(i + 1)).weight < minimum) /*&& residualGraph.getEdge(paths.get(i), paths.get(i).weight!= 0)))*/
                    {
                        minimum = residualGraph.getEdge(paths.get(i), paths.get(i + 1)).weight;
                    }
                }
                maxFlow += minimum;
                for (int i = 0; i < paths.size() - 1; i++)
                {
                    graph.getEdge(paths.get(i), paths.get(i + 1)).weight += minimum;
                    residualGraph.getEdge(paths.get(i), paths.get(i + 1)).weight -= minimum;
                }
            }

            if (!paths.contains(residualGraph.getDestination()) && augPath(paths, residualGraph.getSource(), residualGraph))
            {

                for (int i = 0; i < paths.size() - 1; i++)
                {
                    residualGraph.getEdge(paths.get(i), paths.get(i + 1)).weight = 0;
                }

            }

            paths = pathDFS(source, destination, residualGraph);
        }

//        System.out.println("\nresidualGraph Graph");
//        System.out.println(residualGraph.toString());

        System.out.println("---------------------------------");
        System.out.println("Resulting Graph\n");
		
		answer += maxFlow + "\n" + graph.toString();	
		writeAnswer(filePath+myMcGillID+".txt",answer);
		System.out.println(answer);
	}
	
	
	public static void writeAnswer(String path, String line){
		BufferedReader br = null;
		File file = new File(path);
		
		try {
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(line+"\n");	
		bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	 public static void main(String[] args){
		 String file = "bf2.txt";
		 File f = new File(file);
		 WGraph g = new WGraph(file);
		 fordfulkerson(g.getSource(),g.getDestination(),g,f.getAbsolutePath().replace(".txt",""));
	 }
}
