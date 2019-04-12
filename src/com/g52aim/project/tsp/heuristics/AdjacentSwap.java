package com.g52aim.project.tsp.heuristics;

import java.util.Random;

import com.g52aim.project.tsp.interfaces.HeuristicInterface;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;

/**
 * @author Warren G. Jackson
 */
public class AdjacentSwap extends HeuristicOperators implements HeuristicInterface {

    public AdjacentSwap(Random random) {

        super(random);
    }

    @Override
    public double apply(TSPSolutionInterface solution, double depthOfSearch, double intensityOfMutation) {

        // CHECK implementation of adjacent swap
        int times = getExponentialTimes(intensityOfMutation);
        int[] array = solution.getSolutionRepresentation().getSolutionRepresentation();

        // performs adjacent swaps of n times
        for (int i = 0; i < times; i++) {
            int firstCity = random.nextInt(array.length);
            ActualAdjacentSwap(array, firstCity);
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
        return true;
    }

    @Override
    public boolean usesDepthOfSearch() {

        return true;
    }

}
