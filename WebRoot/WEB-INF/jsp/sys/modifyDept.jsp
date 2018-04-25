<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/head.jsp"%>
	<style type="text/css">
		#mapframe{
		position:relative;
		width:1100px;
		height:800px;
		float:right;
		margin-right:10px;
		frameborder:0;
		scrolling:no;
		}
 		table{
 		position:relative;
 		float:left;
 		}
		
	</style>
<div class="pageContent">
	<form method="post" action="${ctx}/sys/updateDept.action" class="pageForm required-validate" enctype="multipart/form-data" onsubmit="return iframeCallback(this)">
		<input type="hidden" value="${modifybean.id}" name="id"/>
		<div class="pageFormContent" layoutH="97">
			<table>
					<tr>
						<td colspan="2"><dl>
							<dt>名称：</dt>
							<dd><input name="name" value="${modifybean.name}" type="text"  class="required"/></dd>
							</dl></td>
					</tr>
					 <tr>
						<td colspan="2"><dl>
							<dt>所在地址：</dt>
							<dd><input name="address" value="${modifybean.address}" type="text"  class="required"/></dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>规模：</dt>
							<dd><input name="scale" value="${modifybean.scale}" type="text"  class="required"/></dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>经度：</dt>
							<dd><input name="lng" value="${modifybean.lng}" type="text"  class="required"/></dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>纬度：</dt>
							<dd><input name="lat" value="${modifybean.lat}" type="text"  class="required"/></dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl>
							<dt>备注：</dt>
							<dd>
								<textarea rows="5" cols="60" name="note">${modifybean.note }</textarea>
							</dd>
							</dl></td>
					</tr>
					 
				</table>
				<iframe src="${ctx}/point.html" id="mapframe" frameborder="0"   scrolling="no"></iframe>
		</div>
		
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="reset" class="reset">重置</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
