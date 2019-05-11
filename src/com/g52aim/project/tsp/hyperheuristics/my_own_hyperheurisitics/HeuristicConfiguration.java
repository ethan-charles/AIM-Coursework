package com.g52aim.project.tsp.hyperheuristics.my_own_hyperheurisitics;

public class HeuristicConfiguration {

	private double iom, dos;
	
	public HeuristicConfiguration(double iom, double dos) {
		
		this.iom = iom;
		this.dos = dos;
	}

	public double getIom() {
		return iom;
	}

	public double getDos() {
		return dos;
	}
}
