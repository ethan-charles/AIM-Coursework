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

        // CHECK implementation of Next Descent using adjacent swap for the
        int times = getIncrementalTimes(dos);

        for (int i = 0; i < times; i++) {
            ActualNextDescent(solution);
        }
        return solution.getObjectiveFunctionValue();
    }


    private void ActualNextDescent(TSPSolutionInterface solution) {
        // don't ever update the solution find delta only, update means accept the solution
        int[] solutionRepresentation = solution.getSolutionRepresentation().getSolutionRepresentation();
        // have to clone
        int n = solution.getNumberOfCities();
        for (int i = 0; i < n; i++) {
            int[] clonedRepresentation = solutionRepresentation.clone();
            ActualAdjacentSwap(clonedRepresentation, i);

            double newDelta = solution.computeDeltaValue(clonedRepresentation);

            // improve or equal
            if (newDelta >= 0) {
                solution.updateSolutionRepresentationWithDelta(clonedRepresentation, newDelta);
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
