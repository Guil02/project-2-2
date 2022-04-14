package group.seven.model.agents;

import group.seven.enums.Action;
import group.seven.enums.Cardinal;
import group.seven.model.environment.Tile;

public record Move(Action action, int distance, Cardinal direction, Tile destination) { }
//public record Move(Action action, int distance, Agent agent) { }