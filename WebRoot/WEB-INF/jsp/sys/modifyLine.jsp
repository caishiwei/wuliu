<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/head.jsp"%>
<h2 class="contentTitle">修改 线路 信息</h2>
<div class="pageContent">
	<form method="post" action="${ctx}/sys/updateLine.action" class="pageForm required-validate" enctype="multipart/form-data" onsubmit="return iframeCallback(this,navTabAjaxDone)">
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
						<td colspan="2"><dl class="nowrap">
							<dt>起点：</dt>
							<dd>
							<select name="startDept.id" class="" id="fdfse3333333333333333">
								<c:forEach items="${list }" var="item">
									<option value="${item.id }">[${item.name }] ${item.address }</option>
								</c:forEach>
							</select>
							</dd>
							</dl></td>
					</tr>
					<tr>
						<td colspan="2"><dl class="nowrap" >
							<dt>终点：</dt>
							<dd>
							<select name="endDept.id" class="" id="fdfse33333333333333331">
								<c:forEach items="${list }" var="item">
									<option value="${item.id }">[${item.name }] ${item.address }</option>
								</c:forEach>
							</select>
							</dd>
							</dl></td>
					</tr>

				</table>
<script type="text/javascript">
$("#fdfse3333333333333333").val("${modifybean.startDept.id}");
$("#fdfse33333333333333331").val("${modifybean.endDept.id}");
</script>
		<div class="tabs">
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<li class="selected"><a href="javascript:void(0)"><span>中转站列表</span></a></li>
					</ul>
				</div>
			</div>
			<div class="tabsContent" style="height: 250px;">
				<div>
					<table class="list nowrap itemDetail" addButton="添加中转站" width="100%">
						<thead>
							<tr>
								<th type="enum" name="deptItemList[#index#].dept.id" enumUrl="${ctx}/sys/selectDept.action" size="12">配送站点</th>
								<th type="text" name="deptItemList[#index#].stationIndex" defaultVal="#index#" size="12" fieldClass="digits">货运次序</th>
								<th type="del" width="60">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list11 }" var="item">
							<tr>
								<td>[${item.dept.name }] ${item.dept.address } </td>
								<td>${item.stationIndex }</td>
								<td><a title="确实要删除这条记录吗?" target="ajaxTodo" href="${ctx}/sys/deleteLineStation.action?ids=${item.id}" >删除</a></td>
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
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="reset" class="reset">重置</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
