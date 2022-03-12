package org.group7.alt.model.ai;


import org.group7.alt.logic.algorithms.DefaultCoverStrategy;
import org.group7.alt.logic.algorithms.interfaces.GuardStrategy;

import java.awt.*;

public class Guard extends Explorer {

    //markers
    //knowledge base
    GuardStrategy guardStrategy;

    public Guard(Point pos) {
        super(pos);
        guardStrategy = new DefaultCoverStrategy();
    }

    public Guard(Point pos, GuardStrategy strat) {
        super(pos);
        guardStrategy = strat;
    }
}
