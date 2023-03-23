/** Starter code for SP5
 *  @author Chetan Siddappareddy  (CXS210033)
 *  @author Hari Alekya  (AxH210042)
 */



 package LP2;

 import LP2.Graph.Edge;
 import LP2.Graph.Factory;
 import LP2.Graph.Vertex;
 
 import java.io.File;
 import java.util.*;
 
 public class DFS extends Graph.GraphAlgorithm<DFS.DFSVertex> {
     int completed = 2;
     int ongoing = 1;
     int notVisited = 0;
     List<Set<Vertex>> result;
 
 
     public static class DFSVertex implements Factory {
         int cno;
 
         public DFSVertex(Vertex u) {
         }
 
         public DFSVertex make(Vertex u) {
             return new DFSVertex(u);
         }
     }
 
     public DFS(Graph g) {
         super(g, new DFSVertex(null));
     }
 
     public static DFS depthFirstSearch(Graph g) {
         return null;
     }
 
     public static DFS stronglyConnectedComponents(Graph g){
         DFS d = new DFS(g);

         List<Vertex> topOrdering = d.topologicalOrder1();
         List<Vertex> visited = new ArrayList<Vertex>();
         List<Set<Vertex>> result =new ArrayList<Set<Vertex>>();
 
         g.reverseGraph();
 
         while(topOrdering.size()>0){
             Vertex topvertex = topOrdering.remove(0);

             if (visited.contains(topvertex)){
                 continue;
             }

             Set<Vertex> ConComponent = new HashSet<Vertex>();
             d.dfs(topvertex,visited,ConComponent);
             result.add(ConComponent);
 
         }
         d.result=result;
         return d;
     }
 
     public void dfs(Vertex top_vertex,List<Vertex> visited,Set<Vertex> ConComponent){
         visited.add(top_vertex);
         Graph.AdjList adj = g.adj(top_vertex.getIndex());
         for (Edge e : adj.outEdges) {
             if(visited.contains(e.from)){
                 continue;
             }
             dfs(e.from,visited,ConComponent);
         }
         ConComponent.add(top_vertex);
     }
 
     // Member function to find topological order
     public List<Vertex> topologicalOrder1() {
 
         int[] CurrentStatus = new int[g.n];
         Stack<Vertex> stack = new Stack<Vertex>();
         List<Vertex> result = new ArrayList<Vertex>();
 
         // topological Sort starts from here
         for (int id = 0; id < g.n; id += 1) {
 
             if (CurrentStatus[id] == notVisited) {
                 topologicalSort(id, CurrentStatus, stack);
             }
         }
         while (!stack.empty()) {
             result.add(stack.pop());
         }
 
         return result;
     }
 
 
     public void topologicalSort(int id, int[] status, Stack<Vertex> stack) {
         status[id] = ongoing;
         Graph.AdjList adj = g.adj(id);
         for (Edge e : adj.outEdges) {
 
             if(status[e.to.getIndex()] == notVisited){
                 topologicalSort(e.to.getIndex(), status, stack);
             }
         }
 
         stack.add(g.getVertex(id + 1));
         status[id] = completed;
     }
 
     // Find the number of connected components of the graph g by running dfs.
     // Enter the component number of each vertex u in u.cno.
     // Note that the graph g is available as a class field via GraphAlgorithm.
     public int connectedComponents() {
         return 0;
     }
 
     // After running the connected components algorithm, the component no of each
     // vertex can be queried.
     public int cno(Vertex u) {
         return get(u).cno;
     }
 
     // Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
     public static List<Vertex> topologicalOrder1(Graph g) {
         DFS d = new DFS(g);
         return d.topologicalOrder1();
     }
 
     // Find topological oder of a DAG using the second algorithm. Returns null if g
     // is not a DAG.
     public static List<Vertex> topologicalOrder2(Graph g) {
         return null;
     }
 
     public static void main(String[] args) throws Exception {
         String string = "7 7   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 6 7   6 1 1";
 
 
         Scanner in;
         // If there is a command line argument, use it as file from which
         // input is read, otherwise use input from string.
         in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);
         // Read graph from input
         Graph g = Graph.readGraph(in, true);
         g.printGraph(false);
 
         DFS dfs = stronglyConnectedComponents(g);
         System.out.println(dfs.result);
     }
 }