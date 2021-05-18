package com.isnico.dsl;

import com.isnico.dsl.enums.Operate;
import com.isnico.dsl.exception.DslException;
import com.isnico.dsl.tools.CollectionUtil;
import com.isnico.dsl.tools.DFunction;
import com.isnico.dsl.tools.StringEscapeUtils;
import com.isnico.dsl.tools.StringUtil;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Condition {

    private Operate operate;

    private Column column;

    private Object value;

    private List<Condition> conditions;

    private boolean prepared = true;

    public String toSql(final List<Object> args){
        if (operate == null){
            throw new DslException("condition operate is null !");
        }
        String columnName = "";
        if (column != null){
            columnName = column.toSql(args);
        }
        if (operate.getType() == 1){
            if (CollectionUtil.isEmpty(conditions)){
                return "";
            }
            String sql = CollectionUtil.join(conditions.stream().map(condition -> condition.toSql(args)).collect(Collectors.toList()), " " + operate.getOperate() + " ");
            return "(" + sql + ")";
        }else if (operate.getType() == 2){
            if (value == null){
                throw new DslException("the value of " + operate.getName() + " operate is null !");
            }
            if (value instanceof Collection){
                if (((Collection<?>) value).size() != 2){
                    throw new DslException("the value size of " + operate.getName() + " must be 2 !");
                }
                return columnName + " " + operate.getOperate() + " " + toValue(args, CollectionUtil.get((Collection<?>) value, 0)) + " and " + toValue(args, CollectionUtil.get((Collection<?>) value, 1));
            }else if (value.getClass().isArray()){
                if (Array.getLength(value) != 2){
                    throw new DslException("the value size of " + operate.getName() + " must be 2 !");
                }
                return columnName + " " + operate.getOperate() + " " + toValue(args, Array.get(value, 0)) + " and " + toValue(args, Array.get(value, 1));
            }else{
                throw new DslException("the value type of " + operate.getName() + " must be list or array !");
            }
        }else if (operate.getType() == 3){
            return columnName + " " + operate.getOperate() + " " + toValue(args, value);
        }else if (operate.getType() == 4){
            if (value == null){
                throw new DslException("the value of " + operate.getName() + " operate is null !");
            }
            if (value instanceof Collection){
                if (((Collection<?>) value).size() == 0){
                    throw new DslException("the value list of " + operate.getName() + " can't be empty !");
                }
                return columnName + " " + operate.getOperate() + " (" + CollectionUtil.join(((Collection<?>) value).stream().map(o -> toValue(args, o)).collect(Collectors.toList()), " ,") + ")";
            }else if (value.getClass().isArray()){
                int arrLen = Array.getLength(value);
                if (arrLen == 0){
                    throw new DslException("the value list of " + operate.getName() + " can't be empty !");
                }
                Object[] arr = new Object[arrLen];
                for (int i = 0; i < arrLen; i++){
                    arr[i] = Array.get(value, i);
                }
                return columnName + " " + operate.getOperate() + " (" + CollectionUtil.join((Arrays.stream(arr).map(o -> toValue(args, o)).collect(Collectors.toList())), " ,") + ")";
            }else{
                throw new DslException("the value type of " + operate.getName() + " must be list or array !");
            }
        }else if (operate.getType() == 5){
            return columnName + " " + operate.getOperate();
        }
        throw new DslException("unSupported operate " + operate.getName());
    }

    public Object toValue(List<Object> args, Object value){
        if (value == null){
            return "null";
        }
        if (value instanceof Select){
            Context context = ((Select) value).context();
            if (CollectionUtil.isNotEmpty(context.getArgs())){
                args.addAll(context.getArgs());
            }
            return "(" + context.getSql() + ")";
        }else if (value instanceof Column){
            return ((Column) value).toSql(args);
        }else {
            if (prepared){
                args.add(value);
                return "?";
            }else if (value instanceof Number){
                return value;
            }else{
                return "\'" + StringEscapeUtils.escapeJava(value.toString()) + "\'";
            }
        }
    }

}
