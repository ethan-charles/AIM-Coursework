package com.g52aim.project.tsp.solution;

import com.g52aim.project.tsp.interfaces.SolutionRepresentationInterface;

/**
 * 
 * @author Warren G. Jackson
 * 
 *
 */
public class SolutionRepresentation implements SolutionRepresentationInterface {

	int [] representation;

	public SolutionRepresentation(int[] representation) {
		
		// TODO
		this.representation = representation;
	}
	
	@Override
	public int[] getSolutionRepresentation() {

		// TODO
		return this.representation;
	}

	@Override
	public void setSolutionRepresentation(int[] solution) {
		
		// TODO
		this.representation = solution;
	}

	@Override
	public int getNumberOfCities() {

		// TODO
		return representation.length;
	}

	@Override
	public SolutionRepresentationInterface clone() {
		
		// TODO - NOTE clone Object, not copy of Object reference!
		int[] newRepresentation = (int[])this.representation.clone();
		return new SolutionRepresentation(this.representation);
	}

}
