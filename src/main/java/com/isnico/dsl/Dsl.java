package com.isnico.dsl;

import com.isnico.dsl.configure.Configuration;
import com.isnico.dsl.configure.DefaultConfiguration;
import com.isnico.dsl.enums.JoinType;
import com.isnico.dsl.enums.Operate;
import com.isnico.dsl.tools.ClassUtil;
import com.isnico.dsl.tools.DFunction;
import com.isnico.dsl.tools.LambdaUtil;
import lombok.Data;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Data
public class Dsl {

    private Configuration configuration = new DefaultConfiguration();

    public Select select(){
        return new Select();
    }

    public Select select(Column... columns){
        return select(Arrays.asList(columns));
    }

    public Select select(List<Column> columns){
        Select select = new Select();
        select.setColumns(columns);
        return select;
    }

    public Column column(String prefix, String name, String alias){
        return new Column(name, alias, prefix);
    }

    public Column column(String name, String alias){
        return new Column(name, alias, null);
    }

    public Column column(String name){
        return new Column(name, null, null);
    }

    public <T, R> Column column(String prefix, DFunction<T, R> function, String alias){
        SerializedLambda serializedLambda = LambdaUtil.resolve(function);
        Field field = ClassUtil.parseFieldWithGetter(ClassUtil.forClass(serializedLambda.getImplClass()), serializedLambda.getImplMethodName());
        return column(prefix, configuration.rewriteColumn(field), alias);
    }

    public <T, R> Column column(String prefix, DFunction<T, R> function){
        return column(prefix, function, null);
    }

    public <T, R> Column column(DFunction<T, R> function, String alias){
       return column(null, function, alias);
    }

    public <T, R> Column column(DFunction<T, R> function){
        return column(function, null);
    }

    public Join join(JoinType joinType, Table right, Condition on){
        return new Join(joinType, right, on);
    }

    public Join leftJoin(Table right, Condition on){
        return join(JoinType.LEFT, right, on);
    }

    public Join rightJoin(Table right, Condition on){
        return join(JoinType.RIGHT, right, on);
    }

    public Join innerJoin(Table right, Condition on){
        return join(JoinType.INNER, right, on);
    }

    public Join outerJoin(Table right, Condition on){
        return join(JoinType.OUTER, right, on);
    }

    public Table table(String schema, String name, String alias){
        return new Table(name, schema, alias, null);
    }

    public Table table(String name, String alias){
        return table(null, name, alias);
    }

    public Table table(Select query, String alias){
        return new Table(null, null, alias, query);
    }

    public Table table(String name){
        return table(null, name, null);
    }

    public Table table(Select query){
        return new Table(null, null, null, query);
    }

    public Table table(Class<?> clazz, String alias){
        com.isnico.dsl.annotations.Table tableAnnotation = clazz.getDeclaredAnnotation(com.isnico.dsl.annotations.Table.class);
        if (tableAnnotation != null){
            return table(tableAnnotation.schema(), tableAnnotation.value(), tableAnnotation.alias());
        }
        return table(configuration.rewriteTable(clazz), alias);
    }

    public Table table(Class<?> clazz){
        return table(clazz, null);
    }

    public Order order(Column column, boolean asc){
        return new Order(column, asc);
    }

    public Order order(Column column){
        return order(column, false);
    }

    public Group group(Column column){
        return new Group(column);
    }

    public Condition condition(Operate operate, Column column, Object value, List<Condition> conditions, boolean precompile){
        return new Condition(operate, column, value, conditions, precompile);
    }

    public Condition condition(Operate operate, Column column, List<Condition> conditions, Object value){
        return new Condition(operate, column, value, conditions, true);
    }

    public Condition condition(Operate operate, Column column, Object value, boolean precompile){
        return new Condition(operate, column, value, null, precompile);
    }

    public Condition condition(Operate operate, Column column, Object value){
        return new Condition(operate, column, value, null, true);
    }

    public Condition and(List<Condition> conditions){
        return condition(Operate.AND, null, conditions, null);
    }

    public Condition or(List<Condition> conditions){
        return condition(Operate.OR, null, conditions, null);
    }

    public Condition and(Condition... conditions){
        return condition(Operate.AND, null, Arrays.asList(conditions), null);
    }

    public Condition or(Condition... conditions){
        return condition(Operate.OR, null, Arrays.asList(conditions), null);
    }

    public Condition between(Column column, Object left, Object right, boolean... prepared){
        return condition(Operate.BETWEEN, column, Arrays.asList(left, right), prepared.length == 0 || prepared[0]);
    }

    public Condition eq(Column column, Object value, boolean... prepared){
        return condition(Operate.EQ, column, value, prepared.length == 0 || prepared[0]);
    }

    public Condition ne(Column column, Object value, boolean... prepared){
        return condition(Operate.NE, column, value, prepared.length == 0 || prepared[0]);
    }

    public Condition gt(Column column, Object value, boolean... prepared){
        return condition(Operate.GT, column, value, prepared.length == 0 || prepared[0]);
    }

    public Condition gte(Column column, Object value, boolean... prepared){
        return condition(Operate.GTE, column, value, prepared.length == 0 || prepared[0]);
    }

    public Condition lt(Column column, Object value, boolean... prepared){
        return condition(Operate.LT, column, value, prepared.length == 0 || prepared[0]);
    }

    public Condition lte(Column column, Object value, boolean... prepared){
        return condition(Operate.LTE, column, value, prepared.length == 0 || prepared[0]);
    }

    public Condition like(Column column, String value, boolean... prepared){
        return condition(Operate.LIKE, column, value, prepared.length == 0 || prepared[0]);
    }

    public Condition notLike(Column column, String value, boolean... prepared){
        return condition(Operate.NOT_LIKE, column, value, prepared.length == 0 || prepared[0]);
    }

    public Condition isNull(Column column){
        return condition(Operate.NULL, column, null);
    }

    public Condition isNotNull(Column column){
        return condition(Operate.NOT_NULL, column, null);
    }

    public Condition in(Column column, Object value, boolean... prepared){
        return condition(Operate.IN, column, value, prepared.length == 0 || prepared[0]);
    }

    public Condition notIn(Column column, Object value, boolean... prepared){
        return condition(Operate.NOT_IN, column, value, prepared.length == 0 || prepared[0]);
    }

}
