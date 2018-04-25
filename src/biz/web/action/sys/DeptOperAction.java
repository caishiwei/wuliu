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
import biz.entity.FlowLog;
import biz.entity.Transport;
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
public class DeptOperAction extends BaseAction implements ModelDriven<UserOrder> {
	@Autowired
	private BizService service;

	private long uid;
	private UserOrder bean = new UserOrder();
	private Transport transbean = null;

	@Action(value = "add2Transport", results = { @Result(name = "add2", location = "/WEB-INF/jsp/sys/addTransport.jsp") })
	public String add2() {
		List list1 = service.queryByHQL("from Truck where useStatus='空闲'");
		putRequestValue("list1", list1);

		List list2 = service.queryByHQL("from SimpleUser where userType='司机' and id not in(select driver.id from Transport where transStatus='运输中')");
		putRequestValue("list2", list2);
		SimpleUser user = (SimpleUser) getSessionUser();
		List list3 = service.queryByHQL("from Line where startDept.id=?", user.getDept().getId());
		putRequestValue("list3", list3);

		return "add2";
	}

	@Action(value = "queryDeptJoinTransport", results = { @Result(name = "list", location = "/WEB-INF/jsp/sys/queryDeptJoinTransport.jsp") })
	public String queryDeptJoinTransport() {
		SimpleUser user = (SimpleUser) getSessionUser();
		List<Transport> list = service.findJoinTransport(user.getDept());
		putRequestValue("list", list);

		return "list";
	}

	@Action(value = "selectUserOrder", results = { @Result(name = "add2", location = "/WEB-INF/jsp/select/selectUserOrder.jsp") })
	public String selectUserOrder() {
		SimpleUser user = (SimpleUser) getSessionUser();
		List list = service.queryByHQL("from UserOrder where startDept.id=? and orderStatus='待配送'", user.getDept().getId());
		putRequestValue("list", list);
		return "add2";
	}

	@Action(value = "getDeptUserOrder", results = { @Result(name = "getOne", location = "/WEB-INF/jsp/sys/modifyDeptUserOrder.jsp") })
	public String getDeptUserOrder() {
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

	@Action(value = "getDeptTransport", results = { @Result(name = "getOne", location = "/WEB-INF/jsp/sys/getDeptTransport.jsp") })
	public String getDeptTransport() {
		try {
			Transport temp = (Transport) service.get(Transport.class, uid);
			putRequestValue("modifybean", temp);

			List list = service.queryByHQL("from UserOrder where id in(select userOrder.id from TransportOrder where transport.id=?)", uid);
			putRequestValue("list11", list);

			return "getOne";
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	@Action(value = "getDeptJoinTransport", results = { @Result(name = "getOne", location = "/WEB-INF/jsp/sys/getDeptJoinTransport.jsp") })
	public String getDeptJoinTransport() {
		try {
			Transport temp = (Transport) service.get(Transport.class, uid);
			putRequestValue("modifybean", temp);

			List list = service.queryByHQL("from UserOrder where id in(select userOrder.id from TransportOrder where transport.id=?)", uid);
			putRequestValue("list11", list);

			return "getOne";
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	@Action(value = "deleteUserOrder1")
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

	@Action(value = "updateEndDeptUserOrder")
	public String updateEndDeptUserOrder() {
		try {
			UserOrder temp = (UserOrder) service.get(UserOrder.class, bean.getId());
			temp.setEndDept(bean.getEndDept());
			temp.setOrderStatus("待配送");
			service.update(temp);

			FlowLog log = new FlowLog();
			log.setAddDate(DateUtil.getCurrentTime(DateUtil.FULL));
			log.setDept(temp.getStartDept());
			log.setNote("订单确认,等待装车配送");
			log.setId(new Date().getTime());
			log.setUserOrder(temp);
			service.add(log);

			MessageUtil.addRelMessage(getHttpServletRequest(), "更新成功.", "baseAdd");
			return SUCCESS;
		} catch (Exception e) {
			MessageUtil.addMessage(getRequest(), "更新失败");
			return ERROR;
		}
	}

	@Action(value = "updateJoinTransport")
	public String updateJoinTransport() {
		try {
			SimpleUser user = (SimpleUser) getSessionUser();
			service.updateJoinTransport(user.getDept(), uid);

			MessageUtil.addCloseMessage(getRequest(), "交接成功.", "mainquery");
			return SUCCESS;
		} catch (Exception e) {
			MessageUtil.addMessage(getRequest(), "交接失败");
			return ERROR;
		}
	}

	@Action(value = "addTransport")
	public String addTransport() {
		try {
			transbean.setId(new Date().getTime());
			String orderIds = getHttpServletRequest().getParameter("org3.idd");
			service.addTransport(transbean, orderIds);
			MessageUtil.addMessage(getHttpServletRequest(), "添加成功.");
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			MessageUtil.addMessage(getRequest(), "添加失败");
			return ERROR;
		}
	}

	@Action(value = "queryDeptUserOrder", results = { @Result(name = "queryList", location = "/WEB-INF/jsp/sys/listDeptUserOrder.jsp") })
	public String queryDeptUserOrder() {
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
				parmnames.add("startDept.id");
				parmvalues.add(user.getDept().getId());

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

	@Action(value = "queryDeptTransport", results = { @Result(name = "queryList", location = "/WEB-INF/jsp/sys/listDeptTransport.jsp") })
	public String queryDeptTransport() {
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
							parmvalues.add(FieldUtil.format(Transport.class, name, fieldValue));
						}
					}
				}
				SimpleUser user = (SimpleUser) getSessionUser();
				parmnames.add("line.startDept.id");
				parmvalues.add(user.getDept().getId());

				SearchParamBean sbean = new SearchParamBean();
				sbean.setParmnames(parmnames);
				sbean.setParmvalues(parmvalues);

				p.setConditonObject(sbean);
			} else {
				p.setCurrentPageNumber(pageNum);
			}
			Page page = null;
			page = service.find(p, Transport.class);

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

	public BizService getService() {
		return service;
	}

	public void setService(BizService service) {
		this.service = service;
	}

	public UserOrder getBean() {
		return bean;
	}

	public void setBean(UserOrder bean) {
		this.bean = bean;
	}

	public Transport getTransbean() {
		return transbean;
	}

	public void setTransbean(Transport transbean) {
		this.transbean = transbean;
	}

}
