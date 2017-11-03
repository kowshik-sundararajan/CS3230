package assignment3;
/*
 * The program implements a variation of the minimum coin change
 * problem using Dynamic Programming.
 * The variation is that the user can enter one denomination greater than the largest
 * denomination.
 *
 * @author  Kowshik Sundararajan
 * @version 1.0
 * @since   2017 - 11 - 3
 */

import java.util.Scanner;

class MinimumCoinChangeVariation {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //System.out.println("Enter the number of totals");
        int N = sc.nextInt();
        //System.out.println("Enter a denomination greater than 20");
        int K = sc.nextInt();
        //System.out.println("Enter the different totals")
        int[] P = new int[N];
        for (int i = 0; i < N; i++) {
            P[i] = sc.nextInt();
        }

        int solution = solve(N, K, P);
        // System.out.print("The minimum number of coins required to make up the total is:\t");
        System.out.println(solution);
    }

    /**
     * Function to find the minimum number of coins required
     * using Dynamic Programming
     *
     * @param N The number of totals
     * @param K The user entered denomination
     * @param P Array containing the different totals
     * @return The minimum number of coins required
     */
    private static int solve(int N, int K, int[] P) {
        int denominations[] = {1, 4, 6, 9, 20, K};
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