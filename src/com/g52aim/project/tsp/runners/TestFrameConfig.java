package com.g52aim.project.tsp.runners;

import com.g52aim.project.tsp.hyflex.HyFlexTestFrame;

public class TestFrameConfig extends HyFlexTestFrame {

    protected final long RUN_TIME_IN_SECONDS = 1;

    protected final String[] PROBLEM_DOMAINS = {"TSP"};

    protected final int[][] INSTANCE_IDs = {{0, 1, 2}};

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
