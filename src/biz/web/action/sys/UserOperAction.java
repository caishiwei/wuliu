package biz.web.action.sys;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import util.Constant;
import util.DateUtil;
import util.FieldUtil;
import util.MessageUtil;
import util.Page;
import util.SearchParamBean;
import util.StringUtil;
import biz.entity.Dept;
import biz.entity.FlowLog;
import biz.entity.UserOrder;
import biz.entity.main.SimpleUser;
import biz.web.service.impl.BizService;

import com.opensymphony.xwork2.ModelDriven;

import common.action.struts.BaseAction;

/**
 * 部门的增删改,根据方法名称顾名思义
 * 
 * @author jiu
 * 
 */
@ParentPackage("struts-default")
@Namespace("/sys")
@Component
public class UserOperAction extends BaseAction implements ModelDriven<UserOrder> {
	@Autowired
	private BizService service;

	private long uid;
	private UserOrder bean = new UserOrder();

	@Action(value = "add2MyOrder", results = { @Result(name = "add2", location = "/WEB-INF/jsp/user/addUserOrder.jsp") })
	public String add2() {
		List list = service.findAll(Dept.class);
		putRequestValue("list", list);
		return "add2";
	}

	@Action(value = "getMyOrder", results = { @Result(name = "getOne", location = "/WEB-INF/jsp/user/getMyOrder.jsp") })
	public String getMyOrder() {
		try {
			UserOrder temp = (UserOrder) service.get(UserOrder.class, uid);
			putRequestValue("modifybean", temp);

			List list = service.queryByHQL("from Dept where id!=?", temp.getStartDept().getId());
			putRequestValue("list", list);

			List list1 = service.queryByHQL("from FlowLog where userOrder.id=? order by id desc", temp.getId());
			putRequestValue("list1", list1);

			return "getOne";
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	@Action(value = "getMyOrderRecv", results = { @Result(name = "getOne", location = "/WEB-INF/jsp/user/getMyOrderRecv.jsp") })
	public String getMyOrderRecv() {
		try {
			UserOrder temp = (UserOrder) service.get(UserOrder.class, uid);
			putRequestValue("modifybean", temp);

			List list = service.queryByHQL("from Dept where id!=?", temp.getStartDept().getId());
			putRequestValue("list", list);

			List list1 = service.queryByHQL("from FlowLog where userOrder.id=? order by id desc", temp.getId());
			putRequestValue("list1", list1);

			return "getOne";
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	@Action(value = "deleteUserOrder")
	public String delete() {
		try {
			service.delete(UserOrder.class, ids);
			MessageUtil.addRelMessage(getHttpServletRequest(), "删除信息成功.", "mainquery");
			return SUCCESS;
		} catch (Exception e) {
			MessageUtil.addMessage(getRequest(), "删除失败");
			return ERROR;
		}
	}

	@Action(value = "updateUserOrder")
	public String update() {
		try {
			service.update(bean);
			MessageUtil.addMessage(getHttpServletRequest(), "更新成功.");
			return SUCCESS;
		} catch (Exception e) {
			MessageUtil.addMessage(getRequest(), "更新失败");
			return ERROR;
		}
	}

	@Action(value = "updateUserOrderRecv")
	public String updateUserOrderRecv() {
		try {
			UserOrder item = (UserOrder) service.get(UserOrder.class, uid);
			item.setOrderStatus("已签收");
			service.update(item);
			FlowLog log = new FlowLog();
			log.setAddDate(DateUtil.getCurrentTime(DateUtil.FULL));
			log.setDept(null);
			log.setNote("确认签收");
			log.setId(new Date().getTime());
			log.setUserOrder(item);
			service.add(log);
			MessageUtil.addRelMessage(getHttpServletRequest(), "签收成功.", "baseAdd");
			return SUCCESS;
		} catch (Exception e) {
			MessageUtil.addMessage(getRequest(), "更新失败");
			return ERROR;
		}
	}

	@Action(value = "addMyOrder")
	public String add() {
		try {
			bean.setId(new Date().getTime());
			bean.setOrderStatus("新订单");
			bean.setMoney(bean.getGoodsWeight() * 5);
			bean.setAddDate(DateUtil.getCurrentTime(DateUtil.FULL));
			service.add(bean);
			MessageUtil.addMessage(getHttpServletRequest(), "添加成功.");
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			MessageUtil.addMessage(getRequest(), "添加失败");
			return ERROR;
		}
	}

	@Action(value = "queryMyOrder", results = { @Result(name = "queryList", location = "/WEB-INF/jsp/user/listUserOrder.jsp") })
	public String queryMyOrder() {
		try {
			int pageNum = 0;
			String t = getHttpServletRequest().getParameter("pageNum");
			if (StringUtil.notEmpty(t)) {
				pageNum = Integer.valueOf(t);
			}
			Page p = (Page) getHttpSession().getAttribute(Constant.SESSION_PAGE);
			if (pageNum == 0 || p == null) {
				p = new Page();
				p.setCurrentPageNumber(1);
				p.setTotalNumber(0l);
				p.setItemsPerPage(Constant.SESSION_PAGE_NUMBER);

				// 字段名称集合
				LinkedList<String> parmnames = new LinkedList<String>();
				// 字段值集合
				LinkedList<Object> parmvalues = new LinkedList<Object>();
				// 页面参数集合
				Set parm = getHttpServletRequest().getParameterMap().entrySet();
				for (Object o : parm) {
					Entry<String, Object> e = (Entry<String, Object>) o;
					String name = e.getKey();// 页面字段名称
					if (name.startsWith("s_")) {
						String fieldValue = getHttpServletRequest().getParameter(name);// 页面字段值
						if (StringUtil.notEmpty(fieldValue)) {
							name = name.substring(2, name.length());// 实体字段名称
							parmnames.add(name);
							parmvalues.add(FieldUtil.format(UserOrder.class, name, fieldValue));
						}
					}
				}

				SimpleUser user = (SimpleUser) getSessionUser();
				parmnames.add("fromUserPhone");
				parmvalues.add(user.getUser().getUserPhone());

				SearchParamBean sbean = new SearchParamBean();
				sbean.setParmnames(parmnames);
				sbean.setParmvalues(parmvalues);

				p.setConditonObject(sbean);
			} else {
				p.setCurrentPageNumber(pageNum);
			}
			Page page = null;
			page = service.find(p, UserOrder.class);

			getHttpSession().setAttribute(Constant.SESSION_PAGE, page);
			return "queryList";
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	@Action(value = "queryMyRecvOrder", results = { @Result(name = "queryList", location = "/WEB-INF/jsp/user/listUserOrderRecv.jsp") })
	public String queryMyRecvOrder() {
		try {
			int pageNum = 0;
			String t = getHttpServletRequest().getParameter("pageNum");
			if (StringUtil.notEmpty(t)) {
				pageNum = Integer.valueOf(t);
			}
			Page p = (Page) getHttpSession().getAttribute(Constant.SESSION_PAGE);
			if (pageNum == 0 || p == null) {
				p = new Page();
				p.setCurrentPageNumber(1);
				p.setTotalNumber(0l);
				p.setItemsPerPage(Constant.SESSION_PAGE_NUMBER);

				// 字段名称集合
				LinkedList<String> parmnames = new LinkedList<String>();
				// 字段值集合
				LinkedList<Object> parmvalues = new LinkedList<Object>();
				// 页面参数集合
				Set parm = getHttpServletRequest().getParameterMap().entrySet();
				for (Object o : parm) {
					Entry<String, Object> e = (Entry<String, Object>) o;
					String name = e.getKey();// 页面字段名称
					if (name.startsWith("s_")) {
						String fieldValue = getHttpServletRequest().getParameter(name);// 页面字段值
						if (StringUtil.notEmpty(fieldValue)) {
							name = name.substring(2, name.length());// 实体字段名称
							parmnames.add(name);
							parmvalues.add(FieldUtil.format(UserOrder.class, name, fieldValue));
						}
					}
				}

				SimpleUser user = (SimpleUser) getSessionUser();
				parmnames.add("userPhone");
				parmvalues.add(user.getUser().getUserPhone());

				SearchParamBean sbean = new SearchParamBean();
				sbean.setParmnames(parmnames);
				sbean.setParmvalues(parmvalues);

				p.setConditonObject(sbean);
			} else {
				p.setCurrentPageNumber(pageNum);
			}
			Page page = null;
			page = service.find(p, UserOrder.class);

			getHttpSession().setAttribute(Constant.SESSION_PAGE, page);
			return "queryList";
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	public UserOrder getModel() {
		return bean;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	private String ids;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}
