package com.g52aim.project.tsp.hyperheuristics.OWN_IMPLEMENTATION;

public class SimplifiedChoiceFunction {

    private Heuristic[] heuristics;

    private double phi;

    // used to normalise
    private double lastImprovement;

    public SimplifiedChoiceFunction(Heuristic[] heuristics, double initialFitness) {

        this.heuristics = heuristics;
        this.phi = 0.99;
        this.lastImprovement = initialFitness;
    }

    public void updateHeuristicData(Heuristic heuristic, long timeApplied, long timeTaken, double current, double candidate) {

        HeuristicData data = heuristic.getData();
        // if improve, the f_delta will be negative
        data.setF_delta(candidate - current);
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

    public void setLastImprovement(double lastImprovement) {
        this.lastImprovement = lastImprovement;
    }

    public double calculateScore(Heuristic h, long currentTime) {

        HeuristicData Hdata = h.getData();
        double f_delta = Hdata.getF_delta();

        double timeApplyingHeuristic = Hdata.getPreviousApplicationDuration();
        double f1 = this.phi * (-f_delta / this.lastImprovement * timeApplyingHeuristic);
        double lastTimeApplied = Hdata.getTimeLastApplied();
        double timeDifference = (currentTime - lastTimeApplied);
        double f3 = (1 - this.phi) * timeDifference;

        double outputScore = f1 + f3;
        // System.out.println(f1 + " + " + f3 + "= " + outputScore);
        return outputScore;
    }

}
