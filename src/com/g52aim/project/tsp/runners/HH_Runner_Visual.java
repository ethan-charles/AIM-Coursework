package com.g52aim.project.tsp.runners;

import java.awt.Color;
import java.util.Arrays;

import com.g52aim.project.tsp.G52AIMTSP;
import com.g52aim.project.tsp.instance.Location;
import com.g52aim.project.tsp.solution.TSPSolution;
import com.g52aim.project.tsp.visualiser.TSPView;

import AbstractClasses.HyperHeuristic;

/**
 * @author Warren G. Jackson
 * <p>
 * Runs a hyper-heuristic using a default configuration then displays the best solution found.
 */
public abstract class HH_Runner_Visual {

    public HH_Runner_Visual() {

    }

    public void run() {

        long seed = 15032018l;
        long timeLimit = 60_000l;
        G52AIMTSP problem = new G52AIMTSP(seed);
        problem.loadInstance(0);
        HyperHeuristic hh = getHyperHeuristic(seed);
        hh.setTimeLimit(timeLimit);
        hh.loadProblemDomain(problem);
        hh.run();

        System.out.println("f(s_best) = " + hh.getBestSolutionValue());
        Location[] route = transformSolution((TSPSolution) (problem).getBestSolution(), problem);
        new TSPView(route, Color.RED, Color.GREEN);
    }

    /**
     * Transforms the best solution found, represented as a TSPSolution, into an ordering of Location's
     * which the visualiser tool uses to draw the tour.
     */
    protected Location[] transformSolution(TSPSolution solution, G52AIMTSP problem) {

        return problem.getRouteOrderedByLocations();
    }

    /**
     * Allows a general visualiser runner by making the HyperHeuristic abstract.
     * You can sub-class this class to run any hyper-heuristic that you want.
     */
    protected abstract HyperHeuristic getHyperHeuristic(long seed);
}