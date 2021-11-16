
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadAndTest {

    Graph graph;
    ArrayList<int[]> vertices;
    ArrayList<int[]> marked;

    void createGraph() {
        this.graph = new Graph();
        try {
            Scanner in = new Scanner(new File("Graph.txt"));
            while (in.hasNextLine()) {
                String current = in.nextLine();
                String[] parts = current.split(",");
                int[] vertex = {Integer.parseInt(parts[0]),Integer.parseInt(parts[1])};
                System.out.println(vertex[0] + "," + vertex[1]);
                graph.addEdge(vertex[0], vertex[1]);
                vertices.add(vertex);
            }
        } catch (Exception e) {
            System.out.println("Failed");
        }
    }

    public static void main(String[] args) {
        LoadAndTest loadAndTest = new LoadAndTest();
        loadAndTest.createGraph();
        System.out.println(loadAndTest.graph.graph.size());
    }
//
//    void traverse (Graph g) {
//        for (int[] vertex : vertices) {
//
//            if (!marked.contains(vertex)) {
//                dfs(vertex);
//            }
//        }
//    }
//
//    void dfs(int[] vertex) {
//        marked.add(vertex);
//        for (int u : graph.getNeighbors(vertex[0])) {
//            if (x is unmarked {
//                dfs (x);
//            }
//        }
//    }



}
