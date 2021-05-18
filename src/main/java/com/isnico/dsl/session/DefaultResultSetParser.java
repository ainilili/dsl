package com.isnico.dsl.session;

import com.isnico.dsl.configure.Configuration;
import com.isnico.dsl.tools.ClassUtil;
import lombok.Data;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class DefaultResultSetParser extends ResultSetParser{

    private Configuration configuration;

    @Override
    public <T> T parse(Class<T> clazz, ResultSetWrapper resultSetWrapper) throws SQLException, IllegalAccessException, InstantiationException {
        if (ClassUtil.isPrimitive(clazz)){
            return parsePrimitive(clazz, resultSetWrapper);
        }else if (clazz.isAssignableFrom(Map.class)){
            // todo
            return null;
        }else{
            Field[] fields = ClassUtil.getCachedFields(clazz);
            T t = clazz.newInstance();
            for (Field field: fields){
                String column = configuration.rewriteColumn(field);
                if (resultSetWrapper.contains(column)){
                    ClassUtil.setFieldValue(field, t, resultSetWrapper.resultSet().getObject(column));
                }
            }
            return t;
        }
    }

    public <T> T parsePrimitive(Class<T> clazz, ResultSetWrapper resultSetWrapper) throws SQLException {
        return (T) resultSetWrapper.resultSet().getObject(0);
    }




}
