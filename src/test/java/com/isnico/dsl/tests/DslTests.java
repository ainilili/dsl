package com.isnico.dsl.tests;

import com.isnico.dsl.Column;
import com.isnico.dsl.Context;
import com.isnico.dsl.Dsl;
import com.isnico.dsl.configure.DefaultConfiguration;
import com.isnico.dsl.session.DefaultResultSetParser;
import com.isnico.dsl.session.DefaultSqlSession;
import com.isnico.dsl.session.SqlSession;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

import java.sql.SQLException;

public class DslTests extends Dsl{

    @Test
    public void testSelectDsl(){
        Context context = select()
                .from(table(User.class))
                .where(and(
                    eq(column(User::getId), 1),
                    like(column(User::getName), "nico")
                ))
                .context();
        System.out.println(context.getSql());
        System.out.println(context.getArgs());
    }

    @Test
    public void testSelectJoin(){
        Context context = select()
                .from(table(User.class))
                .join(leftJoin(table(Posts.class), eq(column(User::getId), column(Posts::getId))))
                .join(rightJoin(table(Follower.class), eq(column(Posts::getId), column(Follower::getId))))
                .where(and(
                        eq(column(User::getId), 1),
                        like(column(User::getName), "nico")
                ))
                .context();
        System.out.println(context.getSql());
        System.out.println(context.getArgs());
    }

    @Test
    public void testSelectJoinAlias(){
        Context context = select()
                .from(table(User.class, "u"))
                .join(leftJoin(table(Posts.class, "p"), eq(column("u", User::getId), column("p", Posts::getId))))
                .join(rightJoin(table(Follower.class, "f"), eq(column("p", Posts::getId), column("f", Follower::getId))))
                .where(and(
                        eq(column("u", User::getId), 1),
                        like(column("u", User::getName), "nico")
                ))
                .context();
        System.out.println(context.getSql());
        System.out.println(context.getArgs());
    }

    @Test
    public void testSelectSubQuery(){
        Context context = select()
                .from(table(select().from(table(User.class)), "u"))
                .join(leftJoin(table(Posts.class, "p"), eq(column("u", User::getId), column("p", Posts::getId))))
                .join(rightJoin(table(Follower.class, "f"), eq(column("p", Posts::getId), column("f", Follower::getId))))
                .where(and(
                        eq(column("u", User::getId), 1),
                        like(column("u", User::getName), "nico")
                ))
                .context();
        System.out.println(context.getSql());
        System.out.println(context.getArgs());
    }

    @Test
    public void testSession() throws ClassNotFoundException, IllegalAccessException, SQLException, InstantiationException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/test?characterEncoding=UTF-8");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("root");
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        HikariDataSource ds = new HikariDataSource(hikariConfig);

        DefaultSqlSession sqlSession = new DefaultSqlSession();
        DefaultResultSetParser defaultResultSetParser = new DefaultResultSetParser();
        defaultResultSetParser.setConfiguration(new DefaultConfiguration());
        sqlSession.setResultSetParser(defaultResultSetParser);
        sqlSession.setDataSource(ds);

        User user = sqlSession.selectOne(User.class, select().from(table(User.class)).where(eq(column(User::getId), 1)).context());
        System.out.println(user.getId());
    }
}
