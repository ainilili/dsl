package com.isnico.dsl.session;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public abstract class ResultSetParser {

    public abstract <T> T parse(Class<T> clazz, ResultSetWrapper resultSetWrapper) throws SQLException, IllegalAccessException, InstantiationException;
}
