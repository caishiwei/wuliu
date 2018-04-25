<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/head.jsp"%>
<form id="pagerForm" method="post" action="${ctx}/sys/queryMyRecvOrder.action">
	<input type="hidden" name="pageNum" value="" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${ctx}/sys/queryMyRecvOrder.action" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>单号：</label>
				<input type="text" name="s_id" value=""/>
			</li>
			 
		</ul>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="edit" href="${ctx}/sys/getMyOrderRecv.action?uid={sid_select}" target="navTab" rel="baseAdd" warn="请选择一条信息,点击需要修改的行" title="物流信息"><span>查看物流信息</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="100">单号</th>
				<th width="100">寄件人姓名</th>
				<th width="100">寄件人电话</th>
				<th width="100">寄件人地址</th>
				<th width="100">收件人姓名</th>
				<th width="100">收件人电话</th>
				<th width="100">收件人地址</th>
				<th width="100">物件名称</th>
				<th width="100">物件重量</th>
				<th width="100">日期</th>
				<th width="100">状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${SESSION_PAGE.list}" var="item">
			<tr target="sid_select" rel="${item.id }">
				<td>${item.id}</td>
				<td>${item.fromUserName}</td>
				<td>${item.fromUserPhone}</td>
				<td>${item.fromUserAddress}</td>
				<td>${item.userName}</td>
				<td>${item.userPhone}</td>
				<td>${item.address}</td>
				<td>${item.goodsName}</td>
				<td>${item.goodsWeight}</td>
				<td>${item.addDate}</td>
				<td>${item.orderStatus}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>共${SESSION_PAGE.totalNumber}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${SESSION_PAGE.totalNumber}" numPerPage="${SESSION_PAGE.itemsPerPage}" pageNumShown="10" currentPage="${SESSION_PAGE.currentPageNumber}"></div>
	</div>
</div>
<%@ include file="../common/clear.jsp"%>
