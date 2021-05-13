package com.isnico.dsl;

import com.isnico.dsl.enums.JoinType;
import com.isnico.dsl.exception.DslException;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Join {

    private JoinType type;

    private Table right;

    private Condition on;

    public String toSql(List<Object> args){
        if (right == null){
            throw new DslException("right table is null!");
        }
        if (type == null){
            throw new DslException("type is null!");
        }
        String sql = type.getType() + " join " + right.toSql(args);
        if (on != null){
            sql += " on " + on.toSql(args);
        }
        return sql;
    }

}
