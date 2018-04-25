<%@ page pageEncoding="UTF-8"%>
<%@ include file="../common/head.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form id="pagerForm" action="demo/database/dwzOrgLookup2.html">
	<input type="hidden" name="pageNum" value="1" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" method="post" action="demo/database/dwzOrgLookup2.html" onsubmit="return dwzSearch(this, 'dialog');">
	<div class="searchBar">
		<ul class="searchContent">
			 
		</ul>
		<div class="subBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" multLookup="orgId" warn="请选择订单">确认</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">

	<table class="table" layoutH="118" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th width="30"><input type="checkbox" class="checkboxCtrl" group="orgId" /></th>
				<th orderfield="orgName">货物名称</th>
				<th orderfield="orgNum">重量</th>
				<th orderfield="orgNum">配送地址</th>
				<th orderfield="leader">终点配送点</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list }" var="item">
			<tr>
				<td><input type="checkbox" name="orgId" value="{idd:'${item.id}', orgName:'${item.goodsName }'}"/></td>
				<td>${item.goodsName}</td>
				<td>${item.goodsWeight}</td>
				<td>${item.address}</td>
				<td>${item.endDept.name}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
	</div>
</div>