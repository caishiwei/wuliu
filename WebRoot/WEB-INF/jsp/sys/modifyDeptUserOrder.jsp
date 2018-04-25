<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/head.jsp"%>
<h2 class="contentTitle">配送点订单物流信息 (新订单需要指定配送终点站, 待配送订单可进入物流配送)</h2>
<div class="pageContent">
	<form method="post" action="${ctx}/sys/updateEndDeptUserOrder.action" class="pageForm -validate" enctype="multipart/form-data" onsubmit="return iframeCallback(this,navTabAjaxDone)">
		<input type="hidden" value="${modifybean.id}" name="id"/>
		<div class="pageFormContent" layoutH="97">
			<table>
					<tr>
						<td colspan="2"><dl>
							<dt>单号：</dt>
							<dd>${modifybean.id }</dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>货物名称：</dt>
							<dd>${modifybean.goodsName }</dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>货物重量：</dt>
							<dd>${modifybean.goodsWeight }</dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>订单费用：</dt>
							<dd>${modifybean.money }</dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>收件人姓名：</dt>
							<dd>${modifybean.userName }</dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>收件人电话：</dt>
							<dd>${modifybean.userPhone }</dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl class="nowrap">
							<dt>收件人地址：</dt>
							<dd>${modifybean.address }</dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>寄件人姓名：</dt>
							<dd>${modifybean.fromUserName }</dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>寄件人电话：</dt>
							<dd>${modifybean.fromUserPhone }</dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl class="nowrap">
							<dt>寄件人地址：</dt>
							<dd>${modifybean.fromUserAddress }</dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl class="nowrap">
							<dt>配送站：</dt>
							<dd>${modifybean.startDept.name }-${modifybean.startDept.address }
							</dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl class="nowrap">
							<dt>终点站：</dt>
							<dd>${modifybean.endDept.name }-${modifybean.endDept.address }
							</dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl class="nowrap">
							<dt>订单状态：</dt>
							<dd>${modifybean.orderStatus }
							</dd>
							</dl></td>
					</tr>
					<c:if test="${modifybean.orderStatus =='新订单'}">
					<tr>
						<td colspan="2"><dl>
							<dt>终点站：</dt>
							<dd>
							<select name="endDept.id" class="">
									<c:forEach items="${list }" var="item">
										<option value="${item.id }">[${item.name }] ${item.address }</option>
									</c:forEach>
								</select>
							</dd>
							</dl></td>
					</tr>
					</c:if>
					 <tr>
						<td colspan="2"><dl class="nowrap">
							<dt>物流信息：</dt>
							<dd>
							
							<c:forEach items="${list1 }" var="item">
								${item.addDate }  [${item.dept.name}] ${item.note }<br/>
							</c:forEach>
							
							</dd>
							</dl></td>
					</tr>
					 
				</table>
		</div>
		<div class="formBar">
		<c:if test="${modifybean.orderStatus =='新订单'}">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="reset" class="reset">重置</button></div></div></li>
			</ul>
			</c:if>
		</div>
	</form>
</div>
