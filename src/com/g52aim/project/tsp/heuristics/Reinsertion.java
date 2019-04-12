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

        int times = 0;
        int[] array = solution.getSolutionRepresentation().getSolutionRepresentation();
        if (intensityOfMutation >= 0 && intensityOfMutation < 0.2) {
            times = 1;
        } else if (intensityOfMutation < 0.4) {
            times = 2;
        } else if (intensityOfMutation < 0.6) {
            times = 3;
        } else if (intensityOfMutation < 0.8) {
            times = 4;
        } else if (intensityOfMutation < 1.0) {
            times = 5;
        } else if (intensityOfMutation == 1.0) {
            times = 6;
        }

        // performs reinsertion of n times
        for (int i = 0; i < times; i++) {
            // generate two unique index, one to select the inserted element and one to select the reinserted position
            int[] randomIndexes = random.ints(0, array.length).distinct().limit(2).toArray();
            int removedPosition = randomIndexes[0];
            int insertPosition = randomIndexes[1];

            int temp = array[removedPosition];
            int[] result = new int[array.length];

            // remove at removed position
            // copy without the removed element
            System.arraycopy(array, 0, result, 0, removedPosition);
            System.arraycopy(array, removedPosition + 1, result, removedPosition, array.length - removedPosition - 1);
            // reassigning result
            array = result;

            // insert at insert position
            System.arraycopy(array, 0, result, 0, insertPosition);
            System.arraycopy(array, insertPosition, result, insertPosition + 1, array.length - insertPosition - 1);
            result[removedPosition] = temp;
            array = result;
        }

        // set to the new solution
        solution.getSolutionRepresentation().setSolutionRepresentation(array);
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
