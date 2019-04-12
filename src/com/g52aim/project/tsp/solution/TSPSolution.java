package com.g52aim.project.tsp.solution;

import com.g52aim.project.tsp.interfaces.ObjectiveFunctionInterface;
import com.g52aim.project.tsp.interfaces.SolutionRepresentationInterface;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;

public class TSPSolution implements TSPSolutionInterface {

    private SolutionRepresentationInterface representation;

    private double objectiveFunctionValue;

    private ObjectiveFunctionInterface f;

    private int numberOfVariables;

    private double delta;

    // to facilitate roll back of solution
    private int[] previousRepresentation;
    private double previousObjectiveFunctionValue;


    public TSPSolution(SolutionRepresentationInterface representation, double objectiveFunctionValue, int numberOfVariables, ObjectiveFunctionInterface f) {

        this.representation = representation;
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
    public double getDeltaValue() {
        return this.delta;
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
        // keep track of these so it is less expensive to roll back to previous solution
        this.previousRepresentation = this.representation.getSolutionRepresentation();
        this.previousObjectiveFunctionValue = this.objectiveFunctionValue;

        this.representation.setSolutionRepresentation(solution);
        // set the delta, so it can retrieved easier
        this.delta = getDeltaFunctionValue(solution);
        this.setObjectiveFunctionValue(this.objectiveFunctionValue + this.delta);
    }

    public double getDeltaFunctionValue(int[] solution) {
        // to get the computed cost difference between new and old solution
        // pass in the previous solution representation
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

    public void rollBackSolution() {
        this.representation.setSolutionRepresentation(this.previousRepresentation);
        this.objectiveFunctionValue = this.previousObjectiveFunctionValue;
    }
}
