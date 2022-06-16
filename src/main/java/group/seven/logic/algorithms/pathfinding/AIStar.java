package group.seven.logic.algorithms.pathfinding;

import group.seven.enums.Action;
import group.seven.enums.AlgorithmType;
import group.seven.enums.Cardinal;
import group.seven.logic.algorithms.Algorithm;
import group.seven.logic.geometric.Pythagoras;
import group.seven.logic.geometric.Rectangle;
import group.seven.logic.geometric.Vector;
import group.seven.logic.geometric.XY;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Intruder;
import group.seven.model.agents.Move;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.Tile;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import static group.seven.enums.TileType.WALL;

public class AIStar implements Algorithm {

    Scenario scenario;
    Intruder agent;
    Map<XY, ANode> map;
    ANode start;
    ANode goal;


    public AIStar(Agent agent) {
        this.agent = (Intruder) agent;
        scenario = agent.scenario;
        map = new LinkedHashMap<>(scenario.WIDTH * scenario.HEIGHT);
        for (int i = 0; i <= scenario.WIDTH; i++) {
            for (int j = 0; j <= scenario.HEIGHT; j++) {
                XY pos = new XY(i, j);
                map.put(pos, ANode.NodeBuilder.buildDefault(pos));
            }
        }

        XY pos = agent.getXY();
        Rectangle targetArea = scenario.targetArea.area();
        goal = new ANode.NodeBuilder(targetArea.getX() + targetArea.getIntWidth() / 2, targetArea.getY() + targetArea.getIntHeight() / 2).build();
        start = new ANode.NodeBuilder(scenario.getTileMap().getTile(pos))
                .withG(0) //because we are at the initial spawn position
                .withH(goal.getPxy().distance(new Vector(pos)))
                .withTraversed(true)
                .withTimestamp(agent.getTime())
                .withType(scenario.getTileMap().getTile(pos).getType())
                .build();

        ((Intruder) agent).updateOrientationToGoal();
        agent.updateVision();
        updateView();
    }

    public void updateView() {
        XY pos = agent.getXY();
        for (Tile t : agent.getSeenTiles()) {
            ANode tile = (pos.equals(t.getXY())) ? start : (
                    new ANode.NodeBuilder(t.getX(), t.getY())
                            .withType(t.getType())
                            .withTimestamp(agent.getTime())
                            .withG(start.getPxy().distance(new Vector(pos)))
                            .withH(goal.getPxy().distance(new Vector(pos)))
                            .build());

            map.put(t.getXY(), tile);
        }
    }


    @Override
    public Move getNext() {
        Cardinal directionGoal = agent.getOrientationToGoal();
        int steps = 0;
        XY start = agent.getXY();
        XY next = start.add(agent.getDirection().unitVector);
        if (directionGoal.equals(agent.getDirection()) && map.get(next).getType() != WALL) {
            while (directionGoal.equals(agent.getDirection()) && scenario.getTileMap().getTile(next).getType() != WALL) {
                ANode n = map.get(next);
                steps++;
                directionGoal = Pythagoras.fromAngleToCardinal(new Vector(start).angle(n.getPxy()), start, n.getXy());
                start = start.add(agent.getDirection().unitVector);
            }
            return new Move(Action.MOVE_FORWARD, steps, agent);
        } else if (scenario.getTileMap().getTile(next).getType() != WALL) {
            //return new Move(, 0, agent);
        } else {
            return new Move(Action.values()[new Random().nextInt(5)], 0, agent);
        }
        return null;
    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.A_STAR_ALT;
    }
}
