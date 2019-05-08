package com.g52aim.project.tsp.runners;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import com.g52aim.project.tsp.G52AIMTSP;
import com.g52aim.project.tsp.hyperheuristics.OWN_IMPLEMENTATION.SCF_ALA_HH;


public class Parameter_Config_Runner {

    final TestFrameConfig config;

    final int TOTAL_RUNS;
    final String[] DOMAINS;
    final int[][] INSTANCE_IDs;
    final long RUN_TIME;
    final long[] SEEDS;

    // sets of parameter to find which works the best
    final int[] LIST_LENGTH;
    final int[] ALPHA;

    public Parameter_Config_Runner(TestFrameConfig config) {

        this.config = config;

        this.TOTAL_RUNS = config.getTotalRuns();
        this.DOMAINS = config.PROBLEM_DOMAINS;
        this.INSTANCE_IDs = config.getInstanceIDs();
        this.SEEDS = config.getSeeds();
        this.RUN_TIME = config.getRunTime();

        this.LIST_LENGTH = new int[]{10, 20,  100, 200};
        // this.LIST_LENGTH = new int[]{5, 9, 13, 17, 21, 25};
        // larger alpha gives more weight to f1
        this.ALPHA = new int[]{5, 50, 500, 5000, 50000};
    }

    public void runTests() {

        String hyperHeuristicName1 = null;
        double[] bestSolutionFitness_h1 = new double[TOTAL_RUNS];

        for (int domain = 0; domain < DOMAINS.length; domain++) {

            for (int instance = 0; instance < INSTANCE_IDs[domain].length; instance++) {

                int instanceID = INSTANCE_IDs[domain][instance];

                for (int i = 0; i < LIST_LENGTH.length; i++) {
                    for (int k = 0; k < ALPHA.length; k++) {
                        for (int run = 0; run < TOTAL_RUNS; run++) {
                            long seed = SEEDS[run];
                            HyperHeuristic hh = new SCF_ALA_HH(seed, LIST_LENGTH[i], ALPHA[k]);
                            ProblemDomain problem = getNewDomain(DOMAINS[domain], seed);
                            problem.loadInstance(instanceID);
                            hh.setTimeLimit(RUN_TIME);
                            hh.loadProblemDomain(problem);
                            hh.run();

                            hyperHeuristicName1 = hh.toString();
                            bestSolutionFitness_h1[run] = hh.getBestSolutionValue();
                        }
                        StringBuilder sb = new StringBuilder();
                        sb.append(hyperHeuristicName1 + ", " + instanceID + ", " + LIST_LENGTH[i] + ", " + ALPHA[k]);
                        for (double ofv : bestSolutionFitness_h1) {
                            sb.append("," + ofv);
                        }
                        config.saveData("Parameter_Test" + ".csv", sb.toString());

                    }
                }

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

        new Parameter_Config_Runner(new TestFrameConfig()).runTests();
    }
}
