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

    @Override
    public double getObjectiveFunctionValue(SolutionRepresentationInterface solution) {

        // CHECK - return the overall distance of the route including the distance from the last city back to the first!
        int[] array = solution.getSolutionRepresentation();
        int leftAdjacentLocation;
        double totalCost = 0;
        for (int currentLocation = 0; currentLocation < array.length; currentLocation++) {
            leftAdjacentLocation = currentLocation - 1;
            // if it is at the border, get the last city
            if (leftAdjacentLocation == -1) {
                leftAdjacentLocation = array.length - 1;
            }
            // +1 to convert to cityId
            totalCost += getCost(leftAdjacentLocation + 1, currentLocation + 1);
        }
        return totalCost;
    }

    @Override
    public double getCost(int location_a, int location_b) {

        // CHECK - return the distance between the two cities as defined in Section 4.4 of the project description
        // BE AWARE OF THE PARAMETER, IT IS CITY ID, NOT INDICES
        Location A = instance.getLocationForCity(location_a);
        Location B = instance.getLocationForCity(location_b);
        Double distance = Math.sqrt(Math.pow(A.getX() - B.getX(), 2) + Math.pow(A.getY() - B.getY(), 2));
        return distance;
    }

}
