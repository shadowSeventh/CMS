package org.lt.basic.model;
/**
 * 用来传递列表对象的ThreadLocal数据
 * @author li949
 *
 */
@SuppressWarnings("unused")
public class SystemContext {
	/**
	 * 分页大小
	 */
	private static ThreadLocal<Integer> pageSize=new ThreadLocal<>();
	/**
	 * 分页的起始页
	 */
	private static ThreadLocal<Integer> pageOffset=new ThreadLocal<>();
	/**
	 * 列表的排序字段
	 */
	private static ThreadLocal<String> sort=new ThreadLocal<>();
	/**
	 * 列表的排序方式
	 */
	private static ThreadLocal<String> order =new ThreadLocal<>();
	public static Integer getPageSize() {
		return pageSize.get();
	}
	public static void setPageSize(Integer _pageSize) {
		pageSize.set(_pageSize);
	}
	public static Integer getPageOffset() {
		return pageOffset.get();
	}
	public static void setPageOffset(Integer _pageOffset) {
		pageOffset.set(_pageOffset);
	}
	public static String getSort() {
		return sort.get();
	}
	public static void setSort(String _sort) {
		sort.set(_sort);
	}
	public static String getOrder() {
		return order.get();
	}
	public static void setOrder(String _order) {
		order.set(_order); 
	}
	
	private static void removePageSize() {
		pageSize.remove();
	}
	private static void removePageOffset() {
		pageOffset.remove();
	}
	private static void  removeSort() {
		sort.remove();
	}
	private static void  removeOrder() {
		order.remove();
	}
}
