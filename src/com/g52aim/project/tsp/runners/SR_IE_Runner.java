package com.g52aim.project.tsp.runners;

import com.g52aim.project.tsp.G52AIMTSP;
import com.g52aim.project.tsp.hyperheuristics.SR_IE_HH;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

public class SR_IE_Runner {
	
	public static void main(String [] args) {
		
		long seed = 15032018l;
		long timeLimit = 1_000l;
		ProblemDomain problem = new G52AIMTSP(seed);
		problem.loadInstance(2);
		HyperHeuristic hh = new SR_IE_HH(seed);
		hh.setTimeLimit(timeLimit);
		hh.loadProblemDomain(problem);
		hh.run();
		
		System.out.println("f(s_best) = " + hh.getBestSolutionValue());
	}

}
