package com.g52aim.project.tsp.hyperheuristics.OWN_IMPLEMENTATION;

public class SimplifiedChoiceFunction {

    private Heuristic[] heuristics;

    private double phi;

    public SimplifiedChoiceFunction(Heuristic[] heuristics) {

        this.heuristics = heuristics;
        this.phi = 0.99;
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
        System.out.println(bestHeuristicIndex);
        return heuristics[bestHeuristicIndex];
    }

    public double calculateScore(Heuristic h, long currentTime) {

        HeuristicData Hdata = h.getData();
        double f_delta = Hdata.getF_delta();
        double timeApplyingHeuristic = Hdata.getPreviousApplicationDuration();
        double lastTimeApplied = Hdata.getTimeLastApplied();

        // f1 is an evaluation based on the performance of the heuristic
        double f1 = this.phi * (- f_delta / timeApplyingHeuristic);
        double timeDifference = (currentTime - lastTimeApplied) / Math.pow(10, 2);
        // the f3 variable becomes more significant when improvement is not made over time
        double f3 = (1 - this.phi) * timeDifference;
        double outputScore = f1 + f3;
        // System.out.println(f_delta+ " + " + f3 + "= " + outputScore);
        // System.out.println(f1/f3);
        return outputScore;
    }

}
