package com.isnico.dsl.session;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ResultSetWrapper {

    private ResultSet resultSet;

    private ResultSetMetaData resultSetMetaData;

    private Map<String, Integer> columns;

    public ResultSetWrapper(ResultSet resultSet, ResultSetMetaData resultSetMetaData) throws SQLException {
        this.resultSet = resultSet;
        this.resultSetMetaData = resultSetMetaData;
        this.columns = new ConcurrentHashMap<>();
        for (int i = 1; i <= resultSetMetaData.getColumnCount(); i ++){
            columns.put(resultSetMetaData.getColumnName(i), i);
        }
    }

    public boolean contains(String column){
        return columns.containsKey(column);
    }

    public boolean next() throws SQLException {
        return resultSet.next();
    }

    public ResultSet resultSet(){
        return resultSet;
    }
}
