package com.g52aim.project.tsp.solution;

import com.g52aim.project.tsp.interfaces.ObjectiveFunctionInterface;
import com.g52aim.project.tsp.interfaces.SolutionRepresentationInterface;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;

import static java.lang.Math.abs;

public class TSPSolution implements TSPSolutionInterface {

    private SolutionRepresentationInterface representation;

    private double objectiveFunctionValue;

    private ObjectiveFunctionInterface f;

    private int arra;

    public TSPSolution(SolutionRepresentationInterface representation, double objectiveFunctionValue, int numberOfVariables, ObjectiveFunctionInterface f) {
        this.representation = representation;
        // set the previous representation, it is the representation initially
        this.objectiveFunctionValue = objectiveFunctionValue;
        this.arra = numberOfVariables;
        this.f = f;
    }

    @Override
    public double getObjectiveFunctionValue() {

        // CHECKED
        return this.objectiveFunctionValue;
    }

    @Override
    public void setObjectiveFunctionValue(double objectiveFunctionValue) {

        // CHECKED
        this.objectiveFunctionValue = objectiveFunctionValue;
    }

    @Override
    public SolutionRepresentationInterface getSolutionRepresentation() {

        // CHECKED
        return this.representation;
    }

    // CHECK added to facilitate the update of solution
    // update on existing solution
    @Override
    public void updateSolutionRepresentation(int[] solution) {
        /**
         * Update the solution representation
         * With recalculation of total cost
         *
         */

        // set the new representation
        this.getSolutionRepresentation().setSolutionRepresentation(solution);

        // set the new objective function value
        this.setObjectiveFunctionValue(f.getObjectiveFunctionValue(this.getSolutionRepresentation()));

        printSolutionRepresentation(solution);
    }

    @Override
    public void updateSolutionRepresentationWithDelta(int[] solution, double delta) {
        /**
         * Update representation with delta that has been computed
         * so there's no need to recalculate the cost again
         */
        // set the new representation
        this.representation.setSolutionRepresentation(solution);
        // set the new objective function value
        this.setObjectiveFunctionValue(this.objectiveFunctionValue + delta);

        // check
        double correct = f.getObjectiveFunctionValue(getSolutionRepresentation());
        if (abs(this.objectiveFunctionValue - correct) > 0.2) {
            System.out.println("pause");
        }
        printSolutionRepresentation(solution);
    }

    public void printSolutionRepresentation(int[] solution) {
        for (int i = 0; i < solution.length; i++) {
            System.out.printf("%d ", solution[i]);
        }
        System.out.println();
    }

    private int[] findLeftRightIndex(int point) {
        // left index to  point
        int left;
        // right index to  point
        int right;

        if (point == 0) {
            left = this.getNumberOfCities() - 1;
            right = point + 1;
        } else if (point == this.getNumberOfCities() - 1) {
            left = point - 1;
            right = 0;
        } else {
            left = point - 1;
            right = point + 1;
        }
        return new int[]{left, right};
    }

    @Override
    public double computeDeltaReinsertion(int[] newSolution, int removalPoint, int insertionPoint) {
        int[] previousSolution = getSolutionRepresentation().getSolutionRepresentation();
        double delta = 0;
        int n = this.getNumberOfCities();

        // the cities to cities are not altered if removal and insertion each occurs at both end
        if (abs(removalPoint - insertionPoint) == n - 1) {
            return delta;
        }


        // removal point neighbours index
        int[] neighboursIndexR = findLeftRightIndex(removalPoint);
        // insertion point neighbours index
        int[] neighboursIndexI = findLeftRightIndex(insertionPoint);

        // if inserting adjacently
        int adjacencyCheck = removalPoint - insertionPoint;
        if (adjacencyCheck == -1) {
            // where removal is at left and insert at right
            int leftCity = previousSolution[neighboursIndexR[0]];
            int rightCity = previousSolution[neighboursIndexI[1]];
            int removedCity = previousSolution[removalPoint];
            int replacedCity = previousSolution[neighboursIndexR[1]];

            delta -= (
                    f.getCost(leftCity, removedCity) +
                            f.getCost(replacedCity, rightCity)
            );
            // add right edge from removal point
            delta += (
                    f.getCost(removedCity, rightCity) +
                            f.getCost(replacedCity, leftCity)
            );
            return delta;
        } else if (adjacencyCheck == 1) {
            // where removal is at right and insert at left
            int leftCity = previousSolution[neighboursIndexI[0]];
            int rightCity = previousSolution[neighboursIndexR[1]];
            int removedCity = previousSolution[removalPoint];
            int replacedCity = previousSolution[neighboursIndexR[0]];

            delta -= (
                    f.getCost(removedCity, rightCity) +
                            f.getCost(replacedCity, leftCity)
            );
            // add right edge from removal point
            delta += (
                    f.getCost(leftCity, removedCity) +
                            f.getCost(replacedCity, rightCity)
            );
            return delta;
        }

        // deduct the cost of the removed element
        // deduct the cost of the element (which its position will be taken by the removed element)
        // and its left neighbour
        delta -= (
                // removed point and its left neighbour
                f.getCost(previousSolution[removalPoint], previousSolution[neighboursIndexR[0]]) +// removed point and its left neighbour
                        // removed point and its right neighbour
                        f.getCost(previousSolution[removalPoint], previousSolution[neighboursIndexR[1]])
        );
        // add new cost of which the inserted element with new neighbours
        delta += (
                f.getCost(newSolution[insertionPoint], newSolution[neighboursIndexI[0]]) +
                        f.getCost(newSolution[insertionPoint], newSolution[neighboursIndexI[1]])
        );
        if (adjacencyCheck > 0) {
            //  removal is on relative right
            delta += (
                    -f.getCost(previousSolution[neighboursIndexI[0]], previousSolution[insertionPoint]) +
                            f.getCost(previousSolution[neighboursIndexR[0]], previousSolution[neighboursIndexR[1]])
            );
        } else {
            delta += (
                    -f.getCost(previousSolution[neighboursIndexI[1]], previousSolution[insertionPoint]) +
                            f.getCost(previousSolution[neighboursIndexR[0]], previousSolution[neighboursIndexR[1]])
            );
        }
        return delta;
    }

    @Override
    public double computeDeltaAdjSwap(int[] newSolution, int swapPoint, int nextSwapPoint) {
        double delta = 0;
        int left;
        int right;

        if (swapPoint == 0) {
            left = this.getNumberOfCities() - 1;
        } else {
            left = swapPoint - 1;
        }
        if (nextSwapPoint == this.getNumberOfCities() - 1) {
            right = 0;
        } else {
            right = nextSwapPoint + 1;
        }

        // remove edge from nextSwapPoint to right
        // remove edge from left to swapPoint
        int[] previousSolution = getSolutionRepresentation().getSolutionRepresentation();

        delta -= (
                f.getCost(previousSolution[swapPoint], previousSolution[left]) +
                        f.getCost(previousSolution[nextSwapPoint], previousSolution[right])
        );

        // add edge from swapPoint to right
        // add edge from left to nextSwapPoint
        delta += (f.getCost(newSolution[swapPoint], newSolution[left]) +
                f.getCost(newSolution[nextSwapPoint], newSolution[right])
        );

        return delta;
    }

    @Override
    public double computeDeltaTwoOpt(int[] newSolution, int firstSwapPoint, int secondSwapPoint) {
        int[] prevSolution = this.getSolutionRepresentation().getSolutionRepresentation();
        int[] neighboursFirstSwapPoint = findLeftRightIndex(firstSwapPoint);
        int[] neighboursSecondSwapPoint = findLeftRightIndex(secondSwapPoint);

        double delta = 0;

        delta -= (
                f.getCost(prevSolution[secondSwapPoint], prevSolution[neighboursSecondSwapPoint[0]]) +
                        f.getCost(prevSolution[secondSwapPoint], prevSolution[neighboursSecondSwapPoint[1]]) +
                        f.getCost(prevSolution[firstSwapPoint], prevSolution[neighboursFirstSwapPoint[0]]) +
                        f.getCost(prevSolution[firstSwapPoint], prevSolution[neighboursFirstSwapPoint[1]])
        );

        delta += (
                f.getCost(newSolution[firstSwapPoint], newSolution[neighboursFirstSwapPoint[0]]) +
                        f.getCost(newSolution[firstSwapPoint], newSolution[neighboursFirstSwapPoint[1]]) +
                        f.getCost(newSolution[secondSwapPoint], newSolution[neighboursSecondSwapPoint[0]]) +
                        f.getCost(newSolution[secondSwapPoint], newSolution[neighboursSecondSwapPoint[1]])
        );

        return delta;
    }

    @Override
    public TSPSolutionInterface clone() {
        // CHECKED
        // would actually clone the underlying representation (int[])
        SolutionRepresentationInterface newRepresentation = this.representation.clone();
        // create a new solution
        return new TSPSolution(newRepresentation, this.objectiveFunctionValue, this.arra, this.f);
    }

    @Override
    public int getNumberOfCities() {
        // CHECKED
        return this.getSolutionRepresentation().getNumberOfCities();
    }
}
