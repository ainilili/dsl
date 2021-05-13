package com.isnico.dsl.enums;

public enum Operate {

    AND("and", "and", 1),
    OR("or", "or", 1),
    BETWEEN("between", "between", 2),
    EQ("eq", "=", 3),
    NE("not_eq", "!=", 3),
    GT("gt", ">", 3),
    GTE("gte", ">=", 3),
    LT("lt", "<", 3),
    LTE("lte", "<=", 3),
    LIKE("like", "like", 3),
    NOT_LIKE("not like", "not like", 3),
    IN("in", "in", 4),
    NOT_IN("not in", "not in", 4),
    NULL("null", "is null", 5),
    NOT_NULL("not null", "is not null", 5),
    ;

    private final String name;

    private final String operate;

    private final int type;

    Operate(String name, String operate, int type) {
        this.name = name;
        this.operate = operate;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getOperate() {
        return operate;
    }

    public int getType() {
        return type;
    }
}
