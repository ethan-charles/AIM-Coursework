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
        System.out.println("Heuristic: Two Opt");
        // CHECK implementation of two-opt swap heuristic
        int times = getIncrementalTimes(iom);
        int[] solutionArray = solution.getSolutionRepresentation().getSolutionRepresentation();
        int n = solution.getNumberOfCities();

        // performs two-opt swaps of n times
        for (int i = 0; i < times; i++) {
            int firstSwapPoint = random.nextInt(n);
            int secondSwapPoint;
            for (secondSwapPoint = random.nextInt(n); firstSwapPoint == secondSwapPoint; ) {
                // continue until a different index is generated
                secondSwapPoint = random.nextInt(n);
            }
            solutionArray = solutionArray.clone();
            ActualTwoOpt(solutionArray, firstSwapPoint, secondSwapPoint);

            // compute the delta
            double delta = solution.computeDeltaTwoOpt(solutionArray, firstSwapPoint, secondSwapPoint);
            // set the new representation and new objective value
            solution.updateSolutionRepresentationWithDelta(solutionArray, delta);
        }

        return solution.getObjectiveFunctionValue();
    }

    private void ActualTwoOpt(int[] solutionArray, int firstSwapPoint, int secondSwapPoint) {
        /**
         * Caveat: the operation occurs directly on the array due to passed by reference, cloning may required
         */
        int temp = solutionArray[firstSwapPoint];
        solutionArray[firstSwapPoint] = solutionArray[secondSwapPoint];
        solutionArray[secondSwapPoint] = temp;
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
