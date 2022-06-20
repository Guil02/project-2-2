package group.seven.logic.simulation.experimentation;

import group.seven.enums.AlgorithmType;

import java.util.ArrayList;
import java.util.List;

public class DataLogger {

    private double endTime;
    private int rotations;
    private double coverage;
    private int distanceTraveled;
    private int numGuards;
    private int guardKills;
    private int numIntruders;
    private int intrudersAtTarget;
    private double ratioGI;
    private int avgSpeed;
    private int kills;
    private AlgorithmType guardAlgorithm;
    private AlgorithmType intruderAlgorithm;
    private double density; //percentage of map covered in free tiles
    private int captureDistance;
    private int numTargetTiles;
    private double avgShortestPath;
    private AlgorithmType winner;
    private int minDegree;
    private int diameter;

    private List<String> header = new ArrayList<>();

    private DataLogger(Builder builder) {
        endTime = builder.endTime;
        rotations = builder.rotations;
        coverage = builder.coverage;
        distanceTraveled = builder.distanceTraveled;
        numGuards = builder.numGuards;
        guardKills = builder.guardKills;
        numIntruders = builder.numIntruders;
        intrudersAtTarget = builder.intrudersAtTarget;
        ratioGI = builder.ratioGI;
        avgSpeed = builder.avgSpeed;
        kills = builder.kills;
        guardAlgorithm = builder.guardAlgorithm;
        intruderAlgorithm = builder.intruderAlgorithm;
        density = builder.density;
        captureDistance = builder.captureDistance;
        numTargetTiles = builder.numTargetTiles;
        avgShortestPath = builder.avgShortestPath;
        winner = builder.winner;
        minDegree = builder.minDegree;
        diameter = builder.diameter;
    }

    public String[] getHeader() {
        return header.toArray(new String[0]);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(DataLogger copy) {
        Builder builder = new Builder();
        builder.endTime = copy.getEndTime();
        builder.rotations = copy.getRotations();
        builder.coverage = copy.getCoverage();
        builder.distanceTraveled = copy.getDistanceTraveled();
        builder.numGuards = copy.getNumGuards();
        builder.guardKills = copy.getGuardKills();
        builder.numIntruders = copy.getNumIntruders();
        builder.intrudersAtTarget = copy.getIntrudersAtTarget();
        builder.ratioGI = copy.getRatioGI();
        builder.avgSpeed = copy.getAvgSpeed();
        builder.kills = copy.getKills();
        builder.guardAlgorithm = copy.getGuardAlgorithm();
        builder.intruderAlgorithm = copy.getIntruderAlgorithm();
        builder.density = copy.getDensity();
        builder.captureDistance = copy.getCaptureDistance();
        builder.numTargetTiles = copy.getNumTargetTiles();
        builder.avgShortestPath = copy.getAvgShortestPath();
        builder.winner = copy.getWinner();
        builder.minDegree = copy.getMinDegree();
        builder.diameter = copy.getDiameter();
        return builder;
    }

    public double getEndTime() {
        return endTime;
    }

    public int getRotations() {
        return rotations;
    }

    public double getCoverage() {
        return coverage;
    }

    public int getDistanceTraveled() {
        return distanceTraveled;
    }

    public int getNumGuards() {
        return numGuards;
    }

    public int getGuardKills() {
        return guardKills;
    }

    public int getNumIntruders() {
        return numIntruders;
    }

    public int getIntrudersAtTarget() {
        return intrudersAtTarget;
    }

    public double getRatioGI() {
        return ratioGI;
    }

    public int getAvgSpeed() {
        return avgSpeed;
    }

    public int getKills() {
        return kills;
    }

    public AlgorithmType getGuardAlgorithm() {
        return guardAlgorithm;
    }

    public AlgorithmType getIntruderAlgorithm() {
        return intruderAlgorithm;
    }

    public double getDensity() {
        return density;
    }

    public int getCaptureDistance() {
        return captureDistance;
    }

    public int getNumTargetTiles() {
        return numTargetTiles;
    }

    public double getAvgShortestPath() {
        return avgShortestPath;
    }

    public AlgorithmType getWinner() {
        return winner;
    }

    public int getMinDegree() {
        return minDegree;
    }

    public int getDiameter() {
        return diameter;
    }

    /**
     * {@code DataLogger} builder static inner class.
     */
    public static final class Builder {
        private double endTime;
        private int rotations;
        private double coverage;
        private int distanceTraveled;
        private int numGuards;
        private int guardKills;
        private int numIntruders;
        private int intrudersAtTarget;
        private double ratioGI;
        private int avgSpeed;
        private int kills;
        private AlgorithmType guardAlgorithm;
        private AlgorithmType intruderAlgorithm;
        private double density;
        private int captureDistance;
        private int numTargetTiles;
        private double avgShortestPath;
        private AlgorithmType winner;
        private int minDegree;
        private int diameter;

        private Builder() {
        }

        /**
         * Sets the {@code endTime} and returns a reference to this Builder enabling method chaining.
         *
         * @param endTime the {@code endTime} to set
         * @return a reference to this Builder
         */
        public Builder withEndTime(double endTime) {
            this.endTime = endTime;
            return this;
        }

        /**
         * Sets the {@code rotations} and returns a reference to this Builder enabling method chaining.
         *
         * @param rotations the {@code rotations} to set
         * @return a reference to this Builder
         */
        public Builder withRotations(int rotations) {
            this.rotations = rotations;
            return this;
        }

        /**
         * Sets the {@code coverage} and returns a reference to this Builder enabling method chaining.
         *
         * @param coverage the {@code coverage} to set
         * @return a reference to this Builder
         */
        public Builder withCoverage(double coverage) {
            this.coverage = coverage;
            return this;
        }

        /**
         * Sets the {@code distanceTraveled} and returns a reference to this Builder enabling method chaining.
         *
         * @param distanceTraveled the {@code distanceTraveled} to set
         * @return a reference to this Builder
         */
        public Builder withDistanceTraveled(int distanceTraveled) {
            this.distanceTraveled = distanceTraveled;
            return this;
        }

        /**
         * Sets the {@code numGuards} and returns a reference to this Builder enabling method chaining.
         *
         * @param numGuards the {@code numGuards} to set
         * @return a reference to this Builder
         */
        public Builder withNumGuards(int numGuards) {
            this.numGuards = numGuards;
            return this;
        }

        /**
         * Sets the {@code guardKills} and returns a reference to this Builder enabling method chaining.
         *
         * @param guardKills the {@code guardKills} to set
         * @return a reference to this Builder
         */
        public Builder withGuardKills(int guardKills) {
            this.guardKills = guardKills;
            return this;
        }

        /**
         * Sets the {@code numIntruders} and returns a reference to this Builder enabling method chaining.
         *
         * @param numIntruders the {@code numIntruders} to set
         * @return a reference to this Builder
         */
        public Builder withNumIntruders(int numIntruders) {
            this.numIntruders = numIntruders;
            return this;
        }

        /**
         * Sets the {@code intrudersAtTarget} and returns a reference to this Builder enabling method chaining.
         *
         * @param intrudersAtTarget the {@code intrudersAtTarget} to set
         * @return a reference to this Builder
         */
        public Builder withIntrudersAtTarget(int intrudersAtTarget) {
            this.intrudersAtTarget = intrudersAtTarget;
            return this;
        }

        /**
         * Sets the {@code ratioGI} and returns a reference to this Builder enabling method chaining.
         *
         * @param ratioGI the {@code ratioGI} to set
         * @return a reference to this Builder
         */
        public Builder withRatioGI(double ratioGI) {
            this.ratioGI = ratioGI;
            return this;
        }

        /**
         * Sets the {@code avgSpeed} and returns a reference to this Builder enabling method chaining.
         *
         * @param avgSpeed the {@code avgSpeed} to set
         * @return a reference to this Builder
         */
        public Builder withAvgSpeed(int avgSpeed) {
            this.avgSpeed = avgSpeed;
            return this;
        }

        /**
         * Sets the {@code kills} and returns a reference to this Builder enabling method chaining.
         *
         * @param kills the {@code kills} to set
         * @return a reference to this Builder
         */
        public Builder withKills(int kills) {
            this.kills = kills;
            return this;
        }

        /**
         * Sets the {@code guardAlgorithm} and returns a reference to this Builder enabling method chaining.
         *
         * @param guardAlgorithm the {@code guardAlgorithm} to set
         * @return a reference to this Builder
         */
        public Builder withGuardAlgorithm(AlgorithmType guardAlgorithm) {
            this.guardAlgorithm = guardAlgorithm;
            return this;
        }

        /**
         * Sets the {@code intruderAlgorithm} and returns a reference to this Builder enabling method chaining.
         *
         * @param intruderAlgorithm the {@code intruderAlgorithm} to set
         * @return a reference to this Builder
         */
        public Builder withIntruderAlgorithm(AlgorithmType intruderAlgorithm) {
            this.intruderAlgorithm = intruderAlgorithm;
            return this;
        }

        /**
         * Sets the {@code density} and returns a reference to this Builder enabling method chaining.
         *
         * @param density the {@code density} to set
         * @return a reference to this Builder
         */
        public Builder withDensity(double density) {
            this.density = density;
            return this;
        }

        /**
         * Sets the {@code captureDistance} and returns a reference to this Builder enabling method chaining.
         *
         * @param captureDistance the {@code captureDistance} to set
         * @return a reference to this Builder
         */
        public Builder withCaptureDistance(int captureDistance) {
            this.captureDistance = captureDistance;
            return this;
        }

        /**
         * Sets the {@code numTargetTiles} and returns a reference to this Builder enabling method chaining.
         *
         * @param numTargetTiles the {@code numTargetTiles} to set
         * @return a reference to this Builder
         */
        public Builder withNumTargetTiles(int numTargetTiles) {
            this.numTargetTiles = numTargetTiles;
            return this;
        }

        /**
         * Sets the {@code avgShortestPath} and returns a reference to this Builder enabling method chaining.
         *
         * @param avgShortestPath the {@code avgShortestPath} to set
         * @return a reference to this Builder
         */
        public Builder withAvgShortestPath(double avgShortestPath) {
            this.avgShortestPath = avgShortestPath;
            return this;
        }

        /**
         * Sets the {@code winner} and returns a reference to this Builder enabling method chaining.
         *
         * @param winner the {@code winner} to set
         * @return a reference to this Builder
         */
        public Builder withWinner(AlgorithmType winner) {
            this.winner = winner;
            return this;
        }

        /**
         * Sets the {@code minDegree} and returns a reference to this Builder enabling method chaining.
         *
         * @param minDegree the {@code minDegree} to set
         * @return a reference to this Builder
         */
        public Builder withMinDegree(int minDegree) {
            this.minDegree = minDegree;
            return this;
        }

        /**
         * Sets the {@code diameter} and returns a reference to this Builder enabling method chaining.
         *
         * @param diameter the {@code diameter} to set
         * @return a reference to this Builder
         */
        public Builder withDiameter(int diameter) {
            this.diameter = diameter;
            return this;
        }

        /**
         * Returns a {@code DataLogger} built from the parameters previously set.
         *
         * @return a {@code DataLogger} built with parameters of this {@code DataLogger.Builder}
         */
        public DataLogger build() {
            return new DataLogger(this);
        }
    }
}
