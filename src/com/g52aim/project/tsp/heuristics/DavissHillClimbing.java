package com.g52aim.project.tsp.heuristics;

import java.util.Random;

import com.g52aim.project.tsp.interfaces.HeuristicInterface;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;
import com.g52aim.project.tsp.solution.TSPSolution;


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

        // TODO implement Davis's Hill Climbing using adjacent swaps
        int searchTimes = getIncrementalTimes(dos);
        int mutationTimes = getIncrementalTimes(iom);
        int[] array = solution.getSolutionRepresentation().getSolutionRepresentation();

        for (int i = 0; i < searchTimes; i++) {
            TSPSolutionInterface tempSolution = solution.clone();
            double currentFitness = tempSolution.getObjectiveFunctionValue();


            // perform adjacent swap


        }
        return -1;
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

        return false;
    }

    @Override
    public boolean usesDepthOfSearch() {

        return false;
    }
}
