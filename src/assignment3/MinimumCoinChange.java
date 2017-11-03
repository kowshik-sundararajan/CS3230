package assignment3;
/*
 * The program implements the the minimum coin change problem using a
 * Greedy algorithm as well as Dynamic Programming.
 *
 * @author  Kowshik Sundararajan
 * @version 1.0
 * @since   2017 - 11 - 3
 */

import java.util.Scanner;

class MinimumCoinChange {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // System.out.println("Enter the number of totals:");
        int N = sc.nextInt();
        // System.out.println("Enter the different totals:");
        int[] P = new int[N];
        for (int i = 0; i < N; i++) {
            P[i] = sc.nextInt();
        }

        int solution = solveGreedy(N, P);
        // System.out.print("The minimum number of coins required is:\t");
        System.out.println(solution);
    }

    /**
     * Function to find the minimum number of coins required
     * using a Greedy algorithm that always picks the largest
     * denomination first
     *
     * @param N The number of totals
     * @param P Array containing the different totals
     * @return The minimum number of coins required
     */
    private static int solveGreedy(int N, int[] P) {
        int denominations[] = {1, 5, 10, 20, 50, 100};
        int numOfCoins = 0;

        for (int coin : P) {
            int j = denominations.length - 1;
            while (coin > 0) {
                if (denominations[j] <= coin) {
                    coin -= denominations[j];
                    numOfCoins++;
                } else
                    j--;
            }
        }
        return numOfCoins;
    }

    /**
     * Function to find the minimum number of coins required
     * using Dynamic Programming
     *
     * @param N The number of totals
     * @param P Array containing the different totals
     * @return The minimum number of coins required
     */
    public static int solveDP(int N, int[] P) {
        int denominations[] = {1, 5, 10, 20, 50, 100};
        int numOfCoins = 0;

        for (int coin : P) {
            int[] m = new int[coin + 1];

            for (int i = 1; i <= coin; i++) {
                m[i] = Integer.MAX_VALUE;
            }

            for (int i = 1; i <= coin; i++) {
                for (int denomination : denominations) {
                    if (denomination <= i) {
                        int temp = m[i - denomination];
                        if (temp != Integer.MAX_VALUE && (temp + 1 < m[i]))
                            m[i] = temp + 1;
                    }
                }
            }
            numOfCoins += m[coin];
        }
        return numOfCoins;
    }

}