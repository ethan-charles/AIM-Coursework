package com.g52aim.project.tsp.solution;

import com.g52aim.project.tsp.interfaces.SolutionRepresentationInterface;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;

public class TSPSolution implements TSPSolutionInterface {

    private SolutionRepresentationInterface representation;

    private double objectiveFunctionValue;

    private int numberOfVariables;

    public TSPSolution(SolutionRepresentationInterface representation, double objectiveFunctionValue, int numberOfVariables) {

        this.representation = representation;
        this.objectiveFunctionValue = objectiveFunctionValue;
        this.numberOfVariables = numberOfVariables;
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

    @Override
    public TSPSolutionInterface clone() {

        // CHECK
        SolutionRepresentationInterface newRepresentation = this.representation.clone();
        return new TSPSolution(newRepresentation, this.objectiveFunctionValue, this.numberOfVariables);
    }

    @Override
    public int getNumberOfCities() {

        // CHECK
        return this.getSolutionRepresentation().getNumberOfCities();
    }
}
