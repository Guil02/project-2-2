package group.seven.logic.simulation;

import group.seven.Main;
import group.seven.enums.GameMode;
import group.seven.enums.TileType;
import group.seven.gui.SimulationScreen;
import group.seven.logic.geometric.XY;
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
import static group.seven.enums.TileType.*;
import static group.seven.model.environment.Scenario.*;
import static group.seven.utils.Methods.print;

public class Simulator extends AnimationTimer {
    public static Simulator sim;
    public static Random rand = new Random();
    private SimulationScreen display = null;
    private int count = 0;
    private long prev; //used for frame-rate calculation (eventually)
    double elapsedTimeSteps;
    final double timeStep = 0.1; //Or should get from Config or from Scenario, idk
    final boolean guiMode = true;

    public Simulator(Scenario scenario) {
        sim = this;
        spawnAgents(GAME_MODE);
        elapsedTimeSteps = 0;
        if (guiMode) {
            display = new SimulationScreen();
            Main.stage.setScene(new Scene(display));
            Main.stage.centerOnScreen();
        }
        //Either there's a bug in my GUI or in the Vision or in the way agents vision is tracked/stored
        //Arrays.stream(TILE_MAP.agents).forEach(Agent::updateVision);
        if (guiMode) {
            display.render();
        }
        prev = System.nanoTime();
        if (guiMode) {
            start();
        }
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
        if (guiMode) {
            display.render();   //update GUI
            elapsedTimeSteps += timeStep; //update elapsed time steps
        }

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
        //First filters out null agents, then maps agents to their calculatedMoves, and then collects these Moves into a List
        for (Agent agent : TILE_MAP.agents) {
            agent.updateVision();
            agent.updateMap();
        }
        List<Move> allMoves = Arrays.stream(TILE_MAP.agents).filter(Objects::nonNull).map(Agent::calculateMove).toList();

        //List of Moves where the agent's want to move forward (change position). Previous moves List is unaffected.
        List<Move> positionChangeMoves = allMoves.stream().filter(move -> move.action() == MOVE_FORWARD).toList();
        List<Move> rotationChangeMoves = allMoves.stream().filter(move -> move.action() != MOVE_FORWARD).toList();

        CollisionHandler.handle(positionChangeMoves);
        for (Move move : rotationChangeMoves){
            move.agent().executeTurn(move);
            move.agent().clearVision();
            move.agent().updateVision();
            move.agent().updateMap();
        }

        //TODO: determine where to apply the moves to updated the model and the agent's internal model
    }

    /** FOR TESTING PURPOSE ONLY
     * Called every timeStep to update the model.
     * Collects each agent's moves, resolves collisions, updates their vision and applies to the model.
     */
    public void update(List<Move> allMoves) {
        //TODO: sort list such that rotation moves appear last in list. (Not 100% sure if necessary)
        //List of Moves where the agent's want to move forward (change position). Previous moves List is unaffected.
        List<Move> positionChangeMoves = allMoves.stream().filter(move -> move.action() == MOVE_FORWARD).toList();
        List<Move> rotationChangeMoves = allMoves.stream().filter(move -> move.action() != MOVE_FORWARD).toList();

        CollisionHandler.handle(positionChangeMoves);
        for (Move move : rotationChangeMoves){
            move.agent().executeTurn(move);
            move.agent().clearVision();
            move.agent().updateVision();
        }
        updateAllAgents();
        //TODO: determine where to apply the moves to updated the model and the agent's internal model
    }


    private void spawnAgents(GameMode gameMode) {
        print(gameMode);
        switch (gameMode) {
            case EXPLORATION -> spawnAgents(GUARD);

            case SINGLE_INTRUDER, MULTI_INTRUDER -> {
                spawnAgents(GUARD);
                spawnAgents(INTRUDER);
            }
        }
        updateAllAgents();
        print(Arrays.stream(TILE_MAP.agents).filter(Objects::nonNull).map(Agent::getID).toList());
    }

    private void updateAllAgents() {
        for(Agent agent : TILE_MAP.agents){
            agent.update();
        }
    }

    private void spawnAgents(TileType agentType){
        XY point;
        int dx, dy, number;
        switch (agentType) {
            case GUARD -> {
                point = new XY(Scenario.guardSpawnArea.area().getX(), Scenario.guardSpawnArea.area().getY());
                dx = Scenario.guardSpawnArea.area().getIntWidth();
                dy = Scenario.guardSpawnArea.area().getIntHeight();
                number = NUM_GUARDS;
            }
            case INTRUDER -> {
                point = new XY(intruderSpawnArea.area().getX(), intruderSpawnArea.area().getY());
                dx = intruderSpawnArea.area().getIntWidth();
                dy = intruderSpawnArea.area().getIntHeight();
                number = NUM_INTRUDERS;
            }
            default -> {
                point = new XY(0, 0);
                dx = 0;
                dy = 0;
                number = 0;
            }
        }

        int i = 0;
        while(i < number){
            int x = point.x() + (int) (dx * Math.random());
            int y = point.y() + (int) (dy * Math.random());

            Agent agent = switch (agentType) {
                case INTRUDER -> new Intruder(x,y);
                case GUARD -> new Guard(x,y);
                default -> throw new IllegalStateException("Unexpected value: " + agentType);
                //better throw exception to fail-fast to catch bugs quickly, than to pick our heads later down the line
            };
            agent.initializeInitialTile();
            agent.updateVision();
            TILE_MAP.addAgent(agent);
            print("added "+agentType.name()+" : " + agent.getID());
            i++;
        }


    }

    //we don't really need to calculate the total grids every time
    //ideally we just update the coverage when new tiles are explored
    //and we loop through the map matrix in several locations per timestep which is kinda expensive to do so prolly there's a better way.
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
    }
}

