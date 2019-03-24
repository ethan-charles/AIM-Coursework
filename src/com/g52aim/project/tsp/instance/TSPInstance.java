package com.g52aim.project.tsp.instance;


import java.util.HashSet;
import java.util.Random;
import java.util.stream.IntStream;

import com.g52aim.project.tsp.TSPObjectiveFunction;
import com.g52aim.project.tsp.interfaces.ObjectiveFunctionInterface;
import com.g52aim.project.tsp.interfaces.TSPInstanceInterface;
import com.g52aim.project.tsp.solution.SolutionRepresentation;
import com.g52aim.project.tsp.solution.TSPSolution;


public class TSPInstance implements TSPInstanceInterface {
	
	private final Location[] locations;
	
	private final int numberOfCities;
	
	private final Random random;
	
	private ObjectiveFunctionInterface f = null;
	
	public TSPInstance(int numberOfCities, Location[] locations, Random random) {
		
		this.numberOfCities = numberOfCities;
		this.random = random;
		this.locations = locations;
	}

	@Override
	public TSPSolution createSolution(InitialisationMode mode) {
		
		// TODO
		return null;
	}
	
	@Override
	public ObjectiveFunctionInterface getTSPObjectiveFunction() {
		
		// TODO
		return null;
	}

	@Override
	public int getNumberOfCities() {

		// TODO
		return -1;
	}

	@Override
	public Location getLocationForCity(int cityId) {

		// TODO
		return null;
	}

}
