package com.g52aim.project.tsp.interfaces;

import com.g52aim.project.tsp.instance.InitialisationMode;
import com.g52aim.project.tsp.instance.Location;
import com.g52aim.project.tsp.solution.TSPSolution;

public interface TSPInstanceInterface {

	public TSPSolution createSolution(InitialisationMode mode);
	
	public ObjectiveFunctionInterface getTSPObjectiveFunction();
	
	public int getNumberOfCities();
	
	public Location getLocationForCity(int cityId);
}
