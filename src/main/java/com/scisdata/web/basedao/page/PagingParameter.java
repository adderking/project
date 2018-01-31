package com.scisdata.web.basedao.page;


/**
 * 说明：
 * 封装分页参数
 * 创建日期：2017-03-17
 * @author fangshilei
 */
public class PagingParameter extends BaseDomain {
	private static final long serialVersionUID = -5871263750693828476L;

	/** 分页起始行，默认为-1，表示不分页，查询全部记录 */
	private int start = -1;
	/** 每页显示行数，默认为0，表示不分页，查询全部记录 */
	private int limit = 0;
	private int pageNum=0;
	/**
	 * 构造方法，不指定分页起始行和每页显示行数，默认不分页，查询全部记录
	 * 创建日期：2017-03-17
	 * @author fangshilei
	 */
	public PagingParameter(){
	}
	
	/**
	 * 构造方法
	 * @param pageNum
	 * @param limit
	 * 创建日期：2017-03-18
	 * @author fangshilei
	 */
	public PagingParameter(int pageNum,int limit){
		this.start = pageNum > 0 ? (pageNum - 1) * limit : 0;;
		this.limit = limit;
		this.pageNum = pageNum;
	}
	
	public int getStart() {
		return start;
	}
	
	public void setStart(int start) {
		this.start = start;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getLimit() {
		return limit;
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * 判断分页参数是否无效，如果返回true(表示分页参数无效)则不分页，查询全部的记录
	 *
	 * @return
	 * 创建日期：2017-03-15
	 * @author fangshilei
	 */
	public boolean isInvalid() {
		return start < 0 || limit <= 0;
	}

}
