package com.wenx.oauth.authoauth.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;


/**
 * @author wenx
 */
@Slf4j
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {

    /**
     * 方法说明：map转化为对象
     * 
     * @param map
     * @param t
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> t) {
        try {
            T instance = t.newInstance();
            populate(instance, map);
            return instance;
        } catch (Exception e) {
            log.error("[Herodotus] |- BeanUtils mapToBean error！", e);
            return null;
        }
    }
}
