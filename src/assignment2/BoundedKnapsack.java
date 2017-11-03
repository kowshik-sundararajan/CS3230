package assignment2;
/*
 * The Bounded Knapsack program implements the traditional Knapsack algorithm using Dynamic Programming
 * with limited number of each item.
 *
 * @author  Kowshik Sundararajan
 * @version 1.0
 * @since   2017 - 10 - 29
 */

import java.util.Scanner;

class BoundedKnapsack {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //System.out.println("Enter the number of items");
        int N = sc.nextInt();
        //System.out.println("Enter the capacity of the knapsack");
        int C = sc.nextInt();
        int[] W = new int[N];
        int[] V = new int[N];
        int[] L = new int[N];

        //System.out.println("Enter the weight, value and upper bound of each item");
        for (int i = 0; i < N; i++) {
            W[i] = sc.nextInt();
            V[i] = sc.nextInt();
            L[i] = sc.nextInt();
        }

        boolean optimize = true;
        int solution = convertToZeroOne(N, W, V, L, C, optimize);

        //System.out.println("The maximum value that the knapsack can store is: ");
        System.out.println(solution);
    }

    /**
     * Function to convert the bounded knapsack into 0-1 knapsack
     *
     * @param n        The number of items
     * @param w        Array containing the weights of the items
     * @param v        Array containing the values of the items
     * @param l        Array containing the bound of each item
     * @param c        capacity of the knapsack
     * @param optimize true if we want to implement the space optimization
     * @return The maximum value that the knapsack can hold
     */
    private static int convertToZeroOne(int n, int[] w, int[] v, int[] l, int c, boolean optimize) {
        Knapsack knapsack = new Knapsack(n, w, v, l, c);

        if (optimize)
            knapsack = knapsack.optimizedConvertToZeroOne();
        else
            knapsack = knapsack.convertToZeroOne();

        w = new int[knapsack.weight.length];
        v = new int[knapsack.weight.length];
        n = knapsack.weight.length;

        System.arraycopy(knapsack.weight, 0, w, 0, n);
        System.arraycopy(knapsack.value, 0, v, 0, n);

        return solve(n, w, v, c);
    }

    /**
     * Function to find the maximum value that can fit in the knapsack
     *
     * @param n The number of items
     * @param w Array containing the weights of the items
     * @param v Array containing the values of the items
     * @param c capacity of the knapsack
     * @return The maximum value that the knapsack can hold
     */
    private static int solve(int n, int[] w, int[] v, int c) {
        int m[][] = new int[n + 1][c + 1];

        for (int row = 0; row <= n; row++) {
            m[row][0] = 0;
        }

        for (int col = 0; col <= c; col++) {
            m[0][col] = 0;
        }

        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= c; col++) {
                if (w[row - 1] <= col)
                    m[row][col] = Math.max(m[row - 1][col - w[row - 1]] + v[row - 1], m[row - 1][col]);
                else
                    m[row][col] = m[row - 1][col];
            }
        }

        displayBottomUpArray(m);
        return m[n][c];
    }

    /**
     * Function to print the bottom-up table
     *
     * @param m the 2D array representing the bottom-up table
     */
    private static void displayBottomUpArray(int[][] m) {
        System.out.println("The bottom-up table is:");

        for (int[] row : m) {
            for (int j = 0; j < m[0].length; j++) {
                System.out.print(row[j] + "\t");
            }
            System.out.println();
        }
    }
}