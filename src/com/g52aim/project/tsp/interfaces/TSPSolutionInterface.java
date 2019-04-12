package com.g52aim.project.tsp.interfaces;

public interface TSPSolutionInterface extends Cloneable {

    public double getObjectiveFunctionValue();

    public void setObjectiveFunctionValue(double objectiveFunctionValue);

    public SolutionRepresentationInterface getSolutionRepresentation();

    public int getNumberOfCities();

    public TSPSolutionInterface clone();

    public void updateSolutionRepresentation(int[] solution);

    public double getDeltaValue();

    public void rollBackSolution();

    public double computeDeltaValue(int[] solution);

}
