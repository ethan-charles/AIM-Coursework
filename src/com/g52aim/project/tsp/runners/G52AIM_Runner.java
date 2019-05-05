package com.g52aim.project.tsp.runners;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import com.g52aim.project.tsp.G52AIMTSP;
import com.g52aim.project.tsp.hyperheuristics.OWN_IMPLEMENTATION.SCF_ALA_HH;


public class G52AIM_Runner {

    final G52AIM_TestFrameConfig config;

    final int TOTAL_RUNS;
    final String[] DOMAINS;
    final int[][] INSTANCE_IDs;
    final long RUN_TIME;
    final long[] SEEDS;
    final int LIST_LENGTH;

    public G52AIM_Runner(G52AIM_TestFrameConfig config) {

        this.config = config;

        this.TOTAL_RUNS = config.getTotalRuns();
        this.DOMAINS = config.PROBLEM_DOMAINS;
        this.INSTANCE_IDs = config.getInstanceIDs();
        this.SEEDS = config.getSeeds();
        this.RUN_TIME = config.getRunTime();
        this.LIST_LENGTH = config.LIST_LENGTH;
    }

    public void runTests() {

        String hyperHeuristicName = null;
        String problemDomain = null;
        double[] bestSolutionFitness_s = new double[TOTAL_RUNS];

        for (int domain = 0; domain < DOMAINS.length; domain++) {

            for (int instance = 0; instance < INSTANCE_IDs[domain].length; instance++) {

                int instanceID = INSTANCE_IDs[domain][instance];

                for (int run = 0; run < TOTAL_RUNS; run++) {

                    long seed = SEEDS[run];

                    HyperHeuristic hh = new SCF_ALA_HH(seed, LIST_LENGTH);
                    ProblemDomain problem = getNewDomain(DOMAINS[domain], seed);
                    problem.loadInstance(instanceID);
                    hh.setTimeLimit(RUN_TIME);
                    hh.loadProblemDomain(problem);
                    hh.run();

                    hyperHeuristicName = hh.toString();
                    problemDomain = problem.toString();
                    bestSolutionFitness_s[run] = hh.getBestSolutionValue();
                    System.out.println("Instance ID: " + instanceID + "\tTrial: " + run + "\tf(s_{best}) = " + hh.getBestSolutionValue());
                }

                StringBuilder sb = new StringBuilder();
                sb.append(hyperHeuristicName + "," + RUN_TIME + "," + problemDomain + "," + instanceID);
                for (double ofv : bestSolutionFitness_s) {
                    sb.append("," + ofv);
                }

                config.saveData(hyperHeuristicName + ".csv", sb.toString());
            }
        }
    }

    public ProblemDomain getNewDomain(String domain, long seed) {

        ProblemDomain domainObject;

        switch (domain.toUpperCase()) {
            case "TSP":
                domainObject = new G52AIMTSP(seed);
                break;
            default:
                domainObject = null;
        }

        return domainObject;
    }

    public static void main(String[] args) {

        new G52AIM_Runner(new G52AIM_TestFrameConfig()).runTests();
    }
}
