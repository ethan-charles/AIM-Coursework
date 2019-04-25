package com.g52aim.project.tsp.interfaces;

public interface TSPSolutionInterface extends Cloneable {

    public double getObjectiveFunctionValue();

    public void setObjectiveFunctionValue(double objectiveFunctionValue);

    public SolutionRepresentationInterface getSolutionRepresentation();

    public int getNumberOfCities();

    public TSPSolutionInterface clone();

    public void updateSolutionRepresentation(int[] solution);

    public void updateSolutionRepresentationWithDelta(int[] solution, double delta);

    // delta function for specific operator
    public double computeDeltaReinsertion(int[] newSolution, int removalPoint, int insertionPoint);

    public double computeDeltaAdjSwap(int[] newSolution, int swapPoint, int nextSwapPoint);

    public double computeDeltaTwoOpt(int[] newSolution, int firstSwapPoint, int secondSwapPoint);

    // public double computeDeltaXO(int[] newSolution, int firstCutPoint, int secondCutPoint);

    public void printSolutionRepresentation(int[] solution);

}
