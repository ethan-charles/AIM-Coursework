package com.g52aim.project.tsp.heuristics;

import java.util.Random;

import com.g52aim.project.tsp.interfaces.ObjectiveFunctionInterface;

/**
 * @author Warren G. Jackson
 * <p>
 * to save having to re-implement them in all
 * your other heuristics!
 * <p>
 * <p>
 * If this this concept it new to you, you may want
 * to read this article on inheritance:
 * https://www.tutorialspoint.com/java/java_inheritance.htm
 */
public class CrossoverHeuristicOperators {

    protected final Random random;
    protected ObjectiveFunctionInterface f = null;

    public CrossoverHeuristicOperators(Random random) {

        this.random = random;
    }

    public int getIncrementalTimes(double strength) {
        /**
         * get the number of times in increment fashion (+1)
         */
        // CHECKED
        int times = 0;

        if (strength < 0.2) {
            times = 1;
        } else if (strength < 0.4) {
            times = 2;
        } else if (strength < 0.6) {
            times = 3;
        } else if (strength < 0.8) {
            times = 4;
        } else if (strength < 1.0) {
            times = 5;
        } else if (strength == 1.0){
            times = 6;
        }
        return times;
    }

    public void setObjectiveFunction(ObjectiveFunctionInterface f) {

        // CHECKED store the objective function so we can use it later!
        this.f = f;
    }
}
