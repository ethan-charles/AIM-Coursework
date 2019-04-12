package com.g52aim.project.tsp;

import com.g52aim.project.tsp.instance.Location;
import com.g52aim.project.tsp.interfaces.ObjectiveFunctionInterface;
import com.g52aim.project.tsp.interfaces.SolutionRepresentationInterface;
import com.g52aim.project.tsp.interfaces.TSPInstanceInterface;

public class TSPObjectiveFunction implements ObjectiveFunctionInterface {

    private final TSPInstanceInterface instance;

    public TSPObjectiveFunction(TSPInstanceInterface instance) {

        this.instance = instance;
    }


    public double computeDeltaFunctionValue(int[] previousSolution, int[] newSolution) {
        int leftIndex;
        int rightIndex;
        int n = previousSolution.length;
        double costDifference = 0;

        for (int index = 0; index < n; index++) {
            if (previousSolution[index] != newSolution[index]) {
                // if index is first, get the last index
                if (index == 0) {
                    leftIndex = n - 1;
                } else {
                    leftIndex = index - 1;
                }
                // if index is last, get the first index
                if (index == n - 1) {
                    rightIndex = 0;
                } else {
                    rightIndex = index + 1;
                }

                // remove edge
                costDifference -= (getCost(previousSolution[leftIndex], previousSolution[index]) +
                        getCost(previousSolution[index], previousSolution[rightIndex]));
                // add new edge
                costDifference += (getCost(newSolution[leftIndex], newSolution[index]) +
                        getCost(newSolution[index], newSolution[rightIndex]));

            }
        }
        return costDifference;
    }

    @Override
    public double getObjectiveFunctionValue(SolutionRepresentationInterface solution) {

        // CHECK - return the overall distance of the route including the distance from the last city back to the first!
        int[] array = solution.getSolutionRepresentation();
        int leftIndex;
        double totalCost = 0;
        for (int index = 0; index < array.length; index++) {
            leftIndex = index - 1;
            // if it is at the border, get the last city
            if (leftIndex == -1) {
                leftIndex = array.length - 1;
            }
            // +1 to convert to cityId
            totalCost += getCost(array[leftIndex], array[index]);
        }
        return totalCost;
    }

    @Override
    public double getCost(int location_a, int location_b) {

        // CHECK - return the distance between the two cities as defined in Section 4.4 of the project description
        Location A = instance.getLocationForCity(location_a);
        Location B = instance.getLocationForCity(location_b);
        Double distance = Math.sqrt(Math.pow(A.getX() - B.getX(), 2) + Math.pow(A.getY() - B.getY(), 2));
        return distance;
    }

}
