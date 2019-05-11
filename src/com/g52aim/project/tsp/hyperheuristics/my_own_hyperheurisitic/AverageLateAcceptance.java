package com.g52aim.project.tsp.hyperheuristics.my_own_hyperheurisitic;

import java.math.BigDecimal;
import java.util.Arrays;

public class AverageLateAcceptance {

    public final int LIST_LENGTH;

    public BigDecimal sum = BigDecimal.ZERO;

    public double[] acceptedSolutionFitnesses;

    public int index;

    public AverageLateAcceptance(int listLength, double initialSolutionFitness) {

        this.LIST_LENGTH = listLength;
        acceptedSolutionFitnesses = new double[listLength];
        Arrays.fill(acceptedSolutionFitnesses, initialSolutionFitness);
        // start with index = 1, as index zero is preoccupied
        this.index = 1;
        sum = BigDecimal.valueOf(initialSolutionFitness * listLength);
    }


    public double getThresholdValue() {
        return this.sum.doubleValue() / this.LIST_LENGTH;
    }

    public void update(double f) {
        // overriding the currently pointed value with new value
        // and subtract from the sum at the meantime
        sum = sum.subtract(BigDecimal.valueOf(acceptedSolutionFitnesses[index]));
        acceptedSolutionFitnesses[index] = f;

        // move the 'index' pointer on AND loop around the array
        this.index = (this.index + 1) % LIST_LENGTH;
        // update sum
        this.sum = sum.add(BigDecimal.valueOf(f));
    }
}
