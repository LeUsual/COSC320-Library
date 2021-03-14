import java.util.*;
import javafx.util.Pair;

import org.w3c.dom.Node;

public class Library {
    static class Node {
    	private static int count = 0;
        private int value;
        private String name;
        private int id;
        public boolean isVisited = false;

        public Node(String name, int value) {
        	this.id = count;
        	count++;
            this.name = name;
            this.value = value;
        }

        public String getName() { return name; }
        public int getValue() { return value; }
        public int getID() { return id; }
        public void setName(String s) { name = s; }
        public void setValue(int i) { value = i; }
        
        @Override
        public boolean equals(Object o) {
            if(o == this) {
                return true;
            }
        
            if(!(o instanceof Node)) {
                return false;
            }
        
            Node x = (Node) o;
        
            return Integer.compare(value, x.value);
        
        }
        
        public Pair nextMinimum() {
            Integer nextMinimum = Integer.MAX_VALUE;
            Node nextNode = this;
            Iterator<Map.Entry<Vertex,Edge>> it = edges.entrySet()
                .iterator();
            while (it.hasNext()) {
                Map.Entry<Vertex,Edge> pair = it.next();
                if (!pair.getKey().isVisited()) {
                    if (!pair.getValue().isIncluded()) {
                        if (pair.getValue().getWeight() < nextMinimum.getWeight()) {
                            nextMinimum = pair.getValue();
                            nextVertex = pair.getKey();
                        }
                    }
                }
            }
            return new Pair<>(nextVertex, nextMinimum);
        }
    }

    static class Kruskals {
        /**
         * Kruskal's algorithm. A priority queue is generated that is sorted upon edge weights. All elements are assigned
         * to be their own parent, then union-find is run on the priority queue. Note that no more than n - 1 edges are
         * necessary to create the minimum spanning tree for a graph of n nodes.
         * @param gNodes graph containing the nodes
         * @param source source node
         * @param destination destination node
         * @param weight edge weight between source and destination
         * @return the magnitude of the weight of a minimum spanning tree
         */
        public static int kruskals(int gNodes, List<Integer> source, List<Integer> destination, List<Integer> weight) {
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
            while (pq.size() > 0 && currentNumEdges < gNodes - 1) {
                int[] currentEdge = pq.poll();
                if (find(UFMap, currentEdge[0]) != find(UFMap, currentEdge[1])) {
                    union(UFMap, currentEdge[0], currentEdge[1]);
                    currentNumEdges++;
                    minWeight += currentEdge[2];
                }
            }
            return minWeight;
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

    static class UnionFind {
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
            long fib = (long) ((Math.pow(phi, n) - (Math.pow(-phi, -n))) / (2 * phi - 1));
            return fib;
        }
    }
}

class Graph {
    public ArrayList<Map<Node, Integer>> adjList;
    public ArrayList<Node> nodes;
    public int numOfNodes;

    public Graph(int numOfNodes) {
    	this.numOfNodes = numOfNodes
    	nodes = new ArrayList<>();

    }

    void addNode(Node n) {
    	nodes.add(n);

    }

    void removeNode(Node n) {
    	nodes.remove(n);
    }
    
    void addEdge(Node u, Node v, Integer weight) {
    	//This is for an undirected graph with weights.
    	adjList.get(u).add(new Map(v, weight));
    	adjList.get(v).add(new Map(u, weight));
    }

    void addEdge(Node u, Node v) {
    	//This is for an undirected graph with no weights.
    	adjList.get(u).add(v);
    	adjList.get(v).add(u);
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
            int vertex = adjList.get(current).get(i);
            if (vertex != parent)
                if (visited[vertex]) return true;
                else if (hasCycle(vertex, visited, current)) return true;
        }
        return false;
    }

    boolean isConnected() {
    Stack<Node> stack = new Stack<Node>();
    boolean[] visited = new boolean[adjList.size()]
    stack.push(adjList.get(0).get(0));
    
    while(!stack.isEmpty()) {
        Node current = stack.pop();
        visited[current.id] = true;
        for(Node neighbor : adjList.get(current)) {
            if(!visited[neighbor.id]) {
                stack.push(neighbor);
            }
        }
    }
    for(int i = 0; i < visited.length; i++) {
    	if(!visited[i])
    		return false;
    }
    return true;
}
    
     /**
     * simple graph BFS that uses a visited array to avoid processing a node more than once
     * @param s starting node for the BFS
     * @param N number of nodes in graph to search
     */
     private void BFS(int s, int N) {

     Queue<Node> q = new LinkedList<>();
     boolean visited[] = new boolean[N];
     q.add(s);
     visited[s] = true;

     while (!q.isEmpty()) {
         Node next = q.poll();
         if(!next.adj.isEmpty()) {
             for(Node neighbor : next.adj) {
                 if(!visited[neighbor]) {
                     visited[neighbor] = true;
                     q.add(neighbor);
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
        Stack<Integer> stack = new Stack<Integer>();
        boolean[] visited = new boolean[adjList.size()]
        stack.push(s);
        while(!stack.isEmpty()) {
            int current = stack.pop();
            visited[current] = true;
            for(int neighbor : adjList.get(current).adj) {
                if(!visited[neighbor]) {
                    stack.push(neighbor);
                }
            }
        }
    }

}



class Dijkstra {
	Graph tarGraph;
	int numberOfVertices;
	int[] lastParents;
	
	public Dijkstra(Graph tarGraph) {
		this.tarGraph = tarGraph;
		this.numberOfVertices = tarGraph.size();
		this.lastParents = new int[numberOfVertices];
	}
	

	//Dijkstra's implemented from https://www.geeksforgeeks.org/printing-paths-dijkstras-shortest-path-algorithm/
	public long dijkstra(Node start, Node end) {
		//Standard Dijkstra's beginning.
		//d is the distances from start to node n
		int[] d = new int[numberOfVertices];
		//parents is similar to unionfind, essentially points to who the parent of a certain node is.
		int[] parents = new int[numberOfVertices];
		//Visited is standard.
		boolean[] visited = new boolean[numberOfVertices];
		Arrays.fill(d, Integer.MAX_VALUE);
		
		d[start.getID()] = 0;
		
		for(int i = 0; i < numberOfVertices; i++) {
			int u = minDistance(d, visited);
			visited[u] = true;
			
			//Does Dijkstra's as it normall would.
			for(int v = 0; v < numberOfVertices; v++) {
				if(!visited[v] && distances[u][v] != 0 && 
				        d[u] != Integer.MAX_VALUE && d[u] + distances[u][v] < d[v]) {
					d[v] = d[u] + distances[u][v];
					parents[v] = u;
					
				}
			}
		}
		//Assigns parents to lastParents so we can access it outisde the function.
		lastParents = parents;
		return d[end.getID()];
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

publci class Pair{
	Node n;
	Integer edge;
	
	public Pair(Node n, Integer edge) {
		this.n = n;
		this.edge = edge;
	}
}

public class Prim {
    private Graph graph;
    public int numNodes;

    public Prim(Graph graph) {
    	this.graph = graph;
    	this.numNodes = graph.adjList.size();
    		
    }
    
    public void run() {
        if (numNodes > 0) {
            graph.get(0).setVisited(true);
        }
        while (isDisconnected()) {
            Edge nextMinimum = new Edge(Integer.MAX_VALUE);
            Node nextVertex = graph.nodes.get(0);
            for (Node node: graph.nodes) {
                if (Node.isVisited) {
                    Pair candidate = node.nextMinimum();
                    if (candidate.getValue().getWeight() < nextMinimum.getWeight()) {
                        nextMinimum = candidate.getValue();
                        nextVertex = candidate.getKey();
                    }
                }
            }
            nextMinimum.setIncluded(true);
            nextNode.isVisited = true;
        }
    }
    
    private boolean isDisconnected() {
        for (Node n : graph.nodes) {
            if (n.isVisited) {
                return true;
            }
        }
        return false;
    }
}


class PowerSet {

}




