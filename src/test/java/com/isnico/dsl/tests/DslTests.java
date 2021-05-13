package com.isnico.dsl.tests;

import com.isnico.dsl.Column;
import com.isnico.dsl.Context;
import com.isnico.dsl.Dsl;
import org.junit.Test;

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
}
