package com.isnico.dsl.session;

import com.isnico.dsl.Context;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lombok.Data;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class DefaultSqlSession implements SqlSession{

    private DataSource dataSource;

    private ResultSetParser resultSetParser;

    @Override
    public Connection connection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public <T> List<T> selectList(Class<T> clazz, String sql, Object... args) throws SQLException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = preparedStatement(sql, args);
        preparedStatement.execute();
        ResultSetWrapper rw = new ResultSetWrapper(preparedStatement.getResultSet(), preparedStatement.getMetaData());
        List<T> results = new ArrayList<>();
        while(rw.next()){
            results.add(resultSetParser.parse(clazz, rw));
        }
        return results;
    }

    @Override
    public <T> T selectOne(Class<T> clazz, String sql, Object... args) throws SQLException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = preparedStatement(sql, args);
        preparedStatement.execute();
        ResultSetWrapper rw = new ResultSetWrapper(preparedStatement.getResultSet(), preparedStatement.getMetaData());
        T t = null;
        while(rw.next()){
            t = resultSetParser.parse(clazz, rw);
        }
        return t;
    }

    @Override
    public <T> List<T> selectList(Class<T> clazz, Context context) throws SQLException, InstantiationException, IllegalAccessException {
        return selectList(clazz, context.getSql(), context.getArgs());
    }

    @Override
    public <T> T selectOne(Class<T> clazz, Context context) throws SQLException, InstantiationException, IllegalAccessException {
        return selectOne(clazz, context.getSql(), context.getArgs());
    }

    public PreparedStatement preparedStatement(String sql, Object... args) throws SQLException{
        Connection connection = connection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        if (args != null && args.length > 0){
            for (int index = 0; index < args.length; index ++){
                preparedStatement.setObject(index + 1, args[index]);
            }
        }
        return preparedStatement;
    }

}
