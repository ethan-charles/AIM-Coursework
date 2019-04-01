package com.g52aim.project.tsp.heuristics;

import java.util.Random;

import com.g52aim.project.tsp.interfaces.HeuristicInterface;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;

/**
 * @author Warren G. Jackson
 */
public class TwoOpt extends HeuristicOperators implements HeuristicInterface {

    public TwoOpt(Random random) {

        super(random);
    }

    @Override
    public double apply(TSPSolutionInterface solution, double dos, double iom) {

        // CHECK implementation of two-opt swap heuristic
        int times = getIncrementalTimes(iom);
        int[] array = solution.getSolutionRepresentation().getSolutionRepresentation();

        // performs two-opt swaps of n times
        for (int i = 0; i < times; i++) {
            // need checking
            int[] randomIndexes = random.ints(0, array.length).distinct().limit(2).toArray();
            int firstCity = randomIndexes[0];
            int secondCity = randomIndexes[1];
            // swap
            int temp = array[firstCity];
            array[firstCity] = array[secondCity];
            array[secondCity] = temp;
        }

        // set to the new solution
        solution.getSolutionRepresentation().setSolutionRepresentation(array);
        return solution.getObjectiveFunctionValue();
    }

    /*
     * TODO update the methods below to return the correct boolean value.
     */

    @Override
    public boolean isCrossover() {

        return false;
    }

    @Override
    public boolean usesIntensityOfMutation() {

        return false;
    }

    @Override
    public boolean usesDepthOfSearch() {

        return false;
    }

}
