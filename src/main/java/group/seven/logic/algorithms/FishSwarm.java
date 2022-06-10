package group.seven.logic.algorithms;

import group.seven.enums.AlgorithmType;
import group.seven.enums.TileType;
import group.seven.model.agents.Agent;
import group.seven.model.agents.Move;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.Tile;

import java.sql.SQLOutput;
import java.util.LinkedList;
import java.util.List;

public class FishSwarm implements Algorithm {
    Agent agent;
    LinkedList<Move> moves = new LinkedList<>();
    private final int F_MAX = 1;
    private final int CROWDING_FACTOR = 2;
    private final int[][] fitnessMap = new int[Scenario.WIDTH][Scenario.HEIGHT];
    private final int layerLevels = 1; // levels of neighbours to consider

    public FishSwarm(Agent agent) {
        this.agent = agent;
        calculateFitness();

    //  TESTING PURPOSES
    //    for (int i = 0; i<fitnessMap[0].length; i++) {
    //        for (int j = 0; j<fitnessMap[1].length; j++) {
    //            System.out.print(" "+fitnessMap[i][j]+" ");
    //        }
    //        System.out.println();
    //    }
    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.FISH;
    }

    @Override
    public Move getNext() {
        //TODO: CHECK which mode the fish should be
        if (moves.isEmpty()) {
            int x_i = getTileValue(new Tile(agent.getX(),agent.getY()));
            Tile x_c_Tile = getHighestFrontier();
            int x_c = getTileValue(x_c_Tile);
            int fishesInVision = countFishesInVision(agent.getSeenTiles());
            //SWARMING MODE
            //IF f(x_c) > f(x_i) i.e if middle point > current point
            if (x_c > x_i) {
                moves.addAll(pathFind(x_c_Tile));
            }
            //CHASING MODE (another fish)
            //IF f(x_max > f(x_i) and there exists a fish in the visual field --> follow fish
            else if (F_MAX > x_i && fishesInVision!=0) {
                //IF not to crowded follow otherwise escape
                if (fishesInVision < CROWDING_FACTOR )
                    moves.addAll(chasingMode(agent.getSeenTiles()));
                else {
                    //too crowded return random move
                    return new RandomMoves(agent).getNext();
                }
            }
            //PRAYING MODE
            //IF f(x_max) == f(x_c) i.e Intruder seen
            else if (F_MAX == x_c){
                moves.addAll(pathFind(x_c_Tile));
            }
        }

        return moves.poll();

    }


    private int countFishesInVision(List<Tile> seenTiles) {
        int seenFishes=0;
        for (Tile tile : seenTiles) {
            if (tile.getType() == TileType.GUARD) {
                seenFishes++;
            }
        }
        return seenFishes;
    }

    public List<Move> pathFind(Tile x_c) {
        AStarPathFinder pathFinder = new AStarPathFinder(agent,x_c.getXY());
        return pathFinder.findPath();
    }

    public List<Move> chasingMode(List<Tile> vision) {
        //TODO: ensure that vision is ordered
        for (Tile tile : vision) {
            if (tile.getType() == TileType.GUARD) {
                return pathFind(tile);
            }
        }
        System.out.println("ERROR OCCURED");
        return null;
    }

    public int getTileValue(Tile tile){
        return fitnessMap[tile.getX()][tile.getY()];
    }

    public void calculateFitness() {
        int numberOfNeighbours = layerLevels*8;
        for (int x = 0; x < fitnessMap[0].length; x++) {
            for (int y = 0; y < fitnessMap[1].length; y++) {
                //loop through the levels of layers
                List<Tile> neighbours = new LinkedList<>();
                if (Scenario.TILE_MAP.getTile(x,y).getType() != TileType.WALL) {
                    for (int layer = 1; layer <= layerLevels; layer++) {
                        //4 independent four loops for each side
                        neighbours.addAll(traverseNeighbours(Scenario.TILE_MAP.getTile(x,y),layer));
                    }
                    int walls = countWalls(neighbours);
                    fitnessMap[y][x] = numberOfNeighbours- walls;
                }
            }
        }
    }

    public List<Tile> traverseNeighbours(Tile current, int layer) {
        List<Tile> neighbours = new LinkedList<>();
        //traverse top
        for (int column = current.getY()-layer; column <= current.getY()+layer; column++) {
            int row = current.getX()-layer;
            if (row > Scenario.WIDTH && column > Scenario.HEIGHT) {
                break;
            }
            if (row >= 0 && column >= 0) {
                neighbours.add(Scenario.TILE_MAP.getTile(row,column));
            }
        }
        //traverse right
        for (int row = current.getX()-layer; row <= current.getX()+layer; row++) {
            int column = current.getY()+layer;
            if (row > Scenario.WIDTH && column > Scenario.HEIGHT) {
                break;
            }
            if (row >= 0 && column >= 0) {
                neighbours.add(Scenario.TILE_MAP.getTile(row,column));
            }
        }
        //traverse bottom
        for (int column = current.getY()-layer; column <= current.getY()+layer; column++) {
            int row = current.getX()+layer;
            if (row > Scenario.WIDTH && column > Scenario.HEIGHT) {
                break;
            }
            if (row >= 0 && column >= 0) {
                neighbours.add(Scenario.TILE_MAP.getTile(row,column));
            }
        }
        //traverse left
        for (int row = current.getX()-layer; row <= current.getX()+layer; row++) {
            int column = current.getY()-layer;
            if (row > Scenario.WIDTH && column > Scenario.HEIGHT) {
                break;
            }
            if (row >= 0 && column >= 0) {
                neighbours.add(Scenario.TILE_MAP.getTile(row,column));
            }
        }
        return neighbours;
    }

    public int countWalls(List<Tile> neighbours) {
        int numberOfWalls = 0;
        for (Tile tile : neighbours) {
            if (tile.getType() == TileType.WALL) {
                numberOfWalls++;
            }
        }
        return numberOfWalls;
    }

    //TODO: check agent class, and connect the frontier to vision
    public Tile getHighestFrontier(){
        Tile max = agent.getSeenFurthestTiles().get(0);
        for (Tile tile : agent.getSeenFurthestTiles()) {
            if (getTileValue(tile) > getTileValue(max)){
                max = tile;
            }
        }
        return max;
    }
}
