package com.g52aim.project.tsp.interfaces;

import com.g52aim.project.tsp.instance.Location;

public interface Visualisable {

	public Location[] getRouteOrderedByLocations();
	
	public TSPInstanceInterface getLoadedInstance();
}
