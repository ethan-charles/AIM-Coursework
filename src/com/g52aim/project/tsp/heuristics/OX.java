package com.g52aim.project.tsp.heuristics;

import java.util.Random;

import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;
import com.g52aim.project.tsp.interfaces.XOHeuristicInterface;

/**
 * 
 * @author Warren G. Jackson
 *
 */
public class OX extends CrossoverHeuristicOperators implements XOHeuristicInterface {
	
	public OX(Random random) {
		
		super(random);
	}

	@Override
	public double apply(TSPSolutionInterface solution, double depthOfSearch, double intensityOfMutation) {

		// invalid operation, return the same solution!
		return -1;
	}

	@Override
	public double apply(TSPSolutionInterface p1, TSPSolutionInterface p2,
			TSPSolutionInterface c, double depthOfSearch, double intensityOfMutation) {
		
		// TODO implementation of ordered crossover
		return -1;
	}

	/*
	 * TODO update the methods below to return the correct boolean value.
	 */

	@Override
	public boolean isCrossover() {

		return false;
	}

	@Override
	public boolean usesIntensityOfMutation() {

		return false;
	}

	@Override
	public boolean usesDepthOfSearch() {

		return false;
	}
}
