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
        int[] array = solution.getSolutionRepresentation().getSolutionRepresentation().clone();
        int n = array.length;

        // performs two-opt swaps of n times
        for (int i = 0; i < times; i++) {
            int firstIndex = random.nextInt(n);
            int secondIndex;
            for (secondIndex = random.nextInt(n); firstIndex == secondIndex; ) {
                // continue until a different index is generated
                secondIndex = random.nextInt(n);
            }

            // swap
            int temp = array[firstIndex];
            array[firstIndex] = array[secondIndex];
            array[secondIndex] = temp;
        }

        // set to the new solution
        solution.updateSolutionRepresentation(array);
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
