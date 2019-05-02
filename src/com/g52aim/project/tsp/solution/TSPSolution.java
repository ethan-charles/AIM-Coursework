package com.g52aim.project.tsp.solution;

import com.g52aim.project.tsp.interfaces.ObjectiveFunctionInterface;
import com.g52aim.project.tsp.interfaces.SolutionRepresentationInterface;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;

import static java.lang.Math.abs;

public class TSPSolution implements TSPSolutionInterface {

    private SolutionRepresentationInterface representation;

    private double objectiveFunctionValue;

    private ObjectiveFunctionInterface f;

    private int numberOfVariables;

    public TSPSolution(SolutionRepresentationInterface representation, double objectiveFunctionValue, int numberOfVariables) {
        this.representation = representation;
        // set the previous representation, it is the representation initially
        this.objectiveFunctionValue = objectiveFunctionValue;
        this.numberOfVariables = numberOfVariables;
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

        // CHECKED

        // set the new representation
        this.getSolutionRepresentation().setSolutionRepresentation(solution);

        // set the new objective function value
        this.setObjectiveFunctionValue(f.getObjectiveFunctionValue(this.getSolutionRepresentation()));
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
    }

    public void printSolutionRepresentation(int[] solution) {
        for (int city : solution) {
            System.out.printf("%d ", city);
        }
        System.out.println();
    }

    @Override
    public TSPSolutionInterface clone() {
        // CHECKED
        // would actually clone the underlying representation (int[])
        SolutionRepresentationInterface newRepresentation = this.representation.clone();
        // create a new solution
        return new TSPSolution(newRepresentation, this.objectiveFunctionValue, this.numberOfVariables);
    }

    @Override
    public int getNumberOfCities() {
        // CHECKED
        return this.getSolutionRepresentation().getNumberOfCities();
    }
}
