package group.seven.logic.simulation;

import group.seven.Main;
import group.seven.enums.GameMode;
import group.seven.enums.Status;
import group.seven.enums.TileType;
import group.seven.gui.GameEnd;
import group.seven.gui.SimulationScreen;
import group.seven.logic.geometric.XY;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Guard;
import group.seven.model.agents.Intruder;
import group.seven.model.agents.Move;
import group.seven.model.environment.Pheromone;
import group.seven.model.environment.Scenario;
import group.seven.utils.Config;
import group.seven.utils.Tuple;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static group.seven.enums.Action.MOVE_FORWARD;
import static group.seven.enums.TileType.*;
import static group.seven.utils.Methods.print;

public class Simulator extends AnimationTimer {
    public static Random rand = new Random();
    private SimulationScreen display = null;
    private int count = 0;
    private long prev; //used for frame-rate calculation (eventually)
    double elapsedTimeSteps;
    final double timeStep = 0.1; //Or should get from Config or from Scenario, idk
    boolean guiMode = Config.GUI_MODE;
    final int RANGE_TO_CATCH_INTRUDER = 1;
    final int TIME_NEEDED_IN_TARGET_AREA_INTRUDER = 5;

    public static Status status;
    public Scenario scenario;

    public Simulator(Scenario scenario) {
        this.scenario = scenario;
        rand = new Random();

        spawnAgents(scenario.GUARD_GAME_MODE);
        print(scenario.agents.size(), true);

        elapsedTimeSteps = 0;
        if (guiMode) {
            display = new SimulationScreen(this);
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

        status = Status.RUNNING;
        print("Started : " + scenario.NAME + "\n");
    }

    public void pause() {
        stop();
        status = Status.PAUSED;
    }

    /**
     * Main Simulation Loop. Executed every "timeStep"
     * First we update the model by calculating and applying all the agent's moves (if legal),
     * Then update the GUI to reflect the state of the model after the timeStep.
     *
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
        System.out.print("\rElapsed Time Steps: " + elapsedTimeSteps + "\t framerate: " + ((double) now - prev) / 1e9);


//        //Goal: update only every second. I realize this is not what's happening here though since handle is being executed ~60x per second
//        if (((int) elapsedTimeSteps) % 10 == 0) {
//            Tuple<Double, Double> coverage = calculateCoverage();
//            display.updateStats(elapsedTimeSteps, coverage); //guard coverage, will move to SimulationScreen to handle prolly
//        }

        for (Agent a : scenario.agents)
            a.setTime(elapsedTimeSteps);

        //TODO: implement GameOver condition checking
        if (count > 100000) stop();

        prev = System.nanoTime();
    }


    private boolean checkGameOver(GameMode gameMode, TileType agent) {
        //print(gameMode.toString());
        if (agent == GUARD) {
            switch (gameMode) {
                case SINGLE_INTRUDER_CAUGHT -> {
                    status = Status.GUARD_WIN;
                    return true;
                }

                case ALL_INTRUDERS_CAUGHT -> {
                    if (scenario.INTRUDERS_CAUGHT == scenario.NUM_INTRUDERS) {
                        status = Status.GUARD_WIN;
                        return true;
                    }
                }
            }
        }

        if (agent == INTRUDER) {
            switch (gameMode) {
                case ONE_INTRUDER_AT_TARGET -> {
                    status = Status.INTRUDER_WIN;
                    return true;
                }

                case ALL_INTRUDER_AT_TARGET -> {
                    if (scenario.INTRUDERS_AT_TARGET == scenario.NUM_INTRUDERS) {
                        status = Status.INTRUDER_WIN;
                        return true;
                    }
                }
            }
        }

        return false;
    }

    protected void endSimulation() {
        try {
            Thread.sleep(3600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        GameEnd end = new GameEnd(display);
        Main.stage.setScene(new Scene(end));
        Main.stage.centerOnScreen();
    }

    /**
     * Called every timeStep to update the model.
     * Collects each agent's moves, resolves collisions, updates their vision and applies to the model.
     */
    protected void update() {
        //TODO: sort list such that rotation moves appear last in list. (Not 100% sure if necessary)
        //Creates a list of Moves for each agent's calculatedMove.
        //First filters out null agents, then maps agents to their calculatedMoves, and then collects these Moves into a List
        //List<Agent> guards = Arrays.stream(TILE_MAP.agents).filter(a -> a.getType() == GUARD).toList();
        //List<Agent> intruders = Arrays.stream(TILE_MAP.agents).filter(a -> a.getType() == INTRUDER).toList();


        for (Agent agent : scenario.getTileMap().agents) {
            agent.updateVision();
            agent.updateMap();
            if (agent.agentType == GUARD) {
                for (Agent intruder : scenario.TILE_MAP.agents) {
                    if (intruder.agentType == INTRUDER) {
                        if (agent.getXY().equalsWithinRange(intruder.getXY(), RANGE_TO_CATCH_INTRUDER)) {
                            ((Intruder) intruder).killIntruder();

                            if (checkGameOver(scenario.GUARD_GAME_MODE, GUARD)) {
                                System.out.println("GUARDS WON");
                                stop();
                                Agent.IDs = 0;
                                if (guiMode)
                                    endSimulation();
                                //endSimulation();
                            }
                        }
                    }
                }
            } else {//if agent is not Guard it has to be an Intruder
                for (Agent intruder : scenario.TILE_MAP.agents) {
                    if (intruder.agentType == INTRUDER) {
                        if (scenario.targetArea.contains(intruder.getXY())) {
                            int inTargetAreaSince = ((Intruder) intruder).intruderInTargetArea();
                            print("Intruder " + intruder.getID() + " made it to target");
                            print("In target area since: " + inTargetAreaSince);
                            if (inTargetAreaSince >= TIME_NEEDED_IN_TARGET_AREA_INTRUDER) {
                                if (checkGameOver(scenario.INTRUDER_GAME_MODE, INTRUDER)) {
                                    System.out.println("INTRUDER WON");
                                    stop(); // stops AnimationTimer
                                    Agent.IDs = 0;
                                    if (guiMode)
                                        endSimulation();
                                }
                            }
                        } else {
                            ((Intruder) intruder).intruderNotInTargetArea(); //reset time in target area counter
                        }
                    }
                }
            }
        }
        //System.out.println("TARGET AREA: "+targetArea.area().contains(intruder.x,intruder.y));


        //List<Move> allMoves = new LinkedList<>();
        List<Move> positionChangeMoves = new LinkedList<>();
        List<Move> rotationChangeMoves = new LinkedList<>();
        for (Agent a : scenario.TILE_MAP.agents) {
            Move m = a.calculateMove();
            //allMoves.add(m);
            if (m.action() == MOVE_FORWARD)
                positionChangeMoves.add(m);
            else
                rotationChangeMoves.add(m);
        }

        CollisionHandler.handle(positionChangeMoves, scenario);
        for (Move move : rotationChangeMoves) {
            move.agent().executeTurn(move);
            move.agent().clearVision();
            //move.agent().updateVision();
            //move.agent().updateMap();
        }
        updatePheromones();
        updateAllAgents();
    }

    private void updatePheromones() {
        for (Pheromone f : scenario.TILE_MAP.getPheromones()) {
            f.update();
        }
    }

    /**
     * FOR TESTING PURPOSE ONLY
     * Called every timeStep to update the model.
     * Collects each agent's moves, resolves collisions, updates their vision and applies to the model.
     */
    public void update(List<Move> allMoves) {
        //List of Moves where the agent's want to move forward (change position). Previous moves List is unaffected.
        List<Move> positionChangeMoves = allMoves.stream().filter(move -> move.action() == MOVE_FORWARD).toList();
        List<Move> rotationChangeMoves = allMoves.stream().filter(move -> move.action() != MOVE_FORWARD).toList();

        CollisionHandler.handle(positionChangeMoves, scenario);
        for (Move move : rotationChangeMoves) {
            move.agent().executeTurn(move);
//            move.agent().clearVision();
//            move.agent().updateVision();
        }
        updateAllAgents();
    }

    private void spawnAgents(GameMode gameMode) {
        print(gameMode);
        if (gameMode == GameMode.EXPLORATION)
            spawnAgents(GUARD);
        else {
            spawnAgents(GUARD);
            spawnAgents(INTRUDER);
        }
        updateAllAgents();
    }

    private void updateAllAgents() {
        for (Agent agent : scenario.TILE_MAP.agents) {
            agent.update();
        }
    }

    private void spawnAgents(TileType agentType) {
        XY point;
        int dx, dy, number;
        switch (agentType) {
            case GUARD -> {
                point = new XY(scenario.guardSpawnArea.area().getX(), scenario.guardSpawnArea.area().getY());
                dx = scenario.guardSpawnArea.area().getIntWidth();
                dy = scenario.guardSpawnArea.area().getIntHeight();
                number = scenario.NUM_GUARDS;
            }
            case INTRUDER -> {
                point = new XY(scenario.intruderSpawnArea.area().getX(), scenario.intruderSpawnArea.area().getY());
                dx = scenario.intruderSpawnArea.area().getIntWidth();
                dy = scenario.intruderSpawnArea.area().getIntHeight();
                number = scenario.NUM_INTRUDERS;
            }
            default -> {
                point = new XY(0, 0);
                dx = 0;
                dy = 0;
                number = 0;
            }
        }

        int i = 0;
        while (i < number) {
            int x = point.x() + (int) (dx * Math.random());
            int y = point.y() + (int) (dy * Math.random());

            Agent agent = switch (agentType) {
                case INTRUDER -> new Intruder(x, y, scenario, Config.ALGORITHM_INTRUDER);
                case GUARD -> new Guard(x, y, scenario, Config.ALGORITHM_GUARD);
                default -> throw new IllegalStateException("Unexpected value: " + agentType);
                //better throw exception to fail-fast to catch bugs quickly, than to pick our heads later down the line
            };
            agent.initializeInitialTile();
            //agent.updateVision();
            scenario.TILE_MAP.addAgent(agent);
            print("added " + agentType.name() + " : " + agent.getID());
            System.out.println(agent.getType() + ": " + agent.getX() + " " + agent.getY());
            i++;
            scenario.agents.add(agent);
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
        for (int i = 0; i <= scenario.WIDTH; i++) {
            for (int j = 0; j <= scenario.HEIGHT; j++) {
                if (!(scenario.TILE_MAP.map[i][j].getType() == WALL)) {
                    totalGrids++;

                    //for guards
                    if (scenario.TILE_MAP.map[i][j].getExploredGuard()) {
                        guardSeenGrids++;
                    }

                    if (scenario.TILE_MAP.map[i][j].getExploredIntruder()) {
                        intruderSeenGrids++;
                    }
                }
            }
        }

        return new Tuple<>((guardSeenGrids / totalGrids) * 100, (intruderSeenGrids / totalGrids) * 100);
    }
}

