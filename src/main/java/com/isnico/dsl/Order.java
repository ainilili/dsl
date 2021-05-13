package com.isnico.dsl;

import com.isnico.dsl.exception.DslException;
import com.isnico.dsl.tools.CollectionUtil;
import com.isnico.dsl.tools.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Column column;

    private boolean asc;

    public String toSql(List<Object> args){
        if (column == null){
            throw new DslException("order by column is null !");
        }
        return column.toSql(args) + " " + (asc ? "asc" : "desc");
    }
}
