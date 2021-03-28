import java.io.*;
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

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] coins = new int[n];
        for (int i = 0; i < n; i++)
            coins[i] = in.nextInt();

        int even = 0;
        int odd = 0;
        for (int i = 0; i < n; i++)
            if(i % 2 == 0)
                even += coins[i];
            else
                odd += coins[i];
        int sum = 0;
        if (even >= odd) {
            for (int i = 0; i < n; i+=2)
                sum += coins[i];
        }
        else
            for (int i = 1; i < n; i += 2)
                sum += coins[i];

        System.out.println(sum);
    }
}