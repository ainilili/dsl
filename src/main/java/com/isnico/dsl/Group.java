package com.isnico.dsl;

import com.isnico.dsl.exception.DslException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {

    private Column column;

    public String toSql(List<Object> args){
        if (column == null){
            throw new DslException("group by column is null !");
        }
        return column.toSql(args);
    }

}
