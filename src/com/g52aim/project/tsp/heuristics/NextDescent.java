package com.g52aim.project.tsp.heuristics;


import java.util.Random;

import com.g52aim.project.tsp.interfaces.HeuristicInterface;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;


/**
 * @author Warren G. Jackson
 * Performs adjacent swap, returning the first solution with strict improvement
 */
public class NextDescent extends HeuristicOperators implements HeuristicInterface {

    public NextDescent(Random random) {

        super(random);
    }

    @Override
    public double apply(TSPSolutionInterface solution, double dos, double iom) {
        System.out.println("Heuristic: Next Descent");

        // CHECKED implementation of Next Descent using adjacent swap for the
        int times = getIncrementalTimes(dos);

        for (int counter = 0; counter < times; counter++) {
            ActualNextDescent(solution);
        }
        return solution.getObjectiveFunctionValue();
    }


    private void ActualNextDescent(TSPSolutionInterface solution) {
        // CHECKED

        int[] solutionArray = solution.getSolutionRepresentation().getSolutionRepresentation();
        // have to clone
        int numberOfCities = solution.getNumberOfCities();
        for (int index = 0; index < numberOfCities; index++) {
            solutionArray = solutionArray.clone();

            int nextSwapPoint = ActualAdjacentSwap(solutionArray, index, numberOfCities);
            double delta = f.computeDeltaAdjSwap(solution.getSolutionRepresentation().getSolutionRepresentation(), solutionArray, index, nextSwapPoint);
            if (delta < 0) {
                //  persist the representation if improvement is made
                solution.updateSolutionRepresentationWithDelta(solutionArray, delta);
            } else {
                //  revert pointing back to the representation
                solutionArray = solution.getSolutionRepresentation().getSolutionRepresentation();
            }
        }
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

        return false;
    }

    @Override
    public boolean usesDepthOfSearch() {

        return true;
    }
}
