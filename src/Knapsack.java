import java.io.*;
import java.math.*;
import java.util.*;

class Knapsack {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int C = sc.nextInt();
        int[] W = new int[N];
        int[] V = new int[N];
        for (int i = 0; i < N; i++) {
            W[i] = sc.nextInt();
            V[i] = sc.nextInt();
        }

        int solution = solve(N, W, V, C);
        System.out.println(solution);
    }

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