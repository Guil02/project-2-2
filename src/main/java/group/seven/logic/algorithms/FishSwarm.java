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
import java.util.Random;

public class FishSwarm implements Algorithm {
    Agent agent;
    LinkedList<Move> moves = new LinkedList<>();
    private final int layerLevels = 2; // levels of neighbours to consider
    private final int F_MAX = 1;
    private final int CROWDING_FACTOR = 5;
    private final double EPSILON = 0.000001;
    private final double min = -1*8*layerLevels;
    private final double max = 8*layerLevels;
    private final double[][] fitnessMap = new double[Scenario.WIDTH][Scenario.HEIGHT];
    //private final double[][] fitnessMap = new double[Scenario.HEIGHT][Scenario.WIDTH];

    public FishSwarm(Agent agent) {
        this.agent = agent;
        calculateFitness();

    //  TESTING PURPOSES
        for (int i = 0; i<fitnessMap[0].length; i++) {
            for (int j = 0; j<fitnessMap[1].length; j++) {
                System.out.print(" "+fitnessMap[i][j]+" ");
            }
            System.out.println();
        }
    }

    @Override
    public AlgorithmType getType() {
        return AlgorithmType.FISH;
    }

    @Override
    public Move getNext() {

        //TODO: CHECK which mode the fish should be
        if (moves.isEmpty()) {
            //CHECK IF VISION = 0 if so, random flip
            if (agent.getSeenTiles().size() == 0) {
                System.out.println("STUCK");
                moves.add(new Move(Action.FLIP,0,agent));
                moves.add(new Move(Action.MOVE_FORWARD,(int)((Math.random()*Scenario.VIEW_DISTANCE)),agent));
                return moves.poll();
            }
            XY agentGlobalPosition = agent.getXY();
            double x_i = getTileValue(new Tile(agentGlobalPosition.x(),agentGlobalPosition.y()));
            Tile x_c_Tile = getHighestFrontier();
            double x_c = getTileValue(x_c_Tile);
            int fishesInVision = countFishesInVision(agent.getSeenTiles());

            //SWARMING MODE
            //IF f(x_c) > f(x_i) i.e if middle point > current point
            if (x_c >= x_i ){//|| Math.abs(x_c - x_i) < EPSILON) {
                moves.addAll(pathFind(x_c_Tile));
                System.out.println("WHERE ARE WE 1");
            }
            //CHASING MODE (another fish)
            //IF f(x_max > f(x_i) and there exists a fish in the visual field --> follow fish
            else if (F_MAX > x_i && fishesInVision!=0) {
                //IF not to crowded follow otherwise escape
                if (fishesInVision < CROWDING_FACTOR ) {
                    moves.addAll(chasingMode(agent.getSeenTiles()));
                    System.out.println("WHERE ARE WE 2");
                }
                else {
                    //too crowded return random move
                    System.out.println("WHERE ARE WE 3");
                    return new RandomMoves(agent).getNext();
                }
            }
            //PRAYING MODE
            //IF f(x_max) == f(x_c) i.e Intruder seen
            else if (F_MAX == x_c){
                moves.addAll(pathFind(x_c_Tile));
                System.out.println("WHERE ARE WE 4");
            }
            else {
                moves.add(new Move(Action.FLIP,0,agent));
                //int test = (int)(Math.random()*2)+1;
                //int scne = Scenario.VIEW_DISTANCE;
                moves.add(new Move(Action.MOVE_FORWARD,(int)((Math.random()*Scenario.VIEW_DISTANCE)),agent));
                System.out.println();
            }
        }
        //System.out.println("MOVE "+moves);
        return moves.poll();

    }


    private int countFishesInVision(List<Tile> seenTiles) {
        int seenFishes=0;
        for (Tile tile : seenTiles) {
            //TODO: This doesn't work - look in global map to see if Guard
            for (Agent potentialGuard : Scenario.TILE_MAP.agents) {
                if (potentialGuard.getType() == TileType.GUARD && potentialGuard.getXY().equals(tile.getXY())) {
                    seenFishes++;
                }
            }

        }
        return seenFishes;
    }

    public List<Move> pathFind(Tile x_c) {
        AStarPathFinder pathFinder = new AStarPathFinder(agent,x_c.getXY());
        return pathFinder.findPath();
    }

    public List<Move> chasingMode(List<Tile> vision) {
        for (Tile tile : vision) {
            for (Agent potentialGuard : Scenario.TILE_MAP.agents) {
                if (potentialGuard.getType() == TileType.GUARD && potentialGuard.getXY().equals(tile.getXY())) {
                    return pathFind(tile);
                }
            }
        }
        System.out.println("ERROR OCCURED");
        return null;
    }

    public double getTileValue(Tile tile){
        if (tile.getType() == TileType.INTRUDER) {
            return 1;
        }
        return fitnessMap[tile.getX()][tile.getY()];
    }

    public void calculateFitness() {
        double maxVal = 0;
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
                    double random = (Math.round(Math.random()*(max-min+1)+min));
                    fitnessMap[y][x] = numberOfNeighbours - walls + random;
                    if (fitnessMap[y][x] > maxVal) {
                        maxVal = fitnessMap[y][x];
                    }
                }
            }
        }
        //normalize fitness
        for (int x = 0; x < fitnessMap[0].length; x++) {
            for (int y = 0; y < fitnessMap[1].length; y++) {
                if (fitnessMap[y][x] == maxVal) {
                    fitnessMap[y][x] = (fitnessMap[y][x] / maxVal) - 0.00001;
                }
                else {
                    fitnessMap[y][x] = fitnessMap[y][x] / maxVal;
                }
            }
        }
    }

    public List<Tile> traverseNeighbours(Tile current, int layer) {
        List<Tile> neighbours = new LinkedList<>();
        //traverse top
        for (int column = current.getY()-layer; column <= current.getY()+layer; column++) {
            int row = current.getX()-layer;
            if (row > Scenario.WIDTH || column > Scenario.HEIGHT) {
                break;
            }
            if (row >= 0 && column >= 0) {
                neighbours.add(Scenario.TILE_MAP.getTile(row,column));
            }
        }
        //traverse right
        for (int row = current.getX()-layer; row <= current.getX()+layer; row++) {
            int column = current.getY()+layer;
            if (row > Scenario.WIDTH || column > Scenario.HEIGHT) {
                break;
            }
            if (row >= 0 && column >= 0) {
                neighbours.add(Scenario.TILE_MAP.getTile(row,column));
            }
        }
        //traverse bottom
        for (int column = current.getY()-layer; column <= current.getY()+layer; column++) {
            int row = current.getX()+layer;
            if (row > Scenario.WIDTH || column > Scenario.HEIGHT) {
                break;
            }
            if (row >= 0 && column >= 0) {
                neighbours.add(Scenario.TILE_MAP.getTile(row,column));
            }
        }
        //traverse left
        for (int row = current.getX()-layer; row <= current.getX()+layer; row++) {
            int column = current.getY()-layer;
            if (row > Scenario.WIDTH || column > Scenario.HEIGHT) {
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

    public Tile getHighestFrontier(){
        Random rand = new Random();
        Tile max = agent.getSeenTiles().get(rand.nextInt(agent.getSeenTiles().size()));
        for (Tile tile : agent.getSeenTiles()) {
            if (tile.getType() == TileType.INTRUDER) {
                return tile;
            } else if (getTileValue(tile) > getTileValue(max)){
                    max = tile;
            }
        }
        return max;
    }
}
