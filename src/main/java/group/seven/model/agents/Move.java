package group.seven.model.agents;

import group.seven.enums.Action;

//public record Move(Action action, int distance, Cardinal direction, Tile destination) { }

/**
 * Simple encoding of a move. All fields are final.
 * All getters automatically generated.
 * toString(), equals(), hashCode(), also auto-generated (but can be overidden)
 * Can add instance methods but not instance fields.
 * @param action Action Enum: the type of action the agent wishes to perform
 * @param distance the amount of tiles the agent wants to move. Distance is 0 for rotating actions
 * @param agent Reference to the agent object itself
 */
public record Move(Action action, int distance, Agent agent) {}