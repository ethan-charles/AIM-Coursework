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
        // CHECK implementation of reinsertion heuristic
        System.out.println("Reinsertion");

        int times = getIncrementalTimes(intensityOfMutation);
        int[] array = solution.getSolutionRepresentation().getSolutionRepresentation();


        // performs reinsertion of n times
        for (int i = 0; i < times; i++) {
            // generate two unique index, one to select the inserted element and one to select the reinserted position
            int n = array.length;
            int removedPosition = random.nextInt(n);
            int insertPosition;
            for (insertPosition = random.nextInt(n); removedPosition == insertPosition; ) {
                // continue until a different index is generated
                insertPosition = this.random.nextInt(n);
            }

            int temp = array[removedPosition];
            int[] result = new int[array.length];

            // remove at removed position
            // copy without the removed element
            System.arraycopy(array, 0, result, 0, removedPosition);
            System.arraycopy(array, removedPosition + 1, result, removedPosition, n - removedPosition - 1);
            // reassigning result
            array = result;

            // insert at insert position
            System.arraycopy(array, 0, result, 0, insertPosition);
            System.arraycopy(array, insertPosition-1, result, insertPosition, n - insertPosition);
            result[insertPosition] = temp;
            array = result;
        }

        // set to the new solution
        solution.updateSolutionRepresentation(array);
        return solution.getObjectiveFunctionValue();
    }

    /*
     * TODO update the methods below to return the correct boolean value.
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
