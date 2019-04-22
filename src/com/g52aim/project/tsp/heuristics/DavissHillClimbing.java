package com.g52aim.project.tsp.heuristics;

import java.util.Random;

import com.g52aim.project.tsp.interfaces.HeuristicInterface;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;


/**
 * @author Warren G. Jackson
 * Performs adjacent swap, returning the first solution with strict improvement
 */
public class DavissHillClimbing extends HeuristicOperators implements HeuristicInterface {

    public DavissHillClimbing(Random random) {

        super(random);
    }

    @Override
    public double apply(TSPSolutionInterface solution, double dos, double iom) {
        System.out.println("DavissHill");

        // CHECK implement Davis's Hill Climbing using adjacent swaps

        int times = getIncrementalTimes(dos);

        int n = solution.getNumberOfCities();
        int[] orderList = new int[n];
        for (int i = 0; i < n; i++) {
            orderList[i] = i;
        }

        for (int i = 0; i < times; i++) {
            int[] shuffledList = orderList.clone();
            shuffleArray(shuffledList);
            ActualDHC(solution, shuffledList);
        }
        return solution.getObjectiveFunctionValue();
    }

    private void ActualDHC(TSPSolutionInterface solution, int[] orderList) {
        int[] solutionRepresentation = solution.getSolutionRepresentation().getSolutionRepresentation();
        int n = solutionRepresentation.length;

        for (int i = 0; i < n; i++) {
            // will have to clone the array, because this is linked to the actual solution array
            int[] clonedRepresentation = solutionRepresentation.clone();
            ActualAdjacentSwap(clonedRepresentation, orderList[i]);
            solution.updateSolutionRepresentation(clonedRepresentation);

            // the delta value is previously being update by new solution
            if (solution.getDeltaValue() >= 0) {
                // if strict improvement, immediately accept the solution
                // else revert back to previous solution
                solution.rollBackSolution();
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
