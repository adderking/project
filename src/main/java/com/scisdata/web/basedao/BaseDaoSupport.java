package com.scisdata.web.basedao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.scisdata.web.basedao.bulider.PagingSqlBuilder;
import com.scisdata.web.basedao.interfaces.BaseDao;
import com.scisdata.web.basedao.interfaces.MapRowMapper;
import com.scisdata.web.basedao.interfaces.NameHandler;
import com.scisdata.web.basedao.page.DataStore;
import com.scisdata.web.basedao.page.PagingParameter;
import com.scisdata.web.basedao.page.SqlContext;
import com.scisdata.web.exception.DaoAccessException;
import com.scisdata.web.util.DefaultNameHandler;
import com.scisdata.web.util.DefaultRowMapper;
import com.scisdata.web.util.SqlUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 数据查询DAO支持类
 *
 * 创建日期：2017-03-16
 * @author fangshilei
 */
public abstract class BaseDaoSupport<T> implements BaseDao<T>, InitializingBean {
	/** 日志对象 */
	private static final Logger logger = Logger.getLogger(BaseDaoSupport.class);
	/** 实现类日志对象 */
	protected final Logger log = Logger.getLogger(getClass());
	
	/** JDBC模版对象 */
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	/** 名称加工处理器 */
	private NameHandler    nameHandler;
	/** SQL语句参数带名称的JDBC模版对象 */
	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	/** 分页SQL语句创建对象 */
	protected PagingSqlBuilder pagingSqlBuilder;
	/** 具体操作的实体类对象 */
	private Class<T>       entityClass;
	/**
	 * 获得JDBC模版对象
	 *
	 * @return
	 *
	 * 创建日期：2016-03-16
	 * @author fangshilei
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	/**
	 * 获取实际运行时的名称处理器
	 *
	 * @return
	 */
	private NameHandler getActualNameHandler() {
		if (nameHandler == null) {
			synchronized (this) {
				if (nameHandler == null) {
					nameHandler = this.getNameHandler();
				}
			}
		}
		return nameHandler;
	}
	public BaseDaoSupport(){
		Type superclass = getClass().getGenericSuperclass();
		ParameterizedType type = (ParameterizedType) superclass;
		entityClass = (Class<T>) type.getActualTypeArguments()[0];
	}
	/**
	 * 得到名称处理器，子类覆盖此方法实现自己的名称转换处理器
	 *
	 * @return
	 */
	protected NameHandler getNameHandler() {
		return new DefaultNameHandler();
	}
	/**
	 * 获得SQL语句参数带名称的JDBC模版对象
	 *
	 * @return
	 *
	 * 创建日期：2016-03-16
	 * @author fangshilei
	 */
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}

	/**
	 * 获得分页SQL语句创建对象
	 *
	 * @return
	 *
	 * 创建日期：2016-03-16
	 * @author fangshilei
	 */
	public PagingSqlBuilder getPagingSqlBuilder() {
		return pagingSqlBuilder;
	}

	/**
	 * 初始化非注入的属性
	 *
	 *
	 * 创建日期：2016-03-16
	 * @author fangshilei
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		//初始化namedParameterJdbcTemplate
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		//初始化pagingSqlBuilder
		pagingSqlBuilder = new PagingSqlBuilder(((ComboPooledDataSource)
				jdbcTemplate.getDataSource()).getJdbcUrl().replaceAll("://.*$", ""));
	}

	/**
	 * 根据条件查询 参数类型 为Object... 返回list<Map<String,Object>>
	 * @param sql                        SQL语句
	 * @param params                     SQL参数
	 * @return
	 * @throws DaoAccessException
	 */
	@Override
	public List<Map<String, Object>> search(String sql, Object... params) throws DaoAccessException {
		try {
			logger.debug(sql);
			return jdbcTemplate.queryForList(sql, params);
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
	}

	/**
	 * 根据条件查询 参数  list  返回List<Map<String,Object>>
	 * @param sql
	 * @param params
	 * @return
	 * @throws DaoAccessException
	 */
	@Override
	public List<Map<String, Object>> search(String sql, List<Object> params) throws DaoAccessException {
		return search(sql, params.toArray());
	}
	/**
	 * 根据条件查询 参数  Map<String,Object> 返回List<Map<String,Object>>
	 * @param sql
	 * @param params
	 * @return
	 * @throws DaoAccessException
	 */
	@Override
	public List<Map<String, Object>> search(String sql, Map<String, Object> params) throws DaoAccessException {
		try {
			logger.debug(sql);
			return namedParameterJdbcTemplate.queryForList(sql, params);
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
	}
	
	
	@Override
	public <R> List<R> search(String sql, MapRowMapper<R> mapRowMapper, Object... params) throws DaoAccessException {
		List<Map<String, Object>> list = search(sql, params);
		if(list == null) {
			return null;
		}
		List<R> ret = new ArrayList<R>();
		for (int i = 0; i < list.size(); i++) {
			ret.add(mapRowMapper.mapRow(list.get(i), i));
		}
		return ret;
	}

	@Override
	public <R> List<R> search(String sql, MapRowMapper<R> mapRowMapper,
			List<Object> params) throws DaoAccessException {
		return search(sql, mapRowMapper, params.toArray());
	}

	@Override
	public <R> List<R> search(String sql, MapRowMapper<R> mapRowMapper,
			Map<String, Object> params) throws DaoAccessException {
		List<Map<String, Object>> list = search(sql, params);
		if(list == null) {
			return null;
		}
		List<R> ret = new ArrayList<R>();
		for (int i = 0; i < list.size(); i++) {
			ret.add(mapRowMapper.mapRow(list.get(i), i));
		}
		return ret;
	}

	/**
	 * 分页查询 参数 Object 可以多个条件 返回封装后的DataStore<Map<String,Object>>
	 * @param sql                             SQL语句
	 * @param paging                          分页参数
	 * @param params                          SQL参数
	 * @return
	 * @throws DaoAccessException
	 */
	@Override
	public DataStore<Map<String, Object>> search(String sql, PagingParameter paging,
			Object... params) throws DaoAccessException {
		try {
			PagingSqlBuilder pagingSqlBuilder = getPagingSqlBuilder();
			int records = jdbcTemplate.queryForObject(pagingSqlBuilder.getCountSql(sql), params, Integer.class);
			if(records < 0) {
				return null;
			}
			if(records == 0) {
				return new DataStore<Map<String, Object>>(records, new ArrayList<Map<String, Object>>(),paging.getPageNum(),paging.getLimit());
			}
			return new DataStore<Map<String, Object>>(records, search(pagingSqlBuilder.getPagingSql(sql, paging), params),paging.getPageNum(),paging.getLimit());
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
		
	}

	/**
	 * 分页查询 参数 List
	 * @param sql
	 * @param paging
	 * @param params
	 * @return
	 * @throws DaoAccessException
	 */
	@Override
	public DataStore<Map<String, Object>> search(String sql,
												 PagingParameter paging, List<Object> params) throws DaoAccessException {
		return search(sql, paging, params.toArray());
	}

	/**
	 * 分页查询  参数 map
	 * @param sql
	 * @param paging
	 * @param params
	 * @return
	 * @throws DaoAccessException
	 */
	@Override
	public DataStore<Map<String, Object>> search(String sql,
			PagingParameter paging, Map<String, Object> params) throws DaoAccessException {
		try {
			PagingSqlBuilder pagingSqlBuilder = getPagingSqlBuilder();
			int records = namedParameterJdbcTemplate.queryForObject(pagingSqlBuilder.getCountSql(sql), params, Integer.class);
			if(records < 0) {
				return null;
			}
			if(records == 0) {
				return new DataStore<Map<String, Object>>(records, new ArrayList<Map<String, Object>>(),paging.getPageNum(),paging.getLimit());
			}
			return new DataStore<Map<String, Object>>(records, search(pagingSqlBuilder.getPagingSql(sql, paging), params),paging.getPageNum(),paging.getLimit());
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
	}
	

	@Override
	public <R> DataStore<R> search(String sql, MapRowMapper<R> mapRowMapper, PagingParameter paging, Object... params) throws DaoAccessException {
		DataStore<Map<String, Object>> dataStore = search(sql, paging, params);
		if(dataStore == null) {
			return null;
		}
		if(dataStore.getDatas() == null) {
			return new DataStore<R>(dataStore.getRecords(), null,paging.getPageNum(),paging.getLimit());
		}
		List<R> list = new ArrayList<R>();
		for (int i = 0; i < dataStore.getDatas().size(); i++) {
			list.add(mapRowMapper.mapRow(dataStore.getDatas().get(i), i));
		}
		return new DataStore<R>(dataStore.getRecords(), list,paging.getPageNum(),paging.getLimit());
	}

	@Override
	public <R> DataStore<R> search(String sql, MapRowMapper<R> mapRowMapper,
			PagingParameter paging, List<Object> params) throws DaoAccessException {
		return search(sql, mapRowMapper, paging, params.toArray());
	}

	@Override
	public <R> DataStore<R> search(String sql, MapRowMapper<R> mapRowMapper,
			PagingParameter paging, Map<String, Object> params) throws DaoAccessException {
		DataStore<Map<String, Object>> dataStore = search(sql, paging, params);
		if(dataStore == null) {
			return null;
		}
		if(dataStore.getDatas() == null) {
			return new DataStore<R>(dataStore.getRecords(), null,paging.getPageNum(),paging.getLimit());
		}
		List<R> list = new ArrayList<R>();
		for (int i = 0; i < dataStore.getDatas().size(); i++) {
			list.add(mapRowMapper.mapRow(dataStore.getDatas().get(i), i));
		}
		return new DataStore<R>(dataStore.getRecords(), list,paging.getPageNum(),paging.getLimit());
	}

	/**
	 * 分页查询 参数  Object  返回实体 List
	 * @param sql
	 * @param paging
	 * @param params
	 * @param
	 * @return
	 * @throws DaoAccessException
	 */
	public  DataStore pageQueryObject(String sql,PagingParameter paging,Object...params) throws DaoAccessException {
		try {
			PagingSqlBuilder pagingSqlBuilder = getPagingSqlBuilder();
			int records = jdbcTemplate.queryForObject(pagingSqlBuilder.getCountSql(sql), params, Integer.class);
			if(records < 0) {
				return null;
			}
			if(records == 0) {
				return new DataStore(records, new ArrayList(),paging.getPageNum(),paging.getLimit());
			}
			return new DataStore(records, queryList(pagingSqlBuilder.getPagingSql(sql, paging), params),paging.getPageNum(),paging.getLimit());
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
	}
	public  DataStore<Map<String,Object>> pageQuery(String sql,PagingParameter paging,Object...params) throws DaoAccessException {
		try {
			PagingSqlBuilder pagingSqlBuilder = getPagingSqlBuilder();
			int records = jdbcTemplate.queryForObject(pagingSqlBuilder.getCountSql(sql), params, Integer.class);
			if(records < 0) {
				return null;
			}
			if(records == 0) {
				return new DataStore<Map<String,Object>>(records, new ArrayList<Map<String,Object>>(),paging.getPageNum(),paging.getLimit());
			}
			return new DataStore<Map<String,Object>>(records, queryList(pagingSqlBuilder.getPagingSql(sql, paging), params),paging.getPageNum(),paging.getLimit());
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
	}
	public  DataStore<Map<String,Object>> pageQuery(String sql,PagingParameter paging,List<Object> params) throws DaoAccessException {
		try {
			PagingSqlBuilder pagingSqlBuilder = getPagingSqlBuilder();
			int records = jdbcTemplate.queryForObject(pagingSqlBuilder.getCountSql(sql), params.toArray(), Integer.class);
			if(records < 0) {
				return null;
			}
			if(records == 0) {
				return new DataStore<Map<String,Object>>(records, new ArrayList(),paging.getPageNum(),paging.getLimit());
			}
			return new DataStore<Map<String,Object>>(records, queryList(pagingSqlBuilder.getPagingSql(sql, paging), params),paging.getPageNum(),paging.getLimit());
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
	}
	public  DataStore pageQuery(String sql,PagingParameter paging,Map<String,Object> params) throws DaoAccessException {
		try {
			PagingSqlBuilder pagingSqlBuilder = getPagingSqlBuilder();
			int records = namedParameterJdbcTemplate.queryForObject(pagingSqlBuilder.getCountSql(sql), params, Integer.class);
			if(records < 0) {
				return null;
			}
			if(records == 0) {
				return new DataStore(records, new ArrayList(),paging.getPageNum(),paging.getLimit());
			}
			return new DataStore(records, queryList(pagingSqlBuilder.getPagingSql(sql, paging), params),paging.getPageNum(),paging.getLimit());
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
	}
	public  DataStore pageQueryList(String sql,PagingParameter paging,List<Object> params) throws DaoAccessException {
		try {
			PagingSqlBuilder pagingSqlBuilder = getPagingSqlBuilder();
			int records = jdbcTemplate.queryForObject(pagingSqlBuilder.getCountSql(sql), params.toArray(), Integer.class);
			if(records < 0) {
				return null;
			}
			if(records == 0) {
				return new DataStore(records, new ArrayList(),paging.getPageNum(),paging.getLimit());
			}
			return new DataStore(records, queryList(pagingSqlBuilder.getPagingSql(sql, paging), params),paging.getPageNum(),paging.getLimit());
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
	}
	public  DataStore pageQueryMap(String sql,PagingParameter paging,Map<String,Object> params) throws DaoAccessException {
		try {
			PagingSqlBuilder pagingSqlBuilder = getPagingSqlBuilder();
			int records = namedParameterJdbcTemplate.queryForObject(pagingSqlBuilder.getCountSql(sql), params, Integer.class);
			if(records < 0) {
				return null;
			}
			if(records == 0) {
				return new DataStore(records, new ArrayList(),paging.getPageNum(),paging.getLimit());
			}
			return new DataStore(records, queryList(pagingSqlBuilder.getPagingSql(sql, paging), params),paging.getPageNum(),paging.getLimit());
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
	}

	/**
	 * 添加信息  参数为实体  反射sql  ,返回主键
	 * @param entity
	 * @return
	 */
	@Override
	public Long insertBackId(T entity){
		final SqlContext sqlContext = SqlUtils.buildInsertSql(entity, this.getActualNameHandler());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sqlContext.getSql().toString(),
						new String[] { sqlContext.getPrimaryKey() });
				int index = 0;
				for (Object param : sqlContext.getParams()) {
					index++;
					ps.setObject(index, param);
				}
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().longValue();
	}

	/**
	 * 添加信息 参数为实体,反射sql  不返回主键
	 * @param entity
	 */
	public void insert(T entity){
		final SqlContext sqlContext = SqlUtils.buildInsertSql(entity, this.getActualNameHandler());
		jdbcTemplate.update(String.valueOf(sqlContext.getSql()),sqlContext.getParams().toArray());
	}
	public List queryBuliderList(Object entity){
		SqlContext sqlContext = SqlUtils.buildQueryCondition(entity, this.getActualNameHandler());
		return queryList(String.valueOf(sqlContext.getSql()),sqlContext.getParams());
	}
	/**
	 * 添加信息  参数为实体,需要写sql 并返回主键
	 * @param sql
	 * @param entity
	 * @return
	 */
	@Override
	public Long insertBackId(String sql,T entity){
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource ps=new BeanPropertySqlParameterSource(entity);
		namedParameterJdbcTemplate.update(sql,ps,keyHolder);
		return keyHolder.getKey().longValue();
	}

	/**
	 * 添加信息  参数为map,需要写sql  并返回主键
	 * @param sql
	 * @param map
	 * @return
	 */
	@Override
	public Long insertBackId(String sql,Map<String,Object> map){
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
		return keyHolder.getKey().longValue();
	}

	/**
	 * 添加信息  参数为List  不返回主键
	 * @param sql
	 * @param list
	 */
	@Override
	public void insert(String sql,List<Object> list){
		jdbcTemplate.update(sql,list.toArray());
	}

	/**
	 * 添加信息   参数为map 不返回主键
	 * @param sql
	 * @param map
	 */
	@Override
	public void insert(String sql,Map<String,Object> map){
		namedParameterJdbcTemplate.update(sql,map);
	}

	/**
	 * 参数  实体  反射sql  更新 不返回主键
	 * @param entity
	 */
	@Override
	public void update(T entity){
		SqlContext sqlContext = SqlUtils.buildUpdateSql(entity, this.getActualNameHandler());
		jdbcTemplate.update(sqlContext.getSql().toString(), sqlContext.getParams().toArray());
	}

	/**
	 * 参数 map  更新
	 * @param sql
	 * @param map
	 */
	@Override
	public void update(String sql,Map<String,Object> map){
		namedParameterJdbcTemplate.update(sql,map);
	}

	/**
	 * 参数list  更新
	 * @param sql
	 * @param list
	 */
	@Override
	public void update(String sql,List<Object> list){
		jdbcTemplate.update(sql,list.toArray());
	}
	@Override
	public void delete(Long id){
		String tableName = this.getActualNameHandler().getTableName(entityClass);
		String primaryName = this.getNameHandler().getPrimaryName(entityClass);
		String sql = "DELETE FROM " + tableName + " WHERE " + primaryName + " = ?";
		jdbcTemplate.update(sql, id);
	}
	@Override
	public void delete(String sql,Long id){
		jdbcTemplate.update(sql,id);
	}
	@Override
	public  List queryList(String sql,Object...params){
		return jdbcTemplate.queryForList(sql, params);
	}
	@Override
	public  List queryList(String sql,List<Object> params){
		return jdbcTemplate.queryForList(sql, params.toArray());
	}
	@Override
	public  List queryList(String sql,Map<String,Object> params){
		return namedParameterJdbcTemplate.queryForList(sql, params);
	}

	/**
	 * 根据ID查询信息  返回实体, 通过实体反射表名和主键
	 * @param id
	 * @return
	 */
	@Override
	public T getById(Long id){
		String tableName = this.getNameHandler().getTableName(entityClass);
		String primaryName = this.getNameHandler().getPrimaryName(entityClass);
		String sql = "SELECT * FROM " + tableName + " WHERE " + primaryName + " = ?";
		return (T) jdbcTemplate.query(sql,
				new DefaultRowMapper(entityClass, this.getActualNameHandler()), id).get(0);
	}

	/**
	 * 根据ID查询信息  返回Map  通过实体反射 出表名和主键信息
	 * @param id
	 * @return
	 */
	@Override
	public Map<String,Object> getMapById(Long id){
		String tableName = this.getNameHandler().getTableName(entityClass);
		String primaryName = this.getNameHandler().getPrimaryName(entityClass);
		String sql = "SELECT * FROM " + tableName + " WHERE " + primaryName + " = ?";
		return jdbcTemplate.queryForMap(sql,id);
	}

	/**
	 * 通过sql  根据ID查询信息
	 * @param sql
	 * @param id
	 * @return
	 */
	@Override
	public Map<String,Object> getMapById(String sql,Long id){
		return jdbcTemplate.queryForMap(sql,id);
	}
	@Override
	public Map<String,Object> getMapBySql(String sql){
		return jdbcTemplate.queryForMap(sql);
	}
}
