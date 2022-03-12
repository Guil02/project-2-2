package org.group7.alt.model.interaction;

import org.group7.alt.enums.Cardinal;

public class Rotate implements Action {

    Cardinal rotation;

    public Rotate(Cardinal direction) {
        rotation = direction;
    }

    @Override
    public Action getAction() {
        return this;
    }
}
