package com.g52aim.project.tsp.heuristics;

import java.util.Random;

import com.g52aim.project.tsp.interfaces.ObjectiveFunctionInterface;


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

    public int getExponentialTimes(double strength) {
        // base 2
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

    protected void ActualAdjacentSwap(int[] array, int firstCity) {
        // validation check of boundary index
        int secondCity = firstCity + 1;
        if (firstCity == array.length - 1) {
            secondCity = 1;
        }
        // swap
        int temp = array[firstCity];
        array[firstCity] = array[secondCity];
        array[secondCity] = temp;
    }

    public void setObjectiveFunction(ObjectiveFunctionInterface f) {

        // CHECK store the objective function so we can use it later!
        this.f = f;
    }

    protected void shuffleArray(int[] ar) {
        for (int i = ar.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}
