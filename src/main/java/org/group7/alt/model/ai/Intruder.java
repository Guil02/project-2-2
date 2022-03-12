package org.group7.alt.model.ai;

import org.group7.alt.logic.algorithms.interfaces.IntruderStrategy;

import java.awt.*;

public class Intruder extends Explorer {

    IntruderStrategy intruderStrategy;

    public Intruder(Point pos, IntruderStrategy strategy) {
        super(pos);
        intruderStrategy = strategy;
    }
}
