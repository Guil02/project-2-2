package group.seven.logic.algorithms;

import group.seven.enums.Action;
import group.seven.enums.AlgorithmType;
import group.seven.logic.geometric.XY;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Move;
import group.seven.model.environment.Tile;

import java.util.LinkedList;

import static group.seven.enums.TileType.INTRUDER;

public class AntsPursuit implements Algorithm {
    Agent agent;
    private final EVAW evaw;
    private final LinkedList<Move> moves = new LinkedList<>();

    public AntsPursuit(Agent agent) {
        this.agent = agent;
        evaw = new EVAW(agent);
    }

    @Override
    public Move getNext() {
        if (moves.isEmpty()) {
            XY target = seeTarget();
            if (target.x() != -1 && target.y() != -1) {
//                System.out.println("path finding to "+target+"");
                AStarPathFinder pf = new AStarPathFinder(agent, target);
                moves.addAll(pf.findPath());
            } else {
                moves.add(evaw.getNext());
                moves.addAll(evaw.moves);
            }
        }
        if (moves.isEmpty()) {
            return new Move(Action.NOTHING, 0, agent);
        }


//        Move nextMove = moves.get(0);
//        moves.remove(0);
        return moves.poll();
    }

    public XY seeTarget() {
        for (Tile t : agent.getSeenTiles()) {
            for (Agent a : agent.scenario.TILE_MAP.agents) {
                XY agentPos; //doing the coordinate transform is expensive, so only initialize if a == Intruder
                if (a.getType() == INTRUDER && (agentPos = a.getXY()).equals(t.getXY())) {
                    return agentPos;
                }
            }
        }

        //tODO: not sure if makes sense with coordinate transform cuz that's an valid coordinate
        return new XY(-1, -1);
    }

    @Override
    public AlgorithmType getType() {
        return null;
    }
}
