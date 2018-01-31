package com.scisdata.web.util;

import com.scisdata.web.basedao.interfaces.DBTable;
import com.scisdata.web.basedao.interfaces.Key;
import com.scisdata.web.basedao.interfaces.NameHandler;

import java.lang.reflect.Field;

/**
 * 默认名称处理handler
 * 
 * User: fangshilei
 * Date: 2/12/14
 * Time: 4:51 PM
 */
public class DefaultNameHandler implements NameHandler {
    /**
     * 根据类注解获取表名
     *
     * @param clazz
     * @return
     */
    @Override
    public String getTableName(Class<?> clazz) {
        DBTable dbTable = clazz.getAnnotation(DBTable.class);
        return dbTable.value();
    }
    /**
     * 根据属性注解获取主键名
     *
     * @param clazz
     * @return
     */
    @Override
    public String getPrimaryName(Class<?> clazz) {
       Field[] fields = clazz.getDeclaredFields();
        String primaryName = "";
        for (Field f:fields) {
            Key key = f.getAnnotation(Key.class);
            if (key!=null) {
                primaryName = key.value();
                break;
            }
        }
        return primaryName;
    }
//    /**
//     * 根据属性名获取列名
//     *
//     * @param fieldName
//     * @return
//     */
//    @Override
//    public String getColumnName(String fieldName) {
//        //获取字段  不包括主键
//        Field[] fields = clazz.getDeclaredFields();
//        String primaryName = "";
//        for (Field f:fields) {
//            Key key = f.getAnnotation(Key.class);
//            if (!"".equals(key.value()) && key.value() != null) {
//                primaryName = key.value();
//            }
//        }
//        return primaryName;
//        return PREFIX + underlineName;
//    }
}