<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/head.jsp"%>
<h2 class="contentTitle">物流 交接 信息</h2>
<div class="pageContent">
	<form method="post" action="${ctx}/sys/updateJoinTransport.action" class="pageForm required-validate" enctype="multipart/form-data" onsubmit="return iframeCallback(this,navTabAjaxDone)">
		<input type="hidden" value="${modifybean.id}" name="uid"/>
		<div class="pageFormContent" layoutH="97">
			<table>
					<tr>
						<td colspan="2"><dl>
							<dt>车辆：</dt>
							<dd>${modifybean.truck.licensePlate}</dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>司机：</dt>
							<dd>${modifybean.driver.user.userName}</dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>线路：</dt>
							<dd>${modifybean.line.desc}</dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>起运时间：</dt>
							<dd>${modifybean.addDate}</dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>状态：</dt>
							<dd>${modifybean.transStatus}</dd>
							</dl></td>
					</tr>
					 

				</table>
 
		<div class="tabs">
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<li class="selected"><a href="javascript:void(0)"><span>物流订单</span></a></li>
					</ul>
				</div>
			</div>
			<div class="tabsContent" style="height: 250px;">
				<div>
					<table class="list nowrap "   width="100%">
						<thead>
							<tr>
								<th >货物名称</th>
								<th >货物重量</th>
								<th >收货地址</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list11 }" var="item">
							<tr>
								<td>${item.goodsName} </td>
								<td>${item.goodsWeight }</td>
								<td>${item.address }</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<div class="tabsFooter">
				<div class="tabsFooterContent"></div>
			</div>
		</div>
		</div>
		<div class="formBar">
			 <ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">运输已抵达,卸货并出发</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
