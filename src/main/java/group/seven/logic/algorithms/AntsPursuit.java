package group.seven.logic.algorithms;

import group.seven.enums.Action;
import group.seven.enums.AlgorithmType;
import group.seven.enums.TileType;
import group.seven.logic.geometric.XY;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Move;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.Tile;

import java.util.LinkedList;
import java.util.List;

import static group.seven.enums.TileType.INTRUDER;

public class AntsPursuit implements Algorithm{
    Agent agent;
    private EVAW evaw;
    private List<Move> moves = new LinkedList<>();

    public AntsPursuit(Agent agent) {
        this.agent = agent;
        evaw = new EVAW(agent);
    }

    @Override
    public Move getNext() {
        if(moves.isEmpty()){
            XY target = seeTarget();
            if(target.x()!=-1 && target.y() != -1){
                AStarPathFinder pf = new AStarPathFinder(agent, target);
                moves.addAll(pf.findPath());
            }
            else{
                evaw.getNext();
                moves.addAll(evaw.moves);
            }
        }
        if(moves.isEmpty()){
            return new Move(Action.NOTHING, 0,agent);
        }


        Move nextMove = moves.get(0);
        moves.remove(0);
        return nextMove;
    }

    public XY seeTarget(){
        for(Tile t : agent.getSeenTiles()){
            for(Agent a : Scenario.TILE_MAP.agents){
                if(a.getType()== INTRUDER && a.getX()==t.getX() && a.getY() ==t.getY()){
                    return new XY(a.getX(),a.getY());
                }
            }
        }
        return new XY(-1,-1);
    }

    @Override
    public AlgorithmType getType() {
        return null;
    }
}
