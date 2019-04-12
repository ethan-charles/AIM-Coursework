package com.g52aim.project.tsp.heuristics;

import java.util.Random;

import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;
import com.g52aim.project.tsp.interfaces.XOHeuristicInterface;

/**
 * @author Warren G. Jackson
 */
public class OX extends CrossoverHeuristicOperators implements XOHeuristicInterface {

    public OX(Random random) {

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
        int[] p1Array = p1.getSolutionRepresentation().getSolutionRepresentation();
        int[] p2Array = p2.getSolutionRepresentation().getSolutionRepresentation();
        int times = getIncrementalTimes(intensityOfMutation);
        int[][] childTuple;
        for (int i = 0; i < times; i++) {
            int[] child1 = p1Array.clone();
            int[] child2 = p1Array.clone();
            // pass value not reference
            childTuple = ActualOX(child1, child2);
            // reassigning reference
            p1Array = childTuple[0];
            p2Array = childTuple[1];
        }

        if (random.nextInt(2) == 0) {
            c.updateSolutionRepresentation(p1Array);
        } else {
            c.updateSolutionRepresentation(p2Array);
        }
        return c.getObjectiveFunctionValue();
    }

    private int[][] ActualOX(int[] p1Array, int[] p2Array) {
        int n = p1Array.length;

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

        numberOfPointsWillStay = cutpoint_2 - cutpoint_1;
        int[] locationList_p1 = listLocationOfElement(p1Array);
        int[] locationList_p2 = listLocationOfElement(p2Array);

        int ele;
        int pointOfInsertion;

        // "nullify" the location index of staying element, so they will not get considered later
        // so there's no duplicate when copy from one to another
        for (pointOfInsertion = 0; pointOfInsertion < numberOfPointsWillStay; pointOfInsertion++) {
            int locationOfEle;

            // get the element starting from the cutoff point
            ele = p1Array[cutpoint_1 + pointOfInsertion];
            System.out.println(ele);
            // get the location of the element in p2
            locationOfEle = locationList_p2[ele];
            // set the ele to -1 so will not get copied later
            p2Array[locationOfEle] = -1;

            // do the same for child1
            ele = p2Array[cutpoint_1 + pointOfInsertion];
            locationOfEle = locationList_p1[ele];
            p1Array[locationOfEle] = -1;
        }
        int[] a = new int[n];
        System.arraycopy(p1Array, cutpoint_2, a, 0, n - cutpoint_2);
        System.arraycopy(p1Array, 0, a, n - cutpoint_2, cutpoint_2);
        p1Array = a;
        int[] b = new int[n];
        System.arraycopy(p2Array, cutpoint_2, b, 0, n - cutpoint_2);
        System.arraycopy(p2Array, 0, b, n - cutpoint_2, cutpoint_2);
        p2Array = b;

        int[] child1 = p1Array.clone();
        int[] child2 = p2Array.clone();

        // copy from p1Copy to child2
        int counter = 0;
        for (int i = cutpoint_2; i < n; i++) {
            ele = p1Array[counter];
            if (ele != -1) {
                child2[i] = ele;
            } else {
                --i;
            }
            counter++;
        }
        for (int i = 0; i < cutpoint_1; i++) {
            ele = p1Array[counter];
            if (ele != -1) {
                child2[i] = ele;
            } else {
                --i;
            }
            counter++;
        }


        // now copy from p2Copy to child1
        counter = 0;
        for (int i = cutpoint_2; i < n; i++) {
            ele = p2Array[counter];
            if (ele != -1) {
                child1[i] = ele;
            } else {
                --i;
            }
            counter++;
        }
        for (int i = 0; i < cutpoint_1; i++) {
            ele = p2Array[counter];
            if (ele != -1) {
                child1[i] = ele;
            } else {
                --i;
            }
            counter++;
        }
        return new int[][]{child1, child2};
    }

    private static int[] listLocationOfElement(int[] array) {
        // index corresponds to the element
        // the element corresponds to the location (index)
        // used to find the location of in a less expensive way
        int[] locationList = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            locationList[array[i]] = i;
        }
        return locationList;
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
