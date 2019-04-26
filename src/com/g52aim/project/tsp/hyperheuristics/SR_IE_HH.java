package com.g52aim.project.tsp.hyperheuristics;


import java.util.ArrayList;
import java.util.List;

import com.g52aim.project.tsp.G52AIMTSP;
import com.g52aim.project.tsp.SolutionPrinter;
import com.g52aim.project.tsp.instance.Location;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

public class SR_IE_HH extends HyperHeuristic {

    public SR_IE_HH(long seed) {

        super(seed);
    }

    @Override
    protected void solve(ProblemDomain problem) {

        problem.initialiseSolution(0);
        double current = problem.getFunctionValue(0);

        problem.setIntensityOfMutation(0.2);
        problem.setDepthOfSearch(0.2);

        int h = 1;
        long iteration = 0;
        boolean accept;
        System.out.println("Iteration\tf(s)\tf(s')\tAccept");

        while(!hasTimeExpired()) {

            h = rng.nextInt(problem.getNumberOfHeuristics());
            double candidate = problem.applyHeuristic(h, 0, 1);

            accept = candidate <= current;
            if(accept) {

                problem.copySolution(1, 0);
                current = candidate;
            }

            System.out.println(iteration + "\t" + current + "\t" + candidate + "\t" + accept);
            iteration++;
        }

        int[] cities = ((G52AIMTSP) problem).getBestSolution().getSolutionRepresentation().getSolutionRepresentation();
        List<Location> routeLocations = new ArrayList<>();

        for(int i = 0; i < ((G52AIMTSP) problem).getBestSolution().getNumberOfCities(); i++) {
            routeLocations.add(((G52AIMTSP) problem).instance.getLocationForCity(cities[i]));
        }
        SolutionPrinter.printSolution(routeLocations);
    }

    @Override
    public String toString() {

        return "SR_IE_HH";
    }
}