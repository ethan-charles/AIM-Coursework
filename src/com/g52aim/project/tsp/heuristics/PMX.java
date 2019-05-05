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
        return solution.getObjectiveFunctionValue();
    }

    @Override
    public double apply(TSPSolutionInterface p1, TSPSolutionInterface p2,
                        TSPSolutionInterface c, double depthOfSearch, double intensityOfMutation) {

        // CHECK implementation of ordered crossover
        int[] p1Array = p1.getSolutionRepresentation().getSolutionRepresentation().clone();
        int[] p2Array = p2.getSolutionRepresentation().getSolutionRepresentation().clone();
        int n = p1Array.length;

        int times = getIncrementalTimes(intensityOfMutation);
        int[][] childTuple;

        for (int counter = 0; counter < times; counter++) {
            int firstCutPoint = random.nextInt(n);
            int secondCutPoint;
            for (secondCutPoint = random.nextInt(n); secondCutPoint == firstCutPoint; ) {
                secondCutPoint = random.nextInt(n);
            }
            childTuple = ActualPMX(p1Array, p2Array, firstCutPoint, secondCutPoint);
            int[] child1 = childTuple[0];
            int[] child2 = childTuple[1];
            p1Array = child1;
            p2Array = child2;
        }

        // update
        if (random.nextInt(2) == 0) {
            c.updateSolutionRepresentation(p1Array);
        } else {
            c.updateSolutionRepresentation(p2Array);
        }
        return c.getObjectiveFunctionValue();
    }

    private int[][] ActualPMX(int[] p1Array, int[] p2Array, int firstCutPoint, int secondCutPoint) {
        int[] p1copy = p1Array.clone();
        int[] p2copy = p2Array.clone();
        int n = p1Array.length;

        int numberOfPointsWillStay;
        if (secondCutPoint < firstCutPoint) {
            numberOfPointsWillStay = firstCutPoint;
            firstCutPoint = secondCutPoint;
            secondCutPoint = numberOfPointsWillStay;
        }
        if (secondCutPoint - firstCutPoint == n - 1) {
            return new int[][]{p2Array, p1Array};
        }
        // create mapping of  cities to cities
        int[] mapP1 = new int[n];
        int[] mapP2 = new int[n];
        for (int i = 0; i < firstCutPoint; i++) {
            mapP1[p1Array[i] - 1] = -1;
            mapP2[p2Array[i] - 1] = -1;
        }
        for (int i = firstCutPoint; i <= secondCutPoint; i++) {
            mapP1[p1Array[i] - 1] = p2Array[i];
            mapP2[p2Array[i] - 1] = p1Array[i];
        }
        for (int i = secondCutPoint + 1; i < n; i++) {
            // mapP1, contains mapping from P2 to P1, used by p1
            mapP1[p1Array[i] - 1] = -1;
            // mapP2, contains mapping from P1 to P2, used by p2
            mapP2[p2Array[i] - 1] = -1;
        }

        for (int i = 0; i < firstCutPoint; i++) {
            int temp = mapP1[p2Array[i] - 1];
            if (temp == -1) {
                p1copy[i] = p2Array[i];
            } else {
                // if the mapping is already exists in existing mapping, redirect to that
                for (; mapP1[temp - 1] != -1; ) {
                    temp = mapP1[temp - 1];
                }
                p1copy[i] = temp;
            }
            temp = mapP2[p1Array[i] - 1];
            if (temp == -1) {
                p2copy[i] = p1Array[i];
            } else {
                for (; mapP2[temp - 1] != -1; ) {
                    temp = mapP2[temp - 1];
                }
                p2copy[i] = temp;
            }
        }

        for (int i = secondCutPoint + 1; i < n; i++) {
            int temp = mapP1[p2Array[i] - 1];
            if (temp == -1) {
                p1copy[i] = p2Array[i];
            } else {
                for (; mapP1[temp - 1] != -1; ) {
                    temp = mapP1[temp - 1];
                }
                p1copy[i] = temp;
            }
            temp = mapP2[p1Array[i] - 1];
            if (temp == -1) {
                p2copy[i] = p1Array[i];
            } else {
                for (; mapP2[temp - 1] != -1; ) {
                    temp = mapP2[temp - 1];
                }
                p2copy[i] = temp;
            }
        }
        return new int[][]{p2copy, p1copy};
    }
    /*
     * CHECK update the methods below to return the correct boolean value.
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
