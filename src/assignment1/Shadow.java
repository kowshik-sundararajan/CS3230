package assignment1;
/*
 * The Shadow program implements an application that takes in co-ordinates of
 * blocks and finds out the co-ordinates of the shadow formed by merging the
 * blocks. Algorithm implemented: Merge Sort
 *
 * @author  Kowshik Sundararajan
 * @version 1.0
 * @since   2017 - 09 - 23
 */

import java.util.*;

public class Shadow {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // System.out.println("Enter the number of blocks");
        int N = sc.nextInt();

        Block[] A = new Block[N + 1];
        for (int i = 1; i <= N; i++) {
            A[i] = new Block(new Point(0, 0), new Point(0, 0));
            A[i].topLeft.xc = sc.nextInt();
            A[i].topLeft.yc = sc.nextInt();
            A[i].bottomRight.xc = sc.nextInt();
            A[i].bottomRight.yc = sc.nextInt();
        }

        ArrayList<Point> solution = findShadow(1, N, A);
        solution = postProcessing(solution);

        // print the co-ordinates of the shadow
        for (int i = 0; i < solution.size(); i++) {
            System.out.println(solution.get(i).xc + " " + solution.get(i).yc);

        }
    }

    /**
     * Function to find the co-ordinates of the shadow using Merge Sort
     *
     * @param left  The left index of the Block A
     * @param right The right index of the Block A
     * @param A     The Block array containing co-ordinates of all the blocks
     * @return ArrayList containing the co-ordinates from the divide and conquer step
     */
    private static ArrayList<Point> findShadow(int left, int right, Block[] A) {
        // base case
        if (left == right) {
            ArrayList<Point> result = new ArrayList<Point>();
            result.add(A[left].topLeft);
            result.add(A[left].bottomRight);
            return result;
        }

        // merge the two shadows
        ArrayList<Point> L = findShadow(left, (left + right) / 2, A);
        ArrayList<Point> R = findShadow((left + right) / 2 + 1, right, A);

        int currentHeight = 0, rightHeight = 0, leftHeight = 0;
        ArrayList<Point> solution = new ArrayList<Point>();

        while (!L.isEmpty() && !R.isEmpty()) {
            Point leftPt = L.get(0);
            Point rightPt = R.get(0);

            if (leftPt.xc <= rightPt.xc) {
                leftHeight = leftPt.yc;
                L.remove(leftPt);

                if (leftPt.xc == rightPt.xc) { // if x co-ordinates are equal, remove both points from respective matrices
                    rightHeight = rightPt.yc;
                    R.remove(rightPt);
                }

                if (currentHeight != Math.max(leftHeight, rightHeight)) {
                    solution.add(new Point(leftPt.xc, Math.max(leftHeight, rightHeight)));
                    currentHeight = Math.max(leftHeight, rightHeight);
                }

            } else if (rightPt.xc < leftPt.xc) {
                rightHeight = rightPt.yc;
                R.remove(rightPt);

                if (currentHeight != Math.max(leftHeight, rightHeight)) {
                    solution.add(new Point(rightPt.xc, Math.max(leftHeight, rightHeight)));
                    currentHeight = Math.max(leftHeight, rightHeight);
                }
            }
        }

        // iterate through the remaining elements and add them to the solution
        if (!R.isEmpty()) {
            solution.addAll(R);
        } else if (!L.isEmpty()) {
            solution.addAll(L);
        }

        return solution;
    }

    /**
     * Function to add the remaining points that form the shadow
     *
     * @param solution ArrayList that stores the result from the divide and conquer step
     * @return ArrayList containing all the outer points of the shadow
     */
    private static ArrayList<Point> postProcessing(ArrayList<Point> solution) {
        solution.add(0, new Point(solution.get(0).xc, 0));

        for (int i = 1; i < solution.size() - 1; i = i + 2) {
            Point firstPt = solution.get(i);
            Point secondPt = solution.get(i + 1);

            if (firstPt.xc != secondPt.xc || firstPt.yc != secondPt.yc) {
                solution.add(i + 1, new Point(secondPt.xc, firstPt.yc));
            }
        }
        return solution;
    }

    /**
     * Class which generates x and y co-ordinates of a point
     */
    private static class Point {
        int xc;
        int yc;

        // constructor
        public Point(int _xc, int _yc) {
            xc = _xc;
            yc = _yc;
        }
    }

    /**
     * Class which generates a Block containing it's top-left and bottom-right co-ordinates
     */
    private static class Block {
        Point topLeft;
        Point bottomRight;

        // constructor
        public Block(Point _topLeft, Point _bottomRight) {
            topLeft = _topLeft;
            bottomRight = _bottomRight;
        }
    }
}