package org.group7.alt.model.ai;

import org.group7.alt.enums.Cardinal;

public interface Agent {
    //path planning: a path is a sequence of poses, where a pose is its x, y, phi; where phi is its orientation
    //RRT: sampling based path planning. Not exact like A* but constructs a tree with much fewer nodes
    //      RRT* more exact than RRT and closer to optimal. Very useful especially with a full occupancy grdi
    Pose step();
    boolean run();
    boolean stop();
    Pose rotate(Cardinal direction);
}
