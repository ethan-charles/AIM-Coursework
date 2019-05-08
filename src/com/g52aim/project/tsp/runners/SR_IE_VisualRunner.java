package com.g52aim.project.tsp.runners;

import com.g52aim.project.tsp.hyperheuristics.OWN_IMPLEMENTATION.SCF_ALA_HH;
import com.g52aim.project.tsp.hyperheuristics.SR_IE_HH;

import AbstractClasses.HyperHeuristic;

/**
 * @author Warren G. Jackson
 * Runs a simple random IE hyper-heuristic then displays the best solution found
 */
public class SR_IE_VisualRunner extends HH_Runner_Visual {

	@Override
	protected HyperHeuristic getHyperHeuristic(long seed) {
		// change the underlying hyperheuristics
		// return new SR_IE_HH(seed);
		return new SCF_ALA_HH(seed, 200);
	}
	
	public static void main(String [] args) {
		
		HH_Runner_Visual runner = new SR_IE_VisualRunner();
		runner.run();
	}

}
