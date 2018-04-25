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
import util.FieldUtil;
import util.MessageUtil;
import util.Page;
import util.SearchParamBean;
import util.StringUtil;
import biz.entity.main.SysUser;
import biz.web.service.impl.BizService;

import com.opensymphony.xwork2.ModelDriven;
import common.action.struts.BaseAction;
 /**
  * 管理员管理
  * @author jiu
  *
  */
@ParentPackage("struts-default")
@Namespace("/sys")
@Component
public class SysUserAction extends BaseAction implements ModelDriven<SysUser> {
	@Autowired
	private BizService service;

	private long uid;
	private SysUser bean = new SysUser();

	@Action(value = "add2SysUser", results = { @Result(name = "add2", location = "/WEB-INF/jsp/sys/addSysUser.jsp") })
	public String add2() {
		return "add2";
	}
	
	@Action(value = "getSysUser", results = { @Result(name = "getOne", location = "/WEB-INF/jsp/sys/modifySysUser.jsp") })
	public String get() {
		try {
			SysUser temp = (SysUser) service.get(SysUser.class, uid);
			putRequestValue("modifybean", temp);
			return "getOne";
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	@Action(value = "deleteSysUser")
	public String delete() {
		try {
			service.delete(SysUser.class, ids);
			MessageUtil.addRelMessage(getHttpServletRequest(), "删除管理员信息成功.", "mainquery");
			return SUCCESS;
		} catch (Exception e) {
			MessageUtil.addMessage(getRequest(), "删除失败");
			return ERROR;
		}
	}

	@Action(value = "updateSysUser")
	public String update() {
		try {
			service.updateSysUser(bean);
			MessageUtil.addMessage(getHttpServletRequest(), "更新管理员成功.");
			return SUCCESS;
		} catch (Exception e) {
			MessageUtil.addMessage(getRequest(), "更新管理员失败");
			return ERROR;
		}
	}

	@Action(value = "addSysUser")
	public String add() {
		try {
			bean.setId(new Date().getTime());
			service.addSysUser(bean);
			MessageUtil.addMessage(getHttpServletRequest(), "添加管理员成功.");
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			MessageUtil.addMessage(getRequest(), "添加管理员失败");
			return ERROR;
		}
	}

	@Action(value = "querySysUser", results = { @Result(name = "queryList", location = "/WEB-INF/jsp/sys/listSysUser.jsp") })
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
							parmvalues.add(FieldUtil.format(SysUser.class, name, fieldValue));
						}
					}
				}

				SearchParamBean sbean = new SearchParamBean();
				sbean.setParmnames(parmnames);
				sbean.setParmvalues(parmvalues);

				p.setConditonObject(sbean);
			} else {
				p.setCurrentPageNumber(pageNum);
			}
			Page page = null;
			page = service.find(p, SysUser.class);

			getHttpSession().setAttribute(Constant.SESSION_PAGE, page);
			return "queryList";
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	public SysUser getModel() {
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
