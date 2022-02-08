package org.group7.model.component;

/**
 * This ENUM contains all the possible different components with a path to their texture file.
 */
public enum ComponentEnum {
    // all the components with the string path.
    GUARD_COMPONENT(""),
    INTRUDER_COMPONENT(""),
    WALL_COMPONENT(""),
    TELEPORTER_COMPONENT(""),
    SHADED_AREA_COMPONENT(""),
    TARGET_AREA_COMPONENT(""),
    INTRUDER_SPAWN_AREA(""),
    GUARD_SPAWN_AREA("");

    // a string with the path to the texture.
    private final String texture;

    // the internal enum constructor and the getter for the texture.
    ComponentEnum(String texture){this.texture = texture;}
    public String getTexture(){return texture;}
}
