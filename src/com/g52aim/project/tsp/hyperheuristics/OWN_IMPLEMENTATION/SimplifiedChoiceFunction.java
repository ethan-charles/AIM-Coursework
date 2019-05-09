package com.g52aim.project.tsp.hyperheuristics.OWN_IMPLEMENTATION;

public class SimplifiedChoiceFunction {

    private Heuristic[] heuristics;

    private double phi;

    private double lastImprovement = 1;

    private int alpha;

    public SimplifiedChoiceFunction(Heuristic[] heuristics, int alpha) {

        this.heuristics = heuristics;
        this.phi = 0.99;
        this.alpha = alpha;
    }

    public void updateHeuristicData(Heuristic heuristic, long timeApplied, long timeTaken, double diff) {

        HeuristicData data = heuristic.getData();
        // if improve, the f_delta will be negative
        data.setF_delta(diff);
        data.setTimeLastApplied(timeApplied);
        data.setPreviousApplicationDuration(timeTaken);

        // if resulting in improvement
        if (data.getF_delta() < 0) {
            // maximising f1 and minimising f3
            this.phi = 0.99;
        } else {
            // maximising f3
            this.phi = Math.max(this.phi - 0.01, 0.01);
        }
    }

    public Heuristic selectHeuristicToApply() {

        int bestHeuristicIndex = 0;
        double bestHeuristicScore = -Double.MAX_VALUE;
        long timeNow = System.nanoTime();
        for (int i = 0; i < heuristics.length; i++) {
            Heuristic tempHeuristic = heuristics[i];
            double tempScore = calculateScore(tempHeuristic, timeNow);
            // if strict improvement
            if (tempScore > bestHeuristicScore) {
                bestHeuristicScore = tempScore;
                bestHeuristicIndex = i;
            }
        }
        return heuristics[bestHeuristicIndex];
    }

    public void setLastImprovement(double improvement) {
        this.lastImprovement = improvement;
    }

    public double calculateScore(Heuristic h, long currentTime) {

        HeuristicData Hdata = h.getData();
        double f_delta = Hdata.getF_delta();
        // divide by 10000 to make a time unit
        double timeApplyingHeuristic = Hdata.getPreviousApplicationDuration() / 10000;
        double f1 = this.phi * (-f_delta / this.lastImprovement * timeApplyingHeuristic) * alpha;
        double lastTimeApplied = Hdata.getTimeLastApplied();
        double timeDifference = (currentTime - lastTimeApplied) / Math.pow(10, 9);
        double f3 = (1 - this.phi) * timeDifference;
        return f1 + f3;
    }

}
