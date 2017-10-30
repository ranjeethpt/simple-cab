package com.datarepublic.simplecab.util;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

/**
 * Created by ranjeethpt on 30/10/17.
 *
 * @author ranjeethpt
 */
public class CabTripKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append(target.getClass().getName());
        for (Object param : params) {
            sb.append(param.toString());
        }
        return sb.toString();
    }
}
