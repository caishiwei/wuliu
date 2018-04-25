<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/head.jsp"%>
<form id="pagerForm" method="post" action="${ctx}/sys/queryDeptJoinTransport.jsp.action">
	<input type="hidden" name="pageNum" value="" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${ctx}/sys/queryDeptJoinTransport.jsp.action" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
			</li>
		</ul>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">刷新</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="edit" href="${ctx}/sys/getDeptJoinTransport.action?uid={sid_select}" target="navTab" rel="baseAdd" warn="请选择一条信息,点击需要修改的行" title="物流信息"><span>查看物流信息</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="100">物流编号</th>
				<th width="100">车牌号</th>
				<th width="100">司机</th>
				<th width="100">运输线路名称</th>
				<th width="100">线路出发配送点</th>
				<th width="100">线路终点配送点</th>
				<th width="100">日期</th>
				<th width="100">状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="item">
			<tr target="sid_select" rel="${item.id }">
				<td>${item.id}</td>
				<td>${item.truck.licensePlate}</td>
				<td>${item.driver.user.userName}</td>
				<td>${item.line.name}</td>
				<td>${item.line.startDept.name}</td>
				<td>${item.line.endDept.name}</td>
				<td>${item.addDate}</td>
				<td>${item.transStatus}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
	</div>
</div>
<%@ include file="../common/clear.jsp"%>
