package com.g52aim.project.tsp;


import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.IntStream;

import AbstractClasses.HyperHeuristic;
import com.g52aim.project.tsp.heuristics.AdjacentSwap;
import com.g52aim.project.tsp.heuristics.DavissHillClimbing;
import com.g52aim.project.tsp.heuristics.NextDescent;
import com.g52aim.project.tsp.heuristics.PMX;
import com.g52aim.project.tsp.heuristics.Reinsertion;
import com.g52aim.project.tsp.heuristics.TwoOpt;
import com.g52aim.project.tsp.hyperheuristics.SR_IE_HH;
import com.g52aim.project.tsp.instance.InitialisationMode;
import com.g52aim.project.tsp.instance.Location;
import com.g52aim.project.tsp.instance.reader.TSPInstanceReader;
import com.g52aim.project.tsp.interfaces.HeuristicInterface;
import com.g52aim.project.tsp.interfaces.ObjectiveFunctionInterface;
import com.g52aim.project.tsp.interfaces.TSPInstanceInterface;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;
import com.g52aim.project.tsp.interfaces.Visualisable;
import com.g52aim.project.tsp.interfaces.XOHeuristicInterface;

import AbstractClasses.ProblemDomain;

public class G52AIMTSP extends ProblemDomain implements Visualisable {

	private String[] instanceFiles = {
		"d1291", "d18512", "dj38", "usa13509", "qa194", "ch71009"
	};
	
	private TSPSolutionInterface[] solutions;
	
	public TSPSolutionInterface bestSolution;
	
	public TSPInstanceInterface instance;
	
	private HeuristicInterface[] heuristics;
	
	ObjectiveFunctionInterface f = null;
	
	public G52AIMTSP(long seed) {

		super(seed);
		
		// TODO - set default memory size and create the array of low-level heuristics
	}
	
	public TSPSolutionInterface getSolution(int index) {
		
		// TODO 
		return null;
	}
	
	public TSPSolutionInterface getBestSolution() {
		
		// TODO 
		return null;
	}

	@Override
	public double applyHeuristic(int hIndex, int currentIndex, int candidateIndex) {
		
		// TODO - apply heuristic and return the objective value of the candidate solution
		return 0.0d;
	}

	@Override
	public double applyHeuristic(int hIndex, int parent1Index, int parent2Index, int candidateIndex) {
		
		// TODO - apply heuristic and return the objective value of the candidate solution
		return 0.0d;
	}

	@Override
	public String bestSolutionToString() {
		
		// TODO
		return null;
	}

	@Override
	public boolean compareSolutions(int a, int b) {

		// TODO
		return false;
	}

	@Override
	public void copySolution(int a, int b) {

		// TODO - BEWARE this should copy the solution, not the reference to it!
		//			That is, that if we apply a heuristic to the solution in index 'b',
		//			then it does not modify the solution in index 'a' or vice-versa.
	}

	@Override
	public double getBestSolutionValue() {

		// TODO
		return 0.0d;
	}
	
	@Override
	public double getFunctionValue(int index) {
		
		// TODO
		return 0.0d;
	}

	// TODO
	@Override
	public int[] getHeuristicsOfType(HeuristicType type) {
		
		// TODO
		return null;
	}

	@Override
	public int[] getHeuristicsThatUseDepthOfSearch() {
		
		// TODO
		return null;
	}

	@Override
	public int[] getHeuristicsThatUseIntensityOfMutation() {
		
		// TODO
		return null;
	}

	@Override
	public int getNumberOfHeuristics() {

		// TODO - has to be hard-coded due to the design of the HyFlex framework
		return -1;
	}

	@Override
	public int getNumberOfInstances() {

		// TODO
		return -1;
	}

	@Override
	public void initialiseSolution(int index) {

		// TODO - make sure that you also update the best solution!
	}

	@Override
	public void loadInstance(int instanceId) {

		String SEP = FileSystems.getDefault().getSeparator();
		String instanceName = "instances" + SEP + "tsp" + SEP + instanceFiles [instanceId] + ".tsp";

		// TODO create instance reader and problem instance
		// ...

		// TODO set the objective function in each of the heuristics
		// ...
	}

	@Override
	public void setMemorySize(int size) {

		// TODO
	}

	@Override
	public String solutionToString(int index) {

		// TODO
		return null;
	}

	@Override
	public String toString() {

		// TODO change 'PSY...' to be your username
		return "PSY...'s G52AIM TSP";
	}
	
	private void updateBestSolution(int index) {
		
		// TODO
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

	public static void main(String [] args) {
		
		long seed = 527893l;
		long timeLimit = 10_000;
		G52AIMTSP tsp = new G52AIMTSP(seed);
		HyperHeuristic hh = new SR_IE_HH(seed);
		tsp.loadInstance( 0 );
		hh.setTimeLimit(timeLimit);
		hh.loadProblemDomain(tsp);
		hh.run();
		
		double best = hh.getBestSolutionValue();
		System.out.println(best);
		
		// TODO you will need to populate this based on your representation!
		List<Location> routeLocations = new ArrayList<>();
		SolutionPrinter.printSolution(routeLocations);
	}


}
