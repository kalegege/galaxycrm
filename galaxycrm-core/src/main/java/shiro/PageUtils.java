package shiro;

import org.apache.commons.lang.StringUtils;

import shiro.dataobject.Page;

import com.wasu.ptyw.galaxy.dal.persist.SimpleQuery;

/**
 * @author wenguang
 * @date 2015年9月1日
 */
public class PageUtils {

	/**
	 * page参数转换
	 */
	public static void convertPage(SimpleQuery query, Page page) {
		if (query == null || page == null) {
			return;
		}
		query.setCurrentPage(page.getPageNum());
		query.setPageSize(page.getNumPerPage());

		if (StringUtils.isEmpty(page.getOrderField())) {
			return;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(page.getOrderField()).append(" ").append(page.getOrderDirection());
		query.setOrderBy(sb.toString());
	}
}
