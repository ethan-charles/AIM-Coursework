package com.g52aim.project.tsp.instance;


import java.util.Random;
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
        f = new TSPObjectiveFunction(this);
    }

    @Override
    public TSPSolution createSolution(InitialisationMode mode) {
        // CHECKED
        TSPSolution solutions = null;
        if (mode == InitialisationMode.RANDOM) {
            int[] array = random.ints(1, getNumberOfCities() + 1).distinct().limit(getNumberOfCities()).toArray();
            SolutionRepresentation representation = new SolutionRepresentation(array);
            solutions = new TSPSolution(representation, f.getObjectiveFunctionValue(representation), getNumberOfCities());
        }
        return solutions;
    }

    @Override
    public ObjectiveFunctionInterface getTSPObjectiveFunction() {

        // CHECKED
        return this.f;
    }

    @Override
    public int getNumberOfCities() {

        // CHECKED
        return this.numberOfCities;
    }

    @Override
    public Location getLocationForCity(int cityId) {

        // CHECKED
        // index + 1 = cityId
        return locations[cityId - 1];
    }

}
