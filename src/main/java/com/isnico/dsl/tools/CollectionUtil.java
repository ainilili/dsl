package com.isnico.dsl.tools;

import java.util.Collection;

public class CollectionUtil {

    public static boolean isEmpty(Collection<?> collection){
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection){
        return ! isEmpty(collection);
    }

    public static String join(Collection<?> collection, String s){
        int i = 0;
        int len = collection.size();
        StringBuilder builder = new StringBuilder();
        for(Object c: collection){
            builder.append(c);
            if (i < len - 1){
                builder.append(s);
            }
            i ++;
        }
        return builder.toString();
    }

    public static Object get(Collection<?> collection, int index){
        int i = 0;
        for (Object o: collection){
            if (i == index){
                return o;
            }
            i ++;
        }
        return null;
    }
}
