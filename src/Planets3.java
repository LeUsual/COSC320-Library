import java.util.*;

/*
 * COSC320 - Algorithms
 * Created in collaboration with:
 *   - Dakota Joiner 
 *   - Evan Mackinnon
 *   - Lyndsey Wong
 *   - Keegan Pereira
 *   - Krishan Hewitt
 */

public class Planets3 {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int numCase = Integer.parseInt(in.nextLine());
		for(int i = 0; i < numCase; i++) {
			int numPlanets = Integer.parseInt(in.nextLine());
			double[][] dist = new double[numPlanets][numPlanets];
			
			int[] x = new int[numPlanets], y = new int[numPlanets], z = new int[numPlanets];
			Map<String, Integer> planets = new HashMap<>();
			for(int p = 0; p < numPlanets; p++) {
				String[] arr = in.nextLine().split(" ");
				planets.put(arr[0], p);
				x[p] = Integer.parseInt(arr[1]);
				y[p] = Integer.parseInt(arr[2]);
				z[p] = Integer.parseInt(arr[3]);
			}
			
			for(int j = 0; j < dist.length; j++) 
				for(int k = 0; k < dist[j].length; k++) {
					double distance = Math.sqrt((x[j] - x[k]) * (double)(x[j] - x[k]) + (y[j] - y[k]) * (double)(y[j] - y[k]) + (z[j] - z[k]) * (double)(z[j] - z[k]));
					dist[j][k] = dist[k][j] = distance;
				}
			
			int wh = Integer.parseInt(in.nextLine());
			for(int w = 0; w < wh; w++) {
				String[] arr = in.nextLine().split(" ");
				dist[planets.get(arr[0])][planets.get(arr[1])] = 0;
			}
			
			smartDijkstras(dist);
			
			System.out.println("Case " + (i + 1) + ":");
			int queries = Integer.parseInt(in.nextLine());
			for(int q = 0; q < queries; q++) {
				String[] arr = in.nextLine().split(" ");
				int origin = planets.get(arr[0]);
				int destin = planets.get(arr[1]);
				double totalDist = dist[origin][destin];
				
				System.out.println("The distance from " + arr[0] + " to " + arr[1] + " is " + (int) Math.round(totalDist) + " parsecs.");
			}
		}
	}
	
	public static void smartDijkstras(double[][] d) {
		//int[][] d = {{0,1,2},{3,4,5},{6,8,7}};
		/*Iteratively searches from origin to mid, then from mid to dest for all possible
		 *planet traversal distances. [0][0] until [n][n] where n is the number of planets.
		 *Statically sets the planets compared to looking at possible paths then chooses the shortest path.
		 */
		for(int mid = 0; mid < d.length; mid++) {
			for(int origin = 0; origin < d.length; origin++) {
				for(int dest = 0; dest < d.length; dest++) {
					if(d[origin][mid] + d[mid][dest] < d[origin][dest]) {
						d[origin][dest] = d[origin][mid] + d[mid][dest];
					}
				}
			}
		}
	}
}
