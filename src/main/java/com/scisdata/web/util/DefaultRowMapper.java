package com.scisdata.web.util;

import com.scisdata.web.basedao.interfaces.NameHandler;
import org.springframework.jdbc.core.RowMapper;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;

/**
 * 默认通用类型映射转换
 * 
 * User: liyd
 * Date: 2/12/14
 * Time: 10:02 PM
 */
public class DefaultRowMapper implements RowMapper<Object> {
    /** 转换的目标对象 */
    private Class<?>    clazz;
    /** 名称处理器 */
    private NameHandler nameHandler;
    public DefaultRowMapper(Class<?> clazz, NameHandler nameHandler) {
        this.clazz = clazz;
        this.nameHandler = nameHandler;
    }
    @Override
    public Object mapRow(ResultSet resultSet, int i) {
        Object entity = null;
        try {
            entity = ClassUtil.newInstance(this.clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        BeanInfo beanInfo = null;
        try {
            beanInfo = ClassUtil.getSelfBeanInfo(this.clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            String column = pd.getName();//nameHandler.getColumnName(pd.getName());
            Method writeMethod = pd.getWriteMethod();

            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                writeMethod.setAccessible(true);
            }
            try {
                for(int t=1;t<=resultSet.getMetaData().getColumnCount();t++){
                    if(column.equals(resultSet.getMetaData().getColumnName(t))){
                        if("int".equals(pd.getPropertyType().toString()) || pd.getPropertyType().toString().contains("Integer")){
                            writeMethod.invoke(entity, resultSet.getInt(column));
                        }else if("float".equals(pd.getPropertyType().toString()) || pd.getPropertyType().toString().contains("Float")){
                            writeMethod.invoke(entity, resultSet.getFloat(column));
                        }else if("double".equals(pd.getPropertyType().toString()) || pd.getPropertyType().toString().contains("Double")){
                            writeMethod.invoke(entity, resultSet.getDouble(column));
                        }else if("long".equals(pd.getPropertyType().toString()) || pd.getPropertyType().toString().contains("Long")){
                            writeMethod.invoke(entity, resultSet.getLong(column));
                        }else if("short".equals(pd.getPropertyType().toString()) || pd.getPropertyType().toString().contains("Short")) {
                            writeMethod.invoke(entity, resultSet.getShort(column));
                        }else {
                            writeMethod.invoke(entity, resultSet.getObject(column));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return entity;
    }
}