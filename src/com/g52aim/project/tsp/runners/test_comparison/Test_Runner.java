package com.g52aim.project.tsp.runners.test_comparison;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import com.g52aim.project.tsp.G52AIMTSP;
import com.g52aim.project.tsp.hyperheuristics.my_own_hyperheurisitics.SMCF_ALA_HH;
import com.g52aim.project.tsp.hyperheuristics.SR_IE_HH;
import com.g52aim.project.tsp.runners.TestFrameConfig;


public class Test_Runner {

    final TestFrameConfig config;

    final int TOTAL_RUNS;
    final String[] DOMAINS;
    final int[][] INSTANCE_IDs;
    final long RUN_TIME;
    final long[] SEEDS;

    public Test_Runner(TestFrameConfig config) {
        this.config = config;
        this.TOTAL_RUNS = config.getTotalRuns();
        this.DOMAINS = config.getDomains();
        this.INSTANCE_IDs = config.getInstanceIDs();
        this.SEEDS = config.getSeeds();
        this.RUN_TIME = config.getRunTime();
    }

    public void runTests() {

        String hyperHeuristicName1 = null;
        String hyperHeuristicName2 = null;
        String problemDomain = null;
        double[] bestSolutionFitness_h1 = new double[TOTAL_RUNS];
        double[] bestSolutionFitness_h2 = new double[TOTAL_RUNS];

        for (int domain = 0; domain < DOMAINS.length; domain++) {
            for (int instance = 0; instance < INSTANCE_IDs[domain].length; instance++) {
                int instanceID = INSTANCE_IDs[domain][instance];
                for (int run = 0; run < TOTAL_RUNS; run++) {
                    long seed = SEEDS[run];
                    HyperHeuristic hh1 = new SMCF_ALA_HH(seed, 50, 120, 50);
                    ProblemDomain problem = getNewDomain(DOMAINS[domain], seed);
                    problem.loadInstance(instanceID);
                    hh1.setTimeLimit(RUN_TIME);
                    hh1.loadProblemDomain(problem);
                    hh1.run();

                    hyperHeuristicName1 = hh1.toString();
                    bestSolutionFitness_h1[run] = hh1.getBestSolutionValue();
                    System.out.println("Instance ID: " + instanceID + "\tTrial: " + run + "\tf(s_{best}) = " + hh1.getBestSolutionValue());

                    // setting up for 2nd hh
                    HyperHeuristic hh2 = new SR_IE_HH(seed);
                    problem = getNewDomain(DOMAINS[domain], seed);
                    problem.loadInstance(instanceID);
                    problemDomain = problem.toString();
                    hh2.setTimeLimit(RUN_TIME);
                    hh2.loadProblemDomain(problem);
                    hh2.run();

                    hyperHeuristicName2 = hh2.toString();
                    bestSolutionFitness_h2[run] = hh2.getBestSolutionValue();
                    // System.out.println("Instance ID: " + instanceID + "\tTrial: " + run + "\tf(s_{best}) = " + hh2.getBestSolutionValue());
                }

                // saving 1st hh
                StringBuilder sb = new StringBuilder();
                sb.append(hyperHeuristicName1 + "," + RUN_TIME + "," + problemDomain + "," + instanceID);
                double average = 0;
                for (double ofv : bestSolutionFitness_h1) {
                    average += ofv;
                    sb.append("," + ofv);
                }
                sb.append("," + average / 11.0);
                config.saveData("G52AIM_HH1" + ".csv", sb.toString());

                // saving 2nd hh
                sb = new StringBuilder();
                sb.append(hyperHeuristicName2 + "," + RUN_TIME + "," + problemDomain + "," + instanceID);
                average = 0;
                for (double ofv : bestSolutionFitness_h2) {
                    average += ofv;
                    sb.append("," + ofv);
                }
                sb.append("," + average / 11.0);
                config.saveData("G52AIM_HH1" + ".csv", sb.toString());
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

        new Test_Runner(new TestFrameConfig()).runTests();
    }
}
