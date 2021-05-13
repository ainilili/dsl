package com.isnico.dsl.enums;

public enum JoinType {

    LEFT("left"),
    RIGHT("right"),
    INNER("inner"),
    OUTER("outer"),
    ;

    private final String type;

    JoinType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
