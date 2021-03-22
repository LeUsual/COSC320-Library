import java.io.*;
import java.util.*;

public class Diamonds {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int numTestCases = in.nextInt();

        for (int i = 0; i < numTestCases; i++) {
            int numDiamonds = in.nextInt();
            int max = 1;
            int tempMax = 1;
            double[] weights = new double[numDiamonds];
            double[] clarity = new double[numDiamonds];

            // read in info into two separate arrays             
            for (int j = 0; j < numDiamonds; j++) {
                weights[j] = in.nextDouble();
                clarity[j] = in.nextDouble();
            }

            // only increment max if the subsequent diamond is strictly better in terms of weight and clarity
            for (int k = 0; k < numDiamonds; k++) {
                if (numDiamonds == 1) break; // accounts for a single diamond
                if (k == numDiamonds - 2) {
                    if (weights[k] < weights[k + 1] && clarity[k] > clarity[k + 1]) max++;
                    break;
                }
                if (weights[k] < weights[k + 1] && clarity[k] > clarity[k + 1]) max++;
            }

            System.out.println(max);
        }
    }
}