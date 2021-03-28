import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/*
 * COSC320 - Algorithms
 * Created in collaboration with:
 *   - Dakota Joiner 
 *   - Evan Mackinnon 
 *   - Lyndsey Wong
 *   - Keegan Pereira
 *   - Krishan Hewitt
 */

public class rectangle {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int[][] graph = new int[n][m];
        int result = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                graph[i][j] = in.nextInt();
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                boolean[][] visited = new boolean[n][m];
                if (graph[i][j] == 1) {
                    int temp = getRectangle(i, j, graph, visited);
                    if (temp > result && temp > 1) {
                        result = temp;
                    }

                }
            }
        }
        System.out.println(result);
    }

    public static int getRectangle(int row, int col, int[][] graph, boolean[][] visited) {
        Queue<Pair> queue = new LinkedList<>();
        queue.add(new Pair(row, col));
        int total = 0;

        while (!queue.isEmpty()) {
            Pair p = queue.remove();
            if(p.row < 0 || p.col < 0 || p.row >= graph.length || p.col >= graph[0].length)
            return -1;
            if (graph[p.row][p.col] == 0 || visited[p.row][p.col]) {
                continue;
            }


            visited[p.row][p.col] = true;

            queue.add(new Pair(p.row, p.col - 1));
            queue.add(new Pair(p.row, p.col + 1));
            queue.add(new Pair(p.row - 1, p.col));
            queue.add(new Pair(p.row + 1, p.col));
            total++;

        }
        return total;
    }

}

class Pair {
    int row;
    int col;

    public Pair(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
