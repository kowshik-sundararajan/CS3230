/*
 * The Knapsack class helps to implement the Bounded Knapsack problem.
 *
 * @author  Kowshik Sundararajan
 * @version 1.0
 * @since   2017 - 10 - 29
 */

import java.util.ArrayList;

public class Knapsack {
    int N;
    int C;
    int[] weight;
    int[] value;
    int[] bound;
    ArrayList<Integer> weightList;
    ArrayList<Integer> valueList;


    public Knapsack(int N, int[] W, int[] V, int[] L, int C) {
        this.N = N;
        this.C = C;
        weight = new int[W.length];
        value = new int[V.length];
        bound = new int[L.length];

        System.arraycopy(W, 0, weight, 0, W.length);
        System.arraycopy(V, 0, value, 0, V.length);
        System.arraycopy(L, 0, bound, 0, L.length);
        weightList = new ArrayList<>();
        valueList = new ArrayList<>();
    }

    /* Function to convert bounded knapsack to 0-1 knapsack with space optimization */
    public Knapsack optimizedConvertToZeroOne() {
        for (int i = 0; i < N; i++) {
            int temp = 0;
            int k = 1;

            while (temp < bound[i]) {
                if (temp + k > bound[i])
                    k = bound[i] - temp;
                valueList.add(k * value[i]);
                weightList.add(k * weight[i]);
                temp += k;
                k *= 2;
            }
        }

        weight = new int[weightList.size()];
        value = new int[valueList.size()];
        for (int i = 0; i < weightList.size(); i++) {
            weight[i] = weightList.get(i);
            value[i] = valueList.get(i);
        }
        return this;
    }

    /* Function to convert bounded knapsack to 0-1 knapsack */
    public Knapsack convertToZeroOne() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < bound[i]; j++) {
                weightList.add(weight[i]);
                valueList.add(value[i]);
            }
        }

        //reset element of weight and value
        weight = new int[weightList.size()];
        value = new int[valueList.size()];
        for (int i = 0; i < weightList.size(); i++) {
            weight[i] = weightList.get(i);
            value[i] = valueList.get(i);
        }
        return this;
    }
}
