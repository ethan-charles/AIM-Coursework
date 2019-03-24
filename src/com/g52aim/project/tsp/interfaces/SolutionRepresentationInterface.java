package com.g52aim.project.tsp.interfaces;

public interface SolutionRepresentationInterface extends Cloneable {
	
	public int[] getSolutionRepresentation();
	
	public void setSolutionRepresentation(int[] solution);
	
	public int getNumberOfCities();
	
	public SolutionRepresentationInterface clone();
}
