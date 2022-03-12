package org.group7.model.enums;

public enum MoveEnum {
    LEFT(2),
    RIGHT(1);
    private final int id;
    MoveEnum(int id){this.id=id;}
    public int getId (){return id;}


}
