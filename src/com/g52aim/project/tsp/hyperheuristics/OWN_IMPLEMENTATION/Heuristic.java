package com.g52aim.project.tsp.hyperheuristics.OWN_IMPLEMENTATION;

public class Heuristic {

    private final HeuristicConfiguration configuration;

    private final HeuristicData data;

    private final int heuristicId;
    private final int uniqueId;

    public Heuristic(HeuristicConfiguration configuration, int heuristicId, int uniqueId, long startTimeNano) {

        this.configuration = configuration;
        this.data = new HeuristicData(startTimeNano);
        this.heuristicId = heuristicId;
        this.uniqueId = uniqueId;
    }

    public HeuristicConfiguration getConfiguration() {
        return configuration;
    }

    public HeuristicData getData() {
        return data;
    }

    public int getHeuristicId() {
        return heuristicId;
    }

    public int getUniqueId() {
        return uniqueId;
    }
}
