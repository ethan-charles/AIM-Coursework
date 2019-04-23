package com.g52aim.project.tsp.solution;

import com.g52aim.project.tsp.interfaces.ObjectiveFunctionInterface;
import com.g52aim.project.tsp.interfaces.SolutionRepresentationInterface;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;

public class TSPSolution implements TSPSolutionInterface {

    private SolutionRepresentationInterface representation;

    private double objectiveFunctionValue;

    private ObjectiveFunctionInterface f;

    private int numberOfVariables;

    public TSPSolution(SolutionRepresentationInterface representation, double objectiveFunctionValue, int numberOfVariables, ObjectiveFunctionInterface f) {
        this.representation = representation;
        // set the previous representation, it is the representation initially
        this.objectiveFunctionValue = objectiveFunctionValue;
        this.numberOfVariables = numberOfVariables;
        this.f = f;
    }

    @Override
    public double getObjectiveFunctionValue() {

        // CHECK
        return this.objectiveFunctionValue;
    }

    @Override
    public void setObjectiveFunctionValue(double objectiveFunctionValue) {

        // CHECK
        this.objectiveFunctionValue = objectiveFunctionValue;
    }

    @Override
    public SolutionRepresentationInterface getSolutionRepresentation() {

        // CHECK
        return this.representation;
    }

    // CHECK added to facilitate the update of solution
    // update on existing solution
    @Override
    public void updateSolutionRepresentation(int[] solution) {
        /**
         * Update the solution representation and its delta
         *
         */

        // update
        // compute the delta value before actually overriding the current with new representation
        double delta = computeDeltaValue(solution);

        // set the new representation
        this.representation.setSolutionRepresentation(solution);
        // set the new objective function value
        this.setObjectiveFunctionValue(this.objectiveFunctionValue + delta);

        printSolutionRepresentation(solution);
    }

    @Override
    public void updateSolutionRepresentationWithDelta(int[] solution, double delta) {

        // set the new representation
        this.representation.setSolutionRepresentation(solution);
        // set the new objective function value
        this.setObjectiveFunctionValue(this.objectiveFunctionValue + delta);

        printSolutionRepresentation(solution);
    }

    private void printSolutionRepresentation(int[] solution) {
        for (int i = 0; i < solution.length; i++) {
            System.out.printf("%d ", solution[i]);
        }
        System.out.println("");
    }


    @Override
    public double computeDeltaValue(int[] solution) {
        // to get the computed cost difference between new and old solution
        // pass in the previous solution representation
        System.out.println("compute not necessarily update: " + this.f.computeDeltaFunctionValue(this.representation.getSolutionRepresentation(), solution));
        return this.f.computeDeltaFunctionValue(this.representation.getSolutionRepresentation(), solution);
    }

    @Override
    public TSPSolutionInterface clone() {

        // CHECK
        SolutionRepresentationInterface newRepresentation = this.representation.clone();
        return new TSPSolution(newRepresentation, this.objectiveFunctionValue, this.numberOfVariables, this.f);
    }

    @Override
    public int getNumberOfCities() {

        // CHECK
        return this.getSolutionRepresentation().getNumberOfCities();
    }
}
