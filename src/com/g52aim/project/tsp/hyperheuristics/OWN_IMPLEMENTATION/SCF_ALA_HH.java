package com.g52aim.project.tsp.hyperheuristics.OWN_IMPLEMENTATION;


import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import AbstractClasses.ProblemDomain.HeuristicType;

public class SCF_ALA_HH extends HyperHeuristic {

    private final int LIST_LENGTH;

    public SCF_ALA_HH(long seed, int listLength) {

        super(seed);

        this.LIST_LENGTH = listLength;
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
        int[] heuristicStats = new int[heuristicSet.length];


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


        SimplifiedChoiceFunction scf = new SimplifiedChoiceFunction(heuristicSet);
        problem.initialiseSolution(CURRENT_SOLUTION_INDEX);
        double current = problem.getFunctionValue(CURRENT_SOLUTION_INDEX);
        AverageLateAcceptance ala = new AverageLateAcceptance(this.LIST_LENGTH, current);
        double candidate;
        boolean accept;
        int iteration = 0;

        while (!hasTimeExpired()) {
            Heuristic heuristicToApply = scf.selectHeuristicToApply();
            problem.setDepthOfSearch(heuristicToApply.getConfiguration().getDos());
            problem.setIntensityOfMutation(heuristicToApply.getConfiguration().getIom());
            heuristicStats[heuristicToApply.getUniqueId()] += 1;
            Long startTime = System.nanoTime();
            candidate = problem.applyHeuristic(heuristicToApply.getHeuristicId(), CURRENT_SOLUTION_INDEX, CANDIDATE_SOLUTION_INDEX);
            Long endTime = System.nanoTime();
            scf.updateHeuristicData(heuristicToApply, endTime, endTime - startTime, current, candidate);

            // accepting solution
            // accepting the solution if it is better than the current incumbent solution
            // or it is better than the threshold value
            accept = candidate <= current || candidate <= ala.getThresholdValue();
            if (accept) {
                ala.update(candidate);
                // copy from candidate to current solution index
                problem.copySolution(1, 0);
                current = candidate;
            } else {
                ala.update(current);
            }
            // System.out.println(iteration + "\t" + current + "\t" + candidate + "\t" + accept);
            // iteration++;
        }
        for (int i = 0; i < heuristicStats.length; i++) {
            System.out.println("heuristic: " + i + " " + heuristicStats[i]);
        }
    }

    public String toString() {

        return "MCF_ALA_HH";
    }

}
