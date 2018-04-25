<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/head.jsp"%>
<h2 class="contentTitle">修改 车辆 信息</h2>
<div class="pageContent">
	<form method="post" action="${ctx}/sys/updateTruck.action" class="pageForm required-validate" enctype="multipart/form-data" onsubmit="return iframeCallback(this)">
		<input type="hidden" value="${modifybean.id}" name="id"/>
		<div class="pageFormContent" layoutH="97">
			<table>
					<tr>
						<td colspan="2"><dl>
							<dt>车牌号：</dt>
							<dd><input name="licensePlate" value="${modifybean.licensePlate}" type="text"  class="required"/></dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>车型：</dt>
							<dd><input name="vtype" value="${modifybean.vtype}" type="text"  class=""/></dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>车长：</dt>
							<dd><input name="vlength" value="${modifybean.vlength}" type="text"  class=""/></dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>载重：</dt>
							<dd><input name="vload" value="${modifybean.vload}" type="text"  class=""/></dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>内径：</dt>
							<dd><input name="vinner" value="${modifybean.vinner}" type="text"  class=""/></dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>状态：</dt>
							<dd>
							<select name="useStatus">
								<option value="空闲">空闲</option>
								<option value="运输中">运输中</option>
								<option value="报废">报废</option>
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
<script type="text/javascript">
$("#updatesi43434343434343").val("${modifybean.useStatus}");
</script>