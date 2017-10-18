/*
 * The Knapsack program implements the traditional Knapsack algorithm using Dynamic Programming
 * with unlimited number of each item.
 *
 * @author  Kowshik Sundararajan
 * @version 1.0
 * @since   2017 - 10 - 19
 */
import java.util.*;

class Knapsack {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //System.out.println("Enter the number of items");
        int N = sc.nextInt();
        //System.out.println("Enter the capacity of the knapsack");
        int C = sc.nextInt();
        int[] W = new int[N];
        int[] V = new int[N];
        //System.out.println("Enter the weight and value of each item");
        for (int i = 0; i < N; i++) {
            W[i] = sc.nextInt();
            V[i] = sc.nextInt();
        }

        int solution = solve(N, W, V, C);
        //System.out.println("The maximum value that the knapsack can store is: ");
        System.out.println(solution);
    }

    /**
     * Function to find the maximum value that can fit in the knapsack
     *
     * @param N The number of items
     * @param W Array containing the weights of the items
     * @param V Array containing the values of the items
     * @param C Capacity of the knapsack
     * @return The maximum value that the knapsack can hold
     */
    private static int solve(int N, int[] W, int[] V, int C) {
        int m[][] = new int[N + 1][C + 1];

        for (int row = 0; row <= N; row++) {
            m[row][0] = 0;
        }

        for (int col = 0; col <= C; col++) {
            m[0][col] = 0;
        }

        for (int row = 1; row <= N; row++) {
            for (int col = 1; col <= C; col++) {
                if (W[row - 1] <= col)
                    m[row][col] = Math.max(m[row][col - W[row - 1]] + V[row - 1], m[row - 1][col]);
                else
                    m[row][col] = m[row - 1][col];
            }
        }
        return m[N][C];
    }
}