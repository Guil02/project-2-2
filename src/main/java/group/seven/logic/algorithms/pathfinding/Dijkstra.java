package group.seven.logic.algorithms.pathfinding;

import group.seven.enums.AlgorithmType;
import group.seven.logic.algorithms.Algorithm;
import group.seven.logic.geometric.Vector;
import group.seven.model.agents.Move;
import javafx.geometry.Point2D;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dijkstra implements Algorithm {

    public static void main(String[] args) {
//        DNode nodeA = new DNode(new Vector(0, 0));
//        DNode nodeB = new DNode(new Vector(0, 1));
//        DNode nodeC = new DNode(new Vector(2, 5));
//        DNode nodeD = new DNode(new Vector(1, 6));
//        DNode nodeE = new DNode(new Vector(7, 0));
//        DNode nodeF = new DNode(new Vector(8, 4));
//
//
//        nodeA.addAdjacentNode(nodeB, 2);
//        nodeA.addAdjacentNode(nodeC, 4);
//
//        nodeB.addAdjacentNode(nodeC, 3);
//        nodeB.addAdjacentNode(nodeD, 1);
//        nodeB.addAdjacentNode(nodeE, 5);
//
//        nodeC.addAdjacentNode(nodeD, 2);
//
//        nodeD.addAdjacentNode(nodeE, 1);
//        nodeD.addAdjacentNode(nodeF, 4);
//
//        nodeE.addAdjacentNode(nodeF, 2);
//
//        dijkstra(nodeA);
//        printPaths(List.of(nodeA, nodeB, nodeC, nodeE, nodeF));

        int width = 3;
        int height = 3;
        List<DNode> nodes = new ArrayList<>();
        DNode[][] map = new DNode[height][width];

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                DNode node = new DNode(new Vector(row, col));
                map[row][col] = node;
                nodes.add(node);
            }
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
//                if (x - 1 >= 0 && y - 1 > 0 && x + 1 < width && y + 1 < height) {
                try {
                    map[x][y].addAdjacentNode(map[x - 1][y], 1);
                    map[x][y].addAdjacentNode(map[x + 1][y], 1);
                    map[x][y].addAdjacentNode(map[x][y - 1], 1);
                    map[x][y].addAdjacentNode(map[x][y + 1], 1);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        dijkstra(nodes.get(0));
        printPaths(nodes);
    }

    private static void printPaths(List<DNode> nodes) {
        nodes.forEach(node -> {
            String path = node.shortestPath.stream().map(DNode::getName)
                    .collect(Collectors.joining(" -> "));

            System.out.println((path.isBlank()
                    ? "%s : %s".formatted(node.getName(), node.distance)
                    : "%s -> %s : %s".formatted(path, node.getName(), node.distance))
            );
        });

    }

    static class DNode implements Comparable<DNode> {
        static char n = 'A';
        char name;
        final Vector xy;
        int distance = Integer.MAX_VALUE;
        List<DNode> shortestPath = new LinkedList<>();
        Map<DNode, Integer> adjacentNodes = new HashMap<>();

        public DNode(Point2D pxy) {
            xy = (Vector) pxy;
            name = n++;
        }

        public void addAdjacentNode(DNode node, int weight) {
            if (node != null)
                adjacentNodes.put(node, node != this ? weight : 0);
        }

        @Override
        public int compareTo(DNode o) {
            return Integer.compare(this.distance, o.distance);
        }

        public String getName() {
            return "" + name;
        }
    }


    @Override
    public Move getNext() {
        return null;
    }

    public static void dijkstra(DNode start) {
        start.distance = 0;
        Set<DNode> closed = new HashSet<>();
        Queue<DNode> open = new PriorityQueue<>(Collections.singleton(start));
        while (!open.isEmpty()) {
            DNode current = open.poll();
            current.adjacentNodes.entrySet().stream()
                    .filter(e -> !closed.contains(e.getKey()))
                    .forEach(e -> {
                        evaluateDistanceAndPath(e.getKey(), e.getValue(), current);
                        open.add(e.getKey());
                    });
            closed.add(current);
        }
    }

    private static void evaluateDistanceAndPath(DNode adjacent, Integer cost, DNode parent) {
        int distance = parent.distance + cost;
        if (distance < adjacent.distance) {
            adjacent.distance = distance;
            adjacent.shortestPath = Stream.concat(parent.shortestPath.stream(), Stream.of(parent)).toList();
        }
    }

    @Override
    public AlgorithmType getType() {
        return null;
    }
}
