package com.isnico.dsl;

import com.isnico.dsl.enums.Operate;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class Dql {

    protected List<Column> columns;

    protected List<Table> froms;

    protected List<Join> joins;

    protected Condition where;

    protected List<Order> orders;

    protected List<Group> groups;

    protected Integer limit;

    protected Integer offset;

}
