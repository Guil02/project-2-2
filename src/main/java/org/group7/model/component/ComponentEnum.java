package org.group7.model.component;

public enum ComponentEnum {
    GUARD_COMPONENT(0, ""),
    INTRUDER_COMPONENT(1, ""),
    WALL_COMPONENT(2, ""),
    TELEPORTER_COMPONENT(3, ""),
    SHADED_AREA_COMPONENT(4, "");

    private final int id;
    private final String texture;

    ComponentEnum(int id, String texture){ this.id = id; this.texture = texture;}
    public int getId(){return id;}
    public String getTexture(){return texture;}
}
