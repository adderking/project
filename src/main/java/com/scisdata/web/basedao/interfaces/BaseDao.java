package com.scisdata.web.basedao.interfaces;


import com.scisdata.web.basedao.page.DataStore;
import com.scisdata.web.basedao.page.PagingParameter;
import com.scisdata.web.bean.User;
import com.scisdata.web.exception.DaoAccessException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * 数据查询DAO接口
 *
 * 创建日期：2015-9-26
 * 修改说明：
 * @author fangshilei
 */
public interface BaseDao<T> {
	
	/**
	 * 查询SQL语句
	 *
	 * @param sql                        SQL语句
	 * @param params                     SQL参数
	 * @return List<Map<String, Object>> 查询结果
	 * 创建日期：2015-9-26
	 * 修改说明：
	 * @author fangshilei
	 */
	public List<Map<String, Object>> search(String sql, Object... params) throws DaoAccessException;
	public List<Map<String, Object>> search(String sql, List<Object> params) throws DaoAccessException;
	public List<Map<String, Object>> search(String sql, Map<String, Object> params) throws DaoAccessException;
	
	/**
	 * 查询SQL语句
	 *
	 * @param <R>           行记录类型
	 * @param sql           SQL语句
	 * @param mapRowMapper  Map行数据映射对象
	 * @param params        SQL参数 
	 * @return List<R>      查询结果
	 * 创建日期：2015-9-26
	 * 修改说明：
	 * @author fangshilei
	 */
	public <R> List<R> search(String sql, MapRowMapper<R> mapRowMapper, Object... params) throws DaoAccessException;
	public <R> List<R> search(String sql, MapRowMapper<R> mapRowMapper, List<Object> params) throws DaoAccessException;
	public <R> List<R> search(String sql, MapRowMapper<R> mapRowMapper, Map<String, Object> params) throws DaoAccessException;
	
	/**
	 * 分页查询数据
	 *
	 * @param sql                             SQL语句
	 * @param paging                          分页参数
	 * @param params                          SQL参数
	 * @return DataStore<Map<String, Object>> 分页数据
	 * 创建日期：2015-9-26
	 * 修改说明：
	 * @author fangshilei
	 */
	public DataStore<Map<String, Object>> search(String sql, PagingParameter paging, Object... params) throws DaoAccessException;
	public DataStore<Map<String, Object>> search(String sql, PagingParameter paging, List<Object> params) throws DaoAccessException;
	public DataStore<Map<String, Object>> search(String sql, PagingParameter paging, Map<String, Object> params) throws DaoAccessException;
	public List queryBuliderList(Object entity) throws DaoAccessException;
	/**
	 * 分页查询数据
	 *
	 * @param <R>           行记录类型
	 * @param sql           SQL语句
	 * @param mapRowMapper  Map行数据映射对象
	 * @param paging        分页参数
	 * @param params        SQL参数
	 * @return DataStore<R> 分页数据
	 * 创建日期：2015-9-26
	 * 修改说明：
	 * @author fangshilei
	 */
	public <R> DataStore<R> search(String sql, MapRowMapper<R> mapRowMapper, PagingParameter paging, Object... params) throws DaoAccessException;
	public <R> DataStore<R> search(String sql, MapRowMapper<R> mapRowMapper, PagingParameter paging, List<Object> params) throws DaoAccessException;
	public <R> DataStore<R> search(String sql, MapRowMapper<R> mapRowMapper, PagingParameter paging, Map<String, Object> params) throws DaoAccessException;

	/**查询sql语句
	 * 泛型成实体
	 */
	public List queryList(String sql, Object... params);
	public List queryList(String sql, List<Object> params);
	public List queryList(String sql, Map<String, Object> params);
	/**
	 * 分页查询 泛型实体
	 */
	public  DataStore pageQueryObject(String sql, PagingParameter paging, Object... params) throws DaoAccessException;
	public  DataStore pageQueryList(String sql, PagingParameter paging, List<Object> params) throws DaoAccessException;
	public  DataStore pageQueryMap(String sql, PagingParameter paging, Map<String, Object> params) throws DaoAccessException;
	public  DataStore<Map<String,Object>> pageQuery(String sql, PagingParameter paging, Object... params) throws DaoAccessException;
	public  DataStore<Map<String,Object>> pageQuery(String sql, PagingParameter paging, List<Object> params) throws DaoAccessException;
	public  DataStore<Map<String,Object>> pageQuery(String sql, PagingParameter paging, Map<String, Object> params) throws DaoAccessException;
	//public <R> DataStore<User> pageQueryEntityObject(String sql, PagingParameter paging, R entity, Object... params) throws DaoAccessException;
	/**
	 * 添加 信息
	 */
	public Long insertBackId(T entity);
	public void insert(T entity);
	public Long insertBackId(String sql, T entity);
	public Long insertBackId(String sql, Map<String, Object> map);
	public void insert(String sql, List<Object> list);
	public void insert(String sql, Map<String, Object> map);
	/**
	 * 更新记录
	 */
	public void update(T entity);
	public void update(String sql, List<Object> list);
	public void update(String sql, Map<String, Object> map);
	/**
	 * 删除记录
	 */
	public void delete(Long id);
	public void delete(String sql, Long id);
	/**
	 * 根据ID查询信息
	 */
	public T getById(Long id);
	/**
	 * 根据ID查询信息返回Map
	 */
	public Map<String,Object> getMapById(Long id);
	public Map<String,Object> getMapById(String sql, Long id);
	public Map<String,Object> getMapBySql(String sql);
}
