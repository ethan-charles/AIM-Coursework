package com.g52aim.project.tsp.solution;

import com.g52aim.project.tsp.interfaces.SolutionRepresentationInterface;

/**
 * @author Warren G. Jackson
 */
public class SolutionRepresentation implements SolutionRepresentationInterface {

    int[] representation;

    public SolutionRepresentation(int[] representation) {

        // CHECKED
        this.representation = representation;
    }

    @Override
    public int[] getSolutionRepresentation() {

        // CHECKED
        return this.representation;
    }

    @Override
    public void setSolutionRepresentation(int[] solution) {

        // CHECKED
        this.representation = solution;
    }

    @Override
    public int getNumberOfCities() {

        // CHECKED
        return representation.length;
    }

    @Override
    public SolutionRepresentationInterface clone() {
        // CHECKED - NOTE clone Object, not copy of Object reference!
        int[] newRepresentation = new int[this.representation.length];
        System.arraycopy(this.representation, 0, newRepresentation, 0, this.representation.length);
        return new SolutionRepresentation(newRepresentation);
    }

}
