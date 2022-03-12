package org.group7.alt.model.ai;

import org.group7.alt.model.interaction.Action;

public interface Agent {

    Action chooseAction();
    boolean step();
    boolean run();
    boolean stop();
    boolean rotate();
}
