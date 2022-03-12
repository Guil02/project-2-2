package org.group7.model.algorithms;

import org.group7.enums.Actions;
import org.group7.model.Grid;

public interface Algorithm {
    public Grid calculateMovement();
    public Actions calculateAction();
}
