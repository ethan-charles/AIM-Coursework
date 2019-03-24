package com.g52aim.project.tsp.runners;

import com.g52aim.project.tsp.hyperheuristics.InitialisationTest_HH;

import AbstractClasses.HyperHeuristic;

/**
 * @author Warren G. Jackson
 *
 * Runs a hyper-heuristic which just initialises a solution and dispalys that initial solution.
 */
public class InitialisationTest_VisualRunner extends HH_Runner_Visual {

	@Override
	protected HyperHeuristic getHyperHeuristic(long seed) {

		return new InitialisationTest_HH(seed);
	}
	
	public static void main(String [] args) {
		
		HH_Runner_Visual runner = new InitialisationTest_VisualRunner();
		runner.run();
	}

}
