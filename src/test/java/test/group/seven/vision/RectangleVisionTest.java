package test.group.seven.vision;

import group.seven.enums.Action;
import group.seven.logic.simulation.Simulator;
import group.seven.model.agents.Move;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.ScenarioBuilder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


class RectangleVisionTest {
    @Test
    //make sure that in Simulator class gui Mode is false
    public void visionCheck() {
        File file = new File("/Users/mischatomaszrauch/UM/AcademicYear_02/Project2-2/project-2-2/src/main/resources/scenarios/testmap.txt");
        ScenarioBuilder test = new ScenarioBuilder(file);
        Scenario s = test.build();
        Simulator sim = new Simulator(s);

        System.out.println("num guards " + s.NUM_GUARDS);
        System.out.println("num intruder " + s.NUM_INTRUDERS);
        System.out.println("num agent " + s.NUM_AGENTS);


        //Initiale position of agent
        System.out.println("inital position " + Arrays.toString(s.TILE_MAP.agents));
        System.out.println("inital ENV " + s.TILE_MAP.agents[0].getSeenTiles());

        //first move for agent
        Move move = new Move(Action.TURN_DOWN, 0, s.TILE_MAP.agents[0]);
        List<Move> moves = new LinkedList<Move>();
        moves.add(move);
        sim.update(moves);


        System.out.println("position1 " + Arrays.toString(s.TILE_MAP.agents));
        System.out.println("ENV 1" + s.TILE_MAP.agents[0].getSeenTiles());

        //second move for agent
        Move move2 = new Move(Action.MOVE_FORWARD, 3, s.TILE_MAP.agents[0]);
        List<Move> moves2 = new LinkedList<Move>();
        moves2.add(move2);
        sim.update(moves2);


        System.out.println("position 2" + Arrays.toString(s.TILE_MAP.agents));
        System.out.println("ENV 2" + s.TILE_MAP.agents[0].getSeenTiles());
        System.out.println("SIZE OF ENV 2 " + s.TILE_MAP.agents[0].getSeenTiles().size());

    }
}