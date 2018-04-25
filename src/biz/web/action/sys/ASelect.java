//package biz.web.action.sys;
//
//import java.util.LinkedList;
//import java.util.Map.Entry;
//import java.util.Set;
//
//import org.apache.struts2.convention.annotation.Action;
//import org.apache.struts2.convention.annotation.Namespace;
//import org.apache.struts2.convention.annotation.ParentPackage;
//import org.apache.struts2.convention.annotation.Result;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import util.Constant;
//import util.FieldUtil;
//import util.Page;
//import util.SearchParamBean;
//import util.StringUtil;
//import biz.entity.main.School;
//import biz.web.service.ISysService;
//
//import common.action.struts.BaseAction;
// 
//@ParentPackage("struts-default")
//@Namespace("/sel")
//@Component
//public class ASelect extends BaseAction {
//	@Autowired
//	private ISysService service;
//
//	 
//
//	@Action(value = "selectSchool", results = { @Result(name = "queryList", location = "/WEB-INF/jsp/select/selectSchool.jsp") })
//	public String query() {
//		try {
//			int pageNum = 0;
//			String t = getHttpServletRequest().getParameter("pageNum");
//			if (StringUtil.notEmpty(t)) {
//				pageNum = Integer.valueOf(t);
//			}
//			Page p = (Page) getHttpSession().getAttribute(Constant.SESSION_PAGE_UNIT);
//			if (pageNum == 0 || p == null) {
//				p = new Page();
//				p.setCurrentPageNumber(1);
//				p.setTotalNumber(0l);
//				p.setItemsPerPage(Constant.SESSION_PAGE_NUMBER);
//
//				// 字段名称集合
//				LinkedList<String> parmnames = new LinkedList<String>();
//				// 字段值集合
//				LinkedList<Object> parmvalues = new LinkedList<Object>();
//				// 页面参数集合
//				Set parm = getHttpServletRequest().getParameterMap().entrySet();
//				for (Object o : parm) {
//					Entry<String, Object> e = (Entry<String, Object>) o;
//					String name = e.getKey();// 页面字段名称
//					if (name.startsWith("s_")) {
//						String fieldValue = getHttpServletRequest().getParameter(name);// 页面字段值
//						if (StringUtil.notEmpty(fieldValue)) {
//							name = name.substring(2, name.length());// 实体字段名称
//							parmnames.add(name);
//							parmvalues.add(FieldUtil.format(School.class, name, fieldValue));
//						}
//					}
//				}
//
//				SearchParamBean sbean = new SearchParamBean();
//				sbean.setParmnames(parmnames);
//				sbean.setParmvalues(parmvalues);
//
//				p.setConditonObject(sbean);
//			} else {
//				p.setCurrentPageNumber(pageNum);
//			}
//			Page page = null;
//			page = service.find(p, School.class);
//
//			getHttpSession().setAttribute(Constant.SESSION_PAGE_UNIT, page);
//			return "queryList";
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ERROR;
//		}
//	}
//
// 
// 
//
//}
