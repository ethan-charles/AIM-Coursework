package com.g52aim.project.tsp;


import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import AbstractClasses.HyperHeuristic;
import com.g52aim.project.tsp.heuristics.*;
import com.g52aim.project.tsp.hyperheuristics.SR_IE_HH;
import com.g52aim.project.tsp.instance.InitialisationMode;
import com.g52aim.project.tsp.instance.Location;
import com.g52aim.project.tsp.instance.reader.TSPInstanceReader;
import com.g52aim.project.tsp.interfaces.*;
import AbstractClasses.ProblemDomain;
import com.g52aim.project.tsp.solution.TSPSolution;

public class G52AIMTSP extends ProblemDomain implements Visualisable {

    private String[] instanceFiles = {
            "d1291", "d18512", "dj38", "usa13509", "qa194", "ch71009"
    };

    // for testing purposes
    // private String[] instanceFiles = {
    //         "T81", "pcb1173", "ch71009"
    // };

    private TSPSolutionInterface[] solutions;

    private TSPSolutionInterface bestSolution;

    public TSPInstanceInterface instance;

    private HeuristicInterface[] heuristics;

    private double best_solution_value = Double.POSITIVE_INFINITY;

    ObjectiveFunctionInterface f = null;

    public G52AIMTSP(long seed) {

        super(seed);

        // CHECKED - set default memory size and create the array of low-level heuristics

        // set solutions array to default size of 2
        this.solutions = new TSPSolution[2];
        // initialise array of low-level heuristics
        heuristics = new HeuristicInterface[]{new AdjacentSwap(rng), new TwoOpt(rng), new Reinsertion(rng), new NextDescent(rng), new DavissHillClimbing(rng),
                new OX(rng), new PMX(rng),
        };
    }

    public TSPSolutionInterface getSolution(int index) {

        // CHECKED
        return this.solutions[index];
    }

    public TSPSolutionInterface getBestSolution() {

        // CHECKED
        return this.bestSolution;
    }

    @Override
    public double applyHeuristic(int hIndex, int currentIndex, int candidateIndex) {

        // CHECKED - apply heuristic and return the objective value of the candidate solution
        // get a copy
        TSPSolutionInterface solutionCopy = solutions[currentIndex].clone();
        // applying heuristic on copy
        double objectiveValue = heuristics[hIndex].apply(solutionCopy, this.depthOfSearch, this.intensityOfMutation);
        // places the resulting solution at position candidateIndex
        solutions[candidateIndex] = solutionCopy;
        if (objectiveValue < best_solution_value) {
            updateBestSolution(candidateIndex);
        }
        return objectiveValue;
    }

    @Override
    public double applyHeuristic(int hIndex, int parent1Index, int parent2Index, int candidateIndex) {

        // CHECKED- apply heuristic and return the objective value of the candidate solution
        double objectiveValue = ((XOHeuristicInterface) heuristics[hIndex]).apply(solutions[parent1Index], solutions[parent2Index], solutions[candidateIndex], this.depthOfSearch, this.intensityOfMutation);
        if (objectiveValue < best_solution_value) {
            updateBestSolution(candidateIndex);
        }
        return objectiveValue;
    }

    @Override
    public String bestSolutionToString() {

        // CHECKED
        StringBuilder builder = new StringBuilder();
        TSPSolutionInterface solution = this.bestSolution;
        double cost = this.best_solution_value;
        int[] solutionRepresentation = solution.getSolutionRepresentation().getSolutionRepresentation();

        builder.append("Cost = " + cost + "\n");
        for (int i = 0; i < solutionRepresentation.length; ++i) {
            builder.append(" " + solutionRepresentation[i]);
        }
        return builder.toString();
    }

    @Override
    public boolean compareSolutions(int a, int b) {

        // CHECKED
        int[] firstSolutionRepresentation = solutions[a].getSolutionRepresentation().getSolutionRepresentation();
        int[] secondSolutionRepresentation = solutions[b].getSolutionRepresentation().getSolutionRepresentation();

        return Arrays.equals(firstSolutionRepresentation, secondSolutionRepresentation);
    }

    @Override
    public void copySolution(int a, int b) {

        // CHECKED - BEWARE this should copy the solution, not the reference to it!
        //			That is, that if we apply a heuristic to the solution in index 'b',
        //			then it does not modify the solution in index 'a' or vice-versa.
        solutions[b] = solutions[a].clone();
    }

    @Override
    public double getBestSolutionValue() {

        // CHECKED
        return this.best_solution_value;
    }

    @Override
    public double getFunctionValue(int index) {

        // CHECKED
        return getSolution(index).getObjectiveFunctionValue();
    }

    @Override
    public int[] getHeuristicsOfType(HeuristicType type) {

        // CHECKED
        int[] heuristicOfTypeIndices;
        switch (type) {
            case MUTATION:
                heuristicOfTypeIndices = new int[]{0, 1, 2};
                break;
            case LOCAL_SEARCH:
                heuristicOfTypeIndices = new int[]{3, 4};
                break;
            case CROSSOVER:
                heuristicOfTypeIndices = new int[]{5, 6};
                break;
            default:
                heuristicOfTypeIndices = new int[]{};
                break;
        }

        return heuristicOfTypeIndices;
    }

    @Override
    public int[] getHeuristicsThatUseDepthOfSearch() {

        // CHECK
        int[] heuristicArray = new int[0];
        for (int i = 0; i < heuristics.length; i++) {
            if (heuristics[i].usesDepthOfSearch()) {
                int[] temp = new int[heuristicArray.length + 1];
                System.arraycopy(heuristicArray, 0, temp, 0, heuristicArray.length);
                temp[heuristicArray.length + 1] = i;
                heuristicArray = temp;
            }
        }
        return heuristicArray;
    }

    @Override
    public int[] getHeuristicsThatUseIntensityOfMutation() {

        // CHECK
        int[] heuristicArray = new int[0];
        for (int i = 0; i < heuristics.length; i++) {
            if (heuristics[i].usesIntensityOfMutation()) {
                int[] temp = new int[heuristicArray.length + 1];
                System.arraycopy(heuristicArray, 0, temp, 0, heuristicArray.length);
                temp[heuristicArray.length + 1] = i;
                heuristicArray = temp;
            }
        }
        return heuristicArray;
    }

    @Override
    public int getNumberOfHeuristics() {

        // CHECKED - has to be hard-coded due to the design of the HyFlex framework
        return 7;
    }

    @Override
    public int getNumberOfInstances() {

        // CHECKED
        return instanceFiles.length;
    }

    @Override
    public void initialiseSolution(int index) {

        // CHECKED - make sure that you also update the best solution!
        solutions[index] = instance.createSolution(InitialisationMode.RANDOM);
        solutions[index].printSolutionRepresentation(solutions[index].getSolutionRepresentation().getSolutionRepresentation());
        double currentFitness = getFunctionValue(index);
        if (currentFitness < this.best_solution_value) {
            this.updateBestSolution(index);
        }
    }

    @Override
    public void loadInstance(int instanceId) {

        String SEP = FileSystems.getDefault().getSeparator();
        String instanceName = "instances" + SEP + "tsp" + SEP + instanceFiles[instanceId] + ".tsp";

        // CHECKED create instance reader and problem instance
        // ...
        TSPInstanceReader reader = new TSPInstanceReader();
        instance = reader.readTSPInstance(Paths.get(instanceName), rng);

        // CHECKED set the objective function in each of the heuristics
        for (int i = 0; i < heuristics.length; i++) {
            heuristics[i].setObjectiveFunction(instance.getTSPObjectiveFunction());
        }
    }

    @Override
    public void setMemorySize(int size) {
        // CHECKED
        TSPSolutionInterface[] tempSolutions = new TSPSolution[size];
        // never set new memory size if there's no current solution or the new size < 2
        if (this.solutions != null && size >= 2) {
            int i;
            // to prevent index out of bound
            // stop when reach the shorter array size
            // do the migration
            if (tempSolutions.length <= this.solutions.length) {
                for (i = 0; i < tempSolutions.length; i++) {
                    tempSolutions[i] = solutions[i];
                }
            } else {
                for (i = 0; i < this.solutions.length; i++) {
                    tempSolutions[i] = solutions[i];
                }
            }
        }
        this.solutions = tempSolutions;
    }

    @Override
    public String solutionToString(int index) {

        // CHECKED

        StringBuilder builder = new StringBuilder();
        TSPSolutionInterface solution = solutions[index];
        double cost = solution.getObjectiveFunctionValue();
        int[] solutionRepresentation = solution.getSolutionRepresentation().getSolutionRepresentation();

        builder.append("Cost = " + cost + "\n");
        for (int i = 0; i < solutionRepresentation.length; ++i) {
            builder.append(" " + solutionRepresentation[i]);
        }
        return builder.toString();
    }

    @Override
    public String toString() {

        // CHECKED change 'PSY...' to be your username
        return "PSYJHC's G52AIM TSP";
    }

    private void updateBestSolution(int index) {

        // CHECKED
        // update best value and index
        this.best_solution_value = getFunctionValue(index);
        this.bestSolution = solutions[index].clone();
    }

    @Override
    public TSPInstanceInterface getLoadedInstance() {

        return this.instance;
    }

    @Override
    public Location[] getRouteOrderedByLocations() {

        int[] city_ids = getBestSolution().getSolutionRepresentation().getSolutionRepresentation();
        Location[] route = Arrays.stream(city_ids).boxed().map(getLoadedInstance()::getLocationForCity).toArray(Location[]::new);
        return route;
    }

    public static void main(String[] args) {

        long seed = 527893l;
        long timeLimit = 10_000;
        G52AIMTSP tsp = new G52AIMTSP(seed);
        HyperHeuristic hh = new SR_IE_HH(seed);
        tsp.loadInstance(0);
        hh.setTimeLimit(timeLimit);
        hh.loadProblemDomain(tsp);
        hh.run();

        double best = hh.getBestSolutionValue();
        System.out.println(best);

        // CHECKED you will need to populate this based on your representation!
        List<Location> routeLocations = new ArrayList<>();
        Location[] routeLocationList = new Location[routeLocations.size()];
        routeLocations.toArray(routeLocationList);
        SolutionPrinter.printSolution(routeLocationList);
    }


}
