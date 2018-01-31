package com.scisdata.web.basedao.page;

import java.util.List;


/**
 * 封装响应的分页数据
 *
 * 创建日期：2017-03-15
 * @author fangshilei
 */
public class DataStore<T> extends BaseDomain {
	private static final long serialVersionUID = 8856755452533047842L;

	/** 总记录数 */
	private int records;
	/** 数据集合 */
	private List<T> datas;
	private int pageNum;//当前页数
	private int pages;//总页数
	/**
	 * 无参构造方法
	 * 创建日期：2017-03-15
	 * @author fangshilei
	 */
	public DataStore() {
	}

	/**
	 * 构造方法
	 * @param records 总记录数
	 * @param datas   数据集合
	 * 创建日期：2017-03-15
	 * @author fangshilei
	 */
	public DataStore(int records, List<T> datas,int pageNum,int limit) {
		this.records = records;
		this.datas = datas;
		this.pageNum=pageNum;
		this.pages= records / limit + ((records % limit == 0) ? 0 : 1);
	}
	
	/**
	 * 构造方法
	 * @param paging  分页参数
	 * @param datas   全部的数据
	 * 创建日期：2017-03-15
	 * @author fangshilei
	 */
	public DataStore(PagingParameter paging, List<T> datas) {
		if(datas == null) {
			return;
		}
		this.records = datas.size();
		if(paging == null || paging.isInvalid()) {
			this.datas = datas;
		} else {
			int end = paging.getStart()+paging.getLimit();
			if(end > records) {
				end = records;
			}
			this.datas = datas.subList(paging.getStart(), end);
		}
	}

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

}
