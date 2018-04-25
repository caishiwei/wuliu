<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/head.jsp"%>
<h2 class="contentTitle">寄件</h2>
<div class="pageContent">
	<form method="post" action="${ctx}/sys/addMyOrder.action" class="pageForm required-validate" enctype="multipart/form-data" onsubmit="return iframeCallback(this)">
		<div class="pageFormContent" layoutH="97">
			<table>
					<tr>
						<td colspan="2"><dl>
							<dt>货物名称：</dt>
							<dd><input name="goodsName" size="40" type="text"  class="required"/></dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>货物重量：</dt>
							<dd><input name="goodsWeight"  type="text"  class="required number"/></dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>收件人姓名：</dt>
							<dd><input name="userName"  type="text"  class="required"/></dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>收件人电话：</dt>
							<dd><input name="userPhone"  type="text"  class="required phone"/></dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>收件人地址：</dt>
							<dd><input name="address" size="40" type="text"  class="required"/></dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>寄件人姓名：</dt>
							<dd><input name="fromUserName"  type="text" value="${SESSION_BEAN.user.user.userName }" class="required"/></dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>寄件人电话：</dt>
							<dd><input name="fromUserPhone"  type="text" value="${SESSION_BEAN.user.user.userPhone }" class="required phone"/></dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>寄件人地址：</dt>
							<dd><input name="fromUserAddress" size="40" type="text" value="${SESSION_BEAN.user.user.userAddress}" class="required"/></dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>配送站：</dt>
							<dd>
							<select name="startDept.id" class="">
									<c:forEach items="${list }" var="item">
										<option value="${item.id }">[${item.name }] ${item.address }</option>
									</c:forEach>
								</select>
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
