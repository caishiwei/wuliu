package biz.web.action.sys;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
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
import biz.entity.Dept;
import biz.entity.Line;
import biz.entity.LineStation;
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
public class ALine extends BaseAction implements ModelDriven<Line> {
	@Autowired
	private BizService service;

	private long uid;
	private Line bean = new Line();

	@Action(value = "selectDept", results = { @Result(name = "add2", location = "/WEB-INF/jsp/select/db_select.jsp") })
	public String selectDept() {
		List list = service.findAll(Dept.class);
		putRequestValue("list", list);
		return "add2";
	}

	@Action(value = "add2Line", results = { @Result(name = "add2", location = "/WEB-INF/jsp/sys/addLine.jsp") })
	public String add2() {
		List list = service.findAll(Dept.class);
		putRequestValue("list", list);
		return "add2";
	}

	@Action(value = "getLine", results = { @Result(name = "getOne", location = "/WEB-INF/jsp/sys/modifyLine.jsp") })
	public String get() {
		try {
			Line temp = (Line) service.get(Line.class, uid);
			putRequestValue("modifybean", temp);

			List<LineStation> lslist = service.queryByHQL("from LineStation where line.id=? order by id", temp.getId());
			putRequestValue("list11", lslist);
			
			List list = service.findAll(Dept.class);
			putRequestValue("list", list);

			return "getOne";
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	@Action(value = "toMap", results = { @Result(name = "getOne", location = "/map.jsp") })
	public String toMap() {
		try {
			Line temp = (Line) service.get(Line.class, uid);
			putRequestValue("modifybean", temp);
			
			List<LineStation> lslist = service.queryByHQL("from LineStation where line.id=? order by id", temp.getId());
			putRequestValue("list11", lslist);
			String ss  = "";
			for(LineStation ls: lslist){
				ss+="\""+ls.getDept().getAddress()+"\",";
			}
			if(StringUtils.isNotBlank(ss)){
				ss = ss.substring(0,ss.length()-1);
			}
			putRequestValue("waypoints", ss);
			 //"成都","北京"
			
			return "getOne";
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	@Action(value = "deleteLine")
	public String delete() {
		try {
			service.delete(Line.class, ids);
			MessageUtil.addRelMessage(getHttpServletRequest(), "删除信息成功.", "mainquery");
			return SUCCESS;
		} catch (Exception e) {
			MessageUtil.addMessage(getRequest(), "删除失败");
			return ERROR;
		}
	}
	@Action(value = "deleteLineStation")
	public String deleteLineStation() {
		try {
			service.delete(LineStation.class, ids);
			MessageUtil.addRelMessage(getHttpServletRequest(), "删除信息成功.", "baseAdd");
			return SUCCESS;
		} catch (Exception e) {
			MessageUtil.addMessage(getRequest(), "删除失败");
			return ERROR;
		}
	}

	@Action(value = "updateLine")
	public String update() {
		try {
			service.updateLine(bean, deptItemList);
			MessageUtil.addRelMessage(getHttpServletRequest(), "更新信息成功.", "baseAdd");
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			MessageUtil.addMessage(getRequest(), "更新失败");
			return ERROR;
		}
	}

	@Action(value = "addLine")
	public String add() {
		try {
			bean.setId(new Date().getTime());
			service.addLine(bean, deptItemList);
			MessageUtil.addMessage(getHttpServletRequest(), "添加成功.");
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			MessageUtil.addMessage(getRequest(), "添加失败");
			return ERROR;
		}
	}

	@Action(value = "queryLine", results = { @Result(name = "queryList", location = "/WEB-INF/jsp/sys/listLine.jsp") })
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
							parmvalues.add(FieldUtil.format(Line.class, name, fieldValue));
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
			page = service.find(p, Line.class);

			getHttpSession().setAttribute(Constant.SESSION_PAGE, page);
			return "queryList";
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	private List<LineStation> deptItemList;

	public BizService getService() {
		return service;
	}

	public void setService(BizService service) {
		this.service = service;
	}

	public Line getBean() {
		return bean;
	}

	public void setBean(Line bean) {
		this.bean = bean;
	}

	public List getDeptItemList() {
		return deptItemList;
	}

	public void setDeptItemList(List deptItemList) {
		this.deptItemList = deptItemList;
	}

	public Line getModel() {
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
