package com.g52aim.project.tsp.solution;

import com.g52aim.project.tsp.interfaces.SolutionRepresentationInterface;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;

public class TSPSolution implements TSPSolutionInterface {

	private SolutionRepresentationInterface representation;
	
	private double objectiveFunctionValue;
	
	private int numberOfVariables;
	
	public TSPSolution(SolutionRepresentationInterface representation, double objectiveFunctionValue, int numberOfVariables) {
		
		this.representation = representation;
		this.objectiveFunctionValue = objectiveFunctionValue;
		this.numberOfVariables = numberOfVariables;
	}

	@Override
	public double getObjectiveFunctionValue() {

		// TODO
		return -1.0d;
	}

	@Override
	public void setObjectiveFunctionValue(double objectiveFunctionValue) {
		
		// TODO
	}

	@Override
	public SolutionRepresentationInterface getSolutionRepresentation() {
		
		// TODO
		return null;
	}
	
	@Override
	public TSPSolutionInterface clone() {
		
		// TODO
		return null;
	}

	@Override
	public int getNumberOfCities() {
		
		// TODO
		return -1;
	}
}
