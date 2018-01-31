package com.scisdata.web.util;

/**
 * Created by fangshilei on 17/3/24.
 */

import org.apache.log4j.Logger;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 类辅助
 *
 */
public class ClassUtil {
    /** 日志对象 */
    private static Logger log = Logger.getLogger(ClassUtil.class);
    /**
     * Map keyed by class containing CachedIntrospectionResults.
     * Needs to be a WeakHashMap with WeakReferences as values to allow
     * for proper garbage collection in case of multiple class loaders.
     */
    private static final Map<Class, BeanInfo> classCache = Collections
            .synchronizedMap(new WeakHashMap<Class, BeanInfo>());
    /**
     * 获取类本身的BeanInfo，不包含父类属性
     *
     * @param clazz
     * @return
     */
    public static BeanInfo getSelfBeanInfo(Class<?> clazz) throws Exception {
        try {
            BeanInfo beanInfo;
            if (classCache.get(clazz) == null) {
                beanInfo = Introspector.getBeanInfo(clazz, clazz.getSuperclass());
                classCache.put(clazz, beanInfo);
                // Immediately remove class from Introspector cache, to allow for proper
                // garbage collection on class loader shutdown - we cache it here anyway,
                // in a GC-friendly manner. In contrast to CachedIntrospectionResults,
                // Introspector does not use WeakReferences as values of its WeakHashMap!
                Class classToFlush = clazz;
                do {
                    Introspector.flushFromCaches(classToFlush);
                    classToFlush = classToFlush.getSuperclass();
                } while (classToFlush != null);
            } else {
                beanInfo = classCache.get(clazz);
            }
            return beanInfo;
        } catch (IntrospectionException e) {
            log.error("获取BeanInfo失败", e);
            throw new Exception(e);
        }
    }
    /**
     * 初始化实例
     *
     * @param clazz
     * @return
     */
    public static Object newInstance(Class<?> clazz) throws Exception {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            log.error("根据class创建实例失败", e);
            throw new Exception(e);
        }
    }
}

