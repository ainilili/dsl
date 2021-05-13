package com.isnico.dsl;

import com.isnico.dsl.tools.CollectionUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Select extends Dql{

    public Select from(Table... tables){
        return from(Arrays.asList(tables));
    }

    public Select from(List<Table> tables){
        this.froms = tables;
        return this;
    }

    public Select join(Join... joins){
        return join(Arrays.asList(joins));
    }

    public Select join(List<Join> joins){
        this.joins = joins;
        return this;
    }

    public Select join(Join join){
        if (joins == null){
            joins = new ArrayList<>();
        }
        joins.add(join);
        return this;
    }

    public Select where(Condition condition){
        this.where = condition;
        return this;
    }

    public Select limit(Integer limit, Integer offset){
        this.limit = limit;
        this.offset = offset;
        return this;
    }

    public Select limit(Integer limit){
        return this.limit(limit, null);
    }

    public Select groupBy(List<Group> groups){
        this.groups = groups;
        return this;
    }

    public Select groupBy(Group... groups){
        return groupBy(Arrays.asList(groups));
    }

    public Select groupBy(Group group){
        if (this.groups == null){
            this.groups = new ArrayList<Group>();
        }
        this.groups.add(group);
        return this;
    }

    public Select orderBy(List<Order> orders){
        this.orders = orders;
        return this;
    }

    public Select orderBy(Order... orders){
        return orderBy(Arrays.asList(orders));
    }

    public Select orderBy(Order order){
        if (this.orders == null){
            this.orders = new ArrayList<Order>();
        }
        this.orders.add(order);
        return this;
    }

    public Context context(){
        List<Object> args = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("select ");
        if (CollectionUtil.isNotEmpty(columns)){
            sqlBuilder.append(CollectionUtil.join(columns.stream().map(c -> c.toSql(args)).collect(Collectors.toList()), ","));
        }else{
            sqlBuilder.append("*");
        }
        if (CollectionUtil.isNotEmpty(froms)){
            sqlBuilder.append(" from ").append(CollectionUtil.join(froms.stream().map(f -> f.toSql(args)).collect(Collectors.toList()), ","));
        }
        if (CollectionUtil.isNotEmpty(joins)){
            sqlBuilder.append(" ").append(CollectionUtil.join(joins.stream().map(j -> j.toSql(args)).collect(Collectors.toList()), " "));
        }
        if (where != null){
            sqlBuilder.append(" where ").append(where.toSql(args));
        }
        if (CollectionUtil.isNotEmpty(groups)){
            sqlBuilder.append(" group by ").append(CollectionUtil.join(groups.stream().map(g -> g.toSql(args)).collect(Collectors.toList()), ","));
        }
        if (CollectionUtil.isNotEmpty(orders)){
            sqlBuilder.append(" order by ").append(CollectionUtil.join(orders.stream().map(o -> o.toSql(args)).collect(Collectors.toList()), ","));
        }
        if (limit != null){
            sqlBuilder.append("limit ").append(limit);
            if (offset != null){
                sqlBuilder.append(" offset ").append(offset);
            }
        }
        return new Context(sqlBuilder.toString(), args);
    }
}
