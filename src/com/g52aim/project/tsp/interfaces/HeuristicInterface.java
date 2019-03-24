package com.g52aim.project.tsp.interfaces;

public interface HeuristicInterface {

	/**
	 * Applies the current heuristic to the solution <code>solution</code>
	 * and updates the objective value of the solution.
	 * @param solution
	 * @param f
	 */
	public double apply(TSPSolutionInterface solution, double depthOfSearch, double intensityOfMutation);
	
	public boolean isCrossover();
	
	public boolean usesIntensityOfMutation();
	
	public boolean usesDepthOfSearch();
	
	public void setObjectiveFunction(ObjectiveFunctionInterface f);
}
