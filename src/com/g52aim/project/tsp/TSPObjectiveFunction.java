package com.g52aim.project.tsp;

import com.g52aim.project.tsp.instance.Location;
import com.g52aim.project.tsp.interfaces.ObjectiveFunctionInterface;
import com.g52aim.project.tsp.interfaces.SolutionRepresentationInterface;
import com.g52aim.project.tsp.interfaces.TSPInstanceInterface;

import static java.lang.Math.abs;

public class TSPObjectiveFunction implements ObjectiveFunctionInterface {

    private final TSPInstanceInterface instance;

    public TSPObjectiveFunction(TSPInstanceInterface instance) {

        this.instance = instance;
    }

    @Override
    public double getObjectiveFunctionValue(SolutionRepresentationInterface solution) {

        // CHECKED - return the overall distance of the route including the distance from the last city back to the first!
        int[] array = solution.getSolutionRepresentation();
        int numberOfCities = this.instance.getNumberOfCities();
        int leftIndex;
        double totalCost = 0;
        for (int index = 0; index < numberOfCities; index++) {
            leftIndex = index - 1;
            // if it is at the border, get the last city
            if (leftIndex == -1) {
                leftIndex = numberOfCities - 1;
            }
            // +1 to convert to cityId
            totalCost += getCost(array[leftIndex], array[index]);
        }
        return totalCost;
    }

    @Override
    public double getCost(int location_a, int location_b) {

        // CHECKED - return the distance between the two cities as defined in Section 4.4 of the project description
        Location A = instance.getLocationForCity(location_a);
        Location B = instance.getLocationForCity(location_b);
        Double distance = Math.sqrt(Math.pow(A.getX() - B.getX(), 2) + Math.pow(A.getY() - B.getY(), 2));
        return distance;
    }


    private int[] findLeftRightIndex(int point, int numberOfCities) {
        // left index to  point
        int left;
        // right index to  point
        int right;

        if (point == 0) {
            left = numberOfCities - 1;
            right = point + 1;
        } else if (point == numberOfCities - 1) {
            left = point - 1;
            right = 0;
        } else {
            left = point - 1;
            right = point + 1;
        }
        return new int[]{left, right};
    }

    @Override
    public double computeDeltaReinsertion(int[] previousSolution, int[] newSolution, int removalPoint, int insertionPoint) {
        double delta = 0;
        int numberOfCities = this.instance.getNumberOfCities();

        //  the cities to cities are not altered if removal and insertion each occurs at both end
        //  so return delta=0
        if (abs(removalPoint - insertionPoint) == numberOfCities - 1) {
            return 0;
        }

        //  removal point neighbours index
        int[] neighboursIndexR = findLeftRightIndex(removalPoint, numberOfCities);
        //  insertion point neighbours index
        int[] neighboursIndexI = findLeftRightIndex(insertionPoint, numberOfCities);


        int distanceFromRemovalToInsertion = removalPoint - insertionPoint;

        // commonly used point
        int removedCity = previousSolution[removalPoint];
        int replacedCity = previousSolution[insertionPoint];

        // check if inserting adjacently
        if (distanceFromRemovalToInsertion == -1) {
            // where removal is at left and insert at right
            int leftCity = previousSolution[neighboursIndexR[0]];
            int rightCity = previousSolution[neighboursIndexI[1]];

            delta -= (
                    getCost(leftCity, removedCity) +
                            getCost(replacedCity, rightCity)
            );
            // add right edge from removal point
            delta += (
                    getCost(removedCity, rightCity) +
                            getCost(replacedCity, leftCity)
            );
            return delta;
        } else if (distanceFromRemovalToInsertion == 1) {
            // where removal is at right and insert at left
            int leftCity = previousSolution[neighboursIndexI[0]];
            int rightCity = previousSolution[neighboursIndexR[1]];

            delta -= (
                    getCost(removedCity, rightCity) +
                            getCost(replacedCity, leftCity)
            );
            // add right edge from removal point
            delta += (
                    getCost(leftCity, removedCity) +
                            getCost(replacedCity, rightCity)
            );
            return delta;
        }

        // deduct the cost of the removed element
        // deduct the cost of the element (which its position will be taken by the removed element)
        // and its left neighbour
        delta -= (
                // removed point and its left neighbour
                getCost(removedCity, previousSolution[neighboursIndexR[0]]) +// removed point and its left neighbour
                        // removed point and its right neighbour
                        getCost(removedCity, previousSolution[neighboursIndexR[1]])
        );
        // add new cost of which the inserted element with new neighbours
        delta += (
                getCost(removedCity, newSolution[neighboursIndexI[0]]) +
                        getCost(removedCity, newSolution[neighboursIndexI[1]])
        );
        if (distanceFromRemovalToInsertion > 0) {
            //  removal is on relative right
            delta += (
                    -getCost(previousSolution[neighboursIndexI[0]], previousSolution[insertionPoint]) +
                            getCost(previousSolution[neighboursIndexR[0]], previousSolution[neighboursIndexR[1]])
            );
        } else {
            delta += (
                    -getCost(previousSolution[neighboursIndexI[1]], previousSolution[insertionPoint]) +
                            getCost(previousSolution[neighboursIndexR[0]], previousSolution[neighboursIndexR[1]])
            );
        }
        return delta;
    }

    @Override
    public double computeDeltaAdjSwap(int[] previousSolution, int[] newSolution, int swapPoint, int nextSwapPoint) {
        double delta = 0;
        int left;
        int right;
        int numberOfCities = this.instance.getNumberOfCities();

        if (swapPoint == 0) {
            left = numberOfCities - 1;
        } else {
            left = swapPoint - 1;
        }
        if (nextSwapPoint == numberOfCities - 1) {
            right = 0;
        } else {
            right = nextSwapPoint + 1;
        }

        // remove edge from nextSwapPoint to right
        // remove edge from left to swapPoint

        delta -= (
                getCost(previousSolution[swapPoint], previousSolution[left]) +
                        getCost(previousSolution[nextSwapPoint], previousSolution[right])
        );

        // add edge from swapPoint to right
        // add edge from left to nextSwapPoint
        delta += (getCost(newSolution[swapPoint], newSolution[left]) +
                getCost(newSolution[nextSwapPoint], newSolution[right])
        );

        return delta;
    }

    @Override
    public double computeDeltaTwoOpt(int[] prevSolution, int[] newSolution, int firstSwapPoint, int secondSwapPoint) {
        int numberOfCities = this.instance.getNumberOfCities();
        int[] neighboursFirstSwapPoint = findLeftRightIndex(firstSwapPoint, numberOfCities);
        int[] neighboursSecondSwapPoint = findLeftRightIndex(secondSwapPoint, numberOfCities);

        double delta = 0;

        delta -= (
                getCost(prevSolution[secondSwapPoint], prevSolution[neighboursSecondSwapPoint[0]]) +
                        getCost(prevSolution[secondSwapPoint], prevSolution[neighboursSecondSwapPoint[1]]) +
                        getCost(prevSolution[firstSwapPoint], prevSolution[neighboursFirstSwapPoint[0]]) +
                        getCost(prevSolution[firstSwapPoint], prevSolution[neighboursFirstSwapPoint[1]])
        );

        delta += (
                getCost(newSolution[firstSwapPoint], newSolution[neighboursFirstSwapPoint[0]]) +
                        getCost(newSolution[firstSwapPoint], newSolution[neighboursFirstSwapPoint[1]]) +
                        getCost(newSolution[secondSwapPoint], newSolution[neighboursSecondSwapPoint[0]]) +
                        getCost(newSolution[secondSwapPoint], newSolution[neighboursSecondSwapPoint[1]])
        );

        return delta;
    }
}
