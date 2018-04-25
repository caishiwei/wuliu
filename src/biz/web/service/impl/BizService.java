package biz.web.service.impl;

import java.awt.FlowLayout;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.transaction.JOnASTransactionManagerLookup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import util.DateUtil;
import util.MD5;
import util.Page;
import util.StringUtil;
import biz.entity.Dept;
import biz.entity.FlowLog;
import biz.entity.Line;
import biz.entity.LineStation;
import biz.entity.Transport;
import biz.entity.TransportOrder;
import biz.entity.Truck;
import biz.entity.UserOrder;
import biz.entity.main.SimpleUser;
import biz.entity.main.SysUser;
import biz.entity.main.User;
import biz.web.dao.ISysDao;
import common.service.BaseService;

@Service("bizService")
@Repository
public class BizService extends BaseService {

	@Autowired
	private ISysDao dao;

	public List queryByHQL(String hql, Object... values) {
		return dao.queryByHQL(hql, values);
	}

	public void addSimpleUser(SimpleUser obj) {
		User user = obj.getUser();
		user.setUserId(new Date().getTime());
		MD5 md = new MD5();
		user.setUserPassword(md.getMD5ofStr(user.getUserPassword()));
		dao.save(user);
		dao.save(obj);
	}

	public void updateSimpleUser(SimpleUser obj) {
		SimpleUser temp = (SimpleUser) dao.get(SimpleUser.class, obj.getId());
		User user = temp.getUser();
		user.setUserAddress(obj.getUser().getUserAddress());
		user.setUserBirth(obj.getUser().getUserBirth());
		user.setUserEmail(obj.getUser().getUserEmail());
		user.setUserGender(obj.getUser().getUserGender());
		user.setUserName(obj.getUser().getUserName());
		user.setUserPhone(obj.getUser().getUserPhone());
		if (StringUtils.isNotBlank(obj.getUser().getUserPassword())) {
			MD5 md = new MD5();
			user.setUserPassword(md.getMD5ofStr(obj.getUser().getUserPassword()));
		}
		dao.merge(user);
		obj.setUser(user);
		dao.merge(obj);

	}

	/**
	 * 添加对象
	 * 
	 * @param obj
	 */
	public void add(Object obj) {
		dao.save(obj);
	}

	/**
	 * 修改对象
	 * 
	 * @param obj
	 */
	public void update(Object obj) {
		dao.merge(obj);
	}

	/**
	 * 根据id获取对象
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Object get(Class clazz, Serializable id) {
		return dao.get(clazz, id);
	}

	public void deleteUser(String ids) {
		dao.deleteByIds(User.class, ids);
	}

	public void delete(Class clazz, String ids) {
		dao.deleteByIds(clazz, ids);
	}

	public Object findUser(String type, String userid, String pwd) {
		return dao.queryUser(type, userid, pwd);
	}

	public User findUser(String userid) {
		return dao.queryUser(userid);
	}

	public Page findUser(Page page) {
		return dao.list(page, "User");
	}

	public Page find(Page page, Class clazz) {
		return dao.list(page, clazz.getSimpleName());
	}

	public List findAll(Class clazz) {
		return dao.queryByHQL("from " + clazz.getSimpleName());
	}

	public void addSysUser(SysUser obj) {
		User user = obj.getUser();
		user.setUserId(new Date().getTime());
		MD5 md = new MD5();
		user.setUserPassword(md.getMD5ofStr(user.getUserPassword()));
		dao.save(user);
		dao.save(obj);
	}

	public void updateSysUser(SysUser obj) {
		SysUser temp = (SysUser) dao.get(SysUser.class, obj.getId());
		User user = temp.getUser();
		user.setUserAddress(obj.getUser().getUserAddress());
		user.setUserBirth(obj.getUser().getUserBirth());
		user.setUserEmail(obj.getUser().getUserEmail());
		user.setUserGender(obj.getUser().getUserGender());
		user.setUserName(obj.getUser().getUserName());
		user.setUserPhone(obj.getUser().getUserPhone());
		if (StringUtils.isNotBlank(obj.getUser().getUserPassword())) {
			MD5 md = new MD5();
			user.setUserPassword(md.getMD5ofStr(obj.getUser().getUserPassword()));
		}
		dao.merge(user);
		obj.setUser(user);
		dao.merge(obj);

	}

	public List findAll(Class clazz, Map<String, Object> params) {
		return dao.findAll(clazz, params);
	}

	public void addLine(Line bean, List<LineStation> deptItemList) {
		dao.save(bean);
		int index = 1;
		if (deptItemList != null && deptItemList.size() > 0) {
			for (LineStation ls : deptItemList) {
				if (ls != null && ls.getDept() != null) {
					ls.setId(bean.getId() + index++);
					ls.setLine(bean);
					dao.save(ls);
				}
			}
		}
	}

	public void updateLine(Line bean, List<LineStation> deptItemList) {
		dao.update(bean);
		long index = new Date().getTime();
		if (deptItemList != null && deptItemList.size() > 0) {
			for (LineStation ls : deptItemList) {
				if (ls != null && ls.getDept() != null) {
					ls.setId(index++);
					ls.setLine(bean);
					dao.save(ls);
				}
			}
		}

	}

	public void addTransport(Transport transbean, String o) {
		transbean.setTransIndex(1);
		transbean.setTransStatus("运输中");
		transbean.setAddDate(DateUtil.getCurrentTime(DateUtil.FULL));
		dao.save(transbean);

		String[] tempids = o.split(",");
		long logid = new Date().getTime();
		for (String tempid : tempids) {
			UserOrder order = (UserOrder) dao.get(UserOrder.class, Long.valueOf(tempid));
			if (order.getOrderStatus().equals("待配送")) {
				order.setOrderStatus("运输中");
				dao.update(order);

				FlowLog log = new FlowLog();
				log.setAddDate(DateUtil.getCurrentTime(DateUtil.FULL));
				log.setDept(order.getStartDept());
				log.setNote("已装车");
				log.setId(logid++);
				log.setUserOrder(order);
				dao.save(log);

				TransportOrder to = new TransportOrder();
				to.setId(logid);
				to.setTransport(transbean);
				to.setUserOrder(order);
				dao.save(to);
			}
		}

		Truck truck = (Truck) dao.get(Truck.class, transbean.getTruck().getId());
		truck.setUseStatus("运输中");
		dao.update(truck);
	}

	@SuppressWarnings("unchecked")
	public List<Transport> findJoinTransport(Dept dept) {
		List<Transport> ret = new ArrayList<Transport>();

		List<Transport> tlist = dao.queryByHQL("from Transport where transStatus='运输中'");
		for (Transport t : tlist) {
			List<LineStation> stalist = dao.queryByHQL("from LineStation where line.id=? order by id", t.getLine().getId());
			if (t.getTransIndex() <= stalist.size()) {
				if (stalist.get(t.getTransIndex() - 1).getDept().getId().equals(dept.getId())) {
					ret.add(t);
				}
			} else {
				if (t.getLine().getEndDept().getId().equals(dept.getId())) {
					ret.add(t);
				}
			}
		}

		return ret;

	}

	@SuppressWarnings("unchecked")
	public void updateJoinTransport(Dept dept, long uid) {
		Transport t = (Transport) dao.get(Transport.class, uid);
		List<LineStation> stalist = dao.queryByHQL("from LineStation where line.id=? order by id", t.getLine().getId());
		boolean joined = false;
		boolean finished = false;
		if (t.getTransIndex() <= stalist.size()) {
			if (stalist.get(t.getTransIndex() - 1).getDept().getId().equals(dept.getId())) {
				t.setTransIndex(t.getTransIndex() + 1);
				dao.update(t);
				joined = true;
			}
		} else {
			if (t.getLine().getEndDept().getId().equals(dept.getId())) {
				t.setTransStatus("完成");
				dao.update(t);
				t.getTruck().setUseStatus("空闲");
				dao.update(t.getTruck()); 
				joined = true;
				finished = true;
			}
		}
		if (joined) {
			long logid = new Date().getTime();
			List<TransportOrder> tolist = dao.queryByHQL("from TransportOrder where transport.id=?", uid);
			for (TransportOrder to : tolist) {
				FlowLog log = new FlowLog();
				log.setAddDate(DateUtil.getCurrentTime(DateUtil.FULL));
				log.setDept(dept);
				log.setNote("运输抵达配送点 " + dept.getName());
				log.setId(logid++);
				log.setUserOrder(to.getUserOrder());
				dao.save(log);
				//抵达一个配送点,订单到达目的地
				if (to.getUserOrder().getEndDept().getId().equals(dept.getId())) {
					to.getUserOrder().setOrderStatus("已运达");
					dao.update(to.getUserOrder());
				} else if (finished) {//订单没到目的地,但是运输已经完成
					to.getUserOrder().setStartDept(dept);
					to.getUserOrder().setOrderStatus("待配送");
					dao.update(to.getUserOrder());
				}
			}
		}

	}

}
