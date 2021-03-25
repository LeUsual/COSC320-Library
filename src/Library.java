package LabExam;

import java.util.*;

//Created by Dakota Joiner, Krishan Hewitt, Keegan Pereira
//COSC320 - Algorithms
//This is meant for his lab exam.

public class Algorithms {
    static class Node implements Comparator<Node> {
        public static int count = 0;
        public int value;
        public String name;
        public int id;
        public boolean isVisited = false;

        public Node(String name, int value) {
            this.id = count;
            count++;
            this.name = name;
            this.value = value;
        }

        @Override
        public int compare(Node o1, Node o2) {
            return Integer.compare(o1.value, o2.value);
        }

//        @Override
//        public boolean equals(Object o) {
//            if(o == this) {
//                return true;
//            }
//
//            if(!(o instanceof Node)) {
//                return false;
//            }
//
//            Node x = (Node) o;
//
//            return Integer.compare(value, x.value);
//
//        }
    }

    static class Kruskals {
        /**
         * Joins two disjoint sets based on their set representative (who's the ultimate parent of the set).
         * Find the parents of both i and j, then join those two sets together so they have the same set representative.
         * @param m map containing the two sets to join
         * @param i member of set i to be joined
         * @param j member of set j to be joined
         */
        public static void union(Map<Integer, Integer> m, int i, int j) {
            int parentI = find(m, i);
            int parentJ = find(m, j);
            m.put(parentI, parentJ);
        }

        /**
         * Finds the set representative of member i. While a member does not point to itself (therefore not the set
         * representative), assign its parent as the the new current and repeat.
         * @param m map containing elements i in the set for which to find the set representative
         * @param i member i for which to find the set representative
         * @return set representative of the set of which i is a member
         */
        public static int find(Map<Integer, Integer> m, int i) {
            int curr = i;
            while (m.get(curr) != curr)
                curr = m.get(curr);
            return curr;
        }

        /**
         * Kruskal's algorithm. A priority queue is generated that is sorted upon edge weights. All elements are assigned
         * to be their own parent, then union-find is run on the priority queue. Note that no more than n - 1 edges are
         * necessary to create the minimum spanning tree for a graph of n nodes.
         * @param gNodes number of nodes in the graph
         * @param source source node
         * @param destination destination node
         * @param weight edge weight between source and destination
         * @return the magnitude of the weight of a minimum spanning tree
         */
        public static int[][] kruskals(int gNodes, List<Integer> source, List<Integer> destination, List<Integer> weight) {
            // priority queue sorted on edge weights
            PriorityQueue<int[]> pq = new PriorityQueue<>((x,y)->x[2]-y[2]);
            HashMap<Integer, Integer> UFMap = new HashMap<>();

            // add all of the edges to the priority queue
            for (int i = 0; i < source.size(); i++) {
                pq.add(new int[] {source.get(i), destination.get(i), weight.get(i)});
            }

            // assign everyone to be their own parent to start
            for (int i = 1; i <= gNodes; i++) {
                UFMap.put(i, i);
            }

            // run Union-Find on priority queue
            int currentNumEdges = 0;
            int minWeight = 0;
            int[][] path = new int[gNodes - 1][3];
            while (pq.size() > 0 && currentNumEdges < gNodes - 1) {
                int[] currentEdge = pq.poll();
                if (find(UFMap, currentEdge[0]) != find(UFMap, currentEdge[1])) {
                    union(UFMap, currentEdge[0], currentEdge[1]);
                    minWeight += currentEdge[2];
                    path[currentNumEdges][0] = currentEdge[0];
                    path[currentNumEdges][1] = currentEdge[1];
                    path[currentNumEdges][2] = currentEdge[2];
                    currentNumEdges++;
                }
            }
            return path;
        }
    }

    static class Sieve {
        /**
         * Sieve of Eratosthenes to determine prime numbers
         * @param n number for which to generate prime numbers up to
         * @return an array of integers composed of prime numbers less than n
         */
        public int[] primes(int n) {
            boolean[] primes = new boolean[n + 1];
            Arrays.fill(primes, true);
            int count = 0; // keeps track of number of primes encountered so far

            // this is the main sieve algorithm here. Only needs to go to i^2 rather than n per iteration
            for (int i = 2; i * i <= n; i++) {
                for (int j = i * i; j <= n; j++) {
                    if (primes[j]) {
                        primes[j] = false;
                        count++;
                    }
                }
            }

            // generate array of prime numbers
            int[] primeNums = new int[count];
            for (int i = 0; i < count; i++) {
                if (!primes[i]) primeNums[i] = i;
            }
            return primeNums;
        }
    }

    static class ClosedFib {
        /**
         * Closed form formula to generate the nth number of the Fibonacci sequence.
         * @param n the position of the nth number of the Fibonacci sequence
         * @return the value of the nth Fibonacci number
         */
        public static long fibClosed(int n) {
            double sqrt5 = Math.sqrt(5);
            double phi = (1.0 + sqrt5) / 2;
            return (long) ((Math.pow(phi, n) - (Math.pow(-phi, -n))) / (2 * phi - 1));
        }
    }

    static class Dijkstra {
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

    // Prim's Implementation stolen from https://www.baeldung.com/java-prim-algorithm
    static class Prim {
        public List<Vertex> graph;
        public int numNodes;

        public Prim (List<Vertex> graph) {
            this.graph = graph;
        }

        public void run() {
            if (!graph.isEmpty()) graph.get(0).isVisited = true;
            while (isDisconnected()) {
                Edge nextMinimum = new Edge(Integer.MAX_VALUE);
                Vertex nextVertex = graph.get(0);
                for (Vertex vertex: graph) {
                    if (vertex.isVisited) {
                        Pair<Vertex, Edge> candidate = vertex.nextMinimum();
                        if (candidate.edge.weight < nextMinimum.weight) {
                            nextMinimum = candidate.edge;
                            nextVertex = candidate.n;
                        }
                    }
                }
                nextMinimum.isIncluded = true;
                nextVertex.isVisited = true;
            }
        }

        private boolean isDisconnected() {
            for (Vertex n : graph) {
                if (!n.isVisited) {
                    return true;
                }
            }
            return false;
        }

        public String minimumSpanningTreeToString(){
            StringBuilder sb = new StringBuilder();
            for (Vertex vertex : graph){
                sb.append(vertex.includedToString());
            }
            return sb.toString();
        }
    }

    static class Pair<Vertex, Edge> {
        Vertex n;
        Edge edge;

        public Pair(Vertex n, Edge edge) {
            this.n = n;
            this.edge = edge;
        }
    }

    static class Vertex {
        public String name;
        public Map<Vertex, Edge> edges;
        public boolean isVisited = false;

        public Vertex(String name) {
            this.name = name;
            edges = new HashMap<>();
        }

        public Pair<Vertex, Edge> nextMinimum() {
            Edge nextMinimum = new Edge(Integer.MAX_VALUE);
            Vertex nextVertex = this;
            Iterator<Map.Entry<Vertex,Edge>> it = edges.entrySet()
                    .iterator();
            while (it.hasNext()) {
                Map.Entry<Vertex,Edge> pair = it.next();
                if (!pair.getKey().isVisited) {
                    if (!pair.getValue().isIncluded) {
                        if (pair.getValue().weight < nextMinimum.weight) {
                            nextMinimum = pair.getValue();
                            nextVertex = pair.getKey();
                        }
                    }
                }
            }
            return new Pair<>(nextVertex, nextMinimum);
        }

        public String toString() {
            return name;
        }

        public String includedToString(){
            StringBuilder sb = new StringBuilder();
            if (isVisited) {
                Iterator<Map.Entry<Vertex,Edge>> it = edges.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<Vertex,Edge> pair = it.next();
                    if (pair.getValue().isIncluded) {
                        if (!pair.getValue().isPrinted) {
                            sb.append(name);
                            sb.append(" --- ");
                            sb.append(pair.getValue().weight);
                            sb.append(" --- ");
                            sb.append(pair.getKey().name);
                            sb.append("\n");
                            pair.getValue().isPrinted = true;
                        }
                    }
                }
            }
            return sb.toString();
        }
    }

    static class Edge {
        public int weight;
        public boolean isIncluded = false;
        public boolean isPrinted = false;

        public Edge(int weight) {
            this.weight = weight;
        }

        public String toString() {
            return "" + weight;
        }
    }
}

class Graph {
    public ArrayList<ArrayList<Node>> adjList;
    public Map<Node, Map<Node, Integer>> weightsList;
    public ArrayList<Node> nodes;
    public int numOfNodes;

    public Graph(int numOfNodes) {
        this.numOfNodes = numOfNodes;
        nodes = new ArrayList<>();
        weightsList = new HashMap<>();
        adjList = new ArrayList<>();
        for(int i = 0; i < numOfNodes; i++)
            adjList.add(new ArrayList<Node>());
    }

    void addNode(Node n) {
        nodes.add(n);
    }

    void removeNode(Node n) {
        nodes.remove(n);
    }

    void unidirectionalAddEdge(Node u, Node v) {
        weightsList.get(u).put(v);
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

    /**
     * simple graph BFS that uses a visited array to avoid processing a node more than once
     * @param s starting node for the BFS
     * @param N number of nodes in graph to search
     */
    private void BFS(int s) {
        Queue<Integer> q = new LinkedList<>();
        boolean[] visited = new boolean[numOfNodes];
        q.add(s);
        visited[s] = true;

        while (!q.isEmpty()) {
            int next = q.poll();
            System.out.println(next);
            if(!adjList.get(next).isEmpty()) {
                for(Node neighbor : adjList.get(next)) {
                    if(!visited[neighbor.id]) {
                        visited[neighbor.id] = true;
                        q.add(neighbor.id);
                    }
                }
            }
        }
    }

    /**
     * simple graph DFS using a Stack instead of recursion
     * @param s starting node's index for the DFS
     */
    private void DFS(int s) {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[adjList.size()];
        stack.push(s);
        while(!stack.isEmpty()) {
            int current = stack.pop();
            System.out.println(current);
            visited[current] = true;
            for(Node neighbor : adjList.get(current)) {
                if(!visited[neighbor.id]) {
                    stack.push(neighbor.id);
                }
            }
        }
    }

}




