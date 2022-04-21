package group.seven.logic.simulation;

import group.seven.Main;
import group.seven.enums.GameMode;
import group.seven.gui.SimulationScreen;
import group.seven.gui.TempView;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Guard;
import group.seven.model.agents.Intruder;
import group.seven.model.agents.Move;
import group.seven.model.environment.Scenario;
import group.seven.utils.Tuple;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static group.seven.enums.Action.MOVE_FORWARD;
import static group.seven.enums.TileType.WALL;
import static group.seven.model.environment.Scenario.*;
import static group.seven.utils.Methods.print;

public class Simulator extends AnimationTimer {
    public static Simulator sim;
    private final SimulationScreen display;
    private TempView view;
    private int count = 0;
    private long prev; //used for frame-rate calculation (eventually)
    double elapsedTimeSteps;
    final double timeStep = 0.1; //Or should get from Config or from Scenario, idk

    public Simulator(Scenario scenario) {
        sim = this;
        spawnAgents(GAME_MODE);
        elapsedTimeSteps = 0;

        display = new SimulationScreen();

        Main.stage.setScene(new Scene(display));
        Main.stage.centerOnScreen();

        //setInitialVision();
        display.render();


        //view = new TempView(scenario);

        prev = System.nanoTime();
        start();
    }

    public static void pause() {
        sim.stop();
    }

    /**
     * Main Simulation Loop. Executed every "timeStep"
     * First we update the model by calculating and applying all the agent's moves (if legal),
     * Then update the GUI to reflect the state of the model after the timeStep.
     * @param now current time in nanoseconds. Can be used for frame-rate calculation
     */
    @Override
    public void handle(long now) {
        count++;

        update();           //update model
        display.render();   //update GUI
        elapsedTimeSteps += timeStep; //update elapsed time steps

        //Goal: update only every second. I realize this is not what's happening here though since handle is being executed ~60x per second
        if (((int)elapsedTimeSteps) % 10 == 0) {
            Tuple<Double, Double> coverage = calculateCoverage();
            display.updateStats(elapsedTimeSteps, coverage.a()); //guard coverage, will move to SimulationScreen to handle prolly
        }

        //TODO: implement GameOver condition checking
        if (count > 100000) stop();
        prev = System.nanoTime();
    }

    /**
     * Called every timeStep to update the model.
     * Collects each agent's moves, resolves collisions, updates their vision and applies to the model.
     */
    protected void update() {
        //TODO: sort list such that rotation moves appear last in list. (Not 100% sure if necessary)
        //Creates a list of Moves for each agent's calculatedMove.
        //First filters out null agents, then map agents to their calculatedMoves, and then collect these Moves into a List
        List<Move> allMoves = Arrays.stream(TILE_MAP.agents).filter(Objects::nonNull)
                .map(Agent::calculateMove)
                .toList();

        //List of Moves where the agent's want to move forward (change position). Previous moves List is unaffected.
        List<Move> positionChangeMoves = allMoves.stream()
                .filter(move -> move.action() == MOVE_FORWARD).toList();

        //CollisionHandler.handle(positionChangedMoves) or something like that idk

        //TODO: pass list of positionChangeMoves to collision handler
        //TODO: update vision of (rotation) agents
        //TODO: determine where to apply the moves to updated the model and the agent's internal model
    }

    //TODO: actually make agents spawn in their spawn location
    private void spawnAgents(GameMode gameMode) {
        print(gameMode);
        switch (gameMode) {
            case EXPLORATION -> {
                for (int i = 0; i < NUM_GUARDS; i++) {
                    Guard agent = new Guard(5, 10);
                    TILE_MAP.addAgent(agent);
                }
            }

            case SINGLE_INTRUDER, MULTI_INTRUDER -> {
                Random rand = new Random();
                for (int i = 0; i < NUM_GUARDS; i++) {
                    Guard agent = new Guard(5 + rand.nextInt(10), 10 + rand.nextInt(10));
                    TILE_MAP.addAgent(agent);
                    print("added guard : " + agent.getID());
                }

                for (int i = 0; i < NUM_INTRUDERS; i++) {
                    Intruder agent = new Intruder(10, 30);
                    TILE_MAP.addAgent(agent);
                    print("added intruder : " + agent.getID());
                }
            }

        }
        print(Arrays.stream(TILE_MAP.agents).filter(Objects::nonNull).map(Agent::getID).toList());
    }

    //we don't really need to calculate the total grids every time
    //ideally we just update the coverage when new tiles are explored
    //and we loop through the map matrix in several locations per timesteo which is
    //kinda expensive to do so prolly there's a better way.
    //also now we have intruder and guard coverages, but not even sure
    //if necessary to calculate them for this phase (although would be interesting for RQs/experiments)
    public Tuple<Double, Double> calculateCoverage() {
        int guardSeenGrids = 0;
        int intruderSeenGrids = 0;
        double totalGrids = 0;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if(!(TILE_MAP.map[i][j].getType() == WALL)) {
                    totalGrids++;

                    //for guards
                    if(TILE_MAP.map[i][j].getExploredGuard()) {
                        guardSeenGrids++;
                    }

                    if(TILE_MAP.map[i][j].getExploredIntruder()) {
                        intruderSeenGrids++;
                    }
                }
            }
        }

        return new Tuple<>((guardSeenGrids / totalGrids)*100, (intruderSeenGrids/totalGrids)*100);

//        return (seenGrids/totalSize)*100;
//        return (d1/d2)*100;
    }
}

