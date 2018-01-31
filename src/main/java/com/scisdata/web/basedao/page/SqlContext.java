package com.scisdata.web.basedao.page;

/**
 * Created by fangshilei on 17/3/24.
 */

import java.util.List;

/**
 * 执行sql的上下文内容
 */
public class SqlContext {
    /** 执行的sql */
    private StringBuilder sql;
    /** 主键名称 */
    private String        primaryKey;
    /** 参数，对应sql中的?号 */
    private List<Object> params;
    public SqlContext(StringBuilder sql, String primaryKey, List<Object> params) {
        this.sql = sql;
        this.primaryKey = primaryKey;
        this.params = params;
    }

    public StringBuilder getSql() {
        return sql;
    }

    public void setSql(StringBuilder sql) {
        this.sql = sql;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }
}
