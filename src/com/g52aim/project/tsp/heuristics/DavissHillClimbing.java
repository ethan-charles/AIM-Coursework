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
        System.out.println("Heuristic: Daviss Hill");

        // CHECK implement Davis's Hill Climbing using adjacent swaps

        int times = getIncrementalTimes(dos);

        int n = solution.getNumberOfCities();
        int[] orderList = new int[n];
        for (int i = 0; i < n; i++) {
            orderList[i] = i;
        }

        for (int counter = 0; counter < times; counter++) {
            int[] shuffledList = orderList.clone();
            // shuffle the array
            shuffleArray(shuffledList);
            ActualDHC(solution, shuffledList);
        }
        return solution.getObjectiveFunctionValue();
    }

    private void ActualDHC(TSPSolutionInterface solution, int[] orderList) {
        int[] solutionArray = solution.getSolutionRepresentation().getSolutionRepresentation();
        int n = solution.getNumberOfCities();

        for (int index = 0; index < n; index++) {
            // will have to clone the array, so it doesn't change
            solutionArray = solutionArray.clone();
            int swapPoint = orderList[index];

            int nextSwapPoint = ActualAdjacentSwap(solutionArray, swapPoint);
            double delta = f.computeDeltaAdjSwap(solution.getSolutionRepresentation().getSolutionRepresentation(), solutionArray, swapPoint, nextSwapPoint);

            if (delta < 0) {
                //  only accept the new representation if has negative changes of cost
                solution.updateSolutionRepresentationWithDelta(solutionArray, delta);
            } else {
                //  revert back the representation
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
