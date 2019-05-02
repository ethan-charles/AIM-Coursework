package com.g52aim.project.tsp.interfaces;

import com.g52aim.project.tsp.interfaces.SolutionRepresentationInterface;

/**
 * @author Warren G. Jackson
 * @since 1.0.0
 * <p>
 * Interface for an objective function for TSP.
 */
public interface ObjectiveFunctionInterface {

    /**
     * @param SolutionRepresentationInterface The representation of the current solution
     * @return The objective function of the current solution represented by <code>solutionRepresentation</code>
     */
    public double getObjectiveFunctionValue(SolutionRepresentationInterface solutionRepresentation);

    /**
     * @param location_a ID of the starting city.
     * @param location_b ID of the destination city.
     * @return The distance between cities <code>location_a</code> and <code>location_b</code>.
     */
    public double getCost(int location_a, int location_b);

    // delta function for specific operator
    public double computeDeltaReinsertion(int[] previousSolution, int[] newSolution, int removalPoint, int insertionPoint);

    public double computeDeltaAdjSwap(int[] previousSolution, int[] newSolution, int swapPoint, int nextSwapPoint);

    public double computeDeltaTwoOpt(int[] previousSolution, int[] newSolution, int firstSwapPoint, int secondSwapPoint);
}
