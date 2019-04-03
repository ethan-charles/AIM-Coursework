package com.g52aim.project.tsp.solution;

import com.g52aim.project.tsp.interfaces.SolutionRepresentationInterface;

/**
 * @author Warren G. Jackson
 */
public class SolutionRepresentation implements SolutionRepresentationInterface {

    int[] representation;

    public SolutionRepresentation(int[] representation) {

        // CHECK
        this.representation = representation;
    }

    @Override
    public int[] getSolutionRepresentation() {

        // CHECK
        return this.representation;
    }

    @Override
    public void setSolutionRepresentation(int[] solution) {

        // CHECK
        this.representation = solution;
    }

    @Override
    public int getNumberOfCities() {

        // CHECK
        return representation.length;
    }

    @Override
    public SolutionRepresentationInterface clone() {
        // CHECK - NOTE clone Object, not copy of Object reference!
        int[] newRepresentation = new int[this.representation.length];
        System.arraycopy(this.representation, 0, newRepresentation, 0, this.representation.length);
        return new SolutionRepresentation(newRepresentation);
    }

}
