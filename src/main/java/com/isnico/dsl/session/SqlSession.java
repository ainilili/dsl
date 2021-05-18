package com.isnico.dsl.session;

import com.isnico.dsl.Context;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.List;

public interface SqlSession {

    Connection connection() throws SQLException;

    <T> List<T> selectList(Class<T> clazz, String sql, Object... args) throws SQLException, InstantiationException, IllegalAccessException;

    <T> T selectOne(Class<T> clazz, String sql, Object... args) throws SQLException, InstantiationException, IllegalAccessException;

    <T> List<T> selectList(Class<T> clazz, Context context) throws SQLException, InstantiationException, IllegalAccessException;

    <T> T selectOne(Class<T> clazz, Context context) throws SQLException, InstantiationException, IllegalAccessException;

}
