package com.g52aim.project.tsp.heuristics;

import java.util.Random;

import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;
import com.g52aim.project.tsp.interfaces.XOHeuristicInterface;

/**
 * @author Warren G. Jackson
 */
public class PMX extends CrossoverHeuristicOperators implements XOHeuristicInterface {

    public PMX(Random random) {

        super(random);
    }

    @Override
    public double apply(TSPSolutionInterface solution, double depthOfSearch, double intensityOfMutation) {

        // invalid operation, return the same solution!
        return -1;
    }

    @Override
    public double apply(TSPSolutionInterface p1, TSPSolutionInterface p2,
                        TSPSolutionInterface c, double depthOfSearch, double intensityOfMutation) {

        // CHECK implementation of ordered crossover
        int[] p1Array = p1.getSolutionRepresentation().getSolutionRepresentation();
        int[] p2Array = p2.getSolutionRepresentation().getSolutionRepresentation();
        int times = getIncrementalTimes(intensityOfMutation);
        int[][] childTuple;
        for (int i = 0; i < times; i++) {
            childTuple = ActualPMX(p1Array, p2Array);
            int[] child1 = childTuple[0];
            int[] child2 = childTuple[1];
            p1Array = child1;
            p2Array = child2;
        }

        if (random.nextInt(2) == 0) {
            c.getSolutionRepresentation().setSolutionRepresentation(p1Array);
        } else {
            c.getSolutionRepresentation().setSolutionRepresentation(p2Array);
        }
        return c.getObjectiveFunctionValue();
    }

    private int[][] ActualPMX(int[] p1Array, int[] p2Array) {
        int n = p1Array.length;
        int[] p1copy = p1Array.clone();
        int[] p2copy = p2Array.clone();

        int cutpoint_1 = this.random.nextInt(n);
        int cutpoint_2;
        for (cutpoint_2 = this.random.nextInt(n); cutpoint_2 == cutpoint_1; ) {
            // continue until a different index is generated
            cutpoint_2 = this.random.nextInt(n);
        }

        int numberOfPointsWillStay;
        if (cutpoint_2 < cutpoint_1) {
            numberOfPointsWillStay = cutpoint_1;
            cutpoint_1 = cutpoint_2;
            cutpoint_2 = numberOfPointsWillStay;
        }

        // create mapping
        int[] mapP1 = new int[n];
        int[] mapP2 = new int[n];
        for (int i = 0; i < cutpoint_1; i++) {
            mapP1[p1Array[i]] = -1;
            mapP2[p2Array[i]] = -1;
        }
        for (int i = cutpoint_1; i < cutpoint_2; i++) {
            mapP1[p1Array[i]] = p2Array[i];
            mapP2[p2Array[i]] = p1Array[i];
        }
        for (int i = cutpoint_2; i < n; i++) {
            // mapP1, contains mapping from P2 to P1, used by p1
            mapP1[p1Array[i]] = -1;
            // mapP2, contains mapping from P1 to P2, used by p2
            mapP2[p2Array[i]] = -1;
        }

        for (int i = 0; i < cutpoint_1; i++) {
            int temp = mapP1[p2Array[i]];
            if (temp == -1) {
                p1copy[i] = p2Array[i];
            } else {
                p1copy[i] = temp;
            }
            temp = mapP2[p1Array[i]];
            if (temp == -1) {
                p2copy[i] = p1Array[i];
            } else {
                p2copy[i] = temp;
            }
        }

        for (int i = cutpoint_2; i < n; i++) {
            int temp = mapP1[p2Array[i]];
            if (temp == -1) {
                p1copy[i] = p2Array[i];
            } else {
                p1copy[i] = temp;
            }
            temp = mapP2[p1Array[i]];
            if (temp == -1) {
                p2copy[i] = p1Array[i];
            } else {
                p2copy[i] = temp;
            }
        }
        return new int[][]{p2copy, p1copy};
    }
    /*
     * TODO update the methods below to return the correct boolean value.
     */

    @Override
    public boolean isCrossover() {

        return true;
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
