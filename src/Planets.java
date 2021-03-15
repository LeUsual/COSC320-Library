import java.io.*;
import java.util.*;

public class Planets {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int testCases = in.nextInt();

        for (int i = 0; i < testCases; i++) {
            int numOfPlanets = in.nextInt();
            ArrayList<PlanetNode> planets = new ArrayList<>();
            for (int j = 0; j < numOfPlanets; j++) {
                planets.add(new PlanetNode(in.next(), in.nextInt(), in.nextInt(), in.nextInt()));
            }
            Map<String, String> connections = new HashMap<>();
            int numWormholes = in.nextInt();
            for (int j = 0; j < numWormholes; j++) {
                String a = in.next();
                String b = in.next();
                connections.put(a, b);
            }
            int numQueries = in.nextInt();
            System.out.println("Case " + (i + 1) + ":");
            for (int j = 0; j < numQueries; j++) {
                int distance = 0;
                String first = in.next();
                String second = in.next();
                PlanetNode one = planets.get(0), two = planets.get(0);
                for (int k = 0; k < numOfPlanets; k++) {
                    if (planets.get(k).name.equals(first)) one = planets.get(k);
                    if (planets.get(k).name.equals(second)) two = planets.get(k);
                }
                if (connections.containsKey(first) && connections.containsKey(second)) if (connections.get(first).equals(second));
                else {
                    distance = (int) distance(planets.get(planets.indexOf(one)), planets.get(planets.indexOf(two)));
                }
                System.out.println("The distance from " + first + " to " + second + " is " + distance + " parsecs.");
            }
        }
    }

    public static double distance(PlanetNode n1, PlanetNode n2) {
        int deltaXS = Math.abs(n1.x - n2.x); System.err.println(deltaXS);
        int deltaYS = Math.abs(n1.y - n2.y); System.err.println(deltaYS);
        int deltaZS = Math.abs(n1.z - n2.z); System.err.println(deltaZS);
        int distance = (int) Math.floor(Math.sqrt(deltaXS * deltaXS + deltaYS * deltaYS + deltaZS * deltaZS));
        return distance;
    }

    static class PlanetNode {
        public int x, y, z;
        public String name;

        public PlanetNode(String name, int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.name = name;
        }
        public String toString() {
            return name + " " + x + " " + y + " " + z;
        }
    }

}

