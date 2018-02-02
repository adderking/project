package com.scisdata.web.basedao.bulider;


import com.scisdata.web.basedao.page.PagingParameter;
import org.apache.log4j.Logger;


/**
 * 分页SQL语句构建工具类
 *
 * 创建日期：2017-03-20
 * @author fangshilei
 */
public class PagingSqlBuilder {
	/** log4j对象 */
	private static final Logger log = Logger.getLogger(PagingSqlBuilder.class);
	
	private static final String ORACLE = "oracle"; 
	private static final String MYSQL = "mysql"; 
	private static final String SQLSERVER = "sqlserver"; 
	
	/** 数据库的类型 */
	private String dataBaseType;

	public PagingSqlBuilder() {
	}
	
	public PagingSqlBuilder(String dataBaseType) {
		this.dataBaseType = dataBaseType;
	}

	public String getDataBaseType() {
		return dataBaseType;
	}

	public void setDataBaseType(String dataBaseType) {
		this.dataBaseType = dataBaseType;
	}

	/**
	 * 获得计算总记录数的SQL语句
	 *
	 * @param rawSql
	 * @return
	 * 创建日期：2017-03-20
	 * @author fangshilei
	 */
	public String getCountSql(String rawSql) {
		/*String countSql = "SELECT COUNT(*) AS RECORDS " + rawSql.substring(rawSql.toUpperCase().indexOf("FROM"));
		int orderIndex = countSql.toUpperCase().lastIndexOf("ORDER");
		if(orderIndex >= 0) {
			countSql = countSql.substring(0, orderIndex).trim();
		}
		log.debug(countSql);
		return countSql;*/
		int orderIndex = rawSql.toUpperCase().lastIndexOf("ORDER");
		if(orderIndex >= 0) {
			rawSql = rawSql.substring(0, orderIndex).trim();
		}
		String countSql = "SELECT COUNT(t.id) AS RECORDS from ("+ rawSql+") t";
		log.debug(countSql);
		return countSql;
	}
	
	/**
	 * 获得计算分组总记录数的SQL语句
	 *
	 * @param rawSql
	 * @return
	 * 创建日期：2017-03-20
	 * @author fangshilei
	 */
	public String getGroupCountSql(String rawSql) {
		String groupCountSql =  "SELECT SUM(RECORDS) FROM (" + getCountSql(rawSql) + ") AS T";
		log.debug(groupCountSql);
		return groupCountSql;
	}
	
	/**
	 * 获得分页SQL语句
	 *
	 * @param rawSql
	 * @param paging
	 * @return
	 * 创建日期：2017-03-21
	 * @author fangshilei
	 */
	public String getPagingSql(String rawSql, PagingParameter paging) {
		if(paging == null || paging.isInvalid()) {
			log.debug(rawSql);
			return rawSql;
		}
		int rows = paging.getLimit();
		int start = paging.getStart();
		int end = start + rows;
		if(dataBaseType.toLowerCase().indexOf(ORACLE) >= 0) {
			String pagingSql = "SELECT T.*, ROWNUM AS ROW_NUM FROM (" + rawSql + ") AS T WHERE ROWNUM < " + end;
			if(start == 0) {
				log.debug(pagingSql);
				return pagingSql;
			}
			pagingSql = "SELECT * FROM (" + pagingSql + ") AS T_O WHERE ROW_NUM >= " + start;
			log.debug(pagingSql);
			return pagingSql;
		}
		if(dataBaseType.toLowerCase().indexOf(MYSQL) >= 0) {
			String pagingSql = rawSql + " LIMIT " + start + ", " + rows;
			log.debug(pagingSql);
			return pagingSql;
		}
		if(dataBaseType.toLowerCase().indexOf(SQLSERVER) >= 0) {
			String pagingSql = "SELECT TOP " + end + rawSql.trim().substring(6);
			if(start == 0) {
				log.debug(pagingSql);
				return pagingSql;
			}
			String orders = rawSql.substring(rawSql.toUpperCase().lastIndexOf("ORDER"));
			int subIndex = rawSql.toUpperCase().indexOf("FROM") - 1;
			pagingSql = "SELECT * FROM (" + rawSql.substring(0, subIndex) + ", ROW_NUMBER() OVER(" + orders + 
					") AS ROW_NUM " + rawSql.substring(subIndex, rawSql.toUpperCase().lastIndexOf("ORDER")).trim() + 
					") AS T WHERE ROW_NUM >= " + start + " AND ROW_NUM < " + end;
			log.debug(pagingSql);
			return pagingSql;
		}
		return "";
	}

}
