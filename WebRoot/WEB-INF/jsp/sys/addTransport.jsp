<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/head.jsp"%>
<h2 class="contentTitle">添加 物流运输 信息</h2>
<div class="pageContent">
	<form method="post" action="${ctx}/sys/addTransport.action" class="pageForm required-validate" enctype="multipart/form-data" onsubmit="return iframeCallback(this)">
		<div class="pageFormContent" layoutH="97">
			<table>
					<tr>
						<td colspan="2"><dl>
							<dt>车辆：</dt>
							<dd>
							<select name="transbean.truck.id" class=" ">
								<c:forEach items="${list1 }" var="item">
									<option value="${item.id }">${item.licensePlate }</option>
								</c:forEach>
							</select>
							</dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>司机：</dt>
							<dd>
							<select name="transbean.driver.id" class=" ">
								<c:forEach items="${list2 }" var="item">
									<option value="${item.id }">${item.user.userName }</option>
								</c:forEach>
							</select>
							</dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl class="nowrap">
							<dt>线路：</dt>
							<dd>
							<select name="transbean.line.id" class=" ">
								<c:forEach items="${list3 }" var="item">
									<option value="${item.id }">${item.desc }</option>
								</c:forEach>
							</select><span class="info">(选择从本配送点出发的线路)</span>
							</dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl class="nowrap">
							<dt>用户订单：</dt>
							<dd>
							<input name="org3.idd" value="" type="hidden" class="required">
							<input name="org3.orgName" type="text" size="100" readonly="readonly"/>
							<a class="btnLook" href="${ctx }/sys/selectUserOrder.action" lookupGroup="org3">选择订单</a>
							<span class="info">(选择多个用户订单)</span>
							</dd>
							</dl></td>
					</tr>
					 
					 
				</table>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="reset" class="reset">重置</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
