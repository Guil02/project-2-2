package group.seven.logic.algorithms.GeneticNeuralNetwork;

import group.seven.enums.TileType;
import group.seven.model.agents.Agent;
import group.seven.model.environment.Scenario;
import group.seven.model.environment.Tile;

import java.util.List;

import static group.seven.enums.TileType.GUARD;
import static group.seven.enums.TileType.INTRUDER;

public class VisionAnalysis {

    /**
     * This method will count the amount of time the type object appears in the tile.
     * i.e. if tile type is {@link TileType#WALL} then it will check how many tiles in the list have a wall.
     *
     * @param tiles the tiles that need to be checked
     * @param type  the item that needs to be checked for
     * @return the amount of tiles with given type there are in the list.
     */
    public static int numStaticComponent(List<Tile> tiles, TileType type) {
        int count = 0;
        for (Tile tile : tiles) {
            if (tile.getType() == type) {
                count++;
            }
        }
        return count;
    }

    /**
     * same as {@link VisionAnalysis#numStaticComponent(List, TileType)} except for the intruder
     *
     * @param s     you pass the scenario since all the internal agents need to be accessed and since the current agent
     *              in the tile is not stored, we need to access it through another way.
     * @param tiles list of tiles
     * @return amount of intruders in vision
     */
    public static int numIntruders(Scenario s, List<Tile> tiles) {
        int count = 0;
        for (Tile tile : tiles) {
            for (Agent a : s.getTileMap().agents) {
                if (a.getXY().equals(tile.getXY()) && a.getType() == INTRUDER) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * same as {@link VisionAnalysis#numStaticComponent(List, TileType)} except for the guard
     *
     * @param s     you pass the scenario since all the internal agents need to be accessed and since the current agent
     *              in the tile is not stored, we need to access it through another way.
     * @param tiles list of tiles
     * @return amount of guards in vision
     */
    public static int numGuards(Scenario s, List<Tile> tiles) {
        int count = 0;
        for (Tile tile : tiles) {
            for (Agent a : s.getTileMap().agents) {
                if (a.getXY().equals(tile.getXY()) && a.getType() == GUARD) {
                    count++;
                }
            }
        }
        return count;
    }
}
