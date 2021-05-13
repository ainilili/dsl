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
public class Table {

    private String name;

    private String schema;

    private String alias;

    private Select query;

    public String toSql(List<Object> args){
        StringBuilder builder = new StringBuilder();
        if (StringUtil.isNotEmpty(schema)){
            builder.append(schema).append(".");
        }
        if (StringUtil.isNotEmpty(name)){
            builder.append(name);
        }else if (query != null){
            Context context = query.context();
            if (CollectionUtil.isNotEmpty(context.getArgs())){
                args.addAll(context.getArgs());
            }
            builder.append("(").append(context.getSql()).append(")");
        }else{
            throw new DslException("table not defined name or query !");
        }
        if (StringUtil.isNotEmpty(alias)){
            builder.append(" ").append(alias);
        }
        return builder.toString();
    }

}
