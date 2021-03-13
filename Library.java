import java.util.*;

public class Library {
    static class Node {
        private int value;
        private String name;

        public Node(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() { return name; }
        public int getValue() { return value; }
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
    private ArrayList<ArrayList<Node>> adjList;

    public Graph() {

    }

    void addNode(Node n) {

    }

    void removeNode(Node n) {

    }

    void addEdge(int u, int v) {
        
    }

    void removeEdge(int u, int v) {

    }

    boolean isCyclic() {

    }

    boolean isConnected() {

    }
    
        /**
     * simple graph BFS that uses a visited array to avoid processing a node more than once
     * @param start starting node for the BFS
     * @param N number of nodes in graph to search
     */
     private void BFS(Node start, int size) {

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
    
    private void DFS() {
    }

}



class Dijkstra {

}

class Prims {

}


class PowerSet {

}




