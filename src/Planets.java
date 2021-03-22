import java.util.*;

// Use graph

public class Planets {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int testCases = in.nextInt();

        for (int i = 0; i < testCases; i++) {
            int numOfPlanets = in.nextInt();
            ArrayList<PlanetNode> planets = new ArrayList<>();
            for (int j = 0; j < numOfPlanets; j++) {
                planets.add(new PlanetNode(in.next(), in.nextInt(), in.nextInt(), in.nextInt()));
//                String next = in.nextLine();
//                System.err.println(next);
//                String[] nextAr = next.split(" ");
//                planets.add(new PlanetNode(nextAr[0], Integer.parseInt(nextAr[1]), Integer.parseInt(nextAr[2]), Integer.parseInt(nextAr[3])));
            }
            Map<String, String> connections = new HashMap<>();
            int numWormholes = in.nextInt();
            int[] distances = new int[numWormholes];
            for (int j = 0; j < numWormholes; j++) {
//                String wormholes = in.nextLine();
//                String[] wh = wormholes.split(" ");
//                connections.put(wh[0], wh[1]);
                String a = in.next();
                String b = in.next();
                connections.put(a, b);
            }

            int numQueries = in.nextInt();
            System.out.println("Case " + (i + 1) + ":");
            for (int j = 0; j < numQueries; j++) {
                int distance = 0;
//                String connexns = in.nextLine();
//                String[] cn = connexns.split(" ");
//                String first = cn[0];
//                String second = cn[1];
                String first = in.next();
                String second = in.next();
                // if a wormhole exists between first and second
                if (connections.containsKey(first) && connections.get(first).equals(second));

                // else if there is a chain of wormholes between the planets


                // no wormhole exists between these planets
                else {
                    PlanetNode p1 = null, p2 = null;
                    for (int k = 0; k < planets.size(); k++) {
                        if (planets.get(k).name.equals(first)) p1 = planets.get(k);
                        if (planets.get(k).name.equals(second)) p2 = planets.get(k);
                    }
                    distance = distance(p1, p2);
                }
                System.out.println("The distance from " + first + " to " + second + " is " + distance + " parsecs.");
            }
        }
    }

    public static int distance(PlanetNode n1, PlanetNode n2) {
        int deltaXS = Math.abs(n1.x - n2.x); System.err.println("DeltaX: " + deltaXS);
        int deltaYS = Math.abs(n1.y - n2.y); System.err.println("DeltaY: " + deltaYS);
        int deltaZS = Math.abs(n1.z - n2.z); System.err.println("DeltaZ: " + deltaZS);
        return (int) Math.floor(Math.sqrt(deltaXS * deltaXS + deltaYS * deltaYS + deltaZS * deltaZS));
    }

    static class PlanetNode {
        public int x, y, z;
        public String name;
        public boolean hasWormhole;
        public String wormholeTo = "";

        public PlanetNode(String name, int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.name = name;
            hasWormhole = false;
        }
        public String toString() {
            return name + " " + x + " " + y + " " + z;
        }

        public void putWormhole(String s) {
            hasWormhole = true;
            this.wormholeTo = s;
        }
    }
}

