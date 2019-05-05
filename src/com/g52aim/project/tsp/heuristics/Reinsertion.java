package com.g52aim.project.tsp.heuristics;

import java.util.Random;

import com.g52aim.project.tsp.interfaces.HeuristicInterface;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;

/**
 * @author Warren G. Jackson
 */
public class Reinsertion extends HeuristicOperators implements HeuristicInterface {

    public Reinsertion(Random random) {

        super(random);
    }

    @Override
    public double apply(TSPSolutionInterface solution, double depthOfSearch, double intensityOfMutation) {
        // CHECKED implementation of reinsertion heuristic

        int times = getIncrementalTimes(intensityOfMutation);
        int[] solutionArray = solution.getSolutionRepresentation().getSolutionRepresentation();
        // previous solution array used to calculate the delta value
        int numberOfCities = solution.getNumberOfCities();

        for (int counter = 0; counter < times; counter++) {
            int removalPoint = random.nextInt(numberOfCities);
            int insertionPoint;
            for (insertionPoint = random.nextInt(numberOfCities); removalPoint == insertionPoint; ) {
                // continue until a different index is generated
                insertionPoint = random.nextInt(numberOfCities);
            }
            solutionArray = solutionArray.clone();
            ActualReinsertion(solutionArray, insertionPoint, removalPoint);

            // compute the delta
            double delta = f.computeDeltaReinsertion(solution.getSolutionRepresentation().getSolutionRepresentation(), solutionArray, removalPoint, insertionPoint);
            // set the new representation and new objective value
            solution.updateSolutionRepresentationWithDelta(solutionArray, delta);
        }

        return solution.getObjectiveFunctionValue();
    }

    private void ActualReinsertion(int[] solutionArray, int insertionPoint, int removalPoint) {
        /**
         * Caveat: the operation occurs directly on the array due to passed by reference, cloning may required
         */
        // CHECKED
        int removedElement = solutionArray[removalPoint];
        // create a temp array to facilitate the insertion process
        int[] tempArray = new int[solutionArray.length];
        int n = solutionArray.length;

        // remove at removed position
        // copy without the removed element
        System.arraycopy(solutionArray, 0, tempArray, 0, removalPoint);
        System.arraycopy(solutionArray, removalPoint + 1, tempArray, removalPoint, n - removalPoint - 1);

        // insert at insert position
        System.arraycopy(tempArray, 0, solutionArray, 0, insertionPoint);
        System.arraycopy(tempArray, insertionPoint, solutionArray, insertionPoint + 1, n - insertionPoint - 1);
        solutionArray[insertionPoint] = removedElement;
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

        return true;
    }

    @Override
    public boolean usesDepthOfSearch() {

        return false;
    }

}
