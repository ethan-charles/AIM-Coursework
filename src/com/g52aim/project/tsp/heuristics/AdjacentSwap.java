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
        System.out.println("Heuristic: Adjacent Swap");

        // CHECK implementation of adjacent swap
        int times = getQuadraticTimes(intensityOfMutation);
        int[] solutionArray = solution.getSolutionRepresentation().getSolutionRepresentation();
        int n = solution.getNumberOfCities();

        for (int counter = 0; counter < times; counter++) {
            int swapPoint = random.nextInt(n);
            solutionArray = solutionArray.clone();
            ActualAdjacentSwap(solutionArray, swapPoint);
            double delta = solution.computeDeltaAdjSwap(solutionArray, swapPoint);
            solution.updateSolutionRepresentationWithDelta(solutionArray, delta);
        }
        return solution.getObjectiveFunctionValue();
    }

    /*
     * CHECK update the methods below to return the correct boolean value.
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

        return false;
    }

}
