package org.group7.model.component;

/**
 * This ENUM contains all the possible different components with a path to their texture file.
 */
public enum ComponentEnum {
    // all the components with the string path.
    GUARD_COMPONENT(1,""),
    INTRUDER_COMPONENT(2,""),
    WALL_COMPONENT(3,""),
    TELEPORTER_COMPONENT(4,""),
    SHADED_AREA_COMPONENT(5,""),
    TARGET_AREA_COMPONENT(6,""),
    INTRUDER_SPAWN_AREA(7,""),
    GUARD_SPAWN_AREA(8,"");

    // a string with the path to the texture.
    private final String texture;
    // the id of the component
    private final int id;

    // the internal enum constructor and the getter for the texture and the id.
    ComponentEnum(int id, String texture){this.id = id; this.texture = texture;}
    public String getTexture(){return texture;}
    public int getId() {
        return id;
    }
}
