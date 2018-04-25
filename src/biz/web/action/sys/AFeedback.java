package biz.web.action.sys;

import java.util.Date;
import java.util.LinkedList;
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
import biz.entity.Feedback;
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
public class AFeedback extends BaseAction implements ModelDriven<Feedback> {
	@Autowired
	private BizService service;

	private long uid;
	private Feedback bean = new Feedback();

	@Action(value = "add2Feedback", results = { @Result(name = "add2", location = "/WEB-INF/jsp/user/addFeedback.jsp") })
	public String add2() {
		return "add2";
	}

	@Action(value = "getFeedback", results = { @Result(name = "getOne", location = "/WEB-INF/jsp/user/modifyFeedback.jsp") })
	public String get() {
		try {
			Feedback temp = (Feedback) service.get(Feedback.class, uid);
			putRequestValue("modifybean", temp);
			return "getOne";
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	@Action(value = "deleteFeedback")
	public String delete() {
		try {
			service.delete(Feedback.class, ids);
			MessageUtil.addRelMessage(getHttpServletRequest(), "删除信息成功.", "mainquery");
			return SUCCESS;
		} catch (Exception e) {
			MessageUtil.addMessage(getRequest(), "删除失败");
			return ERROR;
		}
	}

	@Action(value = "updateFeedback")
	public String update() {
		try {
			Feedback temp = (Feedback) service.get(Feedback.class, bean.getId());
			temp.setContent(bean.getContent());
			service.update(temp);
			MessageUtil.addMessage(getHttpServletRequest(), "更新成功.");
			return SUCCESS;
		} catch (Exception e) {
			MessageUtil.addMessage(getRequest(), "更新失败");
			return ERROR;
		}
	}

	@Action(value = "addFeedback")
	public String add() {
		try {
			bean.setId(new Date().getTime());
			SimpleUser user = (SimpleUser) getSessionUser();
			bean.setAddDate(DateUtil.getCurrentTime(DateUtil.FULL));
			bean.setUserPhone(user.getUser().getUserPhone());
			service.add(bean);
			MessageUtil.addMessage(getHttpServletRequest(), "添加成功.");
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			MessageUtil.addMessage(getRequest(), "添加失败");
			return ERROR;
		}
	}

	@Action(value = "queryFeedback", results = { @Result(name = "queryList", location = "/WEB-INF/jsp/user/listFeedback.jsp") })
	public String query() {
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
							parmvalues.add(FieldUtil.format(Feedback.class, name, fieldValue));
						}
					}
				}
				Object obj = getSessionUser();
				if (obj instanceof SimpleUser) {
					SimpleUser user = (SimpleUser) obj;
					parmnames.add("userPhone");
					parmvalues.add(user.getUser().getUserPhone());

				}
				SearchParamBean sbean = new SearchParamBean();
				sbean.setParmnames(parmnames);
				sbean.setParmvalues(parmvalues);

				p.setConditonObject(sbean);
			} else {
				p.setCurrentPageNumber(pageNum);
			}
			Page page = null;
			page = service.find(p, Feedback.class);

			getHttpSession().setAttribute(Constant.SESSION_PAGE, page);
			return "queryList";
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	public Feedback getModel() {
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
