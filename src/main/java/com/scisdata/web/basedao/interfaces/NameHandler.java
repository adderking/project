package com.scisdata.web.basedao.interfaces;

/**
 * 名称处理接口
 * 
 * User: fangshilei
 * Date: 2/12/16
 * Time: 4:51 PM
 */
public interface NameHandler {
    /**
     * 根据实体名获取表名
     *
     * @param clazz
     * @return
     */
    public String getTableName(Class<?> clazz);
    /**
     * 根据表名获取主键名
     * 
     * @param clazz
     * @return
     */
    public String getPrimaryName(Class<?> clazz);
//    /**
//     * 根据属性名获取列名
//     *
//     * @param fieldName
//     * @return
//     */
//    public String getColumnName(String fieldName);
}