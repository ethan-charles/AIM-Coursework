package com.g52aim.project.tsp.heuristics;

import java.util.Random;

import com.g52aim.project.tsp.interfaces.ObjectiveFunctionInterface;

/**
 * 
 * @author Warren G. Jackson
 * 
 * TODO you can add any common functionality here
 *		to save having to re-implement them in all 
 *		your other heuristics!
 *
 *
 * If this this concept it new to you, you may want
 * to read this article on inheritance:
 * https://www.tutorialspoint.com/java/java_inheritance.htm 
 */
public class CrossoverHeuristicOperators {
	
	protected final Random random;
	
	public CrossoverHeuristicOperators(Random random) {
	
		this.random = random;
	}

	
	public void setObjectiveFunction(ObjectiveFunctionInterface f) {
		
		// TODO store the objective function so we can use it later!
	}
}
