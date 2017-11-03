package assignment1;
/*
 * The Strassen Matrix program implements an application that takes performs matrix
 * multiplication on large matrices using Strassen's algorithm (with fine-tuning to
 * increase performance).
 *
 * @author  Kowshik Sundararajan
 * @version 1.0
 * @since   2017 - 09 - 23
 */

import java.util.Scanner;

public class Strassen_Matrix {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //System.out.print("Enter the size of the square matrix: ");
        int N = sc.nextInt();
        //System.out.println();

        int[][] A = new int[N][N];
        int[][] B = new int[N][N];

        //System.out.println("Enter the values of the first matrix");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                A[i][j] = sc.nextInt();
            }
        }

        //System.out.println();
        //System.out.println("Enter the values of the second matrix");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                B[i][j] = sc.nextInt();
            }
        }

        //System.out.println("Calculating the strassen multiplied matrix...");

        int[][] C = strassen(N, A, B);

        long total = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                //System.out.println("i, j: (" + i + ", " + j + ")" + C[i][j]);
                total += C[i][j];
            }
        }
        System.out.println(total);
    }

    /**
     * Function to perform matrix multiplication using Strassen's algorithm (with slight tuning)
     *
     * @param N size of the input matrices
     * @param A input matrix A
     * @param B input Matrix B
     * @return The product of the input matrices
     */
    private static int[][] strassen(int N, int[][] A, int[][] B) {

        // if size of the square matrix is smaller than 50, then perform ijk multiplication
        if (N < 50) {
            return ijk_mult(N, A, B);
        } else {
            int newSize = N / 2;

            int[][] A1 = new int[newSize][newSize];
            int[][] A2 = new int[newSize][newSize];
            int[][] A3 = new int[newSize][newSize];
            int[][] A4 = new int[newSize][newSize];

            int[][] B1 = new int[newSize][newSize];
            int[][] B2 = new int[newSize][newSize];
            int[][] B3 = new int[newSize][newSize];
            int[][] B4 = new int[newSize][newSize];

            // dividing each matrix into four sub-matrices
            for (int i = 0; i < newSize; i++) {
                for (int j = 0; j < newSize; j++) {
                    A1[i][j] = A[i][j];
                    A2[i][j] = A[i][newSize + j];
                    A3[i][j] = A[newSize + i][j];
                    A4[i][j] = A[newSize + i][newSize + j];

                    B1[i][j] = B[i][j];
                    B2[i][j] = B[i][newSize + j];
                    B3[i][j] = B[newSize + i][j];
                    B4[i][j] = B[newSize + i][newSize + j];
                }
            }

            // calculate p1 to p7
            int[][] p1 = strassen(newSize, A1, subtract_matrix(newSize, B2, B4)); // p1 = A1 * (B2 - B4)
            int[][] p2 = strassen(newSize, add_matrix(newSize, A1, A2), B4); // p2 = (A1 + A2) * B4
            int[][] p3 = strassen(newSize, add_matrix(newSize, A3, A4), B1); // p3 = (A3 + A4) * B1
            int[][] p4 = strassen(newSize, A4, subtract_matrix(newSize, B3, B1)); // p4 = A4 * (B3 - B1)
            int[][] p5 = strassen(newSize, add_matrix(newSize, A1, A4), add_matrix(newSize, B1, B4)); // p5 = (A1 + A4) * (B1 * B4)
            int[][] p6 = strassen(newSize, subtract_matrix(newSize, A2, A4), add_matrix(newSize, B3, B4)); // p6 = (A2 - A4) * (B3 + B4)
            int[][] p7 = strassen(newSize, subtract_matrix(newSize, A1, A3), add_matrix(newSize, B1, B2)); // p7 = (A1 - A3) * (B1 + B2)

            // calculate the sub-matrices of C
            int[][] C1 = add_matrix(newSize, subtract_matrix(newSize, add_matrix(newSize, p5, p4), p2), p6);
            int[][] C2 = add_matrix(newSize, p1, p2);
            int[][] C3 = add_matrix(newSize, p3, p4);
            int[][] C4 = subtract_matrix(newSize, add_matrix(newSize, p5, p1), add_matrix(newSize, p3, p7));

            // combine the four quarters of C
            return combine_matrix(newSize, C1, C2, C3, C4);
        }

    }

    /**
     * Function to combine four sub-matrices into one matrix
     *
     * @param N    size of the input sub-matrices
     * @param Mat1 sub-matrix 1
     * @param Mat2 sub-matrix 2
     * @param Mat3 sub-matrix 3
     * @param Mat4 sub-matrix 4
     * @return The combined matrix
     */
    private static int[][] combine_matrix(int N, int[][] Mat1, int[][] Mat2, int[][] Mat3, int[][] Mat4) {
        int[][] Mat_Comb = new int[N * 2][N * 2];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                Mat_Comb[i][j] = Mat1[i][j];
                Mat_Comb[i][j + N] = Mat2[i][j];
                Mat_Comb[i + N][j] = Mat3[i][j];
                Mat_Comb[i + N][j + N] = Mat4[i][j];
            }
        }
        return Mat_Comb;
    }

    /**
     * Function to multiply two matrices
     *
     * @param N    size of the input matrices
     * @param Mat1 matrix 1
     * @param Mat2 matrix
     * @return The product of Mat1 and Mat2
     */
    private static int[][] ijk_mult(int N, int[][] Mat1, int[][] Mat2) {
        int[][] Mat3 = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                Mat3[i][j] = 0;
                for (int k = 0; k < N; k++) {
                    Mat3[i][j] = Mat3[i][j] + Mat1[i][k] * Mat2[k][j];
                }
            }
        }
        return Mat3;
    }

    /**
     * Function to add two matrices
     *
     * @param N    size of the input matrices
     * @param Mat1 matrix 1
     * @param Mat2 matrix 2
     * @return The addition of Mat1 and Mat2
     */
    private static int[][] add_matrix(int N, int[][] Mat1, int[][] Mat2) {
        int[][] Mat3 = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                Mat3[i][j] = Mat1[i][j] + Mat2[i][j];
            }
        }

        return Mat3;
    }

    /**
     * Function to subtract two matrices
     *
     * @param N    size of the input matrices
     * @param Mat1 matrix 1
     * @param Mat2 matrix 2
     * @return The subtraction of Mat1 and Mat2
     */
    private static int[][] subtract_matrix(int N, int[][] Mat1, int[][] Mat2) {
        int[][] Mat3 = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                Mat3[i][j] = Mat1[i][j] - Mat2[i][j];
            }
        }

        return Mat3;
    }
}