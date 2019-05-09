package com.g52aim.project.tsp.hyperheuristics.OWN_IMPLEMENTATION;


import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import AbstractClasses.ProblemDomain.HeuristicType;
import com.g52aim.project.tsp.G52AIMTSP;
import com.g52aim.project.tsp.SolutionPrinter;
import com.g52aim.project.tsp.instance.Location;

import java.util.ArrayList;
import java.util.List;

public class SCF_ALA_HH extends HyperHeuristic {

    private final int LIST_LENGTH;
    private final int ALPHA;

    public SCF_ALA_HH(long seed, int listLength, int alpha) {
        super(seed);
        this.LIST_LENGTH = listLength;
        this.ALPHA = alpha;
    }

    public SCF_ALA_HH(long seed, int listLength) {
        // default settings
        super(seed);
        this.LIST_LENGTH = listLength;
        this.ALPHA = 50;
    }

    public void solve(ProblemDomain problem) {

        //  find all heuristics and create all combinations with the IOM and DOS settings
        //  IOM and DOS settings can be stored as 'HeuristicConfiguration' Objects
        //  heuristics along with their 'HeuristicConfiguration' and runtime statistics (for the choice function) can be stored as 'Heuristic' Objects
        final int CURRENT_SOLUTION_INDEX = 0;
        final int CANDIDATE_SOLUTION_INDEX = 1;

        // gather the heuristic index
        int[] mutationHeuristicSet = problem.getHeuristicsOfType(HeuristicType.MUTATION);
        int[] localSearchHeuristicSet = problem.getHeuristicsOfType(HeuristicType.LOCAL_SEARCH);

        double[] strength = new double[]{0, 0.2, 0.4, 0.6, 0.8, 1.0};

        int numberOfVariant = strength.length;
        Heuristic[] heuristicSet = new Heuristic[
                mutationHeuristicSet.length * numberOfVariant +
                        localSearchHeuristicSet.length * numberOfVariant
                ];

        int indexCounter = 0;
        // initialise the same starting time for all heuristics
        long initialStartTime = System.nanoTime();
        for (int i = 0; i < mutationHeuristicSet.length; i++) {
            for (int j = 0; j < numberOfVariant; j++) {
                heuristicSet[indexCounter] = new Heuristic(new HeuristicConfiguration(strength[j], strength[j]), mutationHeuristicSet[i], indexCounter, initialStartTime);
                indexCounter++;
            }
        }
        for (int i = 0; i < localSearchHeuristicSet.length; i++) {
            for (int j = 0; j < numberOfVariant; j++) {
                heuristicSet[indexCounter] = new Heuristic(new HeuristicConfiguration(strength[j], strength[j]), localSearchHeuristicSet[i], indexCounter, initialStartTime);
                indexCounter++;
            }
        }

        problem.initialiseSolution(CURRENT_SOLUTION_INDEX);
        double current = problem.getFunctionValue(CURRENT_SOLUTION_INDEX);
        SimplifiedChoiceFunction scf = new SimplifiedChoiceFunction(heuristicSet, ALPHA);
        AverageLateAcceptance ala = new AverageLateAcceptance(this.LIST_LENGTH, current);
        double candidate;
        boolean accept;
        int stuckCounter = 0;
        while (!hasTimeExpired()) {
            Heuristic heuristicToApply = scf.selectHeuristicToApply();
            problem.setDepthOfSearch(heuristicToApply.getConfiguration().getDos());
            problem.setIntensityOfMutation(heuristicToApply.getConfiguration().getIom());
            Long startTime = System.nanoTime();
            candidate = problem.applyHeuristic(heuristicToApply.getHeuristicId(), CURRENT_SOLUTION_INDEX, CANDIDATE_SOLUTION_INDEX);
            Long endTime = System.nanoTime();
            double diff = candidate - current;
            scf.updateHeuristicData(heuristicToApply, endTime, endTime - startTime, diff);

            // accepting solution
            // accepting the solution if it is better than the current incumbent solution
            // or it is better than the threshold value
            accept = candidate < current;
            if (accept) {
                // eagerly accepting
                scf.setLastImprovement(diff);
                ala.update(candidate);
                problem.copySolution(1, 0);
                current = candidate;
                // reset the stuck counter
                stuckCounter = 0;
            } else {
                stuckCounter++;
                ala.update(current);
                // stuck means no generate solution that is better than the current incumbent
                // if stuck more than 20 times, use the ALA
                if (stuckCounter > 20 && candidate < ala.getThresholdValue()) {
                    problem.copySolution(1, 0);
                    current = candidate;
                }
            }
            // System.out.println(iteration + "\t" + current + "\t" + candidate + "\t" + accept);
            // iteration++;
        }

        // int[] cities = ((G52AIMTSP) problem).getBestSolution().getSolutionRepresentation().getSolutionRepresentation();
        // List<Location> routeLocations = new ArrayList<>();
        //
        // for (int i = 0; i < ((G52AIMTSP) problem).getBestSolution().getNumberOfCities(); i++) {
        //     routeLocations.add(((G52AIMTSP) problem).instance.getLocationForCity(cities[i]));
        // }
        // SolutionPrinter.printSolution(routeLocations);
    }

    public String toString() {

        return "MCF_ALA_HH";
    }

}
