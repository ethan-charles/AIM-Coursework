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
        // CHECK
        TSPSolution solutions = null;
        if (mode == InitialisationMode.RANDOM) {
            int[] array = random.ints(1, getNumberOfCities() + 1).distinct().limit(getNumberOfCities()).toArray();
            SolutionRepresentation representation = new SolutionRepresentation(array);
            f = new TSPObjectiveFunction(this);
            solutions = new TSPSolution(representation, f.getObjectiveFunctionValue(representation), getNumberOfCities(), f);
        }
        return solutions;
    }

    @Override
    public ObjectiveFunctionInterface getTSPObjectiveFunction() {

        // CHECK
        return this.f;
    }

    @Override
    public int getNumberOfCities() {

        // CHECK
        return this.numberOfCities;
    }

    @Override
    public Location getLocationForCity(int cityId) {

        // CHECK
        // index + 1 = cityId
        return locations[cityId - 1];
    }

}
