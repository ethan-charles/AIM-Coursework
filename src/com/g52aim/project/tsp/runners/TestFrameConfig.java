package com.g52aim.project.tsp.runners;

public class TestFrameConfig extends HyFlexTestFrame {

    protected final long RUN_TIME_IN_SECONDS = 60;

    protected final String[] PROBLEM_DOMAINS = {"TSP"};

    protected final int[][] INSTANCE_IDs = {{0}};

    protected final int LIST_LENGTH = 20;

    @Override
    public String[] getDomains() {

        return this.PROBLEM_DOMAINS;
    }

    @Override
    public int[][] getInstanceIDs() {

        return this.INSTANCE_IDs;
    }

    @Override
    public long getRunTime() {

        return (MILLISECONDS_IN_TEN_MINUTES * RUN_TIME_IN_SECONDS) / 600;
    }

}
