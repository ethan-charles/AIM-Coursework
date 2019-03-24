package com.g52aim.project.tsp;

import com.g52aim.project.tsp.instance.Location;
import com.g52aim.project.tsp.interfaces.ObjectiveFunctionInterface;
import com.g52aim.project.tsp.interfaces.SolutionRepresentationInterface;
import com.g52aim.project.tsp.interfaces.TSPInstanceInterface;

public class TSPObjectiveFunction implements ObjectiveFunctionInterface {
	
	private final TSPInstanceInterface instance;
	
	public TSPObjectiveFunction(TSPInstanceInterface instance) {
		
		this.instance = instance;
	}

	@Override
	public double getObjectiveFunctionValue(SolutionRepresentationInterface solution) {
		
		// TODO - return the overall distance of the route including the distance from the last city back to the first!
		return 0.0d;
	}

	@Override
	public double getCost(int location_a, int location_b) {
		
		// TODO - return the distance between the two cities as defined in Section 4.4 of the project description
		return 0.0d;
	}

}
