import java.util.*;

public class Planets2 {

    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        int numTestCases = in.nextInt();

        // loop for test cases
        for (int t = 0; t < numTestCases; t++) {
            int numPlanets = in.nextInt();
            Graph planets = new Graph(numPlanets);

            // read in planets and their coordinates
            for (int i = 0; i < numPlanets; i++) {
                Node p = new Node(in.next(), in.nextInt(), in.nextInt(), in.nextInt());
                planets.addNode(p);
            }

            Dijkstra map = new Dijkstra(planets);
            int numWormholes = in.nextInt();

            // add wormholes to graph with edge weights of 0
            for (int i = 0; i < numWormholes; i++) {
                Node p1 = getPlanet(in.next(), planets.nodes);
                Node p2 = getPlanet(in.next(), planets.nodes);
                planets.unidirectionalAddEdgeWithWeights(p1, p2, 0);
            }

            int numQueries = in.nextInt();
            // loop through queries for which to return the shortest distance

            for (int i = 0; i < numQueries; i++) {
                String source = in.next();
                String destination = in.next();
                Node p1 = getPlanet(source, planets.nodes);
                Node p2 = getPlanet(destination, planets.nodes);

                // use dijkstra's in here to determine shortest distance between chosen planets

                System.out.println("The distance from " + source + " to " + destination + " is " + map.dijkstra(p1, p2) + " parsecs.");
            }
        }
    }

    public static int distance(Node n1, Node n2) {
        double deltaX = Math.abs(n1.x - n2.x);
        double deltaY = Math.abs(n1.y - n2.y);
        double deltaZ = Math.abs(n1.z - n2.z);
        return (int)Math.floor(Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ));
    }

    public static void setEuclideanDistance(Node p1, Node p2, Graph g) {
        int distance = distance(p1, p2);

        // add edges in both directions if the calculated distance is less than
//        if (distance < g.weightsList.get(p1).get(p2) && g.weightsList.get(p1).get(p2) != 0) {

        // if there currently isn't a wormhole between these points, set the distance in both directions
        if (g.weightsList.get(p1).get(p2) != 0) {
            g.weightsList.get(p1).put(p2, distance);
            g.weightsList.get(p2).put(p1, distance);
        }
    }

    public static Node getPlanet(String a, ArrayList<Node> planets) {
        Node p1 = null;
        for (Node planet : planets) {
            if (planet.name.equals(a)) p1 = planet;
        }
        return p1;
    }
}

class Dijkstra {
    Graph tarGraph;
    int numberOfVertices;
    int[] lastParents;

    public Dijkstra(Graph tarGraph) {
        this.tarGraph = tarGraph;
        this.numberOfVertices = tarGraph.adjList.size();
        this.lastParents = new int[numberOfVertices];
    }

    //Dijkstra's implemented from https://www.geeksforgeeks.org/printing-paths-dijkstras-shortest-path-algorithm/
    public long dijkstra(Node start, Node end) {
        //Standard Dijkstra's beginning.
        //d is the distances from start to node n
        int[] d = new int[numberOfVertices];

        //parents is similar to union find, essentially points to who the parent of a certain node is.
        int[] parents = new int[numberOfVertices];

        //Visited is standard.
        boolean[] visited = new boolean[numberOfVertices];
        Arrays.fill(d, Integer.MAX_VALUE);

        d[start.id] = 0;

        // Graph - weightsList = Map<Node, Map<Node, Integer>>
        // Node - neighborWeights = HashMap<Node, Integer>

        for(int i = 0; i < numberOfVertices; i++) {
            int u = minDistance(d, visited);
            Node nodeU = tarGraph.adjList.get(u).get(u);
            visited[u] = true;

            //Does Dijkstra's as it normally would.
            for(int v = 0; v < numberOfVertices; v++) {
                Node nodeV = tarGraph.adjList.get(v).get(v);
                int currentEdge = tarGraph.weightsList.get(nodeU).get(nodeV);
                if(!visited[v] && currentEdge != 0 &&
                        d[u] != Integer.MAX_VALUE && d[u] + currentEdge < d[v]) {
                    d[v] = d[u] + currentEdge;
                    parents[v] = u;

                }
            }
        }
        //Assigns parents to lastParents so we can access it outisde the function.
        lastParents = parents;
        return d[end.id];
    }

    public int minDistance(int[] d, boolean[] visited) {
        long min = Integer.MAX_VALUE;
        int min_index = 0;

        for(int v = 0; v < numberOfVertices; v++)
            if(!visited[v] && d[v] <= min){
                min = d[v];
                min_index = v;
            }
        return min_index;
    }

    //This would be used if the nodes each had a value. (Think of Counting Gold)
    //Specific use case. Implement when necessary.
    /*
    public long getDistance(Node s, Node n, int[] p) {
        if(n.getID() == s.getID()) {
            return nodes.get(s.id).kilos;
        }
        return nodes.get(n).value + getKilos(p[n], p);
    }*/
}

class Node {
    public String name;
    public int x, y, z, id;
    public static int count = 0;
    public boolean visited = false;

    public Node(String name, int x, int y, int z) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.id = count++;
    }

    public String toString() {
        return name;
    }
}

class Graph {
    public ArrayList<ArrayList<Node>> adjList;
    public Map<Node, Map<Node, Integer>> weightsList;
    public Map<Node, Node> unidirectional;
    public ArrayList<Node> nodes;
    public int numOfNodes;

    public Graph(int numOfNodes) {
        this.numOfNodes = numOfNodes;
        nodes = new ArrayList<>();
        weightsList = new HashMap<>();
        adjList = new ArrayList<>();
        for(int i = 0; i < numOfNodes; i++)
            adjList.add(new ArrayList<Node>());
        unidirectional = new HashMap<>();
    }

    void addNode(Node n) {
        nodes.add(n);
    }

    void removeNode(Node n) {
        nodes.remove(n);
    }

    void unidirectionalAddEdgeWithWeights(Node u, Node v, int weight) {
        weightsList.get(u).putIfAbsent(v, weight);
    }

    void addEdge(Node u, Node v, Integer weight) {
        //This is for an undirected graph with weights.
        weightsList.get(u).putIfAbsent(v, weight);
        weightsList.get(v).putIfAbsent(u, weight);
    }

    void addEdge(Node u, Node v) {
        //This is for an undirected graph with no weights.
        adjList.get(u.id).add(v);
        adjList.get(v.id).add(u);
    }

    void removeEdge(int u, int v) {
        //This is for an undirected graph.
        adjList.get(u).remove(v);
        adjList.get(v).remove(u);
    }

    // actual checker to return results of DFS
    boolean isCycle() {
        boolean[] visited = new boolean[numOfNodes];
        for (int i = 0; i < numOfNodes; i++) {
            if (!visited[i]) {
                if (hasCycle(i, visited, -1))
                    return true;
            }
        }
        return false;
    }

    // DFS to progress through graph, accounts for self loops, connected graphs, and disconnected graphs
    boolean hasCycle(int current, boolean[] visited, int parent) {
        visited[current] = true;
        for (int i = 0; i < adjList.get(current).size(); i++) {
            int vertex = adjList.get(current).get(i).id;
            if (vertex != parent)
                if (visited[vertex]) return true;
                else if (hasCycle(vertex, visited, current)) return true;
        }
        return false;
    }

    boolean isConnected() {
        Stack<Node> stack = new Stack<>();
        boolean[] visited = new boolean[adjList.size()];
        stack.push(adjList.get(0).get(0));

        while(!stack.isEmpty()) {
            Node current = stack.pop();
            visited[current.id] = true;
            for(Node neighbor : adjList.get(current.id)) {
                if(!visited[neighbor.id]) {
                    stack.push(neighbor);
                }
            }
        }

        for (boolean b : visited) {
            if (!b)
                return false;
        }
        return true;
    }

    boolean isConnected(Node source, Node destination) {
        Stack<Node> stack = new Stack<>();
        stack.push(source);
        ArrayList<Node> visited = new ArrayList<>();
        visited.add(source);

        while(!stack.isEmpty()) {
            Node current = stack.pop();
            current.visited = true;
            visited.add(current);
            for(Node neighbor : unidirectional.values()) {
                visited.add(neighbor);
                if(!neighbor.visited) {
                    stack.push(neighbor);
                }
            }
        }
        return visited.contains(destination);
    }

//    /**
//     * simple graph BFS that uses a visited array to avoid processing a node more than once
//     * @param s starting node for the BFS
//     * @param N number of nodes in graph to search
//     */
//    private void BFS(int s) {
//        Queue<Integer> q = new LinkedList<>();
//        boolean[] visited = new boolean[numOfNodes];
//        q.add(s);
//        visited[s] = true;
//
//        while (!q.isEmpty()) {
//            int next = q.poll();
//            System.out.println(next);
//            if(!adjList.get(next).isEmpty()) {
//                for(Node neighbor : adjList.get(next)) {
//                    if(!visited[neighbor.id]) {
//                        visited[neighbor.id] = true;
//                        q.add(neighbor.id);
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * simple graph DFS using a Stack instead of recursion
//     * @param s starting node's index for the DFS
//     */
//    private void DFS(int s) {
//        Stack<Integer> stack = new Stack<>();
//        boolean[] visited = new boolean[adjList.size()];
//        stack.push(s);
//        while(!stack.isEmpty()) {
//            int current = stack.pop();
//            System.out.println(current);
//            visited[current] = true;
//            for(Node neighbor : adjList.get(current)) {
//                if(!visited[neighbor.id]) {
//                    stack.push(neighbor.id);
//                }
//            }
//        }
//    }
}