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

        for (int counter = 0; counter < times; counter++) {
            ActualNextDescent(solution);
        }
        return solution.getObjectiveFunctionValue();
    }


    private void ActualNextDescent(TSPSolutionInterface solution) {
        // don't ever update the solution find delta only, update means accept the solution
        int[] solutionArray = solution.getSolutionRepresentation().getSolutionRepresentation();
        // have to clone
        int n = solution.getNumberOfCities();
        for (int index = 0; index < n; index++) {
            solutionArray = solutionArray.clone();
            ActualAdjacentSwap(solutionArray, index);

            double delta = solution.computeDeltaAdjSwap(solutionArray, index);
            if (delta < 0) {
                //  only accept the new representation if has negative changes of cost
                solution.updateSolutionRepresentationWithDelta(solutionArray, delta);
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
