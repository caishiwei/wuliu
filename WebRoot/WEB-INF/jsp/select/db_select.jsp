<%@ page pageEncoding="UTF-8"%>
<%@ include file="../common/head.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<select class="" name="${param.inputName}">
<c:forEach items="${list}" var="item">
	<option value="${item.id }">[${item.name }] ${item.address}</option>
</c:forEach>
</select>