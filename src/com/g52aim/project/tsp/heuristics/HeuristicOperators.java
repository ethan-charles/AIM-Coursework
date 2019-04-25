package com.g52aim.project.tsp.heuristics;

import java.util.Random;

import com.g52aim.project.tsp.interfaces.ObjectiveFunctionInterface;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;


/**
 * @author Warren G. Jackson
 * <p>
 * TODO you can add any common functionality here
 * to save having to re-implement them in all
 * your other heuristics!
 * ( swapping two cities seems to be popular )
 * <p>
 * <p>
 * If this this concept it new to you, you may want
 * to read this article on inheritance:
 * https://www.tutorialspoint.com/java/java_inheritance.htm
 */
public class HeuristicOperators {

    protected final Random random;
    protected ObjectiveFunctionInterface f = null;

    public HeuristicOperators(Random random) {

        this.random = random;
    }

    public int getQuadraticTimes(double strength) {
        /**
         * get the number of times in quadratic fashion (2^n)
         */
        int times = 0;

        if (strength >= 0 && strength < 0.2) {
            times = 1;
        } else if (strength < 0.4) {
            times = 2;
        } else if (strength < 0.6) {
            times = 4;
        } else if (strength < 0.8) {
            times = 8;
        } else if (strength < 1.0) {
            times = 16;
        } else if (strength == 1.0) {
            times = 32;
        }
        return times;
    }

    public int getIncrementalTimes(double strength) {
        /**
         * get the number of times in increment fashion (+1)
         */
        int times = 0;

        if (strength >= 0 && strength < 0.2) {
            times = 1;
        } else if (strength < 0.4) {
            times = 2;
        } else if (strength < 0.6) {
            times = 3;
        } else if (strength < 0.8) {
            times = 4;
        } else if (strength < 1.0) {
            times = 5;
        } else if (strength == 1.0) {
            times = 6;
        }
        return times;
    }

    protected int ActualAdjacentSwap(int[] array, int index) {
        /**
         * Caveat: the operation occurs directly on the array due to passed by reference, cloning may required
         */
        // do validation check of boundary index
        // set the next index to 0 if current index is the end index
        int nextIndex;
        if (index == array.length - 1) {
            nextIndex = 0;
        } else {
            nextIndex = index + 1;
        }
        // swapping
        int temp = array[index];
        array[index] = array[nextIndex];
        array[nextIndex] = temp;

        // return next index so this can be used in the delta function
        return nextIndex;
    }

    public void setObjectiveFunction(ObjectiveFunctionInterface f) {

        // CHECK store the objective function so we can use it later!
        this.f = f;
    }


    protected void shuffleArray(int[] ar) {
        // Fisher–Yates shuffle, runs in O(n)
        for (int i = ar.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}
