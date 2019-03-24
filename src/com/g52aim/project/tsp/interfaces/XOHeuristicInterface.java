package com.g52aim.project.tsp.interfaces;

public interface XOHeuristicInterface extends HeuristicInterface {

	/**
	 * 
	 * @param p1 Parent solution 1
	 * @param p2 Parent solution 2
	 * @param depthOfSearch current DOS setting
	 * @param intensityOfMutation currentIOM setting
	 */
	public double apply(TSPSolutionInterface p1, TSPSolutionInterface p2, TSPSolutionInterface c, double depthOfSearch, double intensityOfMutation);
	
}
