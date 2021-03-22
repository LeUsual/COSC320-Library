import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[] good = {1, 2, 3, 3, 4, 10};
        int[] evil = {1, 2, 2, 2, 3, 5, 11};
        int battles = in.nextInt();
        for(int b = 0; b < battles; b++){
            int goodSum = 0;
            for(int i = 0; i < good.length; i++)
                goodSum += good[i] * in.nextInt();
            int badSum = 0;
            for(int i = 0; i < evil.length; i++)
                badSum += evil[i] * in.nextInt();

            if (goodSum > badSum)
                System.out.println("Battle " + (b + 1) + ": Good triumphs over Evil");
            else if (badSum > goodSum)
                System.out.println("Battle " + (b + 1) + ": Evil eradicates all trace of Good");
            else
                System.out.println("Battle " + (b + 1) + ": No victor on this battle field");
        }
    }
}