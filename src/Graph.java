import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph {
    int numVertex;
    int numEdge;
    ArrayList<ArrayList<Integer>> graph;
    ArrayList<Integer> marked;
    ArrayList<int[]> graphEdges;
    int largestCC;
    HashMap<Integer, Integer> setUnionMap;

    public Graph() {
        numVertex = 0;
        numEdge = 0;
        graph = new ArrayList<>();
        largestCC = Integer.MIN_VALUE;
        graphEdges = new ArrayList<>();
    }

    public Graph(int vertexCount) {
        numVertex = vertexCount;
        numEdge = 0;
        graph = new ArrayList<>(numVertex);
        for (int i = 0; i < numVertex; i++) {
            graph.add(new ArrayList<Integer>());
        }
        largestCC = Integer.MIN_VALUE;
        graphEdges = new ArrayList<>();
    }

    public ArrayList<Integer> getNeighbors(int u) {
        return graph.get(u);
    }

    public boolean edgePresent(int u, int v) {

        return (graph.get(u).contains(v));

    }

    public void addEdge(int u, int v) {
        if (u >= 0 && u < numVertex && v >= 0 && v < numVertex) {
            if (!edgePresent(u, v))
                graph.get(u).add(v);

            if (!edgePresent(v, u))
                graph.get(v).add(u);
            numEdge++;
        } else throw new IndexOutOfBoundsException();

    }

    public void dfs(int vertex, boolean[] marked) {
        marked[vertex] = true;
        largestCC += 1;

        for (int i = 0; i < this.getNeighbors(vertex).size(); i++) {
            int newVertex = this.getNeighbors(vertex).get(i);
            if (!marked[newVertex]) {
                dfs(newVertex, marked);
            }
        }
    }

    public void bfs(int vertex, boolean[] marked) {
        Queue<Integer> pq = new ArrayDeque<>();
        marked[vertex] = true;
        largestCC += 1;
        pq.add(vertex);

        while (!pq.isEmpty()) {
            vertex = pq.poll();
            for (int neighbor : this.getNeighbors(vertex)) {
                if (!marked[neighbor]) {
                    marked[neighbor] = true;
                    pq.add(neighbor);
                }
            }
        }
    }

    public void componentStats(String method) {
        boolean[] marked = new boolean[numVertex];
        int count = 0;
        for (int v = 0; v < numVertex; v++) {
            int holder = largestCC;
            if (!marked[v]) {
                largestCC = 0;
                if ((method == "dfs")) {
                    dfs(v, marked);
                } else {
                    bfs(v, marked);
                }
                count++;
                if(holder > largestCC)
                    largestCC = holder;
            }
        }
        System.out.println("total cc -> " + count);
        System.out.println("max cc size -> " + largestCC);
    }

    public void merge(int v, int u) {
        int i = Math.min(v,u);
        int j = Math.max(v,u);

        for (Integer neighbor : this.getNeighbors(j)) {
            setUnionMap.put(neighbor, i);
        }
    }
    public void setUnion() {
        setUnionMap = new HashMap<>();
        for (int i = 0; i < graph.size(); i++) {
            setUnionMap.put(i,i);
        }

        for (int[] edge : graphEdges) {
            if (setUnionMap.get(edge[0]) != setUnionMap.get(edge[1])) {
                merge(edge[0], edge[1]);
            }
        }
    }

    public void setStats() {
        setUnion();
        HashMap<Integer, Integer> occurences = new HashMap<>();
        int max = Integer.MIN_VALUE;
        for (Integer i : setUnionMap.keySet()) {
            if (!occurences.containsKey(i)) {
                occurences.put(i, 1);
            } else {
                occurences.put(i, occurences.get(i) + 1);
                if (occurences.get(i) > max) max = occurences.get(i);
            }
        }

        System.out.println("total cc -> " + occurences.keySet().size());
        System.out.println("max cc size -> " + max);
    }


    public Graph loadGraphFromFile(String filename, String delimiter, int graphSize) {
        File f = new File(filename);
        try {
            Scanner in = new Scanner(f);
            while (in.hasNextLine()) {
                String[] parts = in.nextLine().split(delimiter);
                graphEdges.add((new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])}));
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        Graph g = new Graph(graphSize);
        for (int[] edge : graphEdges) {
            g.addEdge(edge[0], edge[1]);
        }
        return g;
    }

    public static void main(String[] args) {
        Graph testArea = new Graph();
        Graph emailEnron = testArea.loadGraphFromFile("Email-Enron.txt", "\t", 36692);
        //Graph graph = testArea.loadGraphFromFile("Graph.txt", ",", )
        emailEnron.componentStats("dfs");
        emailEnron.componentStats("bfs");
        emailEnron.setStats();

    }

}


