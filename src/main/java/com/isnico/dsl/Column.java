package com.isnico.dsl;

import com.isnico.dsl.tools.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Column {

    private String name;

    private String alias;

    private String prefix;

    public String toSql(List<Object> args){
        return (StringUtil.isEmpty(prefix) ? "" : prefix + ".") + name + (StringUtil.isEmpty(alias) ? "" : " as " + alias);
    }
}
