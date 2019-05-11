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

        // CHECKED implementation of Next Descent using adjacent swap for the
        int times = getIncrementalTimes(dos);

        double objectiveFunctionValue = ActualNextDescent(solution, times);
        return objectiveFunctionValue;
    }


    private double ActualNextDescent(TSPSolutionInterface solution, int times) {
        // CHECKED

        int[] solutionArray = solution.getSolutionRepresentation().getSolutionRepresentation();
        int numberOfCities = solution.getNumberOfCities();
        int startIndex = random.nextInt(numberOfCities);
        int numberOfImprovement = 0;

        for (int index = startIndex; index < numberOfCities; index++) {
            solutionArray = solutionArray.clone();
            int nextSwapPoint = ActualAdjacentSwap(solutionArray, index, numberOfCities);
            double delta = f.computeDeltaAdjSwap(solution.getSolutionRepresentation().getSolutionRepresentation(), solutionArray, index, nextSwapPoint);
            if (delta < 0) {
                //  persist the representation if improvement is made
                solution.updateSolutionRepresentationWithDelta(solutionArray, delta);
                numberOfImprovement++;
                if (numberOfImprovement == times) {
                    return solution.getObjectiveFunctionValue();
                }
            } else {
                //  revert pointing back to the representation
                solutionArray = solution.getSolutionRepresentation().getSolutionRepresentation();
            }
        }
        for (int index = 0; index < startIndex; index++) {
            solutionArray = solutionArray.clone();
            int nextSwapPoint = ActualAdjacentSwap(solutionArray, index, numberOfCities);
            double delta = f.computeDeltaAdjSwap(solution.getSolutionRepresentation().getSolutionRepresentation(), solutionArray, index, nextSwapPoint);
            if (delta < 0) {
                //  persist the representation if improvement is made
                solution.updateSolutionRepresentationWithDelta(solutionArray, delta);
                numberOfImprovement++;
                if (numberOfImprovement == times) {
                    return solution.getObjectiveFunctionValue();
                }
            } else {
                //  revert pointing back to the representation
                solutionArray = solution.getSolutionRepresentation().getSolutionRepresentation();
            }
        }
        return solution.getObjectiveFunctionValue();
    }

    /*
     * CHECKED update the methods below to return the correct boolean value.
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
